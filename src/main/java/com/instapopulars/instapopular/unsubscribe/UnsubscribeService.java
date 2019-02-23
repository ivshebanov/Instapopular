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

    public void run() {
        try {
            unsubscribeDao.initDriver();
            unsubscribeDao.loginOnWebSite("lilka.lily.1", "Sxsblpwiwn");
            unsubscribeDao.unsubscribeFromUsers(400, null);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
            unsubscribeDao.quitDriver();
        }

    }
}
