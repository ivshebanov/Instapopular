package com.instapopulars.instapopular.groups;

import com.instapopulars.instapopular.Action;
import com.instapopulars.instapopular.repository.InstapopularDAO;
import com.instapopulars.instapopular.view.ViewMap;
import com.instapopulars.instapopular.service.InstagramService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.instapopulars.instapopular.Constant.Attribute.HREF;
import static com.instapopulars.instapopular.Constant.Attribute.REQUEST_SENT;
import static com.instapopulars.instapopular.Constant.Attribute.SUBSCRIPTIONS;
import static com.instapopulars.instapopular.Constant.GroupsConstant.MessageConstants.*;
import static com.instapopulars.instapopular.Constant.GroupsConstant.Xpath.*;
import static com.instapopulars.instapopular.Constant.LinkToInstagram.HOME_PAGE;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.OPEN_SUBSCRIBERS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.SCROLL;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.USER_LINK_TO_SUBSCRIBERS;
import static java.lang.String.format;
import static java.util.Collections.emptyList;

@Service
public class GroupsService {

    private static final Logger logger = LogManager.getLogger(GroupsService.class);

    private final InstagramService instagramService;

    private final InstapopularDAO instapopularDAO;

    public GroupsService(InstagramService instagramService, @Qualifier("propertiesDao") InstapopularDAO instapopularDAO) {
        this.instagramService = instagramService;
        this.instapopularDAO = instapopularDAO;
    }

    void subscribeToUsersInGroup(int countSubscriptions, Action action) {
        try {
            Set<String> groups = instapopularDAO.getGroups().keySet();
            for (String urlGroup : groups) {
                subscribeToUsersInGroup(urlGroup, countSubscriptions, action);
            }
            System.out.println();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            instagramService.quitDriver();
        }
    }

    void loginOnWebSite(String login, String password) {
        try {
            instagramService.initDriver();
            instagramService.loginOnWebSite(login, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            instagramService.quitDriver();
        }
    }

    void addGroup(String userName) {
        try {
            instapopularDAO.addGroup(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeGroup(String userName) {
        try {
            instapopularDAO.removeGroup(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getGroup() {
        try {
            ArrayList<ViewMap> resultView = new ArrayList<>(instagramService.revertMapView(instapopularDAO.getGroups()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    private void subscribeToUsersInGroup(String channelName, int countSubscriptions, Action action) {
        logger.info(format(SUBSCRIBE_TO_GROUP_MEMBERS, channelName, countSubscriptions));
        if (channelName == null || countSubscriptions <= 0 || action == null) {
            return;
        }
        String baseWindowHandle = null;
        if (!format(HOME_PAGE, channelName).equalsIgnoreCase(instagramService.getCurrentUrl())) {
            baseWindowHandle = instagramService.openUrl(format(HOME_PAGE, channelName));
        }
        instagramService.getWebElement(60, OPEN_SUBSCRIBERS).click();
        instagramService.scrollSubscriptions(20);
        for (int i = 1; i <= countSubscriptions; i++) {
            try {
                instagramService.scrollElementSubscriptions(format(SCROLL, i));
                if (action == Action.SUBSCRIBE || action == Action.SUBSCRIBE_AND_LIKE) {
                    if (isSubscribed(format(IS_SUBSCRIBED, i))) {
                        countSubscriptions++;
                        continue;
                    }
                    instagramService.getWebElement(60, format(IS_SUBSCRIBED, i)).click();
                    Thread.sleep(2000);
                }
                if (requestSent(format(IS_SUBSCRIBED, i))) {
                    continue;
                }
                if (action == Action.LIKE || action == Action.SUBSCRIBE_AND_LIKE) {
                    String urlUser = instagramService.getWebElement(60, format(USER_LINK_TO_SUBSCRIBERS, i)).getAttribute(HREF);
                    String userWindowHandle = instagramService.openUrlNewTab(urlUser);
                    int countPhoto;
                    try {
                        countPhoto = checkPhoto();
                    } catch (Exception e) {
                        instagramService.closeTab(userWindowHandle);
                        instagramService.selectTab(baseWindowHandle);
                        continue;
                    }

                    if (countPhoto != 0) {
                        for (int j = 1; j <= countPhoto; j++) {
                            try {
                                String urlPhoto = instagramService.getWebElement(10, format(URL_PHOTO, j)).getAttribute(HREF);
                                instagramService.setLike(urlPhoto);
                            } catch (Exception e) {
                                instagramService.selectTab(userWindowHandle);
                                break;
                            }
                        }
                    }
                    logger.info(format(SUBSCRIBE_TO_GROUP, i));
                    instagramService.closeTab(userWindowHandle);
                    instagramService.selectTab(baseWindowHandle);
                }
                instagramService.timeOut(150, 50);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                instagramService.selectTab(baseWindowHandle);
            }
        }
    }

    private boolean isSubscribed(String xpath) {
        String isSubscribed = instagramService.getWebElement(60, xpath).getText();
        return SUBSCRIPTIONS.equalsIgnoreCase(isSubscribed) || REQUEST_SENT.equalsIgnoreCase(isSubscribed);
    }

    private boolean requestSent(String xpath) {
        String isSubscribed = instagramService.getWebElement(60, xpath).getText();
        return REQUEST_SENT.equalsIgnoreCase(isSubscribed);
    }

    private int checkPhoto() {
        List<WebElement> webElement = instagramService.getWebElements(10, CHECK_PHOTO);
        return (webElement == null) ? 0 : webElement.size();
    }
}
