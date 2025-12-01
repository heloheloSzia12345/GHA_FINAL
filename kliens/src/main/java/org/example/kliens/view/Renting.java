package org.example.kliens.view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.example.kliens.dto.CarDTO;
import org.example.kliens.restclientapi.RestClientApi;

import java.util.List;

public class Renting {

    private final RestClientApi restClientApi;
    private final ObservableList<CarDTO> availableCars;

    private void showPreis(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Preis");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Renting(RestClientApi restClientApi,ObservableList<CarDTO> availableCars) {
        this.restClientApi = restClientApi;
        this.availableCars=availableCars;
    }

    public BorderPane getLayout() throws Exception {
        TableView<CarDTO> tableView= new TableView<>();
        TextField nameField= new TextField();
        nameField.setPromptText("Name");
        TextField licenseNumField= new TextField();
        licenseNumField.setPromptText("License Number");
        DatePicker dateOfBirthPicker= new DatePicker();
        dateOfBirthPicker.setPromptText("Birth Date");
        DatePicker pickUpDatePicker= new DatePicker();
        pickUpDatePicker.setPromptText("Pick-Up Date");
        DatePicker deadlinePicker= new DatePicker();
        deadlinePicker.setPromptText("Deadline");
        TextField licensePlateField= new TextField();
        licensePlateField.setPromptText("License Plate");
        Button executeButton = new Button("\uf00c Execute");
        executeButton.getStyleClass().add("icon-button");

        TableColumn<CarDTO, String> licensePlateCol = new TableColumn<>("License Plate");
        licensePlateCol.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        TableColumn<CarDTO, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        TableColumn<CarDTO, String> carTypeCol = new TableColumn<>("Car Type");
        carTypeCol.setCellValueFactory(new PropertyValueFactory<>("carType"));
        TableColumn<CarDTO, String> colorCol = new TableColumn<>("color");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        tableView.getColumns().addAll(licensePlateCol, brandCol,carTypeCol,colorCol);
        List<CarDTO> cars = restClientApi.getAllCars();
        availableCars.setAll(cars);
        tableView.setItems(availableCars);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        GridPane leftPane = new GridPane();
        leftPane.setHgap(20);
        leftPane.setVgap(15);
        leftPane.setPadding(new Insets(20));
        leftPane.setAlignment(Pos.TOP_LEFT);
        leftPane.add(new Label("Name:"), 0, 0);
        leftPane.add(nameField, 1, 0);
        leftPane.add(new Label("License Number:"), 0, 1);
        leftPane.add(licenseNumField, 1, 1);
        leftPane.add(new Label("Birth Date:"), 0, 2);
        leftPane.add(dateOfBirthPicker, 1, 2);
        leftPane.add(new Label("Pick-Up Date:"), 0, 3);
        leftPane.add(pickUpDatePicker, 1, 3);
        leftPane.add(new Label("Deadline:"), 0, 4);
        leftPane.add(deadlinePicker, 1, 4);
        leftPane.add(new Label("License Plate:"), 0, 5);
        leftPane.add(licensePlateField, 1, 5);
        HBox buttonBox = new HBox(executeButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        leftPane.add(buttonBox, 1, 6);

        BorderPane root = new BorderPane();
        root.setLeft(leftPane);
        root.setCenter(tableView);
        root.setPadding(new Insets(20));

        executeButton.setOnAction(event -> {
            if(nameField.getText().isEmpty() || licenseNumField.getText().isEmpty() || dateOfBirthPicker.getValue()==null || pickUpDatePicker.getValue()==null || deadlinePicker.getValue()==null || licensePlateField.getText().isEmpty()){
                showPreis("Please fill all the fields!");
                return;
            }
            try {
                restClientApi.renting(licensePlateField.getText(), licenseNumField.getText(), nameField.getText(), dateOfBirthPicker.getValue(), pickUpDatePicker.getValue(), deadlinePicker.getValue());
                availableCars.setAll(restClientApi.getAllCars());
                showPreis("Car rented!");
            } catch (Exception e) {
                showError("Error: "+e.getMessage());
            }
        });

        root.getStyleClass().add("renting-root");

        return root;
    }
}
