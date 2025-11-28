package org.example.kliens;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.kliens.dto.CarDTO;
import org.example.kliens.dto.RentalDTO;
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
    private DatePicker dropOffDatePicker;
    private TextField licensePlateField;
    private CheckBox checkBox;
    private Button executeButton;

    public MainController(RestClientApi restClientApi, ObservableList<CarDTO> availableCars) {
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

    public void initialize() throws Exception {
        tableView= new TableView<>();
        nameField= new TextField();
        nameField.setPromptText("Name");
        licenseNumField= new TextField();
        licenseNumField.setPromptText("License Number");
        dateOfBirthPicker= new DatePicker();
        dateOfBirthPicker.setPromptText("Birth Date");
        pickUpDatePicker= new DatePicker();
        pickUpDatePicker.setPromptText("Pick-Up Date");
        deadlinePicker= new DatePicker();
        deadlinePicker.setPromptText("Deadline");
        dropOffDatePicker= new DatePicker();
        dropOffDatePicker.setPromptText("Return Date");
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
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox box=new VBox(10);
        box.setAlignment(Pos.TOP_LEFT);

        box.getChildren().add(checkBox);
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
        box.getChildren().add(new Label("Return Date:"));
        box.getChildren().add(dropOffDatePicker);
        box.getChildren().add(new Label("License Plate:"));
        box.getChildren().add(licensePlateField);
        box.getChildren().add(executeButton);



        flowPane= new FlowPane();
        flowPane.setMinSize(600,500);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(20);
        flowPane.setVgap(20);

        flowPane.getChildren().add(box);
        flowPane.getChildren().add(tableView);

        executeButton.setOnAction(event -> {
            if(checkBox.isSelected()) {
                try {
                    restClientApi.renting(licensePlateField.getText(),licenseNumField.getText(),nameField.getText(),dateOfBirthPicker.getValue(),pickUpDatePicker.getValue(),deadlinePicker.getValue());
                    availableCars.setAll(restClientApi.getAllCars());
                    showPreis("Car rented!");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    RentalDTO rental = restClientApi.dropOffCar(licenseNumField.getText(),nameField.getText(),dropOffDatePicker.getValue());
                    availableCars.setAll(restClientApi.getAllCars());
                    showPreis("Preis "+ rental.getPreis()+"FT");
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
