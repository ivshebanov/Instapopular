package com.instapopulars.instapopular.groups;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupsController {

    private final GroupsService groupsService;

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }


    @GetMapping("/groups")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "groups";
    }
}
