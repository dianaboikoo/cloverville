package com.example.cloverville;

import java.util.ArrayList;
import java.util.List;

public class Resident {

  private String name;
  private int personalPoints;
  private int greenPoints;

  // renamed field
  private List<TradeOffer> takenTradeOffers = new ArrayList<>();

  // owned offers
  private List<TradeOffer> ownedTradeOffers = new ArrayList<>();

  public Resident() { }

  public Resident(String name, int personalPoints, int greenPoints) {
    this.name = name;
    this.personalPoints = personalPoints;
    this.greenPoints = greenPoints;
  }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public int getPersonalPoints() { return personalPoints; }
  public void setPersonalPoints(int personalPoints) { this.personalPoints = personalPoints; }

  public int getGreenPoints() { return greenPoints; }
  public void setGreenPoints(int greenPoints) { this.greenPoints = greenPoints; }

  // renamed list
  public List<TradeOffer> getTakenTradeOffers() {
    return takenTradeOffers;
  }

  public void setTakenTradeOffers(List<TradeOffer> takenTradeOffers) {
    this.takenTradeOffers = takenTradeOffers;
  }

  public List<TradeOffer> getOwnedTradeOffers() {
    return ownedTradeOffers;
  }

  public void setOwnedTradeOffers(List<TradeOffer> ownedTradeOffers) {
    this.ownedTradeOffers = ownedTradeOffers;
  }

  @Override
  public String toString() {
    return name;
  }
}
