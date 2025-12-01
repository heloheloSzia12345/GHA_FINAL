package org.example.kliens.view;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.kliens.dto.CarDTO;
import org.example.kliens.dto.RentalDTO;
import org.example.kliens.restclientapi.RestClientApi;

import java.time.temporal.ChronoUnit;

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

    private void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
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

        VBox informationVBox = new VBox(10);
        informationVBox.setMinHeight(300);
        informationVBox.setMinWidth(200);
        Label dailyPriceLabel = new Label("Daily Price: 10000 FT");
        Label rentalDaysLabel = new Label("Rental Days: ");
        Label base = new Label("Base: ");
        Label penalty = new Label("Penalty: ");
        Label total = new Label("Total: ");

        informationVBox.getChildren().add(dailyPriceLabel);
        informationVBox.getChildren().add(new Separator());
        informationVBox.getChildren().add(rentalDaysLabel);
        informationVBox.getChildren().add(base);
        informationVBox.getChildren().add(penalty);
        informationVBox.getChildren().add(new Separator());
        informationVBox.getChildren().add(total);

        executeButton.setOnAction(event -> {
            if(nameField.getText().isEmpty() ||  licenseNumField.getText().isEmpty() || dropOffDatePicker.getValue() == null){
                showPreis("Please fill all the fields");
                return;
            }
                try {
                    RentalDTO rental = restClientApi.dropOffCar(licenseNumField.getText(),nameField.getText(),dropOffDatePicker.getValue());
                    availableCars.setAll(restClientApi.getAllCars());
                    Long daysPassed = ChronoUnit.DAYS.between(rental.getPickUpDate(), rental.getDropOffDate());
                    int baseAmount = (int) (daysPassed * 10000);
                    int penaltyAmount = rental.getPreis()-baseAmount;
                    rentalDaysLabel.setText("Rental Days: " + daysPassed);
                    base.setText("Base Amount: " + baseAmount + " FT");
                    if (penaltyAmount > 0) {
                        penalty.setText("Penalty Amount: " + penaltyAmount + " FT");
                    }else{
                        penalty.setText("Penalty Amount: 0 FT");
                    }
                    total.setText("Total: " + rental.getPreis() + " FT");
                    showPreis("Preis "+ rental.getPreis()+"FT");
                } catch (Exception e) {
                    showError("Error: "+e.getMessage());
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
        flowPane.getChildren().add(informationVBox);
        return flowPane;
    }






}
