 package com.finsight.finsight_ai.dto;

  public class HoldingResponse {
      public Long id;
      public String symbol;
      public double quantity;
      public double buyPrice;
      public double pnl;

      public HoldingResponse(
              Long id,
              String symbol,
              double quantity,
              double buyPrice,
              double pnl
      ) {
          this.id = id;
          this.symbol = symbol;
          this.quantity = quantity;
          this.buyPrice = buyPrice;
          this.pnl = pnl;
      }
  }
