package com.instapopulars.instapopular.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AnalysisController {

    private AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
        analysisService.loginOnWebSite("lilka.lily.1", "Sxsblpwiwn");
        analysisService.runAnalysis();
    }
}
