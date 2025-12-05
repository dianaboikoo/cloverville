package com.example.cloverville;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

  private static final String DATA_FILE = "trade_offers.json";

  @FXML
  private TableView<TradeOffer> tradeTable;

  @FXML
  private TableColumn<TradeOffer, String> ownerColumn;

  @FXML
  private TableColumn<TradeOffer, String> offerColumn;

  @FXML
  private TableColumn<TradeOffer, String> priceColumn;

  @FXML
  private TableColumn<TradeOffer, String> statusColumn;

  @FXML
  private TextField ownerField;

  @FXML
  private TextField offerField;

  @FXML
  private TextField priceField;

  @FXML
  private TextField statusField;

  private ObservableList<TradeOffer> offers;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    offers = FXCollections.observableArrayList();
    tradeTable.setItems(offers);

    ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
    offerColumn.setCellValueFactory(new PropertyValueFactory<>("tradeOffer"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceOrService"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    // When selecting a row, fill the text fields
    tradeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        ownerField.setText(newSelection.getOwner());
        offerField.setText(newSelection.getTradeOffer());
        priceField.setText(newSelection.getPriceOrService());
        statusField.setText(newSelection.getStatus());
      }
    });

    // 🔽 Load from JSON on startup
    loadOffersFromJson();
  }

  @FXML
  private void handleAddOffer() {
    String owner = ownerField.getText();
    String offer = offerField.getText();
    String price = priceField.getText();
    String status = statusField.getText();

    if (owner.isBlank() && offer.isBlank() && price.isBlank() && status.isBlank()) {
      return;
    }

    offers.add(new TradeOffer(owner, offer, price, status));

    ownerField.clear();
    offerField.clear();
    priceField.clear();
    statusField.clear();

    saveOffersToJson();   // 🔽 save after change
  }

  @FXML
  private void handleDeleteOffer() {
    TradeOffer selected = tradeTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      offers.remove(selected);
      saveOffersToJson();   // 🔽 save after change
    }
  }

  @FXML
  private void handleEditOffer() {
    TradeOffer selected = tradeTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
      return;
    }

    selected.setOwner(ownerField.getText());
    selected.setTradeOffer(offerField.getText());
    selected.setPriceOrService(priceField.getText());
    selected.setStatus(statusField.getText());

    tradeTable.refresh();
    saveOffersToJson();   // 🔽 save after change
  }

  private void loadOffersFromJson() {
    Path path = Paths.get(DATA_FILE);
    if (!Files.exists(path)) {
      return; // no file yet, first run
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
    try (Writer writer = Files.newBufferedWriter(Paths.get(DATA_FILE))) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(new ArrayList<>(offers), writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
