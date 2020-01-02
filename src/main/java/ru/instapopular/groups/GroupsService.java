package ru.instapopular.groups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ru.instapopular.Action;
import ru.instapopular.model.Client;
import ru.instapopular.model.MyGroup;
import ru.instapopular.model.Usr;
import ru.instapopular.repository.ClientRepository;
import ru.instapopular.repository.MyGroupRepository;
import ru.instapopular.service.InstagramService;
import ru.instapopular.view.ViewMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static ru.instapopular.Action.LIKE;
import static ru.instapopular.Action.SUBSCRIBE;
import static ru.instapopular.Action.SUBSCRIBE_AND_LIKE;
import static ru.instapopular.Constant.AnalysisConstant.LINE_BREAK;
import static ru.instapopular.Constant.Attribute.HREF;
import static ru.instapopular.Constant.Attribute.REQUEST_SENT;
import static ru.instapopular.Constant.Attribute.SUBSCRIPTIONS;
import static ru.instapopular.Constant.GroupsConstant.MessageConstants.SCAN_CLIENT;
import static ru.instapopular.Constant.GroupsConstant.MessageConstants.SUBSCRIBE_TO_GROUP;
import static ru.instapopular.Constant.GroupsConstant.MessageConstants.SUBSCRIBE_TO_GROUP_MEMBERS;
import static ru.instapopular.Constant.GroupsConstant.Xpath.CHECK_PHOTO;
import static ru.instapopular.Constant.GroupsConstant.Xpath.IS_SUBSCRIBED;
import static ru.instapopular.Constant.GroupsConstant.Xpath.URL_PHOTO;
import static ru.instapopular.Constant.LinkToInstagram.HOME_PAGE;
import static ru.instapopular.Constant.UnsubscribeConstant.Xpath.COUNT_SUBSCRIBERS;
import static ru.instapopular.Constant.UnsubscribeConstant.Xpath.OPEN_SUBSCRIBERS;
import static ru.instapopular.Constant.UnsubscribeConstant.Xpath.SCROLL;
import static ru.instapopular.Constant.UnsubscribeConstant.Xpath.USER_LINK_TO_SUBSCRIBERS;
import static ru.instapopular.Utils.getSubscribeUser;

@Service
public class GroupsService {

    private static final Logger logger = LogManager.getLogger(GroupsService.class);

    private final InstagramService instagramService;
    private final MyGroupRepository myGroupRepository;
    private final ClientRepository clientRepository;

