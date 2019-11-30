package ru.instapopular.unsubscribe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ru.instapopular.Constant;
import ru.instapopular.model.Like;
import ru.instapopular.model.Usr;
import ru.instapopular.repository.InstapopularDAO;
import ru.instapopular.repository.LikeRepository;
import ru.instapopular.service.InstagramService;
import ru.instapopular.view.ViewMap;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class UnsubscribeService {

    private static final Logger logger = LogManager.getLogger(UnsubscribeService.class);

    private final InstagramService instagramService;
    private final InstapopularDAO instapopularDAO;
    private final LikeRepository likeRepository;

    public UnsubscribeService(LikeRepository likeRepository, InstagramService instagramService, @Qualifier("propertiesDao") InstapopularDAO instapopularDAO) {
        this.instagramService = instagramService;
        this.instapopularDAO = instapopularDAO;
        this.likeRepository = likeRepository;
    }

    void unsubscribe(Usr usr, int count) {
        try {
            unsubscribeFromUsers(count, likeRepository.findGuysByUsrAndActive(usr, true));
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

    void addGuy(Usr usr, String userName) {
        try {
            Like like = likeRepository.findLikeByUsrAndGuys(usr, userName);
            if (like != null) {
                return;
            }
            ApplicationContext context = new AnnotationConfigApplicationContext(Like.class);
            Like newLike = context.getBean(Like.class);
            newLike.setUsr(usr);
            newLike.setActive(true);
            newLike.setGuys(userName);
            newLike.setCountLike(100000);
            likeRepository.save(newLike);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeGuy(Usr usr, String userName) {
        try {
            Like like = likeRepository.findLikeByUsrAndGuys(usr, userName);
            if (like != null) {
                likeRepository.deactivateGuys(usr, userName);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getDoNotUnsubscribeUser(Usr usr) {
        try {
            List<String> guys = likeRepository.findGuysByUsrAndActive(usr, true);
            List<ViewMap> resultView = instagramService.revertToView(guys);
            Collections.sort(resultView);
            return resultView;
        } catch (Exception e) {
            return emptyList();
        }
    }

    private void unsubscribeFromUsers(int countSubscribers, List<String> subscribers) {
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

    private boolean isSubscribed(List<String> subscribers, String xpath) {
        String url = instagramService.getWebElement(60, xpath).getAttribute(Constant.Attribute.TITLE);
        return subscribers.contains(url);
    }
}
