package ru.instapopular.analysis;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.instapopular.model.Usr;

import java.util.Map;

@Controller
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/analysis")
    public String analysis(@AuthenticationPrincipal Usr usr,
                           Map<String, Object> view) {
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto(usr));
        return "analysis";
    }

    @PostMapping("/loginToInst")
    public String loginAnalysis(@AuthenticationPrincipal Usr usr,
                                @RequestParam(name = "login") String login,
                                @RequestParam(name = "password") String password,
                                Map<String, Object> view) {

        if ((login != null && login.length() > 0) || (password != null && password.length() > 0)) {
            analysisService.loginOnWebSite(login, password);
            analysisService.runAnalysis();
        }
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto(usr));
        return "analysis";
    }

    @PostMapping("/analysis")
    public String doNotUnsubscribe(@AuthenticationPrincipal Usr usr,
                                   @RequestParam(name = "doNotUnsubscribe") int doNotUnsubscribe,
                                   Map<String, Object> view) {

        if (doNotUnsubscribe > 0) {
            analysisService.addFirstDoNotUnsubscribe(doNotUnsubscribe);
        }
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto(usr));
        return "analysis";
    }

    @PostMapping("/addRemoveInAnalysis")
    public String addRemove(@AuthenticationPrincipal Usr usr,
                            @RequestParam(name = "addPhoto") String add,
                            @RequestParam(name = "removePhoto") String remove,
                            Map<String, Object> view) {

        if (add != null && add.length() > 0) {
            add = analysisService.cutOfUrl(add);
            if (add.length() > 0) {
                analysisService.addMyPhoto(usr, add);
            }
        } else if (remove != null && remove.length() > 0) {
            remove = analysisService.cutOfUrl(remove);
            if (remove.length() > 0) {
                analysisService.removeMyPhoto(usr, remove);
            }
        }
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto(usr));
        return "analysis";
    }
}
