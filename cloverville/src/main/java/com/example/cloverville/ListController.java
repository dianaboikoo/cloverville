package com.example.cloverville;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ListController {

  @FXML private ListView<Resident> listView;
  @FXML private TextField nameField;

  public static ObservableList<Resident> residents = FXCollections.observableArrayList();
  public static Resident selectedResident;

  @FXML
  public void initialize() {
    listView.setItems(residents);
  }

  @FXML
  private void handleAdd() {
    if (nameField.getText().isEmpty()) return;
    residents.add(new Resident(nameField.getText(), 0, 0));
    nameField.clear();
  }

  @FXML
  private void handleDelete() {
    Resident selected = listView.getSelectionModel().getSelectedItem();
    if (selected != null) {
      residents.remove(selected);
    }
  }

  @FXML
  private void handleExport()
  {
    DataExporter.exportAllData();
  }
  @FXML
  private void handleGoToCompleteTask() {
    SceneManager.switchTo("complete-task.fxml");
  }
  @FXML
  private void handleGoToTaskPage() {
    SceneManager.switchTo("task-list.fxml");
  }

  @FXML
  private void handleEdit() {
    selectedResident = listView.getSelectionModel().getSelectedItem();
    if (selectedResident != null) {
      SceneManager.switchTo("edit-view.fxml");
    }




  }
}
