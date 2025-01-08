module mrm24jee {
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires javafx.graphics;
    requires static lombok;
    requires javafx.controls;
    requires javafx.fxml;

    exports model;
    exports dao;
    exports gui;

    opens gui to javafx.fxml;
    opens gui.controllers to javafx.fxml;
    opens gui.views to javafx.fxml;
    opens model to org.hibernate.orm.core, jakarta.persistence, javafx.base;
}
