package org.example.kliens;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import org.example.kliens.dto.CarDTO;
import org.example.kliens.restclientapi.RestClientApi;

import java.util.List;

public class MainController {
    private final RestClientApi restClientApi;
    private final ObservableList<CarDTO> availableCars;

    private FlowPane flowPane;
    private TableView<CarDTO> tableView;
    private TextField nameField;
    private TextField licenseNumField;
    private DatePicker dateOfBirthPicker;
    private DatePicker pickUpDatePicker;
    private DatePicker deadlinePicker;
    private TextField licensePlateField;
    private CheckBox checkBox;
    private Button executeButton;

    public MainController(RestClientApi restClientApi, ObservableList<CarDTO> availableCars) {
        this.restClientApi = restClientApi;
        this.availableCars = availableCars;
    }

    public void initialize() throws Exception {
        tableView= new TableView<>();
        nameField= new TextField();
        nameField.setPromptText("Name");
        licenseNumField= new TextField();
        licenseNumField.setPromptText("License Number");
        dateOfBirthPicker= new DatePicker();
        pickUpDatePicker= new DatePicker();
        deadlinePicker= new DatePicker();
        licensePlateField= new TextField();
        licensePlateField.setPromptText("License Plate");
        checkBox= new CheckBox("Renting");
        executeButton= new Button("Execute");

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

        flowPane= new FlowPane();
        flowPane.setMinSize(600,500);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(5);
        flowPane.setVgap(5);

        flowPane.getChildren().add(checkBox);
        flowPane.getChildren().add(nameField);
        flowPane.getChildren().add(licenseNumField);
        flowPane.getChildren().add(dateOfBirthPicker);
        flowPane.getChildren().add(pickUpDatePicker);
        flowPane.getChildren().add(deadlinePicker);
        flowPane.getChildren().add(tableView);
        flowPane.getChildren().add(licensePlateField);
        flowPane.getChildren().add(executeButton);

        executeButton.setOnAction(event -> {
            if(checkBox.isSelected()) {
                try {
                    restClientApi.renting(licensePlateField.getText(),licenseNumField.getText(),nameField.getText(),dateOfBirthPicker.getValue(),pickUpDatePicker.getValue(),deadlinePicker.getValue());
                    availableCars.setAll(restClientApi.getAllCars());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    restClientApi.dropOffCar(licensePlateField.getText(),nameField.getText(),deadlinePicker.getValue());
                    availableCars.setAll(restClientApi.getAllCars());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
    public FlowPane getRootLayout(){
        return flowPane;
    }
}
