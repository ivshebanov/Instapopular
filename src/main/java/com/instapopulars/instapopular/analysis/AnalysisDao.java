package com.instapopulars.instapopular.analysis;

import static com.instapopulars.instapopular.Constant.AnalysisConstant.COUNT_USER_LIKE;
import static com.instapopulars.instapopular.Constant.AnalysisConstant.LINE_BREAK;
import static com.instapopulars.instapopular.Constant.AnalysisConstant.LOGIN_USER;
import static com.instapopulars.instapopular.Constant.AnalysisConstant.OPEN_LIKE;
import static com.instapopulars.instapopular.Constant.LinkToInstagram.HOME_PAGE;
import com.instapopulars.instapopular.DAO.InstagramDao;
import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.groups.GroupsDao;
import java.io.IOException;
import static java.lang.String.format;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnalysisDao {

    private static final Logger logger = LogManager.getLogger(GroupsDao.class);

    @Autowired
    private InstagramDao instagramDao;

    @Autowired
    private PropertiesDao propertiesDao;

    public Map<String, Integer> analysisPhotos(Map<String, Integer> photos) throws IOException {
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
        propertiesDao.addMyPhotos(resultPhotos);
        return resultUser;
    }

    private Set<String> getActive(String urlPhoto) {
        if (urlPhoto == null || urlPhoto.length() == 0) {
            return null;
        }
        Set<String> resultActiveUser = new HashSet<>();
        instagramDao.openUrl(format(HOME_PAGE, urlPhoto));
        instagramDao.timeOut(2, 0);
        try {
            instagramDao.getWebElement(60, OPEN_LIKE).click();
            String countUserLike = instagramDao.getWebElement(15, COUNT_USER_LIKE).getText();
            int countUserLikeInt = instagramDao.convertStringToInt(countUserLike);
            for (int i = 0; i < countUserLikeInt / 6 + 1; i++) {
                instagramDao.timeOut(1, 0);
                List<WebElement> elements = instagramDao.getWebElements(15, LOGIN_USER);
                if (elements == null || elements.size() == 0) {
                    continue;
                }
                Set<String> set = getActiveUser(elements);
                if (set == null) {
                    i--;
                    continue;
                }
                resultActiveUser.addAll(set);
                instagramDao.scrollOpenLikeUser(elements.get(elements.size() - 1));
            }
        } catch (NoSuchElementException e) {
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

    public Map<String, Integer> addNewUser(Map<String, Integer> oldUser, Map<String, Integer> newUser) {
        for (Map.Entry<String, Integer> user : newUser.entrySet()) {
            if (oldUser.containsKey(user.getKey())) {
                oldUser.put(user.getKey(), oldUser.get(user.getKey()) + user.getValue());
                continue;
            }
            oldUser.put(user.getKey(), user.getValue());
        }
        return oldUser;
    }

    public void initDriver() {
        instagramDao.initDriver();
    }

    public void quitDriver() {
        instagramDao.quitDriver();
    }

    public boolean loginOnWebSite(String login, String password) {
        return instagramDao.loginOnWebSite(login, password);
    }
}
