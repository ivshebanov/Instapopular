package com.instapopulars.instapopular.hashtag;

import static com.instapopulars.instapopular.Constant.User.LOGIN;
import static com.instapopulars.instapopular.Constant.User.PASSWORD;
import static com.instapopulars.instapopular.hashtag.Action.LIKE;
import static com.instapopulars.instapopular.hashtag.Action.SUBSCRIBE_AND_LIKE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("hashtags")
public class HashtagController {

    private final HashtagService hashtagService;

    @Autowired
    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

    @RequestMapping(value = "/top", method = {GET})
    private void topPublications() {
        hashtagService.loginOnWebSite(LOGIN, PASSWORD);
        hashtagService.topPublications(LIKE);
    }

    @RequestMapping(value = "/new", method = {GET})
    private void newPublications() {
        hashtagService.loginOnWebSite(LOGIN, PASSWORD);
        hashtagService.newPublications(SUBSCRIBE_AND_LIKE, 50);
    }
}
