package org.example.kliens;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.kliens.dto.CarDTO;
import org.example.kliens.restclientapi.RestClientApi;
import org.example.kliens.view.DropOff;
import org.example.kliens.view.Renting;

public class MainController {
    private final RestClientApi restClientApi; // Kommunikáció a REST API-n keresztül
    private final ObservableList<CarDTO> availableCars; // A bérelhető autók, ami folyamat frissül

    public MainController(RestClientApi restClientApi, ObservableList<CarDTO> availableCars) {
        this.restClientApi = restClientApi;
        this.availableCars = availableCars;
    }

    // Főmenü elrendezése
    public VBox getRootLayout() {
        Button rentButton = new Button("\uf5de Rent"); // Fonts Awesome
        rentButton.getStyleClass().add("icon-button");
        Button dropOffButton = new Button("Drop Off  \uf11e");
        dropOffButton.getStyleClass().add("icon-button"); // css
        // Event handling
        rentButton.setOnAction(event -> {
            try {
                renting();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        // Event handling
        dropOffButton.setOnAction(event -> droppingOff());
        // Külső Layout
        VBox vBox = new VBox(10);
        vBox.getStyleClass().add("main-menu-bg");
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(rentButton, dropOffButton);
        return vBox;
    }

    // Bérlés esetén
    private void renting() throws Exception {
        Stage stage = new Stage(); // Új ablak
        Renting renting = new Renting(restClientApi, availableCars);
        stage.setTitle("Renting");
        Scene scene = new Scene(renting.getLayout());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // Visszaadás esetén
    private void droppingOff() {
        Stage stage = new Stage(); // Új ablak
        DropOff dropOff = new DropOff(restClientApi, availableCars);
        stage.setTitle("Drop Off");
        Scene scene = new Scene(dropOff.getLayout());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
