package com.instapopulars.instapopular.unsubscribe;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UnsubscribeController {

    @Autowired
    private UnsubscribeService unsubscribeService;

    @GetMapping("/unsubscribe")
    public String unsubscribe(Map<String, Object> view) {
        view.put("groupView", unsubscribeService.getDoNotUnsubscribeUser());
        return "unsubscribe";
    }

    @PostMapping("/unsub")
    private String unsub(@RequestParam(name = "login") String login,
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "countUnsubscribe", defaultValue = "400") int countUnsubscribe,
                         Map<String, Object> view) {

        if (login == null || login.length() <= 0 || password == null || password.length() <= 0 || countUnsubscribe < 0) {
            view.put("groupView", unsubscribeService.getDoNotUnsubscribeUser());
            return "unsubscribe";
        }
        unsubscribeService.loginOnWebSite(login, password);
        unsubscribeService.unsubscribe(countUnsubscribe);
        unsubscribeService.getDoNotUnsubscribeUser();
        return "unsubscribe";
    }

    @PostMapping("/addRemoveUnsub")
    public String addRemove(@RequestParam(name = "addGroup") String add,
                            @RequestParam(name = "removeGroup") String remove,
                            Map<String, Object> view) {

        if ((add != null && add.length() > 0) && (remove != null && remove.length() > 0)) {
            view.put("groupView", unsubscribeService.getDoNotUnsubscribeUser());
            return "unsubscribe";
        }
        if (add != null && add.length() > 0) {
            view.put("groupView", unsubscribeService.addDoNotUnsubscribeUser(add));
            return "unsubscribe";
        }
        if (remove != null && remove.length() > 0) {
            view.put("groupView", unsubscribeService.removeDoNotUnsubscribeUser(remove));
            return "unsubscribe";
        }
        view.put("groupView", unsubscribeService.getDoNotUnsubscribeUser());
        return "unsubscribe";
    }
}
