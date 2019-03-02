package com.instapopulars.instapopular.unsubscribe;

import static com.instapopulars.instapopular.Constant.User.LOGIN;
import static com.instapopulars.instapopular.Constant.User.PASSWORD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UnsubscribeController {

    private final UnsubscribeService unsubscribeService;

    @Autowired
    public UnsubscribeController(UnsubscribeService unsubscribeService) {
        this.unsubscribeService = unsubscribeService;
        unsubscribe();
    }

    private void unsubscribe(){
        unsubscribeService.loginOnWebSite(LOGIN, PASSWORD);
        unsubscribeService.unsubscribe(400);
    }
}
