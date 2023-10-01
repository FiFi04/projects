package org.example.cars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.cars.model.Car;
public class CarsTablePaneController {
    @FXML
    private TableView<Car> carsTable;
    @FXML
    private TableColumn<Car, String> brandColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, String> yearOfManufactureColumn;
    @FXML
    private TableColumn<Car, String> serviceDateColumn;
    @FXML
    private TableColumn<Car, String> insuranceDateColumn;
    @FXML
    private TableColumn<Car, String> licenseNumberColumn;


    public void initialize() {
    }

    public TableView<Car> getCarsTable() {
        return carsTable;
    }

    public TableColumn<Car, String> getBrandColumn() {
        return brandColumn;
    }

    public TableColumn<Car, String> getModelColumn() {
        return modelColumn;
    }

    public TableColumn<Car, String> getYearOfManufactureColumn() {
        return yearOfManufactureColumn;
    }

    public TableColumn<Car, String> getServiceDateColumn() {
        return serviceDateColumn;
    }

    public TableColumn<Car, String> getInsuranceDateColumn() {
        return insuranceDateColumn;
    }

    public TableColumn<Car, String> getLicenseNumberColumn() {
        return licenseNumberColumn;
    }
}
