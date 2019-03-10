package com.instapopulars.instapopular.hashtag;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashtagService {

    private final HashtagDao hashtagDao;

    @Autowired
    public HashtagService(HashtagDao hashtagDao) {
        this.hashtagDao = hashtagDao;
    }

    public void topPublications(Action action) {
        try {
            Set<String> hashtags = hashtagDao.getHestagFromProperties();
            for (String hashtag : hashtags) {
                hashtagDao.topPublications(hashtag, action);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            hashtagDao.quitDriver();
        }
    }

    public void newPublications(Action action, int countPhoto) {
        try {
            Set<String> hashtags = hashtagDao.getHestagFromProperties();
            for (String hashtag : hashtags) {
                hashtagDao.newPublications(action, countPhoto, hashtag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            hashtagDao.quitDriver();
        }
    }

    public boolean loginOnWebSite(String login, String password) {
        try {
            hashtagDao.initDriver();
            return hashtagDao.loginOnWebSite(login, password);
        } catch (Exception e) {
            e.printStackTrace();
            hashtagDao.quitDriver();
        }
        return false;
    }
}
