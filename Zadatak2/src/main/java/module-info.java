module com.example.zadatak2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.zadatak2 to javafx.fxml;
    exports com.example.zadatak2;
}