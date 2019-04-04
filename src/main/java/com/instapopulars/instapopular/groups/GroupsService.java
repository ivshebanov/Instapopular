package com.instapopulars.instapopular.groups;

import com.instapopulars.instapopular.Action;
import com.instapopulars.instapopular.DAO.PropertiesDao;
import com.instapopulars.instapopular.model.ViewSet;
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

    public void subscribeToUsersInGroup(int countSubscriptions, Action action) {
        try {
            Set<String> groups = propertiesDao.getGroupsFromProperties();
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

    public Set<ViewSet> addGroup(String userName) {
        try {
            return propertiesDao.revertSetView(propertiesDao.addGroupsInProperties(userName));
        } catch (IOException e) {
            return emptySet();
        }
    }

    public Set<ViewSet> removeGroup(String userName) {
        try {
            return propertiesDao.revertSetView(propertiesDao.removeGroupsFromProperties(userName));
        } catch (IOException e) {
            return emptySet();
        }
    }

    public Set<ViewSet> getGroup() {
        try {
            return propertiesDao.revertSetView(propertiesDao.getGroupsFromProperties());
        } catch (IOException e) {
            return emptySet();
        }
    }
}
