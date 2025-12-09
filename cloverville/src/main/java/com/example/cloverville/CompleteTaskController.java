package com.example.cloverville;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class CompleteTaskController {

    @FXML private ComboBox<Resident> residentCombo;
    @FXML private ComboBox<CommunalTask> taskCombo;
    @FXML private ListView<TaskLogEntry> logList;

    @FXML
    public void initialize() {
        // Load available residents and tasks
        residentCombo.setItems(ListController.residents);
        taskCombo.setItems(TaskListController.tasks);

        // Show history
        logList.setItems(TaskLog.log);
    }

    @FXML
    private void handleComplete() {
        Resident r = residentCombo.getValue();
        CommunalTask t = taskCombo.getValue();

        if (r == null || t == null) return;

        // Add green points (Option C 😊)
        int newPoints = r.getGreenPoints() + t.getPersonalPoints();
        r.setPersonalPoints(newPoints);

        // Log entry (Option D 😊)
        TaskLogEntry entry = new TaskLogEntry(r, t);
        TaskLog.log.add(entry);

        // Refresh UI
        logList.refresh();
    }

    @FXML
    private void handleBack() {
        SceneManager.switchTo("list-view.fxml");
    }
}
