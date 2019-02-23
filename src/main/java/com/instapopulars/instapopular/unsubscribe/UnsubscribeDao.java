package com.instapopulars.instapopular.unsubscribe;

import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.MessageConstants.UNSUBSCRIBED_FROM;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.MessageConstants.UNSUBSCRIBE_FROM_USERS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.HREF;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.OPEN_SUBSCRIPTIONS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.SCROLL;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.SUBSCRIPTIONS_BTN;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.UNSUBSCRIBE_BTN;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.USER_LINK_TO_SUBSCRIBERS;
import com.instapopulars.instapopular.DAO.InstagramDao;
import com.instapopulars.instapopular.model.User;
import static java.lang.String.format;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UnsubscribeDao {

    private static final Logger logger = LoggerFactory.getLogger(UnsubscribeDao.class);
    private final InstagramDao instagramDao;
    private WebDriver driver;

    @Autowired
    public UnsubscribeDao(InstagramDao instagramDao) {
        this.instagramDao = instagramDao;
    }

    public void unsubscribeFromUsers(int countSubscribers, List<User> subscribers) {
        logger.info(UNSUBSCRIBE_FROM_USERS);
        if (!instagramDao.openHomePage()) {
            return;
        }
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(OPEN_SUBSCRIPTIONS))).click();
        instagramDao.scrollSubscriptions(20);
        for (int i = 1; i < countSubscribers; i++) {
            try {
                instagramDao.scrollElementSubscriptions(format(SCROLL, i));
                if (subscribers != null && isSubscribed(subscribers, format(USER_LINK_TO_SUBSCRIBERS, i))) {
                    continue;
                }
                Random random = new Random();
                int num = 40000 + random.nextInt(80000 - 40000);
                Thread.sleep(5000);
                (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(format(SUBSCRIPTIONS_BTN, i)))).click();
                (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(UNSUBSCRIBE_BTN))).click();
                logger.info(format(UNSUBSCRIBED_FROM, i));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                instagramDao.scrollSubscriptions(i);
            }
        }

    }

    private boolean isSubscribed(List<User> subscribers, String xpath) {
        String url = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))).getAttribute(HREF);
        User user = instagramDao.getUserByUrl(url);
        return subscribers.contains(user);
    }

    public WebDriver initDriver() {
        driver = instagramDao.initDriver();
        return driver;
    }

    public void quitDriver() {
        instagramDao.quitDriver();
    }

    public boolean loginOnWebSite(String login, String password) {
        return instagramDao.loginOnWebSite(login, password);
    }
}
