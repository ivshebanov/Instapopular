package com.instapopulars.instapopular.unsubscribe;

import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.model.ViewMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnsubscribeService {

    @Autowired
    private UnsubscribeDao unsubscribeDao;

    @Autowired
    private PropertiesDao propertiesDao;

    public void unsubscribe(int count) {
        try {
            unsubscribeDao.unsubscribeFromUsers(count, propertiesDao.getDoNotUnsubscribe());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            unsubscribeDao.quitDriver();
        }
    }

    public void unsubscribeFromUnsigned(int count) {
        try {
            unsubscribeDao.unsubscribeFromUsers(count, propertiesDao.getDoNotUnsubscribe());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            unsubscribeDao.quitDriver();
        }
    }

    public boolean loginOnWebSite(String login, String password) {
        try {
            unsubscribeDao.initDriver();
            return unsubscribeDao.loginOnWebSite(login, password);
        } catch (Exception e) {
            e.printStackTrace();
            unsubscribeDao.quitDriver();
        }
        return false;
    }

    public List<ViewMap> addDoNotUnsubscribeUser(String userName) {
        try {
            List<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.addDoNotUnsubscribe(userName, "")));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    public List<ViewMap> removeDoNotUnsubscribeUser(String userName) {
        try {
            List<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.removeDoNotUnsubscribe(userName)));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    public List<ViewMap> getDoNotUnsubscribeUser() {
        try {
            List<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.getDoNotUnsubscribe()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }
}
