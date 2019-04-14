package com.instapopulars.instapopular.unsubscribe;

import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.model.ViewMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnsubscribeService {

    private static final Logger logger = LogManager.getLogger(UnsubscribeService.class);

    @Autowired
    private UnsubscribeDao unsubscribeDao;

    @Autowired
    private PropertiesDao propertiesDao;

    void unsubscribe(int count) {
        try {
            unsubscribeDao.unsubscribeFromUsers(count, propertiesDao.getDoNotUnsubscribe().keySet());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            unsubscribeDao.quitDriver();
        }
    }

    void loginOnWebSite(String login, String password) {
        try {
            unsubscribeDao.initDriver();
            unsubscribeDao.loginOnWebSite(login, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            unsubscribeDao.quitDriver();
        }
    }

    void addDoNotUnsubscribeUser(String userName) {
        try {
            propertiesDao.addDoNotUnsubscribe(userName, "");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeDoNotUnsubscribeUser(String userName) {
        try {
            propertiesDao.removeDoNotUnsubscribe(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getDoNotUnsubscribeUser() {
        try {
            List<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.getDoNotUnsubscribe()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }
}
