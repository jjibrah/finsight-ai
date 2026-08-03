package com.finsight.finsight_ai.controller;

import com.finsight.finsight_ai.service.GeminiClient;
import com.finsight.finsight_ai.service.PortfolioAnalysisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioAnalysisService analysisService;
    private final GeminiClient geminiClient;

    public PortfolioController(PortfolioAnalysisService analysisService,
                               GeminiClient geminiClient) {
        this.analysisService = analysisService;
        this.geminiClient = geminiClient;
    }

    @PostMapping("/analyse")
    public String analysePortfolio() {

        String prompt = analysisService.buildportfolioPrompt();

        if (prompt.contains("empty")) {
            return "Your portfolio is empty. Add holdings to get AI insights.";
        }

        return geminiClient.getAnalysis(prompt);
    }
}