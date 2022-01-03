module com.example.chatapp_ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.chatapp_ui to javafx.fxml;
    exports com.example.chatapp_ui;

    opens com.example.chatapp_ui.controllers to javafx.fxml;
    exports com.example.chatapp_ui.controllers;
}