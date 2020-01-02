package ru.instapopular;

public interface Constant {

    interface LinkToInstagram {

        String LOGIN_PAGE = "https://www.instagram.com/accounts/login/";
        String HOME_PAGE = "https://www.instagram.com/%s/";
        String HOME_PAGE_2 = "https://www.instagram.com/%s";
        String HASHTAG_PAGE = "https://www.instagram.com/explore/tags/%s/";
    }

    interface Attribute {

        String TITLE = "title";
        String HREF = "href";
        String ARIA_LABEL = "aria-label";
        String REQUEST_SENT = "Запрос отправлен";
        String I_DO_NOT_LIKE = "Не нравится";
        String SUBSCRIPTIONS = "Подписки";
    }

    interface Utils {

        String OS_NAME = "os.name";
        String MAC = "Mac";
        String WINDOWS = "Windows";
    }

    interface DriverConstant {

        interface MessageConstants {
            String GET_DRIVER = "initDriver() Local time: %tT";
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
    }

    interface GroupsConstant {

        interface MessageConstants {
            String SUBSCRIBE_TO_GROUP_MEMBERS = "subscribeToUsersInGroup(%s, %d, %s)";
            String SCAN_CLIENT = "scanClient(%s)";
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
            String DID_NOT_FIND_THE_BUTTON = "Не нашел кнопку %s, нажимаем на %s";
            String LOGIN_FAILED = "Не получилось залогинитья.";
        }

        interface Xpath {
            String LOGIN_USERNAME_INPUT = "//div[2]/div/label/input[@name='username']";
            String LOGIN_PASSWORD_INPUT = "//div[3]/div/label/input[@name='password']";
            String LOGIN_BUTTON_4 = "//div[4]/button";
            String LOGIN_BUTTON_5 = "//form/div[5]/button";
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
            String UNSUBSCRIBE_FROM_USERS = "unsubscribeFromUsers(%d, %d)";
            String UNSUBSCRIBED_FROM = "unsubscribed from %d";
        }

        interface Xpath {
            String ACCOUNT_NAME = "//header/section/div[1]/h1";
            String OPEN_SUBSCRIPTIONS = "//ul/li[3]/a";
            String OPEN_SUBSCRIBERS = "//ul/li[2]/a";
            String COUNT_SUBSCRIBERS = "//ul/li[2]/a/span";
            String SUBSCRIPTIONS_BTN = "//li[%d]/div/div[2]/button";
            String UNSUBSCRIBE_BTN = "//div[3]/button[1]";
            String SCROLL = "//ul/div/li[%d]";
            String USER_LINK_TO_SUBSCRIBERS = "//li[%d]/div/div[1]/div[2]/div[1]/a";
        }
    }

    interface HashtagConstant {

        interface MessageConstants {
            String SUBSCRIBE_TOP_PUBLICATIONS_BY_HASHTAG = "topPublications(hashtag = %s)";
        }

        interface Xpath {
            String PATH_SEARCH_TOP_PUBLICATIONS = "//article/div[1]/div/div/div/div/a";
            String PATH_SEARCH_NEW_PUBLICATIONS = "//article/div[2]/div/div[%d]/div/a";
            String SCROLL_NEW_PUBLICATIONS = "//article/div[2]/div/div[%d]";
        }
    }

    interface AnalysisConstant {
        String OPEN_LIKE = "//div[2]/section[2]/div";
        String LOGIN_USER_MAC = "//div[4]/div/div[2]/div/div/div";
        String LOGIN_USER_WINDOWS = "//div[3]/div/div[2]/div/div/div";
        String SUBSCRIBERS_USER_MAC = "//div[4]/div/div[2]/ul/div/li";
        String SUBSCRIBERS_USER_WINDOWS = "//div[3]/div/div[2]/ul/div/li";
        String COUNT_USER_LIKE = "//div[2]/section[2]/div/div/button/span";
        String LINE_BREAK = "\n";
        String CUT_OF_URL = "p/.*";
        String VIEWS = "Просмотры";
    }

    interface RegistrationServiceConstant {
        String USER_EXIST = "Пользователь существует!";
        String USER_IS_REGISTERED = "Пользователь зарегистрирован!";
        String USER_NOT_REGISTERED = "Пользователь не зарегистрирован!";
        String INVALID_DATA = "Вы ввели невалидные данные, ";
    }
}
