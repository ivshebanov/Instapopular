package com.instapopulars.instapopular.unsubscribe;

import static com.instapopulars.instapopular.Constant.Attribute.HREF;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.MessageConstants.GET_ALL_SUBSCRIBERS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.MessageConstants.UNSUBSCRIBED_FROM;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.MessageConstants.UNSUBSCRIBE_FROM_USERS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.COUNT_SUBSCRIBERS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.OPEN_SUBSCRIBERS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.OPEN_SUBSCRIPTIONS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.SCROLL;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.SUBSCRIPTIONS_BTN;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.UNSUBSCRIBE_BTN;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.USER_LINK_TO_SUBSCRIBERS;
import com.instapopulars.instapopular.DAO.InstagramDao;
import com.instapopulars.instapopular.model.User;
import static java.lang.String.format;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UnsubscribeDao {

    private static final Logger logger = LogManager.getLogger(UnsubscribeDao.class);

    @Autowired
    private InstagramDao instagramDao;

    public void unsubscribeFromUsers(int countSubscribers, Set<String> subscribers) {
        logger.info(format(UNSUBSCRIBE_FROM_USERS, countSubscribers));
        if (!instagramDao.openHomePage()) {
            return;
        }
        Set<User> users = instagramDao.getUsersByUrls(subscribers);
        instagramDao.getWebElement(60, OPEN_SUBSCRIPTIONS).click();
        instagramDao.scrollSubscriptions(20);
        for (int i = 1; i <= countSubscribers; i++) {
            try {
                instagramDao.scrollElementSubscriptions(format(SCROLL, i));
                if (users != null
                        && users.size() != 0
                        && isSubscribed(users, format(USER_LINK_TO_SUBSCRIBERS, i))) {
                    i++;
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

    private boolean isSubscribed(Set<User> subscribers, String xpath) {
        String url = instagramDao.getWebElement(60, xpath).getAttribute(HREF);
        User user = instagramDao.getUserByUrl(url);
        return subscribers.contains(user);
    }

    public Set<String> getAllSubscribers() {
        logger.info(GET_ALL_SUBSCRIBERS);
        Set<String> resultUser = new HashSet<>();
        if (!instagramDao.openHomePage()) {
            return resultUser;
        }
        String countSubscribersStr = instagramDao.getWebElement(60, COUNT_SUBSCRIBERS).getText();
        int countSubscribers = instagramDao.convertStringToInt(countSubscribersStr);
        instagramDao.getWebElement(60, OPEN_SUBSCRIBERS).click();
        if (countSubscribers > 2400) {
            resultUser.addAll(getUserLinks(2400));
        }
        if (countSubscribers < 2400) {
            resultUser.addAll(getUserLinks(countSubscribers));
        }
        return resultUser;
    }

    private Set<String> getUserLinks(int count) {
        Set<String> resultUrls = new HashSet<>();
        instagramDao.scrollSubscriptions(20);
        for (int i = 1; i < count; i++) {
            try {
                instagramDao.scrollElementSubscriptions(format(SCROLL, i));
                String url = instagramDao.getWebElement(60, format(USER_LINK_TO_SUBSCRIBERS, i)).getAttribute(HREF);
                resultUrls.add(url);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                instagramDao.scrollSubscriptions(i);
            }
        }
        return resultUrls;
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
