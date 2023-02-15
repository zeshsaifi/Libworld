module com.zeeshan.libworld {
    requires javafx.controls;
    requires javafx.fxml;
    requires mail;
    requires java.sql;


    opens com.zeeshan.libworld to javafx.fxml;
    exports com.zeeshan.libworld;
}