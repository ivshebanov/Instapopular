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
    private void topPublications(@RequestParam(name = "login", defaultValue = "lilka.lily.1") String login,
                                 @RequestParam(name = "password", defaultValue = "Sxsblpwiwn") String password,
                                 @RequestParam(name = "action", defaultValue = "LIKE") Action action) {

        if (login == null || login.length() <= 0 || password == null || password.length() <= 0 || action == null) {
            return;
        }
        hashtagService.loginOnWebSite(login, password);
        hashtagService.topPublications(action);
    }

    @PostMapping("/new")
    private void newPublications() {
//        hashtagService.loginOnWebSite(LOGIN, PASSWORD);
//        hashtagService.newPublications(SUBSCRIBE_AND_LIKE, 50);
    }
}
