module com.example.cloverville {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.cloverville to javafx.fxml, com.google.gson;
    exports com.example.cloverville;
}