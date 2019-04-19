package com.instapopulars.instapopular.hashtag;

import com.instapopulars.instapopular.Action;
import static com.instapopulars.instapopular.Constant.Attribute.HREF;
import static com.instapopulars.instapopular.Constant.HashtagConstant.MessageConstants.*;
import static com.instapopulars.instapopular.Constant.HashtagConstant.Xpath.*;
import static com.instapopulars.instapopular.Constant.LinkToInstagram.HASHTAG_PAGE;
import com.instapopulars.instapopular.DAO.InstagramDao;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.instapopulars.instapopular.model.ViewMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HashtagDao {

    private static final Logger logger = LogManager.getLogger(HashtagDao.class);

    @Autowired
    private InstagramDao instagramDao;

    void topPublications(String hashtag, Action action) {
        logger.info(format(SUBSCRIBE_TOP_PUBLICATIONS_BY_HASHTAG, hashtag));
        if (hashtag == null || hashtag.length() == 0 || action == null) {
            return;
        }
        if (!format(HASHTAG_PAGE, hashtag).equalsIgnoreCase(instagramDao.getCurrentUrl())) {
            instagramDao.openUrl(format(HASHTAG_PAGE, hashtag));
        }
        List<WebElement> weLinkTopPublications = instagramDao.getWebElements(60, PATH_SEARCH_TOP_PUBLICATIONS);
        List<String> linkTopPublications = getUrlsPhoto(weLinkTopPublications);
        choiceOfAction(action, linkTopPublications);
    }

    void newPublications(Action action, int countPhoto, String hashtag) {
        logger.info(format(SUBSCRIBE_NEW_PUBLICATIONS_BY_HASHTAG, hashtag, countPhoto));
        if (hashtag == null || hashtag.length() == 0 || action == null || countPhoto == 0) {
            return;
        }
        if (!format(HASHTAG_PAGE, hashtag).equalsIgnoreCase(instagramDao.getCurrentUrl())) {
            instagramDao.openUrl(format(HASHTAG_PAGE, hashtag));
        }
        List<WebElement> weLinkTopPublications = getNewPublication(countPhoto);
        List<String> linkTopPublications = getUrlsPhoto(weLinkTopPublications);
        choiceOfAction(action, linkTopPublications);
    }

    private List<WebElement> getNewPublication(int countPhoto) {
        List<WebElement> newPublication = new ArrayList<>();
        int countRow = countPhoto / 3 + 1;
        for (int i = 1; i <= countRow; i++) {
            newPublication.addAll(instagramDao.getWebElements(60, format(PATH_SEARCH_NEW_PUBLICATIONS, i)));
            scrollElement(i);
        }
        return newPublication;
    }

    private void scrollElement(int i) {
        if (i > 14) {
            for (int t = 10; t < 15; t++) {
                instagramDao.scrollElementSubscriptions(format(SCROLL_NEW_PUBLICATIONS, t));
            }
        }
        instagramDao.scrollElementSubscriptions(format(SCROLL_NEW_PUBLICATIONS, i));
        instagramDao.timeOut(2, 0);
    }

    private void choiceOfAction(Action action, List<String> linkToPublications) {
        switch (action) {
            case LIKE:
                like(linkToPublications);
                break;
            case SUBSCRIBE:
                subscribe(linkToPublications);
                break;
            case SUBSCRIBE_AND_LIKE:
                subscribeAndLike(linkToPublications);
                break;
        }
    }

    private void subscribeAndLike(List<String> urls) {
        for (String url : urls) {
            if (instagramDao.setLikeAndSubscribe(url)) {
                instagramDao.timeOut(150, 50);
            }
        }
    }

    private void subscribe(List<String> urls) {
        for (String url : urls) {
            if (instagramDao.subscribe(url)) {
                instagramDao.timeOut(150, 50);
            }
        }
    }

    private void like(List<String> urls) {
        for (String url : urls) {
            if (instagramDao.setLike(url)) {
                instagramDao.timeOut(20, 5);
            }
        }
    }

    private List<String> getUrlsPhoto(List<WebElement> weLinkTopPublications) {
        List<String> resultUrls = new ArrayList<>();
        for (WebElement we : weLinkTopPublications) {
            resultUrls.add(we.getAttribute(HREF));
        }
        return resultUrls;
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
