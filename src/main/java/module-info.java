module com.example.capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.capstone12 to javafx.fxml;
    exports com.example.capstone12;
    opens com.example.capstone12.gui to javafx.fxml;
    exports com.example.capstone12.gui;
}
