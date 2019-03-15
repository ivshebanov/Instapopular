package com.instapopulars.instapopular.groups;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupsService {

    private final GroupsDao groupsDao;

    @Autowired
    public GroupsService(GroupsDao groupsDao) {
        this.groupsDao = groupsDao;
    }

    public void initDriver() {
        groupsDao.initDriver();
    }

    public void subscribeToUsersInGroup(int countSubscriptions) {
        try {
            Set<String> groups = groupsDao.getGroupsFromProperties();
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
}
