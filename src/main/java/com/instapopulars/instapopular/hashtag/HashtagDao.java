package com.instapopulars.instapopular.hashtag;

import com.instapopulars.instapopular.DAO.InstagramDao;
import java.io.IOException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HashtagDao {

    private final InstagramDao instagramDao;

    @Autowired
    public HashtagDao(InstagramDao instagramDao) {
        this.instagramDao = instagramDao;
    }

    public void initDriver() {
        instagramDao.initDriver();
    }

    public void quitDriver() {
        instagramDao.quitDriver();
    }

    public boolean loginOnWebSite(String login, String password) {
        return instagramDao.loginOnWebSite(login, password);
    }

    public Set<String> getHestagFromProperties() throws IOException {
        return instagramDao.getHestagFromProperties();
    }
}
