module com.example.cloverville {
  requires javafx.controls;
  requires javafx.fxml;

  opens com.example.cloverville to javafx.fxml;
  exports com.example.cloverville;
}