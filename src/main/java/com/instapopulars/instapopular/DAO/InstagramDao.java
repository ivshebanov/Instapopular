package com.instapopulars.instapopular.DAO;

import static com.instapopulars.instapopular.Constant.Attribute.ARIA_LABEL;
import static com.instapopulars.instapopular.Constant.Attribute.I_DO_NOT_LIKE;
import static com.instapopulars.instapopular.Constant.Attribute.SUBSCRIPTIONS;
import static com.instapopulars.instapopular.Constant.DriverConstant.Driver.Chrome.WEBDRIVER_CHROME_DRIVER;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.*;
import static com.instapopulars.instapopular.Constant.GroupsConstant.Script.WINDOW_OPEN;
import static com.instapopulars.instapopular.Constant.InstagramConstant.MessageConstants.DID_NOT_FIND_THE_BUTTON;
import static com.instapopulars.instapopular.Constant.InstagramConstant.MessageConstants.LOGIN_ON_WEB_SITE;
import static com.instapopulars.instapopular.Constant.InstagramConstant.Script.SCROLL_INTO_VIEW;
import static com.instapopulars.instapopular.Constant.InstagramConstant.Xpath.*;
import static com.instapopulars.instapopular.Constant.LinkToInstagram.HOME_PAGE;
import static com.instapopulars.instapopular.Constant.LinkToInstagram.LOGIN_PAGE;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.ACCOUNT_NAME;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.SCROLL;
import static com.instapopulars.instapopular.Constant.Utils.OS_NAME;
import static com.instapopulars.instapopular.Utils.getChromeDriver;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.instapopulars.instapopular.model.ViewMap;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class InstagramDao {

    private static final Logger logger = LoggerFactory.getLogger(InstagramDao.class);
    private static final String CHROME_DRIVER_PATH = requireNonNull(ClassLoader.getSystemResource(getChromeDriver())).getPath();

    private WebDriver driver;
    private String login;

    public void initDriver() {
        logger.info(System.getProperty(OS_NAME));
        logger.info(format(GET_DRIVER, Calendar.getInstance()));
        initChromeDriver();
    }

    public void quitDriver() {
        logger.info(format(QUIT_DRIVER, Calendar.getInstance()));
        driver.quit();
    }

    private void initChromeDriver() {
        logger.debug(format(SET_PROPERTY, WEBDRIVER_CHROME_DRIVER, CHROME_DRIVER_PATH));
        System.setProperty(WEBDRIVER_CHROME_DRIVER, CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1400, 900));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
    }

    public void loginOnWebSite(String login, String password) {
        logger.info(format(LOGIN_ON_WEB_SITE, login, password));
        if (login == null || login.length() == 0 || password == null || password.length() == 0) {
            return;
        }
        this.login = login;
        openUrl(LOGIN_PAGE);
        getWebElement(60, LOGIN_USERNAME_INPUT).sendKeys(login);
        getWebElement(60, LOGIN_PASSWORD_INPUT).sendKeys(password);
        try {
            getWebElement(20, LOGIN_BUTTON_4).click();
        } catch (TimeoutException ex) {
            logger.error(format(DID_NOT_FIND_THE_BUTTON, LOGIN_BUTTON_4, LOGIN_BUTTON_5));
            getWebElement(20, LOGIN_BUTTON_5).click();
        }
        String accountName = getWebElement(120, CHECK_LOGIN_BY_NAME).getText();
    }

    public boolean openHomePage() {
        if (!driver.getCurrentUrl().equalsIgnoreCase(format(HOME_PAGE, login))) {
            openUrl(format(HOME_PAGE, login));
        }
        String accountName = getWebElement(60, ACCOUNT_NAME).getText();
        return !login.equalsIgnoreCase(accountName);
    }

    public int convertStringToInt(String count) {
        if (count.length() == 1 || count.length() == 2 || count.length() == 3) {
            return Integer.parseInt(count);
        }
        if (count.length() == 5) {
            return Integer.parseInt(removeCharAt(count, 1));
        }
        if (count.length() == 6) {//переделать
            return Integer.parseInt(removeCharAt(count, 2));
        }
        if (count.length() == 7) {//переделать
            return Integer.parseInt(removeCharAt(count, 3));
        }
        return 0;
    }

    private String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public void scrollSubscriptions(int currentPosition) {
        for (int i = 1; i <= currentPosition; i++) {
            try {
                scrollElementSubscriptions(format(SCROLL, i));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void scrollOpenLikeUser(WebElement countWebElement) {
        try {
            ((JavascriptExecutor) driver).executeScript(SCROLL_INTO_VIEW, countWebElement);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void scrollElementSubscriptions(String xPathElement) {
        WebElement scroll = getWebElement(60, xPathElement);
        Coordinates coordinate = ((Locatable) scroll).getCoordinates();
        coordinate.onPage();
        coordinate.inViewPort();
    }

    public String openUrl(String url) {
        if (url != null && url.length() != 0) {
            driver.get(url);
        }
        return driver.getWindowHandle();
    }

    public String openUrlNewTab(String url) {
        ((JavascriptExecutor) driver).executeScript(WINDOW_OPEN);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        selectTab(tabs.size() - 1);
        return openUrl(url);
    }

    private void selectTab(int countTab) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        selectTab(tabs.get(countTab));
    }

    public void selectTab(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    public void closeTab(String windowHandle) {
        selectTab(windowHandle);
        driver.close();
    }

    public boolean setLike(String urlPhoto) {
        String currentWindowHandle = driver.getWindowHandle();
        String photoWindowHandle = null;
        boolean checkLike = false;
        try {
            photoWindowHandle = openUrlNewTab(urlPhoto);
            if (isNotActiveLike()) {
                getWebElement(60, SET_LIKE).click();
                checkLike = true;
            }
        } catch (Exception ignored) {

        } finally {
            closeTab(photoWindowHandle);
            selectTab(currentWindowHandle);
        }
        return checkLike;
    }

    public boolean subscribe(String urlPhoto) {
        String currentWindowHandle = driver.getWindowHandle();
        String photoWindowHandle = null;
        boolean checkSubscribe = false;
        try {
            photoWindowHandle = openUrlNewTab(urlPhoto);
            if (isNotSubscribe()) {
                getWebElement(60, SUBSCRIBE).click();
                checkSubscribe = true;
            }
            timeOut(2, 0);
        } catch (Exception ignored) {

        } finally {
            closeTab(photoWindowHandle);
            selectTab(currentWindowHandle);
        }
        return checkSubscribe;
    }

    public boolean setLikeAndSubscribe(String urlPhoto) {
        String currentWindowHandle = driver.getWindowHandle();
        String photoWindowHandle = null;
        boolean checkSubscribe = false;
        try {
            photoWindowHandle = openUrlNewTab(urlPhoto);
            if (isNotActiveLike()) {
                getWebElement(60, SET_LIKE).click();
            }
            if (isNotSubscribe()) {
                getWebElement(60, SUBSCRIBE).click();
                checkSubscribe = true;
            }
            timeOut(2, 0);
        } catch (Exception ignored) {

        } finally {
            closeTab(photoWindowHandle);
            selectTab(currentWindowHandle);
        }
        return checkSubscribe;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    private boolean isNotActiveLike() {
        String ariaLable = getWebElement(60, IS_ACTIVE_LIKE).getAttribute(ARIA_LABEL);
        return !I_DO_NOT_LIKE.equalsIgnoreCase(ariaLable);
    }

    private boolean isNotSubscribe() {
        String ariaLable = getWebElement(60, SUBSCRIBE).getText();
        return !SUBSCRIPTIONS.equalsIgnoreCase(ariaLable);
    }

    public WebElement getWebElement(int timeOutInSeconds, String xpathExpression) {
        return (new WebDriverWait(driver, timeOutInSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
    }

    public List<WebElement> getWebElements(int timeOutInSeconds, String xpathExpression) {
        return (new WebDriverWait(driver, timeOutInSeconds)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathExpression)));
    }

    public void timeOut(int timeOutInSeconds, int dispersionInSeconds) {
        timeOutInSeconds *= 1000;
        dispersionInSeconds *= 1000;
        try {
            Random random = new Random();
            int num = dispersionInSeconds + random.nextInt(timeOutInSeconds - dispersionInSeconds);
            Thread.sleep(num);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Set<ViewMap> revertMapView(Map<String, Integer> map) {
        HashSet<ViewMap> resultSet = new HashSet<>();
        ApplicationContext context = new AnnotationConfigApplicationContext(ViewMap.class);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            ViewMap viewMap = context.getBean(ViewMap.class);
            viewMap.setKey(entry.getKey());
            viewMap.setValue(entry.getValue());
            resultSet.add(viewMap);
        }
        return resultSet;
    }
}
