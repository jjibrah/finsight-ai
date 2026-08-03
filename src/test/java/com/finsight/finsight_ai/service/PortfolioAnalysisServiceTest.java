package com.finsight.finsight_ai.service;

import com.finsight.finsight_ai.model.Holding;
import com.finsight.finsight_ai.repository.HoldingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PortfolioAnalysisServiceTest {

    @Mock
    private HoldingRepository repository;

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PortfolioAnalysisService service;

    // -----------------------------
    // ✅ SUCCESS TEST
    // -----------------------------
    @Test
    void shouldBuildPortfolioPromptSuccessfully() {

        Holding h1 = new Holding("AAPL", 10, 150);

        when(repository.findAll()).thenReturn(List.of(h1));
        when(priceService.getPrice("AAPL")).thenReturn(190.0);

        String prompt = service.buildportfolioPrompt();

        assertNotNull(prompt);
        assertTrue(prompt.contains("AAPL"));
        assertTrue(prompt.contains("Total Value"));
        assertTrue(prompt.contains("Total PnL"));
    }

    // -----------------------------
    // ❌ EDGE CASE: EMPTY PORTFOLIO
    // -----------------------------
    @Test
    void shouldReturnEmptyPortfolioMessage() {

        when(repository.findAll()).thenReturn(List.of());

        String prompt = service.buildportfolioPrompt();

        assertEquals(
                "Portfolio is empty. No analysis can be generated.",
                prompt
        );
    }

    // -----------------------------
    // ⚠️ EDGE CASE: ZERO PRICE DATA
    // -----------------------------
    @Test
    void shouldHandleUnknownStockPriceGracefully() {

        Holding h1 = new Holding("UNKNOWN", 5, 100);

        when(repository.findAll()).thenReturn(List.of(h1));
        when(priceService.getPrice("UNKNOWN")).thenReturn(100.0);

        String prompt = service.buildportfolioPrompt();

        assertNotNull(prompt);
        assertTrue(prompt.contains("UNKNOWN"));
        assertTrue(prompt.contains("Total Value"));
    }
}