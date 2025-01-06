module com.emsi.mrm24 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.emsi.mrm24 to javafx.fxml;
    exports com.emsi.mrm24;
}