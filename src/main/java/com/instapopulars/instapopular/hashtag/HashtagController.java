package com.instapopulars.instapopular.hashtag;

import static com.instapopulars.instapopular.Constant.User.LOGIN;
import static com.instapopulars.instapopular.Constant.User.PASSWORD;
import static com.instapopulars.instapopular.hashtag.Action.SUBSCRIBE_AND_LIKE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HashtagController {

    private final HashtagService hashtagService;

    @Autowired
    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
        hashtag();
    }

    private void hashtag() {
        hashtagService.loginOnWebSite(LOGIN, PASSWORD);
        hashtagService.topPublicationsByHashtag(SUBSCRIBE_AND_LIKE);
    }
}
