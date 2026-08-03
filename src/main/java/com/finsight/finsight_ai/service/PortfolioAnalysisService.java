package com.finsight.finsight_ai.service;


import com.finsight.finsight_ai.model.Holding;
import com.finsight.finsight_ai.repository.HoldingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PortfolioAnalysisService {

    private final HoldingRepository repository;
    private final PriceService priceService;


    //constructor
    public PortfolioAnalysisService(HoldingRepository repository, PriceService priceService){
        this.repository=repository;
        this.priceService=priceService;
    }
    public String buildportfolioPrompt(){
        List<Holding> holdings = repository.findAll();

        if (holdings.isEmpty()){
            return "Portfolio is empty. No analysis can be generated.";
        }

        StringBuilder prompt = new StringBuilder();

        prompt.append("Analyze this investment Portfolio: \n\n");

        double totalValue = 0;
        double totalPnL = 0;

        for(Holding h: holdings){
            double currentPrice = priceService.getPrice(h.getsymbol());
            double value = currentPrice * h.getQuantity();
            double pnl = (currentPrice - h.getBuyPrice()) * h.getQuantity();

            totalValue += value;
            totalPnL += pnl;


            prompt.append("-").append(h.getsymbol())
                    .append(": qty ").append(h.getQuantity())
                    .append(", buyPrice = ").append(h.getBuyPrice())
                    .append(", currentPrice").append(currentPrice)
                    .append(", PnL").append(pnl).append("\n");
        }

        prompt.append("\n Total Value: " ).append(totalValue);
        prompt.append("\n Total PnL: " ).append(totalPnL);

        prompt.append("\n\n Provide analysis including :\n ")
                .append("- Risk level\n")
                .append("- Diversification\n")
                .append("- Performance summary\n")
                .append("- Recommendations");

        return prompt.toString();
    }


}
