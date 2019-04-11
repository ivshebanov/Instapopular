package com.instapopulars.instapopular;

public interface Constant {

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
        String TARGET_CLASSES = "target/classes";
        String SRC_MAIN_RESOURCES = "src/main/resources";
    }

    interface Utils {

        String OS_NAME = "os.name";
        String MAC = "Mac";
        String WINDOWS = "Windows";
    }

    interface DriverConstant {

        interface MessageConstants {
            String GET_DRIVER = "getDriver() Local time: %tT";
            String QUIT_DRIVER = "quitDriver() Local time: %tT";
            String SET_PROPERTY = "initDriver(%s, %s)";
        }

        interface Driver {

            interface Chrome {
                String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
                String CHROME_DRIVER_MAC = "chromedriver";
                String CHROME_DRIVER_WINDOWS = "chromedriver.exe";
            }
        }

        interface PropertiesName {
            String DO_NOT_UNSUBSCRIBE = "properties/doNotUnsubscribe.properties";
            String HASHTAGS = "properties/hashtags.properties";
            String GROUPS = "properties/groups.properties";
            String MY_PHOTO = "properties/myPhoto.properties";
            String PHOTO_ANALYSIS_RESULTS = "properties/photoAnalysisResults.properties";
        }
    }

    interface GroupsConstant {

        interface MessageConstants {
            String SUBSCRIBE_TO_GROUP_MEMBERS = "subscribeToUsersInGroup(%s, %d)";
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
            String LOGIN_BUTTON = "//form/div[4]/button";
            String IS_ACTIVE_LIKE = "//div[2]/section[1]/span[1]/button/span";
            String SET_LIKE = "//div[2]/section[1]/span[1]/button";
            String SUBSCRIBE = "//*[@id=\"react-root\"]//div[2]/div[1]/div[2]/button";
        }

        interface Script {
            String SCROLL_INTO_VIEW = "arguments[0].scrollIntoView(false);";
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
            String UNSUBSCRIBE_BTN = "/html/body/div[4]/div/div/div[3]/button[1]";
            String SCROLL = "//ul/div/li[%d]";
            String COUNT_SUBSCRIBERS = "//li[2]/a/span";
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

    interface AnalysisConstant {
        String OPEN_LIKE = "//div[2]/section[2]/div/div/a";
        String LOGIN_USER_MAC = "//div[4]/div/div[2]/div/div/div";
        String LOGIN_USER_WINDOWS = "//div[3]/div/div[2]/div/div/div";
        String COUNT_USER_LIKE = "//div[2]/section[2]/div/div/a/span";
        String LINE_BREAK = "\n";
        String CUT_OF_URL = "p/.*";
        String SLASH = "/";
    }
}
