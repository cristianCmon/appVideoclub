module com.example.appvideoclub {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.appvideoclub to javafx.fxml;
    exports com.example.appvideoclub;
}