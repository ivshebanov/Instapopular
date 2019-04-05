package com.instapopulars.instapopular.analysis;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @GetMapping("/analysis")
    public String groups(Map<String, Object> view) {
        view.put("analysisView", analysisService.getAnalysisPhoto());
        view.put("photoView", analysisService.getMyPhoto());
        return "analysis";
    }
}
