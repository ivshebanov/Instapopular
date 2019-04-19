package com.instapopulars.instapopular.unsubscribe;

import static com.instapopulars.instapopular.Constant.Attribute.TITLE;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.MessageConstants.*;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.*;
import com.instapopulars.instapopular.DAO.InstagramDao;
import static java.lang.String.format;

import java.util.Map;
import java.util.Set;

import com.instapopulars.instapopular.model.ViewMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UnsubscribeDao {

    private static final Logger logger = LogManager.getLogger(UnsubscribeDao.class);

    @Autowired
    private InstagramDao instagramDao;

    void unsubscribeFromUsers(int countSubscribers, Set<String> subscribers) {
        logger.info(format(UNSUBSCRIBE_FROM_USERS, countSubscribers, subscribers.size()));
        if (instagramDao.openHomePage()) {
            return;
        }
        instagramDao.getWebElement(60, OPEN_SUBSCRIPTIONS).click();
        instagramDao.scrollSubscriptions(20);
        for (int i = 1; i <= countSubscribers; i++) {
            try {
                instagramDao.scrollElementSubscriptions(format(SCROLL, i));
                if (subscribers.size() != 0 && isSubscribed(subscribers, format(USER_LINK_TO_SUBSCRIBERS, i))) {
                    countSubscribers += 1;
                    continue;
                }
                instagramDao.timeOut(150, 50);
                instagramDao.getWebElement(60, format(SUBSCRIPTIONS_BTN, i)).click();
                instagramDao.getWebElement(60, UNSUBSCRIBE_BTN).click();
                logger.info(format(UNSUBSCRIBED_FROM, i));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                instagramDao.scrollSubscriptions(i);
            }
        }

    }

    private boolean isSubscribed(Set<String> subscribers, String xpath) {
        String url = instagramDao.getWebElement(60, xpath).getAttribute(TITLE);
        return subscribers.contains(url);
    }

    void initDriver() {
        instagramDao.initDriver();
    }

    void quitDriver() {
        instagramDao.quitDriver();
    }

    void loginOnWebSite(String login, String password) {
        instagramDao.loginOnWebSite(login, password);
    }

    Set<ViewMap> revertMapView(Map<String, Integer> map) {
        return instagramDao.revertMapView(map);
    }
}
