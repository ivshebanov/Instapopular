package ru.instapopular.analysis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ru.instapopular.model.Guys;
import ru.instapopular.model.Photo;
import ru.instapopular.model.Usr;
import ru.instapopular.repository.GuysRepository;
import ru.instapopular.repository.PhotoRepository;
import ru.instapopular.service.InstagramService;
import ru.instapopular.view.ViewMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;
import static ru.instapopular.Constant.AnalysisConstant.COUNT_USER_LIKE;
import static ru.instapopular.Constant.AnalysisConstant.CUT_OF_URL;
import static ru.instapopular.Constant.AnalysisConstant.LINE_BREAK;
import static ru.instapopular.Constant.AnalysisConstant.OPEN_LIKE;
import static ru.instapopular.Constant.AnalysisConstant.VIEWS;
import static ru.instapopular.Constant.LinkToInstagram.HOME_PAGE_2;
import static ru.instapopular.Utils.getLoginUserBtn;

@Service
public class AnalysisService {

    private static final Logger logger = LogManager.getLogger(AnalysisService.class);

    private final InstagramService instagramService;
    private final PhotoRepository photoRepository;
    private final GuysRepository guysRepository;

    public AnalysisService(InstagramService instagramService, PhotoRepository photoRepository, GuysRepository guysRepository) {
        this.instagramService = instagramService;
        this.photoRepository = photoRepository;
        this.guysRepository = guysRepository;
    }

    public void loginOnWebSite(String login, String password) {
        try {
            instagramService.initDriver();
            instagramService.loginOnWebSite(login, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            instagramService.quitDriver();
        }
    }

    void runAnalysis(Usr usr) {
        try {
            List<String> photos = photoRepository.findPhotosByUsrAndActive(usr, true);
            addNewUser(usr, analysisPhotos(usr, photos));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            instagramService.quitDriver();
        }
    }

    void addMyPhoto(Usr usr, String photoName) {
        try {
            photoName = cutOfUrl(photoName);
            Photo photo = photoRepository.findPhotoByUsrAndPhoto(usr, photoName);
            if (photo != null) {
                photoRepository.activatePhoto(usr, photoName);
                return;
            }
            ApplicationContext context = new AnnotationConfigApplicationContext(Photo.class);
            Photo newPhoto = context.getBean(Photo.class);
            newPhoto.setPhoto(photoName);
            newPhoto.setUsr(usr);
            newPhoto.setActive(true);
            photoRepository.save(newPhoto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeMyPhoto(Usr usr, String photoName) {
        try {
            photoName = cutOfUrl(photoName);
            Photo photo = photoRepository.findPhotoByUsrAndPhoto(usr, photoName);
            if (photo != null) {
                photoRepository.deactivatePhoto(usr, photoName);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getAnalysisGuys(Usr usr) {
        try {
            List<String> guys = guysRepository.findGuysByUsrAndActive(usr, true);
            List<ViewMap> resultView = instagramService.revertToView(guys);
            Collections.sort(resultView);
            return resultView;
        } catch (Exception e) {
            return emptyList();
        }
    }

    List<ViewMap> getMyPhoto(Usr usr) {
        try {
            List<String> photos = photoRepository.findPhotosByUsrAndActive(usr, true);
            List<ViewMap> resultView = instagramService.revertToView(photos);
            Collections.sort(resultView);
            return resultView;
        } catch (Exception e) {
            return emptyList();
        }
    }

    private String cutOfUrl(String url) {
        Pattern pattern = Pattern.compile(CUT_OF_URL);
        Matcher matcher = pattern.matcher(url);
        String result = "";
        while (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }

    void doNotUnsubscribe(Usr usr, int count) {
        try {
            List<Guys> guys = guysRepository.findAllByUsr(usr);
            for (Guys guy : guys) {
                if (guy.getCountLike() >= count) {
                    guysRepository.activateGuys(usr, guy.getGuyName());
                } else {
                    guysRepository.deactivateGuys(usr, guy.getGuyName());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void addNewUser(Usr usr, Map<String, Integer> guys) {
        for (Map.Entry<String, Integer> guy : guys.entrySet()) {
            Guys oldGuy = guysRepository.findLikeByUsrAndGuyName(usr, guy.getKey());
            if (oldGuy != null) {
                guysRepository.updateCountLikeAndActiveByUsrAndGuyName(usr, guy.getKey(), oldGuy.getCountLike() + guy.getValue());
                continue;
            }
            ApplicationContext context = new AnnotationConfigApplicationContext(Guys.class);
            Guys newGuys = context.getBean(Guys.class);
            newGuys.setUsr(usr);
            newGuys.setActive(true);
            newGuys.setGuyName(guy.getKey());
            newGuys.setCountLike(guy.getValue());
            guysRepository.save(newGuys);
        }
    }

    private Map<String, Integer> analysisPhotos(Usr usr, List<String> photos) {
        if (photos == null || photos.size() == 0) {
            return null;
        }
        Map<String, Integer> resultUser = new HashMap<>();
        List<String> deactivatePhoto = new ArrayList<>();
        try {
            for (String photo : photos) {
                deactivatePhoto.add(photo);
                Set<String> activeUsers = getActive(photo);
                if (activeUsers == null) {
                    continue;
                }
                addActiveUsersToMap(resultUser, activeUsers);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            for (String photo : deactivatePhoto) {
                photoRepository.deactivatePhoto(usr, photo);
            }
        }

        return resultUser;
    }

    private Set<String> getActive(String urlPhoto) {
        Set<String> resultActiveUser = new HashSet<>();
        instagramService.openUrl(String.format(HOME_PAGE_2, urlPhoto));
        if (isVideo()) return null;
        instagramService.timeOut(2, 0);
        try {
            instagramService.getWebElement(60, OPEN_LIKE).click();
            instagramService.timeOut(3, 0);
            String countUserLike = instagramService.getWebElement(15, COUNT_USER_LIKE).getText();
            int countUserLikeInt = instagramService.convertStringToInt(countUserLike);
            for (int i = 0; i < countUserLikeInt / 6 + 2; i++) {
                instagramService.timeOut(1, 0);
                List<WebElement> elements = null;
                try {
                    elements = instagramService.getWebElements(30, getLoginUserBtn());
                } catch (NoSuchElementException ignored) {
                }
                if (elements == null || elements.size() == 0) {
                    continue;
                }
                Set<String> nameActiveUser = getActiveUser(elements);
                if (nameActiveUser == null) {
                    i--;
                    continue;
                }
                resultActiveUser.addAll(nameActiveUser);
                instagramService.scrollOpenLikeUser(elements.get(elements.size() - 1));
            }
        } catch (Exception e) {
            return resultActiveUser;
        }
        return resultActiveUser;
    }

    private boolean isVideo() {
        String checkVideo = instagramService.getWebElement(15, OPEN_LIKE).getText();
        return checkVideo.contains(VIEWS);
    }

    private Set<String> getActiveUser(List<WebElement> elements) {
        if (elements == null || elements.size() == 0) {
            return new HashSet<>();
        }
        Set<String> resultUser = new HashSet<>();
        try {
            for (WebElement element : elements) {
                resultUser.add(element.getText().split(LINE_BREAK)[0]);
            }
        } catch (StaleElementReferenceException ex) {
            return null;
        }
        return resultUser;
    }

    private void addActiveUsersToMap(Map<String, Integer> mapUser, Set<String> users) {
        for (String user : users) {
            if (mapUser.containsKey(user)) {
                mapUser.put(user, mapUser.get(user) + 1);
                continue;
            }
            mapUser.put(user, 1);
        }
    }
}
