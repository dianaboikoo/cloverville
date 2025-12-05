package com.example.cloverville;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TradeOffersController implements Initializable {

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
    // create list
    offers = FXCollections.observableArrayList();
    tradeTable.setItems(offers);

    // connect columns to TradeOffer getters
    ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
    offerColumn.setCellValueFactory(new PropertyValueFactory<>("tradeOffer"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceOrService"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    tradeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        ownerField.setText(newSelection.getOwner());
        offerField.setText(newSelection.getTradeOffer());
        priceField.setText(newSelection.getPriceOrService());
        statusField.setText(newSelection.getStatus());
      }
    });
  }

  @FXML
  private void handleAddOffer() {
    String owner = ownerField.getText();
    String offer = offerField.getText();
    String price = priceField.getText();
    String status = statusField.getText();

    if (owner.isBlank() && offer.isBlank() && price.isBlank() && status.isBlank()) {
      return; // or show an error dialog if you want
    }

    TradeOffer newOffer = new TradeOffer(owner, offer, price, status);
    offers.add(newOffer);

    // clear fields
    ownerField.clear();
    offerField.clear();
    priceField.clear();
    statusField.clear();
  }

  @FXML
  private void handleDeleteOffer() {
    TradeOffer selected = tradeTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      offers.remove(selected);
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
  }


}
