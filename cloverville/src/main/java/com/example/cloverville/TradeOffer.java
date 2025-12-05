package com.example.cloverville;

public class TradeOffer {
  private String owner;
  private String tradeOffer;
  private String priceOrService;
  private String status;

  public TradeOffer(String owner, String tradeOffer, String priceOrService, String status) {
    this.owner = owner;
    this.tradeOffer = tradeOffer;
    this.priceOrService = priceOrService;
    this.status = status;
  }

  public String getOwner() {
    return owner;
  }

  public String getTradeOffer() {
    return tradeOffer;
  }

  public String getPriceOrService() {
    return priceOrService;
  }

  public String getStatus() {
    return status;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public void setTradeOffer(String tradeOffer) {
    this.tradeOffer = tradeOffer;
  }

  public void setPriceOrService(String priceOrService) {
    this.priceOrService = priceOrService;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
