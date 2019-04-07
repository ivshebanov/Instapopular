package com.instapopulars.instapopular.analysis;

import static com.instapopulars.instapopular.Constant.AnalysisConstant.CUT_OF_URL;
import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.model.ViewMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public String cutOfUrl(String url) {
        Pattern p = Pattern.compile(CUT_OF_URL);
        Matcher matcher = p.matcher(url);
        String result = "";
        while (matcher.find()) {
            result = matcher.group();
        }
        result = result.split("/")[0] + "/"+ result.split("/")[1];
        return result;
    }

    public void addFirstDoNotUnsubscribe(int count){
        try {
            List<ViewMap> photoAnalysisResults = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.getPhotoAnalysisResults()));
            Collections.sort(photoAnalysisResults);
            for (ViewMap entry : photoAnalysisResults){
                if (entry.getValue() >= count){
                    propertiesDao.addDoNotUnsubscribe(entry.getKey());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
