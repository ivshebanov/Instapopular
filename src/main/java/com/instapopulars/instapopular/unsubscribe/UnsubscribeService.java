package com.instapopulars.instapopular.unsubscribe;

import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.model.View;
import java.io.IOException;
import static java.util.Collections.emptySet;
import java.util.Set;
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
            unsubscribeDao.unsubscribeFromUsers(count, unsubscribeDao.getAllSubscribers());
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

    public Set<View> addDoNotUnsubscribeUser(String userName) {
        try {
            return propertiesDao.revertView(propertiesDao.addDoNotUnsubscribe(userName));
        } catch (IOException e) {
            return emptySet();
        }
    }

    public Set<View> removeDoNotUnsubscribeUser(String userName) {
        try {
            return propertiesDao.revertView(propertiesDao.removeDoNotUnsubscribe(userName));
        } catch (IOException e) {
            return emptySet();
        }
    }

    public Set<View> getDoNotUnsubscribeUser() {
        try {
            return propertiesDao.revertView(propertiesDao.getDoNotUnsubscribe());
        } catch (IOException e) {
            return emptySet();
        }
    }
}
