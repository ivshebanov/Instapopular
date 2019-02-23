package com.instapopulars.instapopular.groups;

import static com.instapopulars.instapopular.Constant.GroupsConstant.MessageConstants.*;
import static com.instapopulars.instapopular.Constant.GroupsConstant.Xpath.CHECK_PHOTO;
import static com.instapopulars.instapopular.Constant.GroupsConstant.Xpath.IS_SUBSCRIBED;
import static com.instapopulars.instapopular.Constant.GroupsConstant.Xpath.URL_PHOTO;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Link.HOME_PAGE;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.COUNT_SUBSCRIBERS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.HREF;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.OPEN_SUBSCRIBERS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.SCROLL;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.USER_LINK_TO_SUBSCRIBERS;
import com.instapopulars.instapopular.DAO.InstagramDao;
import java.io.IOException;
import static java.lang.String.format;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroupsDao {

    private static final Logger logger = LoggerFactory.getLogger(GroupsDao.class);
    private final InstagramDao instagramDao;
    private WebDriver driver;

    @Autowired
    public GroupsDao(InstagramDao instagramDao) {
        this.instagramDao = instagramDao;
    }

    public void subscribeToGroupMembers(String channelName) {
        logger.info(format(SUBSCRIBE_TO_GROUP_MEMBERS, channelName));
        String baseWindowHandle = null;
        if (!instagramDao.getCurrentUrl().equalsIgnoreCase(format(HOME_PAGE, channelName))) {
            baseWindowHandle = instagramDao.openUrl(format(HOME_PAGE, channelName));
        }
        String countSubscribersStr = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(COUNT_SUBSCRIBERS))).getText();
        int countSubscribers = instagramDao.convertStringToInt(countSubscribersStr);
        if (countSubscribers > 300) {
            countSubscribers = 300;
        }
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OPEN_SUBSCRIBERS))).click();
        instagramDao.scrollSubscriptions(20);
        for (int i = 1; i < 300; i++) {
            try {
                instagramDao.scrollElementSubscriptions(format(SCROLL, i));
                if (isSubscribed(format(IS_SUBSCRIBED, i))) {
                    countSubscribers++;
                    continue;
                }
                (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(format(IS_SUBSCRIBED, i)))).click();
                Thread.sleep(2000);
                if (requestSent(format(IS_SUBSCRIBED, i))) {
                    continue;
                }

                String urlUser = (new WebDriverWait(driver, 60)).
                        until(ExpectedConditions.presenceOfElementLocated(By.xpath(format(USER_LINK_TO_SUBSCRIBERS, i)))).getAttribute(HREF);
                String userWindowHandle = instagramDao.openUrlNewTab(urlUser);
                int countPhoto;
                try {
                    countPhoto = checkPhoto();
                } catch (Exception e) {
                    instagramDao.closeTab(userWindowHandle);
                    instagramDao.selectTab(baseWindowHandle);
                    continue;
                }

                if (countPhoto != 0) {
                    for (int j = 1; j <= countPhoto; j++) {
                        try {
                            String urlPhoto = (new WebDriverWait(driver, 60)).
                                    until(ExpectedConditions.presenceOfElementLocated(By.xpath(format(URL_PHOTO, j)))).getAttribute(HREF);
                            instagramDao.setLike(urlPhoto);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            instagramDao.selectTab(userWindowHandle);
                        }
                    }
                }
                logger.info(format(SUBSCRIBE_TO_GROUP, i));
                instagramDao.closeTab(userWindowHandle);
                instagramDao.selectTab(baseWindowHandle);
                Random random = new Random();
                int num = 20000 + random.nextInt(100000 - 20000);
                Thread.sleep(num);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                instagramDao.selectTab(baseWindowHandle);
            }
        }
    }

    private boolean isSubscribed(String xpath) {
        String isSubscribed = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))).getText();
        return isSubscribed.equalsIgnoreCase(SUBSCRIPTIONS) || isSubscribed.equalsIgnoreCase(REQUEST_SENT);
    }

    private boolean requestSent(String xpath) {
        String isSubscribed = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))).getText();
        return isSubscribed.equalsIgnoreCase(REQUEST_SENT);
    }

    private int checkPhoto() {
        List<WebElement> webElement = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(CHECK_PHOTO)));
        return (webElement == null) ? 0 : webElement.size();
    }

    public WebDriver initDriver() {
        driver = instagramDao.initDriver();
        return driver;
    }

    public Set<String> getGroupsFromProperties() throws IOException {
        return instagramDao.getGroupsFromProperties();
    }

    public void quitDriver() {
        instagramDao.quitDriver();
    }

    public boolean loginOnWebSite(String login, String password) {
        return instagramDao.loginOnWebSite(login, password);
    }
}
