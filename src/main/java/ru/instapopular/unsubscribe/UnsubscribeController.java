package ru.instapopular.unsubscribe;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.instapopular.model.Usr;

import java.util.Map;

@Controller
public class UnsubscribeController {

    private final UnsubscribeService unsubscribeService;

    public UnsubscribeController(UnsubscribeService unsubscribeService) {
        this.unsubscribeService = unsubscribeService;
    }

    @GetMapping("/unsubscribe")
    public String unsubscribe(@AuthenticationPrincipal Usr usr,
                              Map<String, Object> view) {
        view.put("groupView", unsubscribeService.getDoNotUnsubscribeUser(usr));
        return "unsubscribe";
    }

    @PostMapping("/unsub")
    private String unsub(@AuthenticationPrincipal Usr usr,
                         @RequestParam(name = "countUnsubscribe", defaultValue = "400") int countUnsubscribe,
                         Map<String, Object> view) {

        unsubscribeService.loginOnWebSite(usr.getInstName(), usr.getInstPassword());
        unsubscribeService.unsubscribe(usr, countUnsubscribe);
        view.put("groupView", unsubscribeService.getDoNotUnsubscribeUser(usr));
        return "unsubscribe";
    }

    @PostMapping("/addRemoveUnsub")
    public String addRemove(@AuthenticationPrincipal Usr usr,
                            @RequestParam(name = "addGroup") String add,
                            @RequestParam(name = "removeGroup") String remove,
                            Map<String, Object> view) {

        if (add.length() > 0) {
            unsubscribeService.addGuy(usr, add);
        } else if (remove.length() > 0) {
            unsubscribeService.removeGuy(usr, remove);
        }
        view.put("groupView", unsubscribeService.getDoNotUnsubscribeUser(usr));
        return "unsubscribe";
    }
}
