package ru.instapopular.service;

import ru.instapopular.Constant;
import ru.instapopular.Utils;
import ru.instapopular.model.MyGroup;
import ru.instapopular.model.Photo;
import ru.instapopular.view.ViewMap;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Service
public class InstagramService {

    private static final Logger logger = LoggerFactory.getLogger(InstagramService.class);
    private static final String CHROME_DRIVER_PATH = requireNonNull(ClassLoader.getSystemResource(Utils.getChromeDriver())).getPath();

    private WebDriver driver;
    private String login;

    public void initDriver() {
        logger.info(System.getProperty(Constant.Utils.OS_NAME));
        logger.info(String.format(Constant.DriverConstant.MessageConstants.GET_DRIVER, Calendar.getInstance()));
        initChromeDriver();
    }

    public void quitDriver() {
        logger.info(String.format(Constant.DriverConstant.MessageConstants.QUIT_DRIVER, Calendar.getInstance()));
        driver.quit();
    }

    private void initChromeDriver() {
        logger.debug(String.format(Constant.DriverConstant.MessageConstants.SET_PROPERTY, Constant.DriverConstant.Driver.Chrome.WEBDRIVER_CHROME_DRIVER, CHROME_DRIVER_PATH));
        System.setProperty(Constant.DriverConstant.Driver.Chrome.WEBDRIVER_CHROME_DRIVER, CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1400, 900));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
    }

    public void loginOnWebSite(String login, String password) {
        logger.info(String.format(Constant.InstagramConstant.MessageConstants.LOGIN_ON_WEB_SITE, login, password));
        if (login == null || login.length() == 0 || password == null || password.length() == 0) {
            return;
        }
        this.login = login;
        openUrl(Constant.LinkToInstagram.LOGIN_PAGE);
        getWebElement(60, Constant.InstagramConstant.Xpath.LOGIN_USERNAME_INPUT).sendKeys(login);
        getWebElement(60, Constant.InstagramConstant.Xpath.LOGIN_PASSWORD_INPUT).sendKeys(password);
        try {
            getWebElement(20, Constant.InstagramConstant.Xpath.LOGIN_BUTTON_4).click();
        } catch (TimeoutException ex) {
            logger.error(String.format(Constant.InstagramConstant.MessageConstants.DID_NOT_FIND_THE_BUTTON, Constant.InstagramConstant.Xpath.LOGIN_BUTTON_4, Constant.InstagramConstant.Xpath.LOGIN_BUTTON_5));
            getWebElement(20, Constant.InstagramConstant.Xpath.LOGIN_BUTTON_5).click();
        }
    }

    public boolean openHomePage() {
        if (!driver.getCurrentUrl().equalsIgnoreCase(String.format(Constant.LinkToInstagram.HOME_PAGE, login))) {
            openUrl(String.format(Constant.LinkToInstagram.HOME_PAGE, login));
        }
        String accountName = getWebElement(60, Constant.UnsubscribeConstant.Xpath.ACCOUNT_NAME).getText();
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
                scrollElementSubscriptions(String.format(Constant.UnsubscribeConstant.Xpath.SCROLL, i));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void scrollOpenLikeUser(WebElement countWebElement) {
        try {
            ((JavascriptExecutor) driver).executeScript(Constant.InstagramConstant.Script.SCROLL_INTO_VIEW, countWebElement);
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
        ((JavascriptExecutor) driver).executeScript(Constant.GroupsConstant.Script.WINDOW_OPEN);
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
                getWebElement(60, Constant.InstagramConstant.Xpath.SET_LIKE).click();
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
                getWebElement(60, Constant.InstagramConstant.Xpath.SUBSCRIBE).click();
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
                getWebElement(60, Constant.InstagramConstant.Xpath.SET_LIKE).click();
            }
            if (isNotSubscribe()) {
                getWebElement(60, Constant.InstagramConstant.Xpath.SUBSCRIBE).click();
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
        String ariaLabel = getWebElement(60, Constant.InstagramConstant.Xpath.IS_ACTIVE_LIKE).getAttribute(Constant.Attribute.ARIA_LABEL);
        return !Constant.Attribute.I_DO_NOT_LIKE.equalsIgnoreCase(ariaLabel);
    }

    private boolean isNotSubscribe() {
        String ariaLabel = getWebElement(60, Constant.InstagramConstant.Xpath.SUBSCRIBE).getText();
        return !Constant.Attribute.SUBSCRIPTIONS.equalsIgnoreCase(ariaLabel);
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

    public Set<ViewMap> revertMapViewGroup(List<MyGroup> groups) {
        HashSet<ViewMap> resultSet = new HashSet<>();
        ApplicationContext context = new AnnotationConfigApplicationContext(ViewMap.class);

        for (MyGroup group : groups) {
            if (group.isActive()) {
                ViewMap viewMap = context.getBean(ViewMap.class);
                viewMap.setKey(group.getMyGroup());
                viewMap.setValue(1);
                resultSet.add(viewMap);
            }
        }
        return resultSet;
    }

    public Set<ViewMap> revertMapViewPhoto(List<Photo> photos) {
        HashSet<ViewMap> resultSet = new HashSet<>();
        ApplicationContext context = new AnnotationConfigApplicationContext(ViewMap.class);

        for (Photo photo : photos) {
            if (photo.isActive()) {
                ViewMap viewMap = context.getBean(ViewMap.class);
                viewMap.setKey(photo.getPhoto());
                viewMap.setValue(1);
                resultSet.add(viewMap);
            }
        }
        return resultSet;
    }
}
