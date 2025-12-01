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
    private final RestClientApi restClientApi;
    private final ObservableList<CarDTO> availableCars;

    public MainController(RestClientApi restClientApi, ObservableList<CarDTO> availableCars) {
        this.restClientApi = restClientApi;
        this.availableCars = availableCars;
    }

    public VBox getRootLayout(){
        Button rentButton = new Button("Rent");
        Button dropOffButton = new Button("Drop Off");
        rentButton.setOnAction(event -> {
            try {
                renting();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        dropOffButton.setOnAction(event -> droppingOff());
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(rentButton,dropOffButton);
        return vBox;
    }

    private void renting() throws Exception {
        Stage stage  = new Stage();
        Renting renting= new Renting(restClientApi,availableCars);
        stage.setTitle("Renting");
        Scene scene = new Scene(renting.getLayout());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void droppingOff(){
        Stage stage  = new Stage();
        DropOff dropOff= new DropOff(restClientApi,availableCars);
        stage.setTitle("Drop Off");
        Scene scene = new Scene(dropOff.getLayout());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
