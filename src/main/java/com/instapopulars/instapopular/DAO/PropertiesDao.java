package com.instapopulars.instapopular.DAO;

import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.DO_NOT_UNSUBSCRIBE_MESSAGE;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.GET_GROUPS;
import static com.instapopulars.instapopular.Constant.DriverConstant.MessageConstants.GET_HESTAG_FROM_PROPERTIES;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.DO_NOT_UNSUBSCRIBE;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.GROUPS;
import static com.instapopulars.instapopular.Constant.DriverConstant.PropertiesName.HASHTAGS;
import com.instapopulars.instapopular.model.User;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.util.Objects.requireNonNull;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PropertiesDao {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesDao.class);
    private static final String HESHTEG_PATH = requireNonNull(ClassLoader.getSystemResource(HASHTAGS)).getPath();
    private static final String GROUPS_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(GROUPS)).getPath());
    private static final String DO_NOT_UNSUBSCRIBE_PATH = convertPath(requireNonNull(ClassLoader.getSystemResource(DO_NOT_UNSUBSCRIBE)).getPath());

    @Autowired
    private InstagramDao instagramDao;

    private static String convertPath(String path) {
        path = path.replace("target/classes", "src/main/resources");
        return path;
    }

    public Set<String> getHestagFromProperties() throws IOException {
        logger.info(GET_HESTAG_FROM_PROPERTIES);
        Properties properties = new Properties();
        properties.load(new FileReader(new File(HESHTEG_PATH)));
        return properties.stringPropertyNames();
    }

    public Set<String> getGroupsFromProperties() throws IOException {
        logger.info(GET_GROUPS);
        Properties properties = new Properties();
        properties.load(new FileReader(new File(GROUPS_PATH)));
        return properties.stringPropertyNames();
    }

    public Set<User> getDoNotUnsubscribe() throws IOException {
        logger.info(DO_NOT_UNSUBSCRIBE_MESSAGE);
        Properties properties = new Properties();
        properties.load(new FileReader(new File(DO_NOT_UNSUBSCRIBE_PATH)));
        return instagramDao.getUsersByUrls(properties.stringPropertyNames());
    }

    public Set<String> addHestagInProperties(String hastag){
        return null;
    }

    public Set<String> addGroupsInProperties(String hastag){
        return null;
    }

    public Set<String> addDoNotUnsubscribe(String hastag){
        return null;
    }

    public Set<String> removeHestagFromProperties(String hastag){
        return null;
    }

    public Set<String> removeGroupsFromProperties(String hastag){
        return null;
    }

    public Set<String> removeDoNotUnsubscribe(String hastag){
        return null;
    }
}
