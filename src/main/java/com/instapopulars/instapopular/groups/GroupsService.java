package com.instapopulars.instapopular.groups;

import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.model.View;
import java.io.IOException;
import static java.util.Collections.emptySet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupsService {

    @Autowired
    private GroupsDao groupsDao;

    @Autowired
    private PropertiesDao propertiesDao;

    public void initDriver() {
        groupsDao.initDriver();
    }

    public void subscribeToUsersInGroup(int countSubscriptions) {
        try {
            Set<String> groups = propertiesDao.getGroupsFromProperties();
            for (String urlGroup : groups) {
                groupsDao.subscribeToUsersInGroup(urlGroup, countSubscriptions);
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            groupsDao.quitDriver();
        }
    }

    public boolean loginOnWebSite(String login, String password) {
        try {
            groupsDao.initDriver();
            return groupsDao.loginOnWebSite(login, password);
        } catch (Exception e) {
            e.printStackTrace();
            groupsDao.quitDriver();
        }
        return false;
    }

    public Set<View> addGroup(String userName) {
        try {
            return propertiesDao.revertView(propertiesDao.addGroupsInProperties(userName));
        } catch (IOException e) {
            return emptySet();
        }
    }

    public Set<View> removeGroup(String userName) {
        try {
            return propertiesDao.revertView(propertiesDao.removeGroupsFromProperties(userName));
        } catch (IOException e) {
            return emptySet();
        }
    }

    public Set<View> getGroup() {
        try {
            return propertiesDao.revertView(propertiesDao.getGroupsFromProperties());
        } catch (IOException e) {
            return emptySet();
        }
    }
}
