package com.instapopulars.instapopular.unsubscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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

        if ((login != null && login.length() > 0) || (password != null && password.length() > 0)) {
            unsubscribeService.loginOnWebSite(login, password);
            unsubscribeService.unsubscribe(countUnsubscribe);
        }
        view.put("groupView", unsubscribeService.getDoNotUnsubscribeUser());
        return "unsubscribe";
    }

    @PostMapping("/addRemoveUnsub")
    public String addRemove(@RequestParam(name = "addGroup") String add,
                            @RequestParam(name = "removeGroup") String remove,
                            Map<String, Object> view) {

        if (add != null && add.length() > 0) {
            unsubscribeService.addDoNotUnsubscribeUser(add);
        } else if (remove != null && remove.length() > 0) {
            unsubscribeService.removeDoNotUnsubscribeUser(remove);
        }
        view.put("groupView", unsubscribeService.getDoNotUnsubscribeUser());
        return "unsubscribe";
    }
}
