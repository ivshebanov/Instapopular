package com.instapopulars.instapopular.groups;

import static com.instapopulars.instapopular.Constant.Attribute.HREF;
import static com.instapopulars.instapopular.Constant.Attribute.REQUEST_SENT;
import static com.instapopulars.instapopular.Constant.Attribute.SUBSCRIPTIONS;
import static com.instapopulars.instapopular.Constant.GroupsConstant.MessageConstants.SUBSCRIBE_TO_GROUP;
import static com.instapopulars.instapopular.Constant.GroupsConstant.MessageConstants.SUBSCRIBE_TO_GROUP_MEMBERS;
import static com.instapopulars.instapopular.Constant.GroupsConstant.Xpath.CHECK_PHOTO;
import static com.instapopulars.instapopular.Constant.GroupsConstant.Xpath.IS_SUBSCRIBED;
import static com.instapopulars.instapopular.Constant.GroupsConstant.Xpath.URL_PHOTO;
import static com.instapopulars.instapopular.Constant.LinkToInstagram.HOME_PAGE;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.OPEN_SUBSCRIBERS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.SCROLL;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.USER_LINK_TO_SUBSCRIBERS;
import com.instapopulars.instapopular.DAO.InstagramDao;
import java.io.IOException;
import static java.lang.String.format;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroupsDao {

    private static final Logger logger = LogManager.getLogger(GroupsDao.class);
    private final InstagramDao instagramDao;

    @Autowired
    public GroupsDao(InstagramDao instagramDao) {
        this.instagramDao = instagramDao;
    }

    public void subscribeToUsersInGroup(String channelName, int countSubscriptions) {
        logger.info(format(SUBSCRIBE_TO_GROUP_MEMBERS, channelName, countSubscriptions));
        if (channelName == null || countSubscriptions == 0) {
            return;
        }
        String baseWindowHandle = null;
        if (!format(HOME_PAGE, channelName).equalsIgnoreCase(instagramDao.getCurrentUrl())) {
            baseWindowHandle = instagramDao.openUrl(format(HOME_PAGE, channelName));
        }
        instagramDao.getWebElement(60, OPEN_SUBSCRIBERS).click();
        instagramDao.scrollSubscriptions(20);
        for (int i = 1; i < countSubscriptions; i++) {
            try {
                instagramDao.scrollElementSubscriptions(format(SCROLL, i));
                if (isSubscribed(format(IS_SUBSCRIBED, i))) {
                    countSubscriptions++;
                    continue;
                }
                instagramDao.getWebElement(60, format(IS_SUBSCRIBED, i)).click();
                Thread.sleep(2000);
                if (requestSent(format(IS_SUBSCRIBED, i))) {
                    continue;
                }

                String urlUser = instagramDao.getWebElement(60, format(USER_LINK_TO_SUBSCRIBERS, i)).getAttribute(HREF);
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
                            String urlPhoto = instagramDao.getWebElement(10, format(URL_PHOTO, j)).getAttribute(HREF);
                            instagramDao.setLike(urlPhoto);
                        } catch (Exception e) {
                            instagramDao.selectTab(userWindowHandle);
                            break;
                        }
                    }
                }
                logger.info(format(SUBSCRIBE_TO_GROUP, i));
                instagramDao.closeTab(userWindowHandle);
                instagramDao.selectTab(baseWindowHandle);
                instagramDao.timeOut(150, 50);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                instagramDao.selectTab(baseWindowHandle);
            }
        }
    }

    private boolean isSubscribed(String xpath) {
        String isSubscribed = instagramDao.getWebElement(60, xpath).getText();
        return SUBSCRIPTIONS.equalsIgnoreCase(isSubscribed) || REQUEST_SENT.equalsIgnoreCase(isSubscribed);
    }

    private boolean requestSent(String xpath) {
        String isSubscribed = instagramDao.getWebElement(60, xpath).getText();
        return REQUEST_SENT.equalsIgnoreCase(isSubscribed);
    }

    private int checkPhoto() {
        List<WebElement> webElement = instagramDao.getWebElements(10, CHECK_PHOTO);
        return (webElement == null) ? 0 : webElement.size();
    }

    public void initDriver() {
        instagramDao.initDriver();
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
