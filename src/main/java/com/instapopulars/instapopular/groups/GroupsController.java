package com.instapopulars.instapopular.groups;

import com.instapopulars.instapopular.Action;
import java.util.Map;
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
    public String groups(Map<String, Object> view) {
        view.put("groupView", groupsService.getGroup());
        return "groups";
    }

    @PostMapping("/group")
    public String group(@RequestParam(name = "login") String login,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "countSubscriptions", defaultValue = "350") int countSubscriptions,
                        @RequestParam(name = "action", defaultValue = "SUBSCRIBE_AND_LIKE") Action action,
                        Map<String, Object> view) {

        if (login == null || login.length() <= 0
                || password == null || password.length() <= 0
                || countSubscriptions < 0 || action == null) {
            view.put("groupView", groupsService.getGroup());
            return "groups";
        }
        groupsService.loginOnWebSite(login, password);
        groupsService.subscribeToUsersInGroup(countSubscriptions, action);
        view.put("groupView", groupsService.getGroup());
        return "groups";
    }

    @PostMapping("/addRemove")
    public String addRemove(@RequestParam(name = "addGroup") String add,
                            @RequestParam(name = "removeGroup") String remove,
                            Map<String, Object> view) {

        if ((add != null && add.length() > 0) && (remove != null && remove.length() > 0)) {
            view.put("groupView", groupsService.getGroup());
            return "groups";
        }
        if (add != null && add.length() > 0) {
            view.put("groupView", groupsService.addGroup(add));
            return "groups";
        }
        if (remove != null && remove.length() > 0) {
            view.put("groupView", groupsService.removeGroup(remove));
            return "groups";
        }
        view.put("groupView", groupsService.getGroup());
        return "groups";
    }
}
