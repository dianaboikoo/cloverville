package com.example.residents;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditController {

    @FXML private TextField nameField;
    @FXML private TextField personalPointsField;
    @FXML private TextField greenPointsField;

    @FXML
    public void initialize() {
        Resident r = ListController.selectedResident;

        nameField.setText(r.getName());
        personalPointsField.setText(String.valueOf(r.getPersonalPoints()));
        greenPointsField.setText(String.valueOf(r.getGreenPoints()));
    }

    @FXML
    private void handleSave() {
        Resident r = ListController.selectedResident;

        r.setName(nameField.getText());
        r.setPersonalPoints(Integer.parseInt(personalPointsField.getText()));
        r.setGreenPoints(Integer.parseInt(greenPointsField.getText()));

        SceneManager.switchTo("list-view.fxml");
    }

    @FXML
    private void handleBack() {
        SceneManager.switchTo("list-view.fxml");
    }
}
