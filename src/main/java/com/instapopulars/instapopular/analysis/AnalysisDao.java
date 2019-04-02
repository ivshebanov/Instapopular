package com.instapopulars.instapopular.analysis;

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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
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

        instagramDao.initDriver();
        instagramDao.loginOnWebSite("lilka.lily.1", "Sxsblpwiwn");
        HashMap<String, Integer> resultUser = new HashMap<>();
        HashMap<String, Integer> resultPhotos = new HashMap<>(photos);
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
//        propertiesDao.addMyPhotos(resultPhotos);
        return resultUser;
    }

    private Set<String> getActive(String urlPhoto) {
        if (urlPhoto == null || urlPhoto.length() == 0) {
            return null;
        }
        Set<String> resultActiveUser = new HashSet<>();
        String windowHandlePage = instagramDao.openUrlNewTab(format(HOME_PAGE, urlPhoto));
        try {
            instagramDao.getWebElement(60, OPEN_LIKE).click();
            for (int i = 0; i < 11; i++) {
                instagramDao.timeOut(1, 0);
                List<WebElement> elements = instagramDao.getDriver().findElements(By.xpath("/html/body/div[4]/div/div[2]/div/div/div"));
                if (elements == null || elements.size() == 0) {
                    continue;
                }
                Set<String> set = getActiveUser(elements);
                resultActiveUser.addAll(set);
                System.out.println(elements.size());//
                ((JavascriptExecutor) instagramDao.getDriver()).executeScript("arguments[0].scrollIntoView(false);", elements.get(elements.size() - 1));
            }
        } catch (NoSuchElementException e) {
            return resultActiveUser;
        } finally {
            instagramDao.closeTab(windowHandlePage);
        }
        return resultActiveUser;
    }

    private Set<String> getActiveUser(List<WebElement> elements) {
        if (elements == null || elements.size() == 0) {
            return new HashSet<>();
        }
        Set<String> resultUser = new HashSet<>();
        for (WebElement element : elements) {
            resultUser.add(element.getText().split("\n")[0]);
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
