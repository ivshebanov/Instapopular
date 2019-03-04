package com.instapopulars.instapopular.unsubscribe;

import static com.instapopulars.instapopular.Constant.User.LOGIN;
import static com.instapopulars.instapopular.Constant.User.PASSWORD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class UnsubscribeController {

    private static final Logger logger = LogManager.getLogger(UnsubscribeController.class);
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

    public String index() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        return "Howdy! Check out the Logs to see the output...";
    }
}
