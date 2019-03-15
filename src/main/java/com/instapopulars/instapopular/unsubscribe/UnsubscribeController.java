package com.instapopulars.instapopular.unsubscribe;

import static com.instapopulars.instapopular.Constant.User.LOGIN;
import static com.instapopulars.instapopular.Constant.User.PASSWORD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("unsubscribe")
public class UnsubscribeController {

    private final UnsubscribeService unsubscribeService;

    @Autowired
    public UnsubscribeController(UnsubscribeService unsubscribeService) {
        this.unsubscribeService = unsubscribeService;
    }

    @RequestMapping(value = "/unsub", method = {GET})
    private void unsubscribe() {
        unsubscribeService.loginOnWebSite(LOGIN, PASSWORD);
        unsubscribeService.unsubscribe(400);
    }
}
