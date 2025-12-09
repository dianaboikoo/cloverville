package com.example.cloverville;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class GreenActionsController {

    @FXML private TextField nameField;
    @FXML private TextField pointsField;
    @FXML private ComboBox<Resident> residentCombo;
    @FXML private ListView<String> actionList;

    // Stores a readable history of assigned actions
    public static ObservableList<String> assignedActions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        residentCombo.setItems(ListController.residents);
        actionList.setItems(assignedActions);
    }

    @FXML
    private void handleAdd() {
        String name = nameField.getText();
        String pointsText = pointsField.getText();
        Resident resident = residentCombo.getValue();

        if (name.isEmpty() || pointsText.isEmpty() || resident == null) {
            System.out.println("Missing fields!");
            return;
        }

        int points = Integer.parseInt(pointsText);

        // Add points to the resident immediately
        resident.setGreenPoints(resident.getGreenPoints() + points);

        // Save the action record
        String record = resident.getName() + " received +" + points + " points for: " + name;
        assignedActions.add(record);

        // Clear input fields
        nameField.clear();
        pointsField.clear();
        residentCombo.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() {
        SceneManager.switchTo("main-menu.fxml");
    }
}
