package com.instapopulars.instapopular.unsubscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnsubscribeService {

    private final UnsubscribeDao unsubscribeDao;

    @Autowired
    public UnsubscribeService(UnsubscribeDao unsubscribeDao) {
        this.unsubscribeDao = unsubscribeDao;
    }

    public void unsubscribe(int count) {
        try {
            unsubscribeDao.unsubscribeFromUsers(count, null);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            unsubscribeDao.quitDriver();
        }
    }

    public void unsubscribeFromUnsigned(int count){
        try {
            unsubscribeDao.unsubscribeFromUsers(count, unsubscribeDao.getAllSubscribers());
            System.out.println();
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
        } catch (Exception e){
            e.printStackTrace();
            unsubscribeDao.quitDriver();
        }
        return false;
    }
}
