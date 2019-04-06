package com.instapopulars.instapopular.analysis;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @GetMapping("/analysis")
    public String analysis(Map<String, Object> view) {
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto());
        return "analysis";
    }

    @PostMapping("/login")
    public String loginAnalysis(@RequestParam(name = "login") String login,
                                @RequestParam(name = "password") String password,
                                Map<String, Object> view) {

        if (login == null || login.length() <= 0
                || password == null || password.length() <= 0) {
            view.put("analysisView", analysisService.getAnalysisPhoto());
            view.put("photoView", analysisService.getMyPhoto());
            return "analysis";
        }
        analysisService.loginOnWebSite(login, password);
        analysisService.runAnalysis();
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto());
        return "analysis";
    }

    @PostMapping("/addRemoveInAnalysis")
    public String addRemove(@RequestParam(name = "addPhoto") String add,
                            @RequestParam(name = "removePhoto") String remove,
                            Map<String, Object> view) {

        if ((add != null && add.length() > 0) && (remove != null && remove.length() > 0)) {
            view.put("analysisView", analysisService.getAnalysisPhoto());
            view.put("photoView", analysisService.getMyPhoto());
            return "analysis";
        }
        //https://www.instagram.com/p/Bv62bQMngfE/
        if (add != null && add.length() > 0) {
            view.put("photoView", analysisService.addMyPhoto(add));
        }
        if (remove != null && remove.length() > 0) {
            view.put("photoView", analysisService.removeMyPhoto(remove));
        }
        view.put("analysisView", analysisService.getAnalysisPhoto());
        return "analysis";
    }
}
