package ru.instapopular.groups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ru.instapopular.Action;
import ru.instapopular.Constant;
import ru.instapopular.model.MyGroup;
import ru.instapopular.model.Usr;
import ru.instapopular.repository.MyGroupRepository;
import ru.instapopular.service.InstagramService;
import ru.instapopular.view.ViewMap;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class GroupsService {

    private static final Logger logger = LogManager.getLogger(GroupsService.class);

    private final InstagramService instagramService;
    private final MyGroupRepository myGroupRepository;

    public GroupsService(InstagramService instagramService, MyGroupRepository myGroupRepository) {
        this.instagramService = instagramService;
        this.myGroupRepository = myGroupRepository;
    }

    void subscribeToUsersInGroup(Usr usr, int countSubscriptions, Action action) {
        try {
            List<String> groups = myGroupRepository.findMyGroupByUsrAndActive(usr, true);
            for (String urlGroup : groups) {
                subscribeToUsersInGroup(urlGroup, countSubscriptions, action);
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

    void addGroup(Usr usr, String groupName) {
        try {
            MyGroup myDeativateGroup = myGroupRepository.findMyGroupByUsrAndMyGroup(usr, groupName);
            if (myDeativateGroup != null) {
                myGroupRepository.activateMyGroup(usr, groupName);
                return;
            }
            ApplicationContext context = new AnnotationConfigApplicationContext(MyGroup.class);
            MyGroup group = context.getBean(MyGroup.class);
            group.setUsr(usr);
            group.setActive(true);
            group.setMyGroup(groupName);
            myGroupRepository.save(group);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeGroup(Usr usr, String groupName) {
        try {
            MyGroup myActivateGroup = myGroupRepository.findMyGroupByUsrAndMyGroup(usr, groupName);
            if (myActivateGroup != null) {
                myGroupRepository.deactivateMyGroup(usr, groupName);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getActiveGroup(Usr usr) {
        try {
            List<String> myActiveGroup = myGroupRepository.findMyGroupByUsrAndActive(usr, true);
            List<ViewMap> myActiveGroupViewMap = instagramService.revertToView(myActiveGroup);
            Collections.sort(myActiveGroupViewMap);
            return myActiveGroupViewMap;
        } catch (Exception e) {
            return emptyList();
        }
    }

    private void subscribeToUsersInGroup(String channelName, int countSubscriptions, Action action) {
        logger.info(String.format(Constant.GroupsConstant.MessageConstants.SUBSCRIBE_TO_GROUP_MEMBERS, channelName, countSubscriptions));
        String baseWindowHandle = null;
        if (!String.format(Constant.LinkToInstagram.HOME_PAGE, channelName).equalsIgnoreCase(instagramService.getCurrentUrl())) {
            baseWindowHandle = instagramService.openUrl(String.format(Constant.LinkToInstagram.HOME_PAGE, channelName));
        }
        instagramService.getWebElement(60, Constant.UnsubscribeConstant.Xpath.OPEN_SUBSCRIBERS).click();
        instagramService.scrollSubscriptions(20);
        for (int i = 1; i <= countSubscriptions; i++) {
            try {
                instagramService.scrollElementSubscriptions(String.format(Constant.UnsubscribeConstant.Xpath.SCROLL, i));
                if (action == Action.SUBSCRIBE || action == Action.SUBSCRIBE_AND_LIKE) {
                    if (isSubscribed(String.format(Constant.GroupsConstant.Xpath.IS_SUBSCRIBED, i))) {
                        countSubscriptions++;
                        continue;
                    }
                    instagramService.getWebElement(60, String.format(Constant.GroupsConstant.Xpath.IS_SUBSCRIBED, i)).click();
                    Thread.sleep(2000);
                }
                if (requestSent(String.format(Constant.GroupsConstant.Xpath.IS_SUBSCRIBED, i))) {
                    continue;
                }
                if (action == Action.LIKE || action == Action.SUBSCRIBE_AND_LIKE) {
                    String urlUser = instagramService.getWebElement(60, String.format(Constant.UnsubscribeConstant.Xpath.USER_LINK_TO_SUBSCRIBERS, i)).getAttribute(Constant.Attribute.HREF);
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
                                String urlPhoto = instagramService.getWebElement(10, String.format(Constant.GroupsConstant.Xpath.URL_PHOTO, j)).getAttribute(Constant.Attribute.HREF);
                                instagramService.setLike(urlPhoto);
                            } catch (Exception e) {
                                instagramService.selectTab(userWindowHandle);
                                break;
                            }
                        }
                    }
                    logger.info(String.format(Constant.GroupsConstant.MessageConstants.SUBSCRIBE_TO_GROUP, i));
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
        return Constant.Attribute.SUBSCRIPTIONS.equalsIgnoreCase(isSubscribed) || Constant.Attribute.REQUEST_SENT.equalsIgnoreCase(isSubscribed);
    }

    private boolean requestSent(String xpath) {
        String isSubscribed = instagramService.getWebElement(60, xpath).getText();
        return Constant.Attribute.REQUEST_SENT.equalsIgnoreCase(isSubscribed);
    }

    private int checkPhoto() {
        List<WebElement> webElement = instagramService.getWebElements(10, Constant.GroupsConstant.Xpath.CHECK_PHOTO);
        return (webElement == null) ? 0 : webElement.size();
    }
}
