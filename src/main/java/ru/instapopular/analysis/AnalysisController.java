package ru.instapopular.analysis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/analysis")
    public String analysis(Map<String, Object> view) {
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto());
        return "analysis";
    }

    @PostMapping("/loginToInst")
    public String loginAnalysis(@RequestParam(name = "login") String login,
                                @RequestParam(name = "password") String password,
                                Map<String, Object> view) {

        if ((login != null && login.length() > 0) || (password != null && password.length() > 0)) {
            analysisService.loginOnWebSite(login, password);
            analysisService.runAnalysis();
        }
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto());
        return "analysis";
    }

    @PostMapping("/analysis")
    public String doNotUnsubscribe(@RequestParam(name = "doNotUnsubscribe") int doNotUnsubscribe,
                                   Map<String, Object> view) {

        if (doNotUnsubscribe > 0) {
            analysisService.addFirstDoNotUnsubscribe(doNotUnsubscribe);
        }
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto());
        return "analysis";
    }

    @PostMapping("/addRemoveInAnalysis")
    public String addRemove(@RequestParam(name = "addPhoto") String add,
                            @RequestParam(name = "removePhoto") String remove,
                            Map<String, Object> view) {

        if (add != null && add.length() > 0) {
            add = analysisService.cutOfUrl(add);
            if (add.length() > 0) {
                analysisService.addMyPhoto(add);
            }
        } else if (remove != null && remove.length() > 0) {
            remove = analysisService.cutOfUrl(remove);
            if (remove.length() > 0) {
                analysisService.removeMyPhoto(remove);
            }
        }
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto());
        return "analysis";
    }
}
