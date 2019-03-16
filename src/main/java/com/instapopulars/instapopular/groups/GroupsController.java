package com.instapopulars.instapopular.groups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

    @GetMapping("/groups")
    public String groups() {
        return "groups";
    }

    @PostMapping("/group")
    public void group(@RequestParam(name = "login", defaultValue = "lilka.lily.1") String login,
                        @RequestParam(name = "password", defaultValue = "Sxsblpwiwn") String password,
                        @RequestParam(name = "countSubscriptions", defaultValue = "350") int countSubscriptions) {

        if (login == null || login.length() <= 0 || password == null || password.length() <= 0 || countSubscriptions < 0) {
            return;
        }
        groupsService.loginOnWebSite(login, password);
        groupsService.subscribeToUsersInGroup(countSubscriptions);
    }
}
