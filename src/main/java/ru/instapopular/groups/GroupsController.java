package ru.instapopular.groups;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.instapopular.Action;
import ru.instapopular.model.Usr;

import java.util.Map;

@Controller
public class GroupsController {

    private final GroupsService groupsService;

    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @GetMapping("/groups")
    public String groups(@AuthenticationPrincipal Usr usr,
                         Map<String, Object> view) {
        view.put("groupView", groupsService.getActiveGroup(usr));
        view.put("scanClient", groupsService.getActiveClient(usr));
        return "groups";
    }

    @PostMapping("/scanClient")
    public String scanClient(@AuthenticationPrincipal Usr usr,
                             Map<String, Object> view) {

        groupsService.loginOnWebSite(usr.getInstName(), usr.getInstPassword());
        groupsService.scanClient(usr);
        view.put("groupView", groupsService.getActiveGroup(usr));
        view.put("scanClient", groupsService.getActiveClient(usr));
        return "groups";
    }

    @PostMapping("/group")
    public String group(@AuthenticationPrincipal Usr usr,
                        @RequestParam(name = "countSubscriptions", defaultValue = "350") int countSubscriptions,
                        @RequestParam(name = "action", defaultValue = "SUBSCRIBE_AND_LIKE") Action action,
                        Map<String, Object> view) {

        groupsService.loginOnWebSite(usr.getInstName(), usr.getInstPassword());
        groupsService.subscribeToUsersInGroup(usr, countSubscriptions, action);
        view.put("groupView", groupsService.getActiveGroup(usr));
        view.put("scanClient", groupsService.getActiveClient(usr));
        return "groups";
    }

    @PostMapping("/addRemove")
    public String addRemove(@AuthenticationPrincipal Usr usr,
                            @RequestParam(name = "addGroup") String add,
                            @RequestParam(name = "removeGroup") String remove,
                            Map<String, Object> view) {

        if (add.length() > 0) {
            groupsService.addGroup(usr, add);
        } else if (remove.length() > 0) {
            groupsService.removeGroup(usr, remove);
        }
        view.put("groupView", groupsService.getActiveGroup(usr));
        view.put("scanClient", groupsService.getActiveClient(usr));
        return "groups";
    }
}
