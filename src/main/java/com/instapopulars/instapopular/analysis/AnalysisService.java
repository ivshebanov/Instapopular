package com.instapopulars.instapopular.analysis;

import static com.instapopulars.instapopular.Constant.AnalysisConstant.CUT_OF_URL;
import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.model.ViewMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    private static final Logger logger = LogManager.getLogger(AnalysisService.class);

    @Autowired
    private AnalysisDao analysisDao;

    @Autowired
    private PropertiesDao propertiesDao;

    public void loginOnWebSite(String login, String password) {
        try {
            analysisDao.initDriver();
            analysisDao.loginOnWebSite(login, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    void runAnalysis() {
        try {
            Map<String, Integer> user = analysisDao.analysisPhotos(propertiesDao.getMyPhoto());
            propertiesDao.addPhotoAnalysisResults(analysisDao.addNewUser(propertiesDao.getPhotoAnalysisResults(), user));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            analysisDao.quitDriver();
        }
    }

    void addMyPhoto(String userName) {
        try {
            Map<String, Integer> currentMyPhoto = propertiesDao.getMyPhoto();
            if (!currentMyPhoto.containsKey(userName)) {
                propertiesDao.addMyPhoto(userName, String.valueOf(0));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeMyPhoto(String userName) {
        try {
            propertiesDao.removeMyPhoto(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getAnalysisPhoto() {
        try {
            List<ViewMap> resultView = new ArrayList<>(analysisDao.revertMapView(propertiesDao.getPhotoAnalysisResults()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    List<ViewMap> getMyPhoto() {
        try {
            List<ViewMap> resultView = new ArrayList<>(analysisDao.revertMapView(propertiesDao.getMyPhoto()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    String cutOfUrl(String url) {
        Pattern p = Pattern.compile(CUT_OF_URL);
        Matcher matcher = p.matcher(url);
        String result = "";
        while (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }

    void addFirstDoNotUnsubscribe(int count) {
        try {
            Map<String, Integer> photoAnalysisResults = propertiesDao.getPhotoAnalysisResults();
            for (Map.Entry<String, Integer> entry : photoAnalysisResults.entrySet()) {
                if (entry.getValue() >= count) {
                    propertiesDao.addDoNotUnsubscribe(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
