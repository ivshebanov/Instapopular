package com.instapopulars.instapopular.analysis;

import com.instapopulars.instapopular.repository.InstapopularDAO;
import com.instapopulars.instapopular.service.InstagramService;
import com.instapopulars.instapopular.view.ViewMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.instapopulars.instapopular.Constant.AnalysisConstant.*;
import static com.instapopulars.instapopular.Constant.LinkToInstagram.HOME_PAGE_2;
import static com.instapopulars.instapopular.Utils.getLoginUserBtn;
import static java.lang.String.format;
import static java.util.Collections.emptyList;

@Service
public class AnalysisService {

    private static final Logger logger = LogManager.getLogger(AnalysisService.class);

    private final InstagramService instagramService;

    private final InstapopularDAO instapopularDAO;

    public AnalysisService(InstagramService instagramService, @Qualifier("propertiesDao") InstapopularDAO instapopularDAO) {
        this.instagramService = instagramService;
        this.instapopularDAO = instapopularDAO;
    }

    public void loginOnWebSite(String login, String password) {
        try {
            instagramService.initDriver();
            instagramService.loginOnWebSite(login, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    void runAnalysis() {
        try {
            Map<String, Integer> user = analysisPhotos(instapopularDAO.getMyPhoto());
            instapopularDAO.addPhotoAnalysisResults(addNewUser(instapopularDAO.getPhotoAnalysisResults(), user));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            instagramService.quitDriver();
        }
    }

    void addMyPhoto(String userName) {
        try {
            Map<String, Integer> currentMyPhoto = instapopularDAO.getMyPhoto();
            if (!currentMyPhoto.containsKey(userName)) {
                instapopularDAO.addMyPhoto(userName, String.valueOf(0));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeMyPhoto(String userName) {
        try {
            instapopularDAO.removeMyPhoto(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getAnalysisPhoto() {
        try {
            List<ViewMap> resultView = new ArrayList<>(instagramService.revertMapView(instapopularDAO.getPhotoAnalysisResults()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    List<ViewMap> getMyPhoto() {
        try {
            List<ViewMap> resultView = new ArrayList<>(instagramService.revertMapView(instapopularDAO.getMyPhoto()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    String cutOfUrl(String url) {
        Pattern p = Pattern.compile(CUT_OF_URL);
        Matcher matcher = p.matcher(url);
        String result = "";
        while (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }

    void addFirstDoNotUnsubscribe(int count) {
        try {
            Map<String, Integer> photoAnalysisResults = instapopularDAO.getPhotoAnalysisResults();
            for (Map.Entry<String, Integer> entry : photoAnalysisResults.entrySet()) {
                if (entry.getValue() >= count) {
                    instapopularDAO.addDoNotUnsubscribe(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Map<String, Integer> addNewUser(Map<String, Integer> oldUser, Map<String, Integer> newUser) {
        for (Map.Entry<String, Integer> user : newUser.entrySet()) {
            if (oldUser.containsKey(user.getKey())) {
                oldUser.put(user.getKey(), oldUser.get(user.getKey()) + user.getValue());
                continue;
            }
            oldUser.put(user.getKey(), user.getValue());
        }
        return oldUser;
    }

    private Map<String, Integer> analysisPhotos(Map<String, Integer> photos) throws IOException {
        if (photos == null || photos.size() == 0) {
            return null;
        }
        Map<String, Integer> resultUser = new HashMap<>();
        Map<String, Integer> resultPhotos = new HashMap<>(photos);
        try {
            for (Map.Entry<String, Integer> photo : photos.entrySet()) {
                if (photo.getValue() == 1) {
                    continue;
                }
                if (photo.getValue() == 0) {
                    Set<String> activeUsers = getActive(photo.getKey());
                    if (activeUsers == null || activeUsers.size() == 0) {
                        continue;
                    }
                    addActiveUsersToMap(resultUser, activeUsers);
                    resultPhotos.put(photo.getKey(), 1);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        instapopularDAO.addMyPhotos(resultPhotos);
        return resultUser;
    }

    private Set<String> getActive(String urlPhoto) {
        if (urlPhoto == null || urlPhoto.length() == 0) {
            return null;
        }
        Set<String> resultActiveUser = new HashSet<>();
        instagramService.openUrl(format(HOME_PAGE_2, urlPhoto));
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
                Set<String> set = getActiveUser(elements);
                if (set == null) {
                    i--;
                    continue;
                }
                resultActiveUser.addAll(set);
                instagramService.scrollOpenLikeUser(elements.get(elements.size() - 1));
            }
        } catch (Exception e) {
            return resultActiveUser;
        }
        return resultActiveUser;
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
