package com.instapopulars.instapopular.groups;

import static com.instapopulars.instapopular.Constant.User.LOGIN;
import static com.instapopulars.instapopular.Constant.User.PASSWORD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GroupsController {

    private final GroupsService groupsService;

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
        groups();
    }

    private void groups() {
//        groupsService.loginOnWebSite(LOGIN, PASSWORD);
//        groupsService.run(300);
    }
}
