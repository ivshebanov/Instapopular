package ru.instapopular.groups;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.instapopular.Action;
import ru.instapopular.model.MyGroup;
import ru.instapopular.model.Usr;
import ru.instapopular.repository.MyGroupRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/group1")
public class GroupEndpoint {

    private final GroupsService groupsService;
    private final MyGroupRepository myGroupRepository;

    public GroupEndpoint(GroupsService groupsService, MyGroupRepository myGroupRepository) {
        this.groupsService = groupsService;
        this.myGroupRepository = myGroupRepository;
    }

    @GetMapping("/scan")
    public void scanClient(@AuthenticationPrincipal Usr usr) {

        groupsService.loginOnWebSite(usr.getInstName(), usr.getInstPassword());
        groupsService.scanClient(usr);
    }

    @PostMapping("/start")
    public void start(@AuthenticationPrincipal Usr usr,
                      @RequestParam(name = "countSubscriptions", defaultValue = "350") int countSubscriptions,
                      @RequestParam(name = "action", defaultValue = "LIKE") Action action) {

        groupsService.loginOnWebSite(usr.getInstName(), usr.getInstPassword());
        groupsService.subscribeToUsersInGroup(usr, countSubscriptions, action);
    }

    @GetMapping
    public ResponseEntity<List<MyGroup>> getMyGroups(@AuthenticationPrincipal Usr usr) {

        List<MyGroup> resultGroup = groupsService.getMyGroupsByUsr(usr);
        return ResponseEntity.ok(resultGroup);
    }

    @PostMapping(produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<MyGroup> createGroup(@AuthenticationPrincipal Usr usr,
                                               @RequestBody MyGroup myGroup) {

        MyGroup resultGroup = groupsService.createGroup(myGroup, usr);
        return ResponseEntity.ok(resultGroup);
    }

    @PutMapping("{id}")
    public MyGroup updateGroup(@PathVariable("id") MyGroup myGroupFromRepo,
                               @RequestBody MyGroup myGroup) {

        return groupsService.updateGroup(myGroup, myGroupFromRepo);
    }

    @DeleteMapping("{id}")
    public void deleteGroup(@PathVariable("id") MyGroup myGroup) {
        groupsService.deleteGroup(myGroup);
    }

    @GetMapping("/profile")
    public Map<Object, Object> data(@AuthenticationPrincipal Usr usr) {
        Map<Object, Object> data = new HashMap<>();

        if (usr != null) {
            data.put("profile", usr);
            data.put("messages", myGroupRepository.findAllByUsr(usr));
        }
        return data;
    }
}
