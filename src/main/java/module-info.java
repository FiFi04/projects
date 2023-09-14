module cars {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    exports org.example.cars.view to javafx.graphics;
    opens org.example.cars.controller to javafx.fxml;
}