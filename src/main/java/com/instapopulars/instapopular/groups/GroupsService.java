package com.instapopulars.instapopular.groups;

import java.io.IOException;
import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupsService {

    private final GroupsDao groupsDao;

    @Autowired
    public GroupsService(GroupsDao groupsDao) {
        this.groupsDao = groupsDao;
    }

    public WebDriver initDriver(){
        return groupsDao.initDriver();
    }

    public void run(){
        try {
            groupsDao.initDriver();
            groupsDao.loginOnWebSite("lilka.lily.1", "Sxsblpwiwn");
            Set<String> groups = groupsDao.getGroupsFromProperties();
            for (String urlGroup : groups){
                groupsDao.subscribeToGroupMembers(urlGroup);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
            groupsDao.quitDriver();
        }
    }
}
