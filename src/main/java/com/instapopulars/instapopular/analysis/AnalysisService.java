package com.instapopulars.instapopular.analysis;

import com.instapopulars.instapopular.DAO.PropertiesDao;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    @Autowired
    private AnalysisDao analysisDao;

    @Autowired
    private PropertiesDao propertiesDao;

    public void run(){
        try {
            analysisDao.analysisPhotos(propertiesDao.getMyPhoto());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
