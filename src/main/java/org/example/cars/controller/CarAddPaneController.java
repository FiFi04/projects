package org.example.cars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CarAddPaneController {

    @FXML
    private Button addButton;

    @FXML
    private TextField brandTextField;

    @FXML
    private DatePicker insuranceDatePicker;

    @FXML
    private TextField licencePlateTextField;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField prodYearTextField;

    @FXML
    private DatePicker serviceDatePicker;

    public void initialize() {
    }

    public Button getAddButton() {
        return addButton;
    }

    public TextField getBrandTextField() {
        return brandTextField;
    }

    public DatePicker getInsuranceDatePicker() {
        return insuranceDatePicker;
    }

    public TextField getLicencePlateTextField() {
        return licencePlateTextField;
    }

    public TextField getModelTextField() {
        return modelTextField;
    }

    public TextField getProdYearTextField() {
        return prodYearTextField;
    }

    public DatePicker getServiceDatePicker() {
        return serviceDatePicker;
    }
}

