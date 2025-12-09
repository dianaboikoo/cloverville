package com.example.cloverville;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager{

    private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void switchTo(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxml));
            Scene scene = new Scene(loader.load());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
