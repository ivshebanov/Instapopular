package com.instapopulars.instapopular.DAO;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.instapopulars.instapopular.Constant.Attribute.SRC_MAIN_RESOURCES;
import static com.instapopulars.instapopular.Constant.Attribute.TARGET_CLASSES;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.*;
import static java.util.Objects.requireNonNull;

@Repository
public class PropertiesDao implements IntapopularDAO {

    private static final String HESHTEG_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(HASHTAGS)).getPath());
    private static final String GROUPS_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(GROUPS)).getPath());
    private static final String DO_NOT_UNSUBSCRIBE_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(DO_NOT_UNSUBSCRIBE)).getPath());
    private static final String MY_PHOTO_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(MY_PHOTO)).getPath());
    private static final String PHOTO_ANALYSIS_RESULTS_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(PHOTO_ANALYSIS_RESULTS)).getPath());

    @Override
    public Map<String, Integer> getHestags() throws IOException {
        return getMapFromProperties(getProperties(HESHTEG_PATH));
    }

    @Override
    public Map<String, Integer> getGroups() throws IOException {
        return getMapFromProperties(getProperties(GROUPS_PATH));
    }

    @Override
    public Map<String, Integer> getDoNotUnsubscribe() throws IOException {
        return getMapFromProperties(getProperties(DO_NOT_UNSUBSCRIBE_PATH));
    }

    @Override
    public Map<String, Integer> getMyPhoto() throws IOException {
        return getMapFromProperties(getProperties(MY_PHOTO_PATH));
    }

    @Override
    public Map<String, Integer> getPhotoAnalysisResults() throws IOException {
        return getMapFromProperties(getProperties(PHOTO_ANALYSIS_RESULTS_PATH));
    }

    //------

    @Override
    public void addHestag(String hastag) throws IOException {
        addProperties(HESHTEG_PATH, hastag, "0");
    }

    @Override
    public void addGroup(String group) throws IOException {
        addProperties(GROUPS_PATH, group, "0");
    }

    @Override
    public void addDoNotUnsubscribe(String userName, String countLike) throws IOException {
        addProperties(DO_NOT_UNSUBSCRIBE_PATH, userName, countLike);
    }

    @Override
    public void addMyPhoto(String key, String value) throws IOException {
        addProperties(MY_PHOTO_PATH, key, value);
    }

    @Override
    public void addMyPhotos(Map<String, Integer> photos) throws IOException {
        for (Map.Entry<String, Integer> entry : photos.entrySet()) {
            addMyPhoto(entry.getKey(), String.valueOf(entry.getValue()));
        }
    }

    @Override
    public void addPhotoAnalysisResults(Map<String, Integer> photos) throws IOException {
        for (Map.Entry<String, Integer> entry : photos.entrySet()) {
            addProperties(PHOTO_ANALYSIS_RESULTS_PATH, entry.getKey(), String.valueOf(entry.getValue()));
        }
    }

    //------

    @Override
    public void removeHestag(String hastag) throws IOException {
        removeProperties(HESHTEG_PATH, hastag);
    }

    @Override
    public void removeGroup(String group) throws IOException {
        removeProperties(GROUPS_PATH, group);
    }

    @Override
    public void removeDoNotUnsubscribe(String userName) throws IOException {
        removeProperties(DO_NOT_UNSUBSCRIBE_PATH, userName);
    }

    @Override
    public void removeMyPhoto(String photo) throws IOException {
        removeProperties(MY_PHOTO_PATH, photo);
    }

    //------

    private Properties getProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(new File(path)));
        return properties;
    }

    private void addProperties(String path, String key, String value) throws IOException {
        File file = new File(path);
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        properties.put(key, value);
        properties.store(new FileWriter(file), null);
    }

    private void removeProperties(String path, String prop) throws IOException {
        File file = new File(path);
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        properties.remove(prop);
        properties.store(new FileWriter(file), null);
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