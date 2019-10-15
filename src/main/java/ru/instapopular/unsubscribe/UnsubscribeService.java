package ru.instapopular.unsubscribe;

import ru.instapopular.Constant;
import ru.instapopular.repository.InstapopularDAO;
import ru.instapopular.service.InstagramService;
import ru.instapopular.view.ViewMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Collections.emptyList;

@Service
public class UnsubscribeService {

    private static final Logger logger = LogManager.getLogger(UnsubscribeService.class);

    private final InstagramService instagramService;

    private final InstapopularDAO instapopularDAO;

    public UnsubscribeService(InstagramService instagramService, @Qualifier("propertiesDao") InstapopularDAO instapopularDAO) {
        this.instagramService = instagramService;
        this.instapopularDAO = instapopularDAO;
    }

    void unsubscribe(int count) {
        try {
            unsubscribeFromUsers(count, instapopularDAO.getDoNotUnsubscribe().keySet());
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
            instapopularDAO.addDoNotUnsubscribe(userName, "");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeDoNotUnsubscribeUser(String userName) {
        try {
            instapopularDAO.removeDoNotUnsubscribe(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getDoNotUnsubscribeUser() {
        try {
            List<ViewMap> resultView = new ArrayList<>(instagramService.revertMapView(instapopularDAO.getDoNotUnsubscribe()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    private void unsubscribeFromUsers(int countSubscribers, Set<String> subscribers) {
        logger.info(String.format(Constant.UnsubscribeConstant.MessageConstants.UNSUBSCRIBE_FROM_USERS, countSubscribers, subscribers.size()));
        if (instagramService.openHomePage()) {
            return;
        }
        instagramService.getWebElement(60, Constant.UnsubscribeConstant.Xpath.OPEN_SUBSCRIPTIONS).click();
        instagramService.scrollSubscriptions(20);
        for (int i = 1; i <= countSubscribers; i++) {
            try {
                instagramService.scrollElementSubscriptions(String.format(Constant.UnsubscribeConstant.Xpath.SCROLL, i));
                if (subscribers.size() != 0 && isSubscribed(subscribers, String.format(Constant.UnsubscribeConstant.Xpath.USER_LINK_TO_SUBSCRIBERS, i))) {
                    countSubscribers += 1;
                    continue;
                }
                instagramService.timeOut(150, 50);
                instagramService.getWebElement(60, String.format(Constant.UnsubscribeConstant.Xpath.SUBSCRIPTIONS_BTN, i)).click();
                instagramService.getWebElement(60, Constant.UnsubscribeConstant.Xpath.UNSUBSCRIBE_BTN).click();
                logger.info(String.format(Constant.UnsubscribeConstant.MessageConstants.UNSUBSCRIBED_FROM, i));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                instagramService.scrollSubscriptions(i);
            }
        }

    }

    private boolean isSubscribed(Set<String> subscribers, String xpath) {
        String url = instagramService.getWebElement(60, xpath).getAttribute(Constant.Attribute.TITLE);
        return subscribers.contains(url);
    }
}
