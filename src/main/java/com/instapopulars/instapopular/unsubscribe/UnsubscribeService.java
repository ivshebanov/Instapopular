package com.instapopulars.instapopular.unsubscribe;

import com.instapopulars.instapopular.DAO.IntapopularDAO;
import com.instapopulars.instapopular.view.ViewMap;
import com.instapopulars.instapopular.service.InstagramService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.instapopulars.instapopular.Constant.Attribute.TITLE;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.MessageConstants.*;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.*;
import static java.lang.String.format;
import static java.util.Collections.emptyList;

@Service
public class UnsubscribeService {

    private static final Logger logger = LogManager.getLogger(UnsubscribeService.class);

    @Autowired
    private InstagramService instagramService;

    @Autowired
    private IntapopularDAO intapopularDAO;

    void unsubscribe(int count) {
        try {
            unsubscribeFromUsers(count, intapopularDAO.getDoNotUnsubscribe().keySet());
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

    void addDoNotUnsubscribeUser(String userName) {
        try {
            intapopularDAO.addDoNotUnsubscribe(userName, "");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeDoNotUnsubscribeUser(String userName) {
        try {
            intapopularDAO.removeDoNotUnsubscribe(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getDoNotUnsubscribeUser() {
        try {
            List<ViewMap> resultView = new ArrayList<>(instagramService.revertMapView(intapopularDAO.getDoNotUnsubscribe()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    private void unsubscribeFromUsers(int countSubscribers, Set<String> subscribers) {
        logger.info(format(UNSUBSCRIBE_FROM_USERS, countSubscribers, subscribers.size()));
        if (instagramService.openHomePage()) {
            return;
        }
        instagramService.getWebElement(60, OPEN_SUBSCRIPTIONS).click();
        instagramService.scrollSubscriptions(20);
        for (int i = 1; i <= countSubscribers; i++) {
            try {
                instagramService.scrollElementSubscriptions(format(SCROLL, i));
                if (subscribers.size() != 0 && isSubscribed(subscribers, format(USER_LINK_TO_SUBSCRIBERS, i))) {
                    countSubscribers += 1;
                    continue;
                }
                instagramService.timeOut(150, 50);
                instagramService.getWebElement(60, format(SUBSCRIPTIONS_BTN, i)).click();
                instagramService.getWebElement(60, UNSUBSCRIBE_BTN).click();
                logger.info(format(UNSUBSCRIBED_FROM, i));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                instagramService.scrollSubscriptions(i);
            }
        }

    }

    private boolean isSubscribed(Set<String> subscribers, String xpath) {
        String url = instagramService.getWebElement(60, xpath).getAttribute(TITLE);
        return subscribers.contains(url);
    }
}
