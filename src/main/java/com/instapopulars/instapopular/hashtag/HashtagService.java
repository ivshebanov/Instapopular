package com.instapopulars.instapopular.hashtag;

import com.instapopulars.instapopular.Action;
import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.model.ViewMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashtagService {

    private static final Logger logger = LogManager.getLogger(HashtagService.class);

    @Autowired
    private HashtagDao hashtagDao;

    @Autowired
    private PropertiesDao propertiesDao;

    void topPublications(Action action) {
        try {
            Set<String> hashtags = propertiesDao.getHestagFromProperties().keySet();
            for (String hashtag : hashtags) {
                hashtagDao.topPublications(hashtag, action);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            hashtagDao.quitDriver();
        }
    }

    public void newPublications(Action action, int countPhoto) {
        try {
            Set<String> hashtags = propertiesDao.getHestagFromProperties().keySet();
            for (String hashtag : hashtags) {
                hashtagDao.newPublications(action, countPhoto, hashtag);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            hashtagDao.quitDriver();
        }
    }

    void loginOnWebSite(String login, String password) {
        try {
            hashtagDao.initDriver();
            hashtagDao.loginOnWebSite(login, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            hashtagDao.quitDriver();
        }
    }

    void addHestag(String userName) {
        try {
            propertiesDao.addHestagInProperties(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeHestag(String userName) {
        try {
            propertiesDao.removeHestagFromProperties(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getHestags() {
        try {
            ArrayList<ViewMap> resultView = new ArrayList<>(hashtagDao.revertMapView(propertiesDao.getHestagFromProperties()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }
}
