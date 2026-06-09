// create mock values
package com.finsight.finsight_ai.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class PriceService {
    //storage for the prices
    private final Map<String, Double> prices= new HashMap<>();

    //constructor
    public PriceService(){
        prices.put("AAPL",190.00);
        prices.put("TSLA",190.00);
        prices.put("GOOGL",190.00);
        prices.put("AMZN",190.00);
    }

    //get the prices of stocks
    public double getPrice(String symbol){
        return prices.getOrDefault(symbol.toUpperCase(), 100.0);
    }

}
