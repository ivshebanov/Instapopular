package com.instapopulars.instapopular.unsubscribe;

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
    public String unsubscribe() {
        return "unsubscribe";
    }

    @PostMapping("/unsub")
    private String unsub(@RequestParam(name = "login") String login,
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "countUnsubscribe", defaultValue = "400") int countUnsubscribe) {

        if (login == null || login.length() <= 0 || password == null || password.length() <= 0 || countUnsubscribe < 0) {
            return "unsubscribe";
        }
        unsubscribeService.loginOnWebSite(login, password);
        unsubscribeService.unsubscribe(countUnsubscribe);
        return "unsubscribe";
    }
}
