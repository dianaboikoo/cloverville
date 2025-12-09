package com.example.cloverville;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TaskEditController {

    @FXML private TextField nameField;
    @FXML private TextArea descriptionField;
    @FXML private TextField personalPointsField;

    @FXML
    public void initialize() {
        CommunalTask task = TaskListController.selectedTask;

        nameField.setText(task.getName());
        descriptionField.setText(task.getDescription());
        personalPointsField.setText(String.valueOf(task.getPersonalPoints()));
    }

    @FXML
    private void handleSave() {
        CommunalTask task = TaskListController.selectedTask;

        task.setName(nameField.getText());
        task.setDescription(descriptionField.getText());
        task.setPersonalPoints(Integer.parseInt(personalPointsField.getText()));

        SceneManager.switchTo("task-list.fxml");
    }

    @FXML
    private void handleBack() {
        SceneManager.switchTo("task-list.fxml");
    }
}
