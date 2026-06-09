package com.finsight.finsight_ai.service;


import com.finsight.finsight_ai.model.Holding;
import com.finsight.finsight_ai.repository.HoldingRepository;
import org.springframework.stereotype.Service;

import java.util.List;


//create the business logic
@Service
public class HoldingService {
    private final HoldingRepository repository;
    private final PriceService priceService;

    public HoldingService(HoldingRepository repository,PriceService priceService){
        this.repository = repository;
        this.priceService = priceService;
    }

    public Holding addHolding(Holding hodling){
        return repository.save(hodling);
    }

    public List<Holding> getAllHolding(){
        return repository.findAll();
    }

    public void deleteHolding(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Holding not found with id: "+ id);
        }
        repository.deleteById(id);
    }

    //P&L calculations
    public double calculatePnL(Holding holding){
        double currentPrice = priceService.getPrice(holding.getsymbol());
        return (currentPrice-holding.getBuyPrice())* holding.getQuantity();
    }

    public double calculatePortfolioValue(){
        return repository.findAll().stream().mapToDouble(h-> priceService.getPrice(h.getsymbol())*h.getQuantity()).sum();
    }
}
