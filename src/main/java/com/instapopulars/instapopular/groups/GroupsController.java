package com.instapopulars.instapopular.groups;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GroupsController {

    private final GroupsService groupsService;

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
        WebDriver webDriver = getDriver();
    }

    public WebDriver getDriver(){
        return groupsService.getDriver();
    }
}
