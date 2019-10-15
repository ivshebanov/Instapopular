package ru.instapopular.hashtag;

import ru.instapopular.Action;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class HashtagController {

    private final HashtagService hashtagService;

    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

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

        if ((login != null && login.length() > 0) || (password != null && password.length() > 0)) {
            hashtagService.loginOnWebSite(login, password);
            hashtagService.topPublications(action);
        }
        view.put("hashtagView", hashtagService.getHestags());
        return "hashtags";
    }

    @PostMapping("/addRemoveHashtag")
    public String addRemove(@RequestParam(name = "addHashtag") String add,
                            @RequestParam(name = "removeHashtag") String remove,
                            Map<String, Object> view) {

        if (add != null && add.length() > 0) {
            hashtagService.addHestag(add);
        } else if (remove != null && remove.length() > 0) {
            hashtagService.removeHestag(remove);
        }
        view.put("hashtagView", hashtagService.getHestags());
        return "hashtags";
    }
}
