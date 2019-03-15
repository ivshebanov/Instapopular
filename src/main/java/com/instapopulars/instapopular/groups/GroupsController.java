package com.instapopulars.instapopular.groups;

import static com.instapopulars.instapopular.Constant.User.LOGIN;
import static com.instapopulars.instapopular.Constant.User.PASSWORD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "groups")
public class GroupsController {

    private final GroupsService groupsService;

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @RequestMapping(value = "/group", method = {GET})
    private void groups() {
        groupsService.loginOnWebSite(LOGIN, PASSWORD);
        groupsService.subscribeToUsersInGroup(350);
    }
}
