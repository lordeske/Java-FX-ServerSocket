module com.example.zadatcic {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zadatcic to javafx.fxml;
    exports com.example.zadatcic;
}