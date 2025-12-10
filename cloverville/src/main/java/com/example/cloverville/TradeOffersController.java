package com.example.cloverville;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TradeOffersController implements Initializable {

  private static final String OFFERS_FILE = "trade_offers.json";
  private static final String RESIDENTS_FILE = "residents.json";

  @FXML private TableView<TradeOffer> tradeTable;
  @FXML private TableColumn<TradeOffer, String> ownerColumn;
  @FXML private TableColumn<TradeOffer, String> offerColumn;
  @FXML private TableColumn<TradeOffer, String> priceColumn;
  @FXML private TableColumn<TradeOffer, String> statusColumn;

  @FXML private TextField offerField;
  @FXML private TextField priceField;

  @FXML private ComboBox<Resident> ownerComboBox;
  @FXML private ComboBox<Resident> residentComboBox;
  @FXML private ComboBox<TradeOffer> taskComboBox;

  private ObservableList<TradeOffer> offers;
  private ObservableList<Resident> residents;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    offers = FXCollections.observableArrayList();
    residents = FXCollections.observableArrayList();

    tradeTable.setItems(offers);
    residentComboBox.setItems(residents);
    ownerComboBox.setItems(residents);
    taskComboBox.setItems(offers);

    ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
    offerColumn.setCellValueFactory(new PropertyValueFactory<>("tradeOffer"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceOrService"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    tradeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        offerField.setText(newSelection.getTradeOffer());
        priceField.setText(newSelection.getPriceOrService());
      }
    });

    loadOffersFromJson();
    loadResidentsFromJson();
  }

  @FXML
  private void handleAddOffer() {
    Resident owner = ownerComboBox.getSelectionModel().getSelectedItem();
    String offer = offerField.getText();
    String priceDescription = priceField.getText();

    if (owner == null || offer.isBlank()) {
      System.out.println("No owner or empty offer -> aborting add.");
      return;
    }

    // try to interpret price as points (optional)
    Integer pointCost = null;
    if (!priceDescription.isBlank()) {
      try {
        pointCost = Integer.parseInt(priceDescription.trim());
      } catch (NumberFormatException e) {
        // not a number → treat as “service” description
        pointCost = null;
      }
    }

    TradeOffer newOffer = new TradeOffer(
        owner.getName(),
        offer,
        priceDescription,
        "Unassigned",
        pointCost
    );

    offers.add(newOffer);

    owner.getOwnedTradeOffers().add(newOffer);

    ownerComboBox.getSelectionModel().clearSelection();
    offerField.clear();
    priceField.clear();

    saveOffersToJson();
    saveResidentsToJson();
  }

  @FXML
  private void handleDeleteOffer() {
    TradeOffer selected = tradeTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      offers.remove(selected);

      for (Resident r : residents) {
        r.getTakenTradeOffers().removeIf(o -> o == selected);
        r.getOwnedTradeOffers().removeIf(o -> o == selected);   // 🔹 new
      }

      saveOffersToJson();
      saveResidentsToJson();
    }
  }

  @FXML
  private void handleEditOffer() {
    TradeOffer selected = tradeTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
      return;
    }

   // selected.setOwner(ownerField.getText());
    selected.setTradeOffer(offerField.getText());
    selected.setPriceOrService(priceField.getText());

    tradeTable.refresh();
    saveOffersToJson();
  }

  @FXML
  private void handleAssignOffer() {
    TradeOffer selectedOffer = taskComboBox.getSelectionModel().getSelectedItem();
    Resident assignedResident = residentComboBox.getSelectionModel().getSelectedItem();

    if (selectedOffer == null || assignedResident == null) {
      System.out.println("No task or resident selected.");
      return;
    }

    System.out.println("=== ASSIGN OFFER ===");
    System.out.println("Offer: " + selectedOffer.getTradeOffer());
    System.out.println("Price/Service text: " + selectedOffer.getPriceOrService());
    System.out.println("Point cost stored on offer: " + selectedOffer.getPointCost());


    Resident ownerResident = residents.stream()
        .filter(r -> r.getName().equals(selectedOffer.getOwner()))
        .findFirst()
        .orElse(null);

    if (ownerResident == null) {
      System.out.println("Owner resident NOT FOUND in residents list!");
    } else {
      System.out.println("Owner resident FOUND: " + ownerResident.getName() +
          " (points=" + ownerResident.getPersonalPoints() + ")");
    }

    Integer cost = selectedOffer.getPointCost();
    if (cost != null && cost > 0 && ownerResident != null) {

      if (assignedResident.getPersonalPoints() < cost) {
        System.out.println("Not enough points to take this offer!");
        return;
      }

      System.out.println("Transferring " + cost + " points from "
          + assignedResident.getName() + " to " + ownerResident.getName());

      assignedResident.setPersonalPoints(
          assignedResident.getPersonalPoints() - cost
      );

      ownerResident.setPersonalPoints(
          ownerResident.getPersonalPoints() + cost
      );

      System.out.println("New points: " + assignedResident.getName() + " = "
          + assignedResident.getPersonalPoints() + ", "
          + ownerResident.getName() + " = " + ownerResident.getPersonalPoints());
    } else {
      System.out.println("No point transfer (cost is null/0 or owner not found).");
    }

    selectedOffer.setStatus("Assigned to " + assignedResident.getName());
    tradeTable.refresh();

    if (!assignedResident.getTakenTradeOffers().contains(selectedOffer)) {
      assignedResident.getTakenTradeOffers().add(selectedOffer);
    }

    saveOffersToJson();
    saveResidentsToJson();

    System.out.println("Saved offers and residents to JSON.\n");
  }




  // ---------- JSON LOAD/SAVE for offers ----------

  private void loadOffersFromJson() {
    Path path = Paths.get(OFFERS_FILE);
    if (!Files.exists(path)) {
      return;
    }

    try (Reader reader = Files.newBufferedReader(path)) {
      Gson gson = new Gson();
      Type listType = new TypeToken<List<TradeOffer>>() {}.getType();
      List<TradeOffer> loaded = gson.fromJson(reader, listType);
      if (loaded != null) {
        offers.setAll(loaded);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveOffersToJson() {
    try (Writer writer = Files.newBufferedWriter(Paths.get(OFFERS_FILE))) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(new ArrayList<>(offers), writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // ---------- JSON LOAD/SAVE for residents ----------

  private void loadResidentsFromJson() {
    Path path = Paths.get(RESIDENTS_FILE);
    if (!Files.exists(path)) {
      return;
    }

    try (Reader reader = Files.newBufferedReader(path)) {
      Gson gson = new Gson();
      Type listType = new TypeToken<List<Resident>>() {}.getType();
      List<Resident> loaded = gson.fromJson(reader, listType);
      if (loaded != null) {
        residents.setAll(loaded);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveResidentsToJson() {
    try (Writer writer = Files.newBufferedWriter(Paths.get(RESIDENTS_FILE))) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(new ArrayList<>(residents), writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
