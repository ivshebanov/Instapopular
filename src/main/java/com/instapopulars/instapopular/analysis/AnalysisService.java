package com.instapopulars.instapopular.analysis;

import com.instapopulars.instapopular.DAO.PropertiesDao;
import java.io.IOException;
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
        } finally {
            analysisDao.quitDriver();
        }
        return false;
    }

    public void runAnalysis() {
        try {
            Map<String, Integer> user = analysisDao.analysisPhotos(propertiesDao.getMyPhoto());
            propertiesDao.addPhotoAnalysisResults(analysisDao.addNewUser(propertiesDao.getPhotoAnalysisResults(), user));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
