package ru.instapopular.hashtag;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import ru.instapopular.Action;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.instapopular.model.Usr;

import java.util.Map;

@Controller
public class HashtagController {

    private final HashtagService hashtagService;

    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

    @GetMapping("/hashtags")
    public String hashtags(@AuthenticationPrincipal Usr usr,
                           Map<String, Object> view) {

        view.put("hashtagView", hashtagService.getHestags(usr));
        return "hashtags";
    }

    @PostMapping("/top")
    private String topPublications(@AuthenticationPrincipal Usr usr,
                                   @RequestParam(name = "action", defaultValue = "LIKE") Action action,
                                   Map<String, Object> view) {

        hashtagService.loginOnWebSite(usr.getInstName(), usr.getInstPassword());
        hashtagService.topPublications(usr, action);
        view.put("hashtagView", hashtagService.getHestags(usr));
        return "hashtags";
    }

    @PostMapping("/addRemoveHashtag")
    public String addRemove(@AuthenticationPrincipal Usr usr,
                            @RequestParam(name = "addHashtag") String add,
                            @RequestParam(name = "removeHashtag") String remove,
                            Map<String, Object> view) {

        if (add.length() > 0) {
            hashtagService.addHestag(usr, add);
        } else if (remove.length() > 0) {
            hashtagService.removeHestag(usr, remove);
        }
        view.put("hashtagView", hashtagService.getHestags(usr));
        return "hashtags";
    }
}