    public GroupsService(InstagramService instagramService, MyGroupRepository myGroupRepository, ClientRepository clientRepository) {
        this.instagramService = instagramService;
        this.myGroupRepository = myGroupRepository;
        this.clientRepository = clientRepository;
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

    void scanClient(Usr usr) {
        Map<String, Integer> resultClientName = new HashMap<>();
        try {
            List<String> groups = myGroupRepository.findMyGroupByUsrAndActive(usr, true);
            for (String groupName : groups) {
                Set<String> clientNames = scanClient(groupName);
                addNameClientToMap(resultClientName, clientNames);
                removeGroup(usr, groupName);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            saveResultClientName(usr, resultClientName);
            instagramService.quitDriver();
        }
    }

    private void saveResultClientName(Usr usr, Map<String, Integer> resultClientName) {
        List<String> allClient = clientRepository.findClientNameByUsr(usr);
        for (Map.Entry<String, Integer> entry : resultClientName.entrySet()) {
            if (allClient.contains(entry.getKey())) {
                Client client = clientRepository.findClientByUsrAndClientName(usr, entry.getKey());
                int frequency = client.getFrequency() + entry.getValue();
                clientRepository.updateFrequencyByUsrAndClientName(usr, entry.getKey(), frequency);
                continue;
            }
            saveNewClient(usr, entry);
        }
    }

    private void saveNewClient(Usr usr, Map.Entry<String, Integer> entry) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Client.class);
        Client client = context.getBean(Client.class);
        client.setUsr(usr);
        client.setClientName(entry.getKey());
        client.setFrequency(entry.getValue());
        client.setActive(true);
        clientRepository.save(client);
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

    List<ViewMap> getActiveClient(Usr usr) {
        try {
            List<String> myActiveGroup = clientRepository.findClientNameByUsrAndActive(usr, true);
            List<ViewMap> myActiveGroupViewMap = instagramService.revertToView(myActiveGroup);
            Collections.sort(myActiveGroupViewMap);
            return myActiveGroupViewMap;
        } catch (Exception e) {
            return emptyList();
        }
    }

    private Set<String> scanClient(String channelName) {
        logger.info(format(SCAN_CLIENT, channelName));
        if (!format(HOME_PAGE, channelName).equalsIgnoreCase(instagramService.getCurrentUrl())) {
            instagramService.openUrl(format(HOME_PAGE, channelName));
        }
        String countSubscribers = instagramService.getWebElement(15, COUNT_SUBSCRIBERS).getText();
        int countSubscribersInt = instagramService.convertStringToInt(countSubscribers);
        if (countSubscribersInt > 2400) countSubscribersInt = 2300;//2400

        instagramService.getWebElement(60, OPEN_SUBSCRIBERS).click();
        instagramService.scrollSubscriptions(20);
        instagramService.timeOut(2, 0);
        instagramService.scrollSubscriptions(countSubscribersInt);

        List<WebElement> elements = null;
        try {
            elements = instagramService.getWebElements(30, getSubscribeUser());
        } catch (NoSuchElementException ignored) {
        }
        return getActiveUser(elements);
    }

    private Set<String> getActiveUser(List<WebElement> elements) {
        if (elements == null || elements.size() == 0) {
            return new HashSet<>();
        }
        Set<String> resultUser = new HashSet<>();
        try {
            for (WebElement element : elements) {
                resultUser.add(element.getText().split(LINE_BREAK)[0]);
            }
        } catch (StaleElementReferenceException ex) {
            return null;
        }
        return resultUser;
    }

    private void subscribeToUsersInGroup(String channelName, int countSubscriptions, Action action) {
        logger.info(format(SUBSCRIBE_TO_GROUP_MEMBERS, channelName, countSubscriptions, action.toString()));
        String baseWindowHandle = null;
        if (!format(HOME_PAGE, channelName).equalsIgnoreCase(instagramService.getCurrentUrl())) {
            baseWindowHandle = instagramService.openUrl(format(HOME_PAGE, channelName));
        }
        instagramService.getWebElement(60, OPEN_SUBSCRIBERS).click();
        instagramService.scrollSubscriptions(20);
        for (int i = 1; i <= countSubscriptions; i++) {
            try {
                instagramService.scrollElementSubscriptions(format(SCROLL, i));
                if (action == SUBSCRIBE || action == SUBSCRIBE_AND_LIKE) {
                    if (isSubscribed(format(IS_SUBSCRIBED, i))) {
                        countSubscriptions++;
                        continue;
                    }
                    instagramService.getWebElement(60, format(IS_SUBSCRIBED, i)).click();
                    Thread.sleep(2000);
                }
                if (requestSent(format(IS_SUBSCRIBED, i))) continue;
                if (action == LIKE || action == SUBSCRIBE_AND_LIKE) {
                    String urlUser = instagramService.getWebElement(60, format(USER_LINK_TO_SUBSCRIBERS, i)).getAttribute(HREF);
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
                                instagramService.setLike(instagramService.getWebElement(10, format(URL_PHOTO, j)).getAttribute(HREF));
                            } catch (Exception e) {
                                instagramService.selectTab(userWindowHandle);
                                break;
                            }
                        }
                    }
                    logger.info(format(SUBSCRIBE_TO_GROUP, i));
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
        return SUBSCRIPTIONS.equalsIgnoreCase(isSubscribed)
                || REQUEST_SENT.equalsIgnoreCase(isSubscribed);
    }

    private boolean requestSent(String xpath) {
        String isSubscribed = instagramService.getWebElement(60, xpath).getText();
        return REQUEST_SENT.equalsIgnoreCase(isSubscribed);
    }

    private int checkPhoto() {
        List<WebElement> webElement = instagramService.getWebElements(10, CHECK_PHOTO);
        return (webElement == null) ? 0 : webElement.size();
    }

    private void addNameClientToMap(Map<String, Integer> mapClient, Set<String> client) {
        for (String user : client) {
            if (mapClient.containsKey(user)) {
                mapClient.put(user, mapClient.get(user) + 1);
                continue;
            }
            mapClient.put(user, 1);
        }
    }
}
