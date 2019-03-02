package com.instapopulars.instapopular.DAO;

import static com.instapopulars.instapopular.Constant.DriverConstant.Driver.Chrome.CHROME_DRIVER;
import static com.instapopulars.instapopular.Constant.DriverConstant.Driver.Chrome.WEBDRIVER_CHROME_DRIVER;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.GET_DRIVER;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.GET_GROUPS;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.GET_HESTAG_FROM_PROPERTIES;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.GET_LOGIN_AND_PASSWORD_FROM_PROPERTIES;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.QUIT_DRIVER;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.SET_PROPERTY;
import static com.instapopulars.instapopular.Constant.DriverConstant.propertiesName.ACCOUNT;
import static com.instapopulars.instapopular.Constant.DriverConstant.propertiesName.GROUPS;
import static com.instapopulars.instapopular.Constant.DriverConstant.propertiesName.HASHTAGS;
import static com.instapopulars.instapopular.Constant.GroupsConstant.Script.WINDOW_OPEN;
import static com.instapopulars.instapopular.Constant.InstagramConstant.MessageConstants.I_DO_NOT_LIKE;
import static com.instapopulars.instapopular.Constant.InstagramConstant.MessageConstants.LOGIN_ON_WEB_SITE;
import static com.instapopulars.instapopular.Constant.InstagramConstant.Xpath.ARIA_LABEL;
import static com.instapopulars.instapopular.Constant.InstagramConstant.Xpath.CHECK_LOGIN_BY_NAME;
import static com.instapopulars.instapopular.Constant.InstagramConstant.Xpath.IS_ACTIVE_LIKE;
import static com.instapopulars.instapopular.Constant.InstagramConstant.Xpath.LOGIN_BUTTON;
import static com.instapopulars.instapopular.Constant.InstagramConstant.Xpath.LOGIN_PAGE;
import static com.instapopulars.instapopular.Constant.InstagramConstant.Xpath.LOGIN_PASSWORD_INPUT;
import static com.instapopulars.instapopular.Constant.InstagramConstant.Xpath.LOGIN_USERNAME_INPUT;
import static com.instapopulars.instapopular.Constant.InstagramConstant.Xpath.SET_LIKE;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Link.HOME_PAGE;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.ACCOUNT_NAME;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.SCROLL;
import com.instapopulars.instapopular.model.User;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InstagramDao {
    private static final Logger logger = LoggerFactory.getLogger(InstagramDao.class);
    private static ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    private static final String HESHTEG_PATH = requireNonNull(classloader.getResource(HASHTAGS)).getPath();
    private static final String ACCOUNT_PATH = requireNonNull(classloader.getResource(ACCOUNT)).getPath();
    private static final String GROUPS_PATH = requireNonNull(classloader.getResource(GROUPS)).getPath();
    private static final String CHROME_DRIVER_PATH = requireNonNull(classloader.getResource(CHROME_DRIVER)).getPath();

    private WebDriver driver;
    private String login;

    public synchronized Map<String, String> getLoginAndPasswordFromProperties() throws IOException {
        logger.info(GET_LOGIN_AND_PASSWORD_FROM_PROPERTIES);
        Map<String, String> resultLoginAndPassword = new LinkedHashMap<>();
        Properties properties = new Properties();
        properties.load(new FileReader(new File(ACCOUNT_PATH)));
        for (String key : properties.stringPropertyNames()) {
            resultLoginAndPassword.put(key, properties.getProperty(key));
        }
        return resultLoginAndPassword;
    }

    public synchronized Set<String> getHestagFromProperties() throws IOException {
        logger.info(GET_HESTAG_FROM_PROPERTIES);
        Set<String> resultHeshtegs = new LinkedHashSet<>();
        Properties properties = new Properties();
        properties.load(new FileReader(new File(HESHTEG_PATH)));
        for (String key : properties.stringPropertyNames()) {
            resultHeshtegs.add(properties.getProperty(key));
        }
        return resultHeshtegs;
    }

    public Set<String> getGroupsFromProperties() throws IOException {
        logger.info(GET_GROUPS);
        Set<String> resultGroups = new LinkedHashSet<>();
        Properties properties = new Properties();
        properties.load(new FileReader(new File(GROUPS_PATH)));
        for (String key : properties.stringPropertyNames()) {
            resultGroups.add(properties.getProperty(key));
        }
        return resultGroups;
    }

    public synchronized WebDriver initDriver() {
        logger.info(format(GET_DRIVER, Calendar.getInstance()));
        init();
        return driver;
    }

    public synchronized void quitDriver() {
        logger.info(format(QUIT_DRIVER, Calendar.getInstance()));
        driver.quit();
    }

    private void init() {
        logger.debug(format(SET_PROPERTY, WEBDRIVER_CHROME_DRIVER, CHROME_DRIVER_PATH));
        System.setProperty(WEBDRIVER_CHROME_DRIVER, CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1400, 900));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
    }

    public boolean loginOnWebSite(String login, String password) {
        logger.info(format(LOGIN_ON_WEB_SITE, login, password));
        this.login = login;
        openUrl(LOGIN_PAGE);
        getWebElement(60, LOGIN_USERNAME_INPUT).sendKeys(login);
        getWebElement(60, LOGIN_PASSWORD_INPUT).sendKeys(password);
        getWebElement(60, LOGIN_BUTTON).click();
        String accountName = getWebElement(60, CHECK_LOGIN_BY_NAME).getText();
        return login.equalsIgnoreCase(accountName);
    }

    public boolean openHomePage() {
        if (!driver.getCurrentUrl().equalsIgnoreCase(format(HOME_PAGE, login))) {
            openUrl(format(HOME_PAGE, login));
        }
        String accountName = getWebElement(60, ACCOUNT_NAME).getText();
        return login.equalsIgnoreCase(accountName);
    }

    public int convertStringToInt(String count) {
        if (count.length() == 1 || count.length() == 2 || count.length() == 3) {
            return Integer.parseInt(count);
        }
        if (count.length() == 5) {
            return Integer.parseInt(removeCharAt(count, 1));
        }
        if (count.length() == 6) {
            return Integer.parseInt(removeCharAt(count, 2));
        }
        if (count.length() == 7) {
            return Integer.parseInt(removeCharAt(count, 3));
        }
        return 0;
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

    public void scrollElementSubscriptions(String xPathElement) {
        WebElement scroll = getWebElement(60, xPathElement);
        Coordinates coordinate = ((Locatable) scroll).getCoordinates();
        coordinate.onPage();
        coordinate.inViewPort();
    }

    private static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public User getUserByUrl(String url) {
        User user = new User();
        user.setLinkAccount(url);
        user.setName(genLoginByUrl(url));
        return user;
    }

    public String genLoginByUrl(String url) {
        return url.substring(26, url.length() - 1);
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

    public void selectTab(int countTab) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        selectTab(tabs.get(countTab));
    }

    public void selectTab(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    public void closeTab(int countTab) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        closeTab(tabs.get(countTab));
    }

    public void closeTab(String windowHandle) {
        selectTab(windowHandle);
        driver.close();
    }

    public void setLike(String urlPhoto) {
        String currentWindowHandle = driver.getWindowHandle();
        String photoWindowHandle = null;
        try {
            photoWindowHandle = openUrlNewTab(urlPhoto);
            if (!isActiveLike()) {
                getWebElement(60, SET_LIKE).click();
            }
        } catch (Exception ignored) {

        } finally {
            closeTab(photoWindowHandle);
            selectTab(currentWindowHandle);
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    private boolean isActiveLike() {
        String ariaLable = getWebElement(60, IS_ACTIVE_LIKE).getAttribute(ARIA_LABEL);
        return ariaLable.equalsIgnoreCase(I_DO_NOT_LIKE);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getLogin() {
        return login;
    }

    public WebElement getWebElement(int timeOutlnSeconds, String xpathExpression) {
        return (new WebDriverWait(driver, timeOutlnSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
    }

    public List<WebElement> getWebElements(int timeOutlnSeconds, String xpathExpression) {
        return (new WebDriverWait(driver, timeOutlnSeconds)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathExpression)));

    }
}
