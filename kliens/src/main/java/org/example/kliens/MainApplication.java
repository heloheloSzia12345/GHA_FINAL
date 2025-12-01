package org.example.kliens;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.kliens.restclientapi.RestClientApi;

import java.net.http.HttpClient;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        MainController mainController = new MainController(new RestClientApi(httpClient,objectMapper), FXCollections.observableArrayList());
        Scene scene = new Scene(mainController.getRootLayout(),800,600);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("Car Rental System");
        stage.setScene(scene);
        stage.show();
    }
}