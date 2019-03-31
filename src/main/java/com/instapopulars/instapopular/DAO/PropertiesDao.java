package com.instapopulars.instapopular.DAO;

import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.DO_NOT_UNSUBSCRIBE;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.GROUPS;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.HASHTAGS;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.MY_PHOTO;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.PHOTO_ANALYSIS_RESULTS;
import com.instapopulars.instapopular.model.ViewSet;
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

    private static String convertPath(String path) {
        path = path.replace("target/classes", "src/main/resources");
        return path;
    }

    public Set<String> getHestagFromProperties() throws IOException {
        return getNameProperties(HESHTEG_PATH);
    }

    public Set<String> getGroupsFromProperties() throws IOException {
        return getNameProperties(GROUPS_PATH);
    }

    public Set<String> getDoNotUnsubscribe() throws IOException {
        return getNameProperties(DO_NOT_UNSUBSCRIBE_PATH);
    }

    public Map<String, Integer> getMyPhoto() throws IOException {
        return getMapProperties(MY_PHOTO_PATH);
    }

    public Map<String, Integer> getPhotoAnalysisResults() throws IOException {
        return getMapProperties(PHOTO_ANALYSIS_RESULTS_PATH);
    }

    public Set<String> addHestagInProperties(String hastag) throws IOException {
        return addSetProperties(HESHTEG_PATH, hastag);
    }

    public Set<String> addGroupsInProperties(String group) throws IOException {
        return addSetProperties(GROUPS_PATH, group);
    }

    public Set<String> addDoNotUnsubscribe(String userName) throws IOException {
        return addSetProperties(DO_NOT_UNSUBSCRIBE_PATH, userName);
    }

    public Map<String, Integer> addMyPhotos(Map<String, Integer> photos) throws IOException {
        for (Map.Entry<String, Integer> entry : photos.entrySet()) {
            addMapProperties(MY_PHOTO_PATH, entry.getKey(), String.valueOf(entry.getValue()));
        }
        return getMyPhoto();
    }

    public Map<String, Integer> addMyPhoto(String key, String value) throws IOException {
        return addMapProperties(MY_PHOTO_PATH, key, value);
    }

    public Map<String, Integer> addPhotoAnalysisResults(String key, String value) throws IOException {
        return addMapProperties(PHOTO_ANALYSIS_RESULTS_PATH, key, value);
    }

    public Set<String> removeHestagFromProperties(String hastag) throws IOException {
        return removeSetProperties(HESHTEG_PATH, hastag);
    }

    public Set<String> removeGroupsFromProperties(String group) throws IOException {
        return removeSetProperties(GROUPS_PATH, group);
    }

    public Set<String> removeDoNotUnsubscribe(String userName) throws IOException {
        return removeSetProperties(DO_NOT_UNSUBSCRIBE_PATH, userName);
    }

    public Map<String, Integer> removeMyPhoto(String photo) throws IOException {
        return removeMapProperties(MY_PHOTO_PATH, photo);
    }

    public Map<String, Integer> removePhotoAnalysisResults(String userName) throws IOException {
        return removeMapProperties(PHOTO_ANALYSIS_RESULTS_PATH, userName);
    }

    public Set<ViewSet> revertSetView(Set<String> set) {
        Set<ViewSet> resultSet = new HashSet<>();
        for (String str : set) {
            resultSet.add(new ViewSet(str));
        }
        return resultSet;
    }

    public Map<String, String> revertMapView(Map<String, Integer> map) {
        Map<String, String> resultMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            resultMap.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return resultMap;
    }

    private Map<String, Integer> getMapProperties(String path) throws IOException {
        return getMapFromProperties(getProperties(path));
    }

    private Set<String> getNameProperties(String path) throws IOException {
        return getProperties(path).stringPropertyNames();
    }

    private Properties getProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(new File(path)));
        return properties;
    }

    private Map<String, Integer> addMapProperties(String path, String key, String value) throws IOException {
        return getMapFromProperties(addProperties(path, key, value));
    }

    private Set<String> addSetProperties(String path, String key) throws IOException {
        return addProperties(path, key, "").stringPropertyNames();
    }

    private Properties addProperties(String path, String key, String value) throws IOException {
        File file = new File(path);
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        properties.put(key, value);
        properties.store(new FileWriter(file), null);
        return properties;
    }

    private Map<String, Integer> removeMapProperties(String path, String prop) throws IOException {
        return getMapFromProperties(removeProperties(path, prop));
    }

    private Set<String> removeSetProperties(String path, String prop) throws IOException {
        return removeProperties(path, prop).stringPropertyNames();
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
            resultMap.put(name, Integer.parseInt(properties.getProperty(name)));
        }
        return resultMap;
    }
}