package com.example.cloverville;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Resident {

  private final StringProperty name = new SimpleStringProperty();
  private final IntegerProperty personalPoints = new SimpleIntegerProperty();
  private final IntegerProperty greenPoints = new SimpleIntegerProperty();

  // 🔹 NEW: list of assigned trade offers (normal field, not a property)
  private List<TradeOffer> assignedTradeOffers = new ArrayList<>();

  // 🔹 Needed by Gson for JSON loading
  public Resident() {
    // properties are already initialised above
  }

  public Resident(String name, int personalPoints, int greenPoints) {
    this(); // call no-arg constructor
    this.name.set(name);
    this.personalPoints.set(personalPoints);
    this.greenPoints.set(greenPoints);
  }

  public String getName() { return name.get(); }
  public void setName(String n) { name.set(n); }
  public StringProperty nameProperty() { return name; }

  public int getPersonalPoints() { return personalPoints.get(); }
  public void setPersonalPoints(int p) { personalPoints.set(p); }
  public IntegerProperty personalPointsProperty() { return personalPoints; }

  public int getGreenPoints() { return greenPoints.get(); }
  public void setGreenPoints(int g) { greenPoints.set(g); }
  public IntegerProperty greenPointsProperty() { return greenPoints; }

  // 🔹 NEW: used by TradeOffersController
  public List<TradeOffer> getAssignedTradeOffers() {
    return assignedTradeOffers;
  }

  public void setAssignedTradeOffers(List<TradeOffer> assignedTradeOffers) {
    this.assignedTradeOffers = assignedTradeOffers;
  }

  @Override
  public String toString() {
    return getName();
  }
}
