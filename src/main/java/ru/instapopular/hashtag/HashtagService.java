package ru.instapopular.hashtag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ru.instapopular.Action;
import ru.instapopular.Constant;
import ru.instapopular.model.Hashtag;
import ru.instapopular.model.Usr;
import ru.instapopular.repository.HashtagRepository;
import ru.instapopular.service.InstagramService;
import ru.instapopular.view.ViewMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class HashtagService {

    private static final Logger logger = LogManager.getLogger(HashtagService.class);

    private final InstagramService instagramService;
    private final HashtagRepository hashtagRepository;

    public HashtagService(InstagramService instagramService, HashtagRepository hashtagRepository) {
        this.instagramService = instagramService;
        this.hashtagRepository = hashtagRepository;
    }

    void topPublications(Usr usr, Action action) {
        try {
            List<String> hashtags = hashtagRepository.findHashtagsByUsrAndActive(usr, true);
            for (String hashtag : hashtags) {
                topPublications(hashtag, action);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            instagramService.quitDriver();
        }
    }

    void newPublications(Usr usr, Action action, int countPhoto) {
        try {
            List<String> hashtags = hashtagRepository.findHashtagsByUsrAndActive(usr, true);
            for (String hashtag : hashtags) {
                newPublications(action, countPhoto, hashtag);
            }
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

    void addHestag(Usr usr, String hashtagName) {
        try {
            Hashtag hashtag = hashtagRepository.findHashtagByUsrAndHashtag(usr, hashtagName);
            if (hashtag != null) {
                hashtagRepository.activateHashtag(usr, hashtagName);
                return;
            }
            ApplicationContext context = new AnnotationConfigApplicationContext(Hashtag.class);
            Hashtag newHashtag = context.getBean(Hashtag.class);
            newHashtag.setUsr(usr);
            newHashtag.setActive(true);
            newHashtag.setHashtag(hashtagName);
            hashtagRepository.save(newHashtag);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeHestag(Usr usr, String hashtagName) {
        try {
            Hashtag photo = hashtagRepository.findHashtagByUsrAndHashtag(usr, hashtagName);
            if (photo != null) {
                hashtagRepository.deactivateHashtag(usr, hashtagName);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getHestags(Usr usr) {
        try {
            List<String> hashtags = hashtagRepository.findHashtagsByUsrAndActive(usr, true);
            List<ViewMap> resultView = instagramService.revertToView(hashtags);
            Collections.sort(resultView);
            return resultView;
        } catch (Exception e) {
            return emptyList();
        }
    }

    private void topPublications(String hashtag, Action action) {
        logger.info(String.format(Constant.HashtagConstant.MessageConstants.SUBSCRIBE_TOP_PUBLICATIONS_BY_HASHTAG, hashtag));
        if (hashtag == null || hashtag.length() == 0 || action == null) {
            return;
        }
        if (!String.format(Constant.LinkToInstagram.HASHTAG_PAGE, hashtag).equalsIgnoreCase(instagramService.getCurrentUrl())) {
            instagramService.openUrl(String.format(Constant.LinkToInstagram.HASHTAG_PAGE, hashtag));
        }
        List<WebElement> weLinkTopPublications = instagramService.getWebElements(60, Constant.HashtagConstant.Xpath.PATH_SEARCH_TOP_PUBLICATIONS);
        List<String> linkTopPublications = getUrlsPhoto(weLinkTopPublications);
        choiceOfAction(action, linkTopPublications);
    }

    private void newPublications(Action action, int countPhoto, String hashtag) {
//        logger.info(format(SUBSCRIBE_NEW_PUBLICATIONS_BY_HASHTAG, hashtag, countPhoto));
//        if (hashtag == null || hashtag.length() == 0 || action == null || countPhoto < 0) {
//            return;
//        }
//        if (!format(HASHTAG_PAGE, hashtag).equalsIgnoreCase(instagramService.getCurrentUrl())) {
//            instagramService.openUrl(format(HASHTAG_PAGE, hashtag));
//        }
//        List<WebElement> weLinkTopPublications = getNewPublication(countPhoto);
//        List<String> linkTopPublications = getUrlsPhoto(weLinkTopPublications);
//        choiceOfAction(action, linkTopPublications);
    }

    private List<WebElement> getNewPublication(int countPhoto) {
        List<WebElement> newPublication = new ArrayList<>();
        int countRow = countPhoto / 3 + 1;
        for (int i = 1; i <= countRow; i++) {
            newPublication.addAll(instagramService.getWebElements(60, String.format(Constant.HashtagConstant.Xpath.PATH_SEARCH_NEW_PUBLICATIONS, i)));
            scrollElement(i);
        }
        return newPublication;
    }

    private void scrollElement(int i) {
        if (i > 14) {
            for (int t = 10; t < 15; t++) {
                instagramService.scrollElementSubscriptions(String.format(Constant.HashtagConstant.Xpath.SCROLL_NEW_PUBLICATIONS, t));
            }
        }
        instagramService.scrollElementSubscriptions(String.format(Constant.HashtagConstant.Xpath.SCROLL_NEW_PUBLICATIONS, i));
        instagramService.timeOut(2, 0);
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
            if (instagramService.setLikeAndSubscribe(url)) {
                instagramService.timeOut(150, 50);
            }
        }
    }

    private void subscribe(List<String> urls) {
        for (String url : urls) {
            if (instagramService.subscribe(url)) {
                instagramService.timeOut(150, 50);
            }
        }
    }

    private void like(List<String> urls) {
        for (String url : urls) {
            if (instagramService.setLike(url)) {
                instagramService.timeOut(20, 5);
            }
        }
    }

    private List<String> getUrlsPhoto(List<WebElement> weLinkTopPublications) {
        List<String> resultUrls = new ArrayList<>();
        for (WebElement we : weLinkTopPublications) {
            resultUrls.add(we.getAttribute(Constant.Attribute.HREF));
        }
        return resultUrls;
    }
}
