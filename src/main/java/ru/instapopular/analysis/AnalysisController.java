package ru.instapopular.analysis;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.instapopular.model.Usr;

import javax.validation.constraints.PositiveOrZero;
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
        view.put("analysisView", analysisService.getAnalysisGuys(usr));
        view.put("photoView", analysisService.getMyPhoto(usr));
        return "analysis";
    }

    @PostMapping("/loginToInst")
    public String loginAnalysis(@AuthenticationPrincipal Usr usr,
                                Map<String, Object> view) {

        analysisService.loginOnWebSite(usr.getInstName(), usr.getInstPassword());
        analysisService.runAnalysis(usr);
        view.put("analysisView", analysisService.getAnalysisGuys(usr));
        view.put("photoView", analysisService.getMyPhoto(usr));
        return "analysis";
    }

    @PostMapping("/analysis")
    public String doNotUnsubscribe(@AuthenticationPrincipal Usr usr,
                                   @RequestParam(name = "doNotUnsubscribe") @PositiveOrZero int doNotUnsubscribe,
                                   Map<String, Object> view) {

        analysisService.doNotUnsubscribe(usr, doNotUnsubscribe);
        view.put("analysisView", analysisService.getAnalysisGuys(usr));
        view.put("photoView", analysisService.getMyPhoto(usr));
        return "analysis";
    }

    @PostMapping("/addRemoveInAnalysis")
    public String addRemove(@AuthenticationPrincipal Usr usr,
                            @RequestParam(name = "addPhoto") String add,
                            @RequestParam(name = "removePhoto") String remove,
                            Map<String, Object> view) {

        if (add.length() > 0) {
            analysisService.addMyPhoto(usr, add);
        } else if (remove.length() > 0) {
            analysisService.removeMyPhoto(usr, remove);
        }
        view.put("analysisView", analysisService.getAnalysisGuys(usr));
        view.put("photoView", analysisService.getMyPhoto(usr));
        return "analysis";
    }
}
