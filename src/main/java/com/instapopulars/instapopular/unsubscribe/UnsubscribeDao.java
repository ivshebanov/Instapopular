package com.instapopulars.instapopular.unsubscribe;

import static com.instapopulars.instapopular.Constant.Attribute.HREF;
import static com.instapopulars.instapopular.Constant.LinkToInstagram.HOME_PAGE;
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
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UnsubscribeDao {

    private static final Logger logger = LogManager.getLogger(UnsubscribeDao.class);
    private final InstagramDao instagramDao;

    @Autowired
    public UnsubscribeDao(InstagramDao instagramDao) {
        this.instagramDao = instagramDao;
    }

    public void unsubscribeFromUsers(int countSubscribers, List<User> subscribers) {
        logger.info(format(UNSUBSCRIBE_FROM_USERS, countSubscribers));
        if (!instagramDao.openHomePage()) {
            return;
        }
        instagramDao.getWebElement(60, OPEN_SUBSCRIPTIONS).click();
        instagramDao.scrollSubscriptions(20);
        for (int i = 1; i < countSubscribers; i++) {
            try {
                instagramDao.scrollElementSubscriptions(format(SCROLL, i));
                if (subscribers != null && isSubscribed(subscribers, format(USER_LINK_TO_SUBSCRIBERS, i))) {
                    i++;
                    continue;
                }
                instagramDao.timeOut(150000, 50000);
                instagramDao.getWebElement(60, format(SUBSCRIPTIONS_BTN, i)).click();
                instagramDao.getWebElement(60, UNSUBSCRIBE_BTN).click();
                logger.info(format(UNSUBSCRIBED_FROM, i));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                instagramDao.scrollSubscriptions(i);
            }
        }

    }

    private boolean isSubscribed(List<User> subscribers, String xpath) {
        String url = instagramDao.getWebElement(60, xpath).getAttribute(HREF);
        User user = instagramDao.getUserByUrl(url);
        return subscribers.contains(user);
    }

    public List<User> getAllSubscribers() {
        logger.info(GET_ALL_SUBSCRIBERS);
        List<User> resultUser = new ArrayList<>();
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

    private List<User> getUserLinks(int count) {
        List<User> resultUrls = new ArrayList<>();
        instagramDao.scrollSubscriptions(20);
        for (int i = 1; i < count; i++) {
            try {
                instagramDao.scrollElementSubscriptions(format(SCROLL, i));
                String url = instagramDao.getWebElement(60, format(USER_LINK_TO_SUBSCRIBERS, i)).getAttribute(HREF);
                resultUrls.add(instagramDao.getUserByUrl(url));
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
