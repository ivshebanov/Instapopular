package com.instapopulars.instapopular.DAO;

import static com.instapopulars.instapopular.Constant.Attribute.SRC_MAIN_RESOURCES;
import static com.instapopulars.instapopular.Constant.Attribute.TARGET_CLASSES;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.*;
import com.instapopulars.instapopular.model.ViewMap;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Properties;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PropertiesDao {

    private static final String HESHTEG_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(HASHTAGS)).getPath());
    private static final String GROUPS_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(GROUPS)).getPath());
    private static final String DO_NOT_UNSUBSCRIBE_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(DO_NOT_UNSUBSCRIBE)).getPath());
    private static final String MY_PHOTO_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(MY_PHOTO)).getPath());
    private static final String PHOTO_ANALYSIS_RESULTS_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(PHOTO_ANALYSIS_RESULTS)).getPath());

    @Autowired
    private InstagramDao instagramDao;

    public Set<ViewMap> revertMapView(Map<String, Integer> map) {
        HashSet<ViewMap> resultSet = new HashSet<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            resultSet.add(new ViewMap(entry.getKey(), entry.getValue()));
        }
        return resultSet;
    }

    //------

    public Map<String, Integer> getHestagFromProperties() throws IOException {
        return getMapFromProperties(getProperties(HESHTEG_PATH));
    }

    public Map<String, Integer> getGroupsFromProperties() throws IOException {
        return getMapFromProperties(getProperties(GROUPS_PATH));
    }

    public Map<String, Integer> getDoNotUnsubscribe() throws IOException {
        return getMapFromProperties(getProperties(DO_NOT_UNSUBSCRIBE_PATH));
    }

    public Map<String, Integer> getMyPhoto() throws IOException {
        return getMapFromProperties(getProperties(MY_PHOTO_PATH));
    }

    public Map<String, Integer> getPhotoAnalysisResults() throws IOException {
        return getMapFromProperties(getProperties(PHOTO_ANALYSIS_RESULTS_PATH));
    }

    //------

    public Map<String, Integer> addHestagInProperties(String hastag) throws IOException {
        return getMapFromProperties(addProperties(HESHTEG_PATH, hastag, "0"));
    }

    public Map<String, Integer> addGroupsInProperties(String group) throws IOException {
        return getMapFromProperties(addProperties(GROUPS_PATH, group, "0"));
    }

    public Map<String, Integer> addDoNotUnsubscribe(String userName, String countLike) throws IOException {
        return getMapFromProperties(addProperties(DO_NOT_UNSUBSCRIBE_PATH, userName, countLike));
    }

    public Map<String, Integer> addMyPhoto(String key, String value) throws IOException {
        return getMapFromProperties(addProperties(MY_PHOTO_PATH, key, value));
    }

    public void addMyPhotos(Map<String, Integer> photos) throws IOException {
        for (Map.Entry<String, Integer> entry : photos.entrySet()) {
            addMyPhoto(entry.getKey(), String.valueOf(entry.getValue()));
        }
        getMyPhoto();
    }

    public void addPhotoAnalysisResults(Map<String, Integer> photos) throws IOException {
        for (Map.Entry<String, Integer> entry : photos.entrySet()) {
            getMapFromProperties(addProperties(PHOTO_ANALYSIS_RESULTS_PATH, entry.getKey(), String.valueOf(entry.getValue())));
        }
        getMyPhoto();
    }

    //------

    public Map<String, Integer> removeHestagFromProperties(String hastag) throws IOException {
        return getMapFromProperties(removeProperties(HESHTEG_PATH, hastag));
    }

    public Map<String, Integer> removeGroupsFromProperties(String group) throws IOException {
        return getMapFromProperties(removeProperties(GROUPS_PATH, group));
    }

    public Map<String, Integer> removeDoNotUnsubscribe(String userName) throws IOException {
        return getMapFromProperties(removeProperties(DO_NOT_UNSUBSCRIBE_PATH, userName));
    }

    public Map<String, Integer> removeMyPhoto(String photo) throws IOException {
        return getMapFromProperties(removeProperties(MY_PHOTO_PATH, photo));
    }

    //------

    private Properties getProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(new File(path)));
        return properties;
    }

    private Properties addProperties(String path, String key, String value) throws IOException {
        File file = new File(path);
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        properties.put(key, value);
        properties.store(new FileWriter(file), null);
        return properties;
    }

    private Properties removeProperties(String path, String prop) throws IOException {
        File file = new File(path);
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        properties.remove(prop);
        properties.store(new FileWriter(file), null);
        return properties;
    }

    private Map<String, Integer> getMapFromProperties(Properties properties) {
        Map<String, Integer> resultMap = new HashMap<>();
        for (String name : properties.stringPropertyNames()) {
            String value = properties.getProperty(name);
            if (value != null && value.length() != 0) {
                resultMap.put(name, Integer.parseInt(value));
                continue;
            }
            resultMap.put(name, 0);
        }
        return resultMap;
    }

    private static String convertPath(String path) {
        path = path.replace(TARGET_CLASSES, SRC_MAIN_RESOURCES);
        return path;
    }
}