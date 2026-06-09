package com.finsight.finsight_ai.dto;

public class HoldingResponse {
    public Long id;
    public String symbol;
    public double quantity;
    public double buyPrice;
    public double pnl;

    //constructor
    public HoldingResponse(Long id, String symbol, double buyPrice, double quantity, double pnl){
        this.id=id;
        this.symbol=symbol;
        this.buyPrice=  buyPrice;
        this.quantity=  quantity;
        this.pnl=pnl;
    }
}
