package com.instapopulars.instapopular;

public interface Constant {

    interface User {
//        String LOGIN = "lilka.lily.1";
//        String PASSWORD = "Sxsblpwiwn";

        String LOGIN = "kj2a";
        String PASSWORD = "U3fony6c";
    }

    interface LinkToInstagram {
        String LOGIN_PAGE = "https://www.instagram.com/accounts/login/";
        String HOME_PAGE = "https://www.instagram.com/%s/";
        String HASHTAG_PAGE = "https://www.instagram.com/explore/tags/%s/";
    }

    interface Attribute {
        String HREF = "href";
        String ARIA_LABEL = "aria-label";
        String REQUEST_SENT = "Запрос отправлен";
        String I_DO_NOT_LIKE = "Не нравится";
        String SUBSCRIPTIONS = "Подписки";
    }

    interface DriverConstant {

        interface MessageConstants {
            String GET_LOGIN_AND_PASSWORD_FROM_PROPERTIES = "getLoginAndPasswordFromProperties()";
            String DO_NOT_UNSUBSCRIBE_MESSAGE = "doNotUnsubscribe()";
            String GET_HESTAG_FROM_PROPERTIES = "getHestagFromProperties()";
            String GET_GROUPS = "getGroupsFromProperties()";
            String GET_DRIVER = "getDriver() Local time: %tT";
            String QUIT_DRIVER = "quitDriver() Local time: %tT";
            String SET_PROPERTY = "initDriver(%s, %s)";
        }

        interface Driver {

            interface Chrome {
                String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
//                String CHROME_DRIVER = "chromedriver";//mac
                String CHROME_DRIVER="chromedriver.exe";//windows
            }
        }

        interface PropertiesName {
            String DO_NOT_UNSUBSCRIBE = "doNotUnsubscribe.properties";
            String HASHTAGS = "hashtags.properties";
            String ACCOUNT = "account.properties";
            String GROUPS = "groups.properties";
        }
    }

    interface GroupsConstant {

        interface MessageConstants {
            String SUBSCRIBE_TO_GROUP_MEMBERS = "subscribeToGroupMembers(%s, %d)";
            String SUBSCRIBE_TO_GROUP = "subscribe from %d";
        }

        interface Xpath {
            String URL_PHOTO = "//article/div[1]/div/div[1]/div[%d]/a";
            String IS_SUBSCRIBED = "//li[%d]/div/div[2]/button";
            String CHECK_PHOTO = "//article/div/div/div[1]/div";
        }

        interface Script {
            String WINDOW_OPEN = "window.open()";
        }
    }

    interface InstagramConstant {

        interface MessageConstants {
            String LOGIN_ON_WEB_SITE = "loginOnWebSite(%s, %s)";
        }

        interface Xpath {
            String LOGIN_USERNAME_INPUT = "//*[@id=\"react-root\"]//div/div[1]/input[@name='username']";
            String LOGIN_PASSWORD_INPUT = "//*[@id=\"react-root\"]//div/div[1]/input[@name='password']";
            String CHECK_LOGIN_BY_NAME = "//*[@id=\"react-root\"]//div[1]/div/div[2]/div[1]/a";
            String LOGIN_BUTTON = "//*[@id=\"react-root\"]//form/div[3]/button";
            String IS_ACTIVE_LIKE = "//div[2]/section[1]/span[1]/button/span";
            String SET_LIKE = "//div[2]/section[1]/span[1]/button";
            String SUBSCRIBE = "//*[@id=\"react-root\"]//div[2]/div[1]/div[2]/button";
        }
    }

    interface UnsubscribeConstant {

        interface MessageConstants {
            String UNSUBSCRIBE_FROM_USERS = "unsubscribeFromUsers(%d)";
            String UNSUBSCRIBED_FROM = "unsubscribed from %d";
            String GET_ALL_SUBSCRIBERS = "getAllSubscribers()";
        }

        interface Xpath {
            String ACCOUNT_NAME = "//*[@id=\"react-root\"]//div/header/section/div[1]/h1";
            String OPEN_SUBSCRIPTIONS = "//*[@id=\"react-root\"]//div/header/section/ul/li[3]/a";
            String OPEN_SUBSCRIBERS = "//*[@id=\"react-root\"]//div/header/section/ul/li[2]/a";
            String SUBSCRIPTIONS_BTN = "//li[%d]/div/div[2]/button";
//            String UNSUBSCRIBE_BTN = "/html/body/div[4]/div/div/div[3]/button[1]";//mac
            String UNSUBSCRIBE_BTN = "/html/body/div[3]/div/div/div[3]/button[1]";//windows
            String SCROLL = "//ul/div/li[%d]";
            String COUNT_SUBSCRIBERS = "//li[2]/a/span";
            String COUNT_SUBSCRIPTIONS = "//li[3]/a/span";
            String USER_LINK_TO_SUBSCRIBERS = "//li[%d]/div/div[1]/div[2]/div[1]/a";
        }
    }

    interface HashtagConstant {

        interface MessageConstants {
            String SUBSCRIBE_TOP_PUBLICATIONS_BY_HASHTAG = "topPublications(hashtag = %s)";
            String SUBSCRIBE_NEW_PUBLICATIONS_BY_HASHTAG = "newPublications(%s, %d)";
        }

        interface Xpath {
            String PATH_SEARCH_TOP_PUBLICATIONS = "//article/div[1]/div/div/div/div/a";
            String PATH_SEARCH_NEW_PUBLICATIONS = "//article/div[2]/div/div[%d]/div/a";
            String SCROLL_NEW_PUBLICATIONS = "//article/div[2]/div/div[%d]";
        }
    }
}
