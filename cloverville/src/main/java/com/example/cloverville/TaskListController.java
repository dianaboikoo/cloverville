package com.example.cloverville;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TaskListController {

    @FXML private ListView<CommunalTask> taskListView;
    @FXML private TextField nameField;
    @FXML private TextArea descriptionField;
    @FXML private TextField personalPointsField;

    // THIS is what CompleteTaskController uses:
    public static ObservableList<CommunalTask> tasks = FXCollections.observableArrayList();

    // Also needed so the Edit page knows which task was selected
    public static CommunalTask selectedTask;

    @FXML
    public void initialize() {
        taskListView.setItems(tasks);
    }

    @FXML
    private void handleAdd() {
        String name = nameField.getText();
        String desc = descriptionField.getText();
        int points = personalPointsField.getText().isEmpty() ? 0 : Integer.parseInt(personalPointsField.getText());

        if (name.isEmpty()) return;

        CommunalTask task = new CommunalTask(name, desc, points);
        tasks.add(task);

        nameField.clear();
        descriptionField.clear();
        personalPointsField.clear();
    }

    @FXML
    private void handleDelete() {
        CommunalTask selected = taskListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tasks.remove(selected);
        }
    }
    @FXML
    private void handleBack() {
        SceneManager.switchTo("list-view.fxml"); // Go back to residents page, or home page later
    }


    @FXML
    private void handleEdit() {
        selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            SceneManager.switchTo("task-edit.fxml");
        }
    }
}
