package com.instapopulars.instapopular.unsubscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UnsubscribeController {

    private final UnsubscribeService unsubscribeService;

    @Autowired
    public UnsubscribeController(UnsubscribeService unsubscribeService) {
        this.unsubscribeService = unsubscribeService;
        unsubscribeService.loginOnWebSite("lilka.lily.1", "Sxsblpwiwn");
        unsubscribeService.unsubscribe(400);
    }
}
