package com.instapopulars.instapopular.hashtag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HashtagController {

    @Autowired
    private HashtagService hashtagService;

    @GetMapping("/hashtags")
    public String hashtags() {
        return "hashtags";
    }

    @PostMapping("/top")
    private String topPublications(@RequestParam(name = "login") String login,
                                   @RequestParam(name = "password") String password,
                                   @RequestParam(name = "action", defaultValue = "LIKE") Action action) {

        if (login == null || login.length() <= 0 || password == null || password.length() <= 0 || action == null) {
            return "hashtags";
        }
        hashtagService.loginOnWebSite(login, password);
        hashtagService.topPublications(action);
        return "hashtags";
    }

    @PostMapping("/new")
    private void newPublications() {
//        hashtagService.loginOnWebSite(LOGIN, PASSWORD);
//        hashtagService.newPublications(SUBSCRIBE_AND_LIKE, 50);
    }
}
