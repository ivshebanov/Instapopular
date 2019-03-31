package com.instapopulars.instapopular.analysis;

import static com.instapopulars.instapopular.Constant.LinkToInstagram.HOME_PAGE;
import com.instapopulars.instapopular.DAO.InstagramDao;
import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.groups.GroupsDao;
import java.io.IOException;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnalysisDao {

    private static final Logger logger = LogManager.getLogger(GroupsDao.class);

    @Autowired
    private InstagramDao instagramDao;

    @Autowired
    private PropertiesDao propertiesDao;

    public Map<String, Integer> analysisPhotos(Map<String, Integer> photos) throws IOException {
        if (photos == null || photos.size() == 0) {
            return null;
        }
        HashMap<String, Integer> resultUser = new HashMap<>();
        HashMap<String, Integer> resultPhotos = new HashMap<>(photos);
        try {
            for (Map.Entry<String, Integer> photo : photos.entrySet()) {
                if (photo.getValue() == 1) {
                    continue;
                }
                if (photo.getValue() == 0) {
                    List<String> activeUsers = getActive(photo.getKey());
                    if (activeUsers == null || activeUsers.size() == 0) {
                        continue;
                    }
                    addActiveUsersToMap(resultUser, activeUsers);
                    resultPhotos.put(photo.getKey(), 1);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        propertiesDao.addMyPhotos(resultPhotos);
        return resultUser;
    }

    private List<String> getActive(String urlPhoto) {
        if (urlPhoto == null || urlPhoto.length() == 0) {
            return null;
        }
        ArrayList<String> resultActiveUser = new ArrayList<>();
        String windowHandlePage = instagramDao.openUrl(format(HOME_PAGE, urlPhoto));

        return resultActiveUser;
    }

    private void addActiveUsersToMap(Map<String, Integer> mapUser, List<String> users) {
        for (String user : users) {
            if (mapUser.containsKey(user)) {
                mapUser.put(user, mapUser.get(user) + 1);
                continue;
            }
            mapUser.put(user, 1);
        }
    }
}
