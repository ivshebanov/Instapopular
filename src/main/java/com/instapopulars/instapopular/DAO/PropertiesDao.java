package com.instapopulars.instapopular.DAO;

import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.DO_NOT_UNSUBSCRIBE;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.GROUPS;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.HASHTAGS;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    @Autowired
    private InstagramDao instagramDao;

    private static String convertPath(String path) {
        path = path.replace("target/classes", "src/main/resources");
        return path;
    }

    public Set<String> getHestagFromProperties() throws IOException {
        return getProperties(HESHTEG_PATH);
    }

    public Set<String> getGroupsFromProperties() throws IOException {
        return getProperties(GROUPS_PATH);
    }

    public Set<String> getDoNotUnsubscribe() throws IOException {
        return getProperties(DO_NOT_UNSUBSCRIBE_PATH);
    }

    public Set<String> addHestagInProperties(String hastag) throws IOException {
        return addProperties(HESHTEG_PATH, hastag);
    }

    public Set<String> addGroupsInProperties(String group) throws IOException {
        return addProperties(GROUPS_PATH, group);
    }

    public Set<String> addDoNotUnsubscribe(String userName) throws IOException {
        return addProperties(DO_NOT_UNSUBSCRIBE_PATH, userName);
    }

    public Set<String> removeHestagFromProperties(String hastag) throws IOException {
        return removeProperties(HESHTEG_PATH, hastag);
    }

    public Set<String> removeGroupsFromProperties(String hastag) throws IOException {
        return removeProperties(GROUPS_PATH, hastag);
    }

    public Set<String> removeDoNotUnsubscribe(String hastag) throws IOException {
        return removeProperties(DO_NOT_UNSUBSCRIBE_PATH, hastag);
    }

    private Set<String> getProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(new File(path)));
        return properties.stringPropertyNames();
    }

    private Set<String> addProperties(String path, String prop) throws IOException {
        File file = new File(path);
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        properties.put(prop, "");
        properties.store(new FileWriter(file), null);
        return properties.stringPropertyNames();
    }

    private Set<String> removeProperties(String path, String prop) throws IOException {
        File file = new File(path);
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        properties.remove(prop);
        properties.store(new FileWriter(file), null);
        return properties.stringPropertyNames();
    }
}