package com.instapopulars.instapopular.hashtag;

import com.instapopulars.instapopular.Action;
import java.util.Map;
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
    public String hashtags(Map<String, Object> view) {
        view.put("hashtagView", hashtagService.getHestags());
        return "hashtags";
    }

    @PostMapping("/top")
    private String topPublications(@RequestParam(name = "login") String login,
                                   @RequestParam(name = "password") String password,
                                   @RequestParam(name = "action", defaultValue = "LIKE") Action action,
                                   Map<String, Object> view) {

        if (login == null || login.length() <= 0 || password == null || password.length() <= 0 || action == null) {
            view.put("hashtagView", hashtagService.getHestags());
            return "hashtags";
        }
        hashtagService.loginOnWebSite(login, password);
        hashtagService.topPublications(action);
        view.put("hashtagView", hashtagService.getHestags());
        return "hashtags";
    }

    @PostMapping("/addRemoveHashtag")
    public String addRemove(@RequestParam(name = "addHashtag") String add,
                            @RequestParam(name = "removeHashtag") String remove,
                            Map<String, Object> view) {

        if ((add != null && add.length() > 0) && (remove != null && remove.length() > 0)) {
            view.put("hashtagView", hashtagService.getHestags());
            return "hashtags";
        }
        if (add != null && add.length() > 0) {
            view.put("hashtagView", hashtagService.addHestag(add));
            return "hashtags";
        }
        if (remove != null && remove.length() > 0) {
            view.put("hashtagView", hashtagService.removeHestag(remove));
            return "hashtags";
        }
        view.put("hashtagView", hashtagService.getHestags());
        return "hashtags";
    }

    @PostMapping("/new")
    private void newPublications() {
//        hashtagService.loginOnWebSite(LOGIN, PASSWORD);
//        hashtagService.newPublications(SUBSCRIBE_AND_LIKE, 50);
    }
}
