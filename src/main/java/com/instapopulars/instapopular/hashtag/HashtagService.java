package com.instapopulars.instapopular.hashtag;

import com.instapopulars.instapopular.DAO.PropertiesDao;
import java.io.IOException;
import static java.util.Collections.emptySet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashtagService {

    @Autowired
    private HashtagDao hashtagDao;

    @Autowired
    private PropertiesDao propertiesDao;

    public void topPublications(Action action) {
        try {
            Set<String> hashtags = propertiesDao.getHestagFromProperties();
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
            Set<String> hashtags = propertiesDao.getHestagFromProperties();
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

    public Set<String> addHestag(String userName) {
        try {
            return propertiesDao.addHestagInProperties(userName);
        } catch (IOException e) {
            return emptySet();
        }
    }

    public Set<String> removeHestag(String userName) {
        try {
            return propertiesDao.removeHestagFromProperties(userName);
        } catch (IOException e) {
            return emptySet();
        }
    }
}
