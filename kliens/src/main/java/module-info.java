module org.example.kliens {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires static lombok;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    opens org.example.kliens to javafx.fxml;
    exports org.example.kliens;
}