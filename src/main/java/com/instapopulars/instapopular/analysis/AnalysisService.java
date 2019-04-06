package com.instapopulars.instapopular.analysis;

import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.model.ViewMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    @Autowired
    private AnalysisDao analysisDao;

    @Autowired
    private PropertiesDao propertiesDao;

    public boolean loginOnWebSite(String login, String password) {
        try {
            analysisDao.initDriver();
            return analysisDao.loginOnWebSite(login, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void runAnalysis() {
        try {
            Map<String, Integer> user = analysisDao.analysisPhotos(propertiesDao.getMyPhoto());
            propertiesDao.addPhotoAnalysisResults(analysisDao.addNewUser(propertiesDao.getPhotoAnalysisResults(), user));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            analysisDao.quitDriver();
        }
    }

    public List<ViewMap> addMyPhoto(String userName) {
        try {
            ArrayList<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.addMyPhoto(userName, String.valueOf(0))));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    public List<ViewMap> removeMyPhoto(String userName) {
        try {
            ArrayList<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.removeMyPhoto(userName)));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    public List<ViewMap> getAnalysisPhoto() {
        try {
            List<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.getPhotoAnalysisResults()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    public List<ViewMap> getMyPhoto() {
        try {
            List<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.getMyPhoto()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }
}
