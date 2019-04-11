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
            Set<String> hashtags = propertiesDao.getHestagFromProperties().keySet();
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
            Set<String> hashtags = propertiesDao.getHestagFromProperties().keySet();
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

    public List<ViewMap> addHestag(String userName) {
        try {
            ArrayList<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.addHestagInProperties(userName)));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    public List<ViewMap> removeHestag(String userName) {
        try {
            ArrayList<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.removeHestagFromProperties(userName)));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    public List<ViewMap> getHestags() {
        try {
            ArrayList<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.getHestagFromProperties()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }
}
