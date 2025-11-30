package org.example.kliens.view;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.kliens.dto.CarDTO;
import org.example.kliens.dto.RentalDTO;
import org.example.kliens.restclientapi.RestClientApi;

public class DropOff {

    private final RestClientApi restClientApi;
    private final ObservableList<CarDTO> availableCars;

    public DropOff(RestClientApi restClientApi,ObservableList<CarDTO> availableCars) {
        this.restClientApi = restClientApi;
        this.availableCars = availableCars;
    }

    private void showPreis(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Preis");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public FlowPane getLayout(){
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField licenseNumField = new TextField();
        licenseNumField.setPromptText("License Number");

        DatePicker dropOffDatePicker = new DatePicker();
        dropOffDatePicker.setPromptText("Drop-Off Date");

        Button executeButton = new Button("Execute");

        executeButton.setOnAction(event -> {
                try {
                    RentalDTO rental = restClientApi.dropOffCar(licenseNumField.getText(),nameField.getText(),dropOffDatePicker.getValue());
                    availableCars.setAll(restClientApi.getAllCars());
                    showPreis("Preis "+ rental.getPreis()+"FT");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        });

        VBox box=new VBox(10);
        box.setAlignment(Pos.TOP_LEFT);

        box.getChildren().add(new Label("Name:"));
        box.getChildren().add(nameField);
        box.getChildren().add(new Label("License Number: "));
        box.getChildren().add(licenseNumField);
        box.getChildren().add(new Label("Return Date:"));
        box.getChildren().add(dropOffDatePicker);
        box.getChildren().add(executeButton);

        FlowPane flowPane= new FlowPane();
        flowPane.setMinSize(600,500);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(20);
        flowPane.setVgap(20);

        flowPane.getChildren().add(box);
        return flowPane;
    }






}
