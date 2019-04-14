package com.instapopulars.instapopular.groups;

import com.instapopulars.instapopular.Action;
import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.model.ViewMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupsService {

    private static final Logger logger = LogManager.getLogger(GroupsService.class);

    @Autowired
    private GroupsDao groupsDao;

    @Autowired
    private PropertiesDao propertiesDao;

    void subscribeToUsersInGroup(int countSubscriptions, Action action) {
        try {
            Set<String> groups = propertiesDao.getGroupsFromProperties().keySet();
            for (String urlGroup : groups) {
                groupsDao.subscribeToUsersInGroup(urlGroup, countSubscriptions, action);
            }
            System.out.println();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            groupsDao.quitDriver();
        }
    }

    void loginOnWebSite(String login, String password) {
        try {
            groupsDao.initDriver();
            groupsDao.loginOnWebSite(login, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            groupsDao.quitDriver();
        }
    }

    void addGroup(String userName) {
        try {
            propertiesDao.addGroupsInProperties(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    void removeGroup(String userName) {
        try {
            propertiesDao.removeGroupsFromProperties(userName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    List<ViewMap> getGroup() {
        try {
            ArrayList<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.getGroupsFromProperties()));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }
}
