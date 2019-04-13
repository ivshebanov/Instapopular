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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupsService {

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
            e.printStackTrace();
        } finally {
            groupsDao.quitDriver();
        }
    }

    void loginOnWebSite(String login, String password) {
        try {
            groupsDao.initDriver();
            groupsDao.loginOnWebSite(login, password);
        } catch (Exception e) {
            e.printStackTrace();
            groupsDao.quitDriver();
        }
    }

    List<ViewMap> addGroup(String userName) {
        try {
            ArrayList<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.addGroupsInProperties(userName)));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
        }
    }

    List<ViewMap> removeGroup(String userName) {
        try {
            ArrayList<ViewMap> resultView = new ArrayList<>(propertiesDao.revertMapView(propertiesDao.removeGroupsFromProperties(userName)));
            Collections.sort(resultView);
            return resultView;
        } catch (IOException e) {
            return emptyList();
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
