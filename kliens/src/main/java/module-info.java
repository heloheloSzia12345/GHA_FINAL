module org.example.kliens {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires static lombok;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires javafx.base;
    opens org.example.kliens to javafx.fxml, javafx.graphics;
    opens org.example.kliens.dto to com.fasterxml.jackson.databind, javafx.base;
    opens org.example.kliens.restclientapi to javafx.fxml;
    exports org.example.kliens;
    exports org.example.kliens.dto;
    exports org.example.kliens.restclientapi;
}