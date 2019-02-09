package com.instapopulars.instapopular.DAO;

import static com.instapopulars.instapopular.Constant.DriverConstant.Driver.CHROME_DRIVER;
import static com.instapopulars.instapopular.Constant.DriverConstant.Driver.WEBDRIVER;
import static com.instapopulars.instapopular.Constant.DriverConstant.Driver.WEBDRIVER_CHROME_DRIVER;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.GET_DRIVER;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.GET_GROUPS;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.GET_HESTAG_FROM_PROPERTIES;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.GET_LOGIN_AND_PASSWORD_FROM_PROPERTIES;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.QUIT_DRIVER;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.SET_PROPERTY;
import static com.instapopulars.instapopular.Constant.DriverConstant.propertiesName.ACCOUNT;
import static com.instapopulars.instapopular.Constant.DriverConstant.propertiesName.GROUPS;
import static com.instapopulars.instapopular.Constant.DriverConstant.propertiesName.HASHTAGS;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.String.format;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DriverDao {
    private static final Logger logger = LoggerFactory.getLogger(DriverDao.class);
    private static ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    private static final String HESHTEG_PATH = requireNonNull(classloader.getResource(HASHTAGS)).getPath();
    private static final String ACCOUNT_PATH = requireNonNull(classloader.getResource(ACCOUNT)).getPath();
    private static final String GROUPS_PATH = requireNonNull(classloader.getResource(GROUPS)).getPath();
    private static ResourceBundle resWebdriver = ResourceBundle.getBundle(WEBDRIVER, Locale.ENGLISH);

    private WebDriver driver;

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

    public Set<String> getGroups() throws IOException {
        logger.info(GET_GROUPS);
        Set<String> resultGroups = new LinkedHashSet<>();
        Properties properties = new Properties();
        properties.load(new FileReader(new File(GROUPS_PATH)));
        for (String key : properties.stringPropertyNames()) {
            resultGroups.add(properties.getProperty(key));
        }
        return resultGroups;
    }

    public synchronized WebDriver getDriver() {
        logger.info(format(GET_DRIVER, Calendar.getInstance()));
        init();
        return driver;
    }

    public synchronized void quitDriver() {
        logger.info(format(QUIT_DRIVER, Calendar.getInstance()));
        driver.quit();
    }

    private void init() {
        String key = resWebdriver.getString(WEBDRIVER_CHROME_DRIVER);
        String value = requireNonNull(classloader.getResource(resWebdriver.getString(CHROME_DRIVER))).getPath();
        logger.debug(format(SET_PROPERTY, key, value));
        System.setProperty(key, value);
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1400, 900));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
    }
}
