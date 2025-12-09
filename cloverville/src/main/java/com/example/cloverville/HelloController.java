package com.example.cloverville;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController
{
  @FXML
  private Label welcomeText;

  @FXML
  protected void onHelloButtonClick()
  {
    welcomeText.setText("Welcome to JavaFX Application!");
  }

  @FXML
  public void openTradeOffers() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("trade_offers.fxml"));
    Scene scene = new Scene(loader.load());
    Stage stage = new Stage();
    stage.setTitle("Trade Offers");
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void openResidents() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("list-view.fxml"));
    Scene scene = new Scene(loader.load());
    Stage stage = new Stage();
    stage.setTitle("Residents");
    stage.setScene(scene);
    stage.show();
  }
}
