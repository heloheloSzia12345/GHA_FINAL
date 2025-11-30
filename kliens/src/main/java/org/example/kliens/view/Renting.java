package org.example.kliens.view;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
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

    public Renting(RestClientApi restClientApi,ObservableList<CarDTO> availableCars) {
        this.restClientApi = restClientApi;
        this.availableCars=availableCars;
    }

    public FlowPane getLayout() throws Exception {
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
        Button executeButton= new Button("Execute");

        TableColumn<CarDTO, String> licensePlateCol = new TableColumn<>("LicensePlate");
        licensePlateCol.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        TableColumn<CarDTO, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        TableColumn<CarDTO, String> carTypeCol = new TableColumn<>("carType");
        carTypeCol.setCellValueFactory(new PropertyValueFactory<>("carType"));
        TableColumn<CarDTO, String> colorCol = new TableColumn<>("color");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        tableView.getColumns().addAll(licensePlateCol, brandCol,carTypeCol,colorCol);
        List<CarDTO> cars = restClientApi.getAllCars();
        availableCars.setAll(cars);
        tableView.setItems(availableCars);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox box=new VBox(10);
        box.setAlignment(Pos.TOP_LEFT);

        box.getChildren().add(new Label("Name:"));
        box.getChildren().add(nameField);
        box.getChildren().add(new Label("License Number: "));
        box.getChildren().add(licenseNumField);
        box.getChildren().add(new Label("Birth Date:"));
        box.getChildren().add(dateOfBirthPicker);
        box.getChildren().add(new Label("Pick-Up Date:"));
        box.getChildren().add(pickUpDatePicker);
        box.getChildren().add(new Label("Deadline:"));
        box.getChildren().add(deadlinePicker);
        box.getChildren().add(new Label("License Plate:"));
        box.getChildren().add(licensePlateField);
        box.getChildren().add(executeButton);

        FlowPane flowPane= new FlowPane();
        flowPane.setMinSize(600,500);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(20);
        flowPane.setVgap(20);

        flowPane.getChildren().add(box);
        flowPane.getChildren().add(tableView);

        executeButton.setOnAction(event -> {
            try {
                restClientApi.renting(licensePlateField.getText(), licenseNumField.getText(), nameField.getText(), dateOfBirthPicker.getValue(), pickUpDatePicker.getValue(), deadlinePicker.getValue());
                availableCars.setAll(restClientApi.getAllCars());
                showPreis("Car rented!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return flowPane;
    }
}
