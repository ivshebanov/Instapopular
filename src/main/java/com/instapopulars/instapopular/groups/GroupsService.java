package com.instapopulars.instapopular.groups;

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

    public WebDriver getDriver(){
        return groupsDao.getDriver();
    }
}
