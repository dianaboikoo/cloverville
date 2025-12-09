package com.example.cloverville;

import java.util.ArrayList;
import java.util.List;

public class Resident {

  private String name;
  private int personalPoints;
  private List<TradeOffer> assignedTradeOffers;

  // Needed by Gson
  public Resident() {
    this.assignedTradeOffers = new ArrayList<>();
  }

  public Resident(String name, int personalPoints) {
    this.name = name;
    this.personalPoints = personalPoints;
    this.assignedTradeOffers = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public int getPersonalPoints() {
    return personalPoints;
  }

  public List<TradeOffer> getAssignedTradeOffers() {
    return assignedTradeOffers;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPersonalPoints(int personalPoints) {
    this.personalPoints = personalPoints;
  }

  public void setAssignedTradeOffers(List<TradeOffer> assignedTradeOffers) {
    this.assignedTradeOffers = assignedTradeOffers;
  }

  // So ComboBox shows the name
  @Override
  public String toString() {
    return name;
  }
}
