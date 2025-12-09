package com.example.cloverville;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ResidentsController {

    @FXML
    private ListView<Resident> residentListView;

    @FXML
    private TextField nameField;

    @FXML
    private TextField personalPointsField;

    @FXML
    private TextField greenPointsField;

    private final ObservableList<Resident> residents = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        residentListView.setItems(residents);

        residentListView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> showResidentDetails(newVal));
    }

    private void showResidentDetails(Resident resident) {
        if (resident != null) {
            nameField.setText(resident.getName());
            personalPointsField.setText(String.valueOf(resident.getPersonalPoints()));
            greenPointsField.setText(String.valueOf(resident.getGreenPoints()));
        } else {
            nameField.clear();
            personalPointsField.clear();
            greenPointsField.clear();
        }
    }

    @FXML
    private void handleAdd() {
        String name = nameField.getText();
        int personal = personalPointsField.getText().isEmpty() ? 0 : Integer.parseInt(personalPointsField.getText());
        int green = greenPointsField.getText().isEmpty() ? 0 : Integer.parseInt(greenPointsField.getText());

        Resident newResident = new Resident(name, personal, green);
        residents.add(newResident);

        // Select the new resident in the list
        residentListView.getSelectionModel().select(newResident);
    }


    @FXML
    private void handleSave() {
        Resident selected = residentListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setName(nameField.getText());
            selected.setPersonalPoints(Integer.parseInt(personalPointsField.getText()));
            selected.setGreenPoints(Integer.parseInt(greenPointsField.getText()));
            residentListView.refresh();
        }
    }

    @FXML
    private void handleDelete() {
        Resident selected = residentListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            residents.remove(selected);
        }
    }
}
