module com.emsi.mrm24 {   // This declares your module name
    requires javafx.controls;   // This declares that your module depends on the javafx.controls module
    requires javafx.fxml;       // This declares that your module depends on the javafx.fxml module

    requires org.kordamp.bootstrapfx.core;   // External dependency (BootstrapFX)

    requires lombok;   // Lombok dependency for annotations

    opens com.emsi.mrm24 to javafx.fxml;   // This allows JavaFX to access the packages in com.emsi.mrm24
    exports com.emsi.mrm24;   // Expose the packages for external use
}
