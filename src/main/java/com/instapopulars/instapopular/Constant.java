package com.instapopulars.instapopular;

public interface Constant {

    interface User {
        String LOGIN = "kj2a";
        String PASSWORD ="U3fony6c";

//        String LOGIN = "lilka.lily.1";
//        String PASSWORD ="Sxsblpwiwn";
    }

    interface DriverConstant {

        interface MessageConstants {
            String GET_LOGIN_AND_PASSWORD_FROM_PROPERTIES = "getLoginAndPasswordFromProperties()";
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

            interface Firefox {
                String WEBDRIVER_GECKO_DRIVER = "webdriver.gecko.driver";
                String GECKO_DRIVER = "geckodriver";
            }
        }

        interface propertiesName {
            String HASHTAGS = "hashtags.properties";
            String ACCOUNT = "account.properties";
            String GROUPS = "groups.properties";
        }
    }

    interface GroupsConstant {

        interface MessageConstants {
            String SUBSCRIBE_TO_GROUP_MEMBERS = "subscribeToGroupMembers(%s, %d)";
            String SUBSCRIPTIONS = "Подписки";
            String REQUEST_SENT = "Запрос отправлен";
            String SUBSCRIBE_TO_GROUP = "subscribe from %d";
        }

        interface Xpath {
            String IS_SUBSCRIBED = "//li[%d]/div/div[2]/button";
            String URL_PHOTO = "//article/div[1]/div/div[1]/div[%d]/a";
            String CHECK_PHOTO = "//article/div/div/div[1]/div";
        }

        interface Script {
            String WINDOW_OPEN = "window.open()";
        }
    }

    interface InstagramConstant {

        interface MessageConstants {
            String LOGIN_ON_WEB_SITE = "loginOnWebSite(%s, %s)";
            String LOGIN_ON_WEB_SITE_SUCCESS = "loginOnWebSite() = success";
            String I_DO_NOT_LIKE = "Не нравится";
        }

        interface Xpath {
            String LOGIN_PAGE = "https://www.instagram.com/accounts/login/";
            String LOGIN_USERNAME_INPUT = "//*[@id=\"react-root\"]//div/div[1]/input[@name='username']";
            String LOGIN_PASSWORD_INPUT = "//*[@id=\"react-root\"]//div/div[1]/input[@name='password']";
            String LOGIN_BUTTON = "//*[@id=\"react-root\"]//form/div[3]/button";
            String CHECK_LOGIN_BY_NAME = "//*[@id=\"react-root\"]//div[1]/div/div[2]/div[1]/a";
            String IS_ACTIVE_LIKE = "//div[2]/section[1]/span[1]/button/span";
            String ARIA_LABEL = "aria-label";
            String SET_LIKE = "//div[2]/section[1]/span[1]/button";
        }
    }

    interface UnsubscribeConstant {

        interface MessageConstants {
            String UNSUBSCRIBE_FROM_USERS = "unsubscribeFromUsers(%d)";
            String GET_ALL_SUBSCRIBERS = "getAllSubscribers()";
            String GET_ALL_SUBSCRIPTIONS = "getAllSubscriptions()";
            String UNSUBSCRIBED_FROM = "unsubscribed from %d";
        }

        interface Xpath {
            String HREF = "href";
            String ACCOUNT_NAME = "//*[@id=\"react-root\"]//div/header/section/div[1]/h1";
            String OPEN_SUBSCRIPTIONS = "//*[@id=\"react-root\"]//div/header/section/ul/li[3]/a";
            String OPEN_SUBSCRIBERS = "//*[@id=\"react-root\"]//div/header/section/ul/li[2]/a";
            String SUBSCRIPTIONS_BTN = "//li[%d]/div/div[2]/button";
            //html/body/div[4]/div/div/div[3]/button[1] - UNSUBSCRIBE_BTN mac
            //html/body/div[3]/div/div/div[3]/button[1] - UNSUBSCRIBE_BTN windows
            String UNSUBSCRIBE_BTN = "/html/body/div[3]/div/div/div[3]/button[1]";
            String SCROLL = "//ul/div/li[%d]";
            String COUNT_SUBSCRIBERS = "//li[2]/a/span";
            String COUNT_SUBSCRIPTIONS = "//li[3]/a/span";
            String USER_LINK_TO_SUBSCRIBERS = "//li[%d]/div/div[1]/div[2]/div[1]/a";
        }

        interface Link {
            String HOME_PAGE = "https://www.instagram.com/%s/";
        }
    }

}
