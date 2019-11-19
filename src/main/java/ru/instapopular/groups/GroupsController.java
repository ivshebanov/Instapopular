package ru.instapopular.groups;

import ru.instapopular.Action;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GroupsController {

    private final GroupsService groupsService;

    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

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

        if ((login != null && login.length() > 0) || (password != null && password.length() > 0)) {
            groupsService.loginOnWebSite(login, password);
            groupsService.subscribeToUsersInGroup(countSubscriptions, action);
        }
        view.put("groupView", groupsService.getGroup());
        return "groups";
    }

    @PostMapping("/addRemove")
    public String addRemove(@RequestParam(name = "addGroup") String add,
                            @RequestParam(name = "removeGroup") String remove,
                            Map<String, Object> view) {

        if (add != null && add.length() > 0) {
            groupsService.addGroup(add);
        } else if (remove != null && remove.length() > 0) {
            groupsService.removeGroup(remove);
        }
        view.put("groupView", groupsService.getGroup());
        return "groups";
    }
}
