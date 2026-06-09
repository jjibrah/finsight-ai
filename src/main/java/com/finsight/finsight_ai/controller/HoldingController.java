package com.finsight.finsight_ai.controller;

import com.finsight.finsight_ai.dto.HoldingRequest;
import com.finsight.finsight_ai.dto.HoldingResponse;
import com.finsight.finsight_ai.model.Holding;
import com.finsight.finsight_ai.service.HoldingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/holdings")
public class HoldingController {
    private HoldingService service;

    public HoldingController(HoldingService services){
        this.service=service;
    }

    @PostMapping
    public HoldingResponse add(@RequestBody HoldingRequest request){
        Holding holding = new Holding(request.symbol,request.quantity,request.buyPrice);

        Holding saved = service.addHolding(holding);
        double pnl=service.calculatePnL(saved);
        return new HoldingResponse(
                saved.getId(),
                saved.getsymbol(),
                saved.getQuantity(),
                saved.getBuyPrice(),
                pnl
        );
    }

    @GetMapping
    public List<HoldingResponse> getAll(){
        return service.getAllHolding().stream().map(h->new HoldingResponse(
                h.getId(),
                h.getsymbol(),
                h.getQuantity(),
                h.getBuyPrice(),
                service.calculatePnL(h)
        )).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.deleteHolding(id);
        return "Deleted Holding with id: "+id;
    }
}
