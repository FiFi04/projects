package org.example.cars.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.cars.model.Car;
import org.example.cars.service.CarService;

import java.io.IOException;
import java.util.List;

public class MainPaneController {
    @FXML
    private ListView<String> optionsListView;
    @FXML
    private CarsTablePaneController carsTablePaneController;
    @FXML
    private MonthInputPaneController monthInputPaneController;
    private CarService carService = new CarService();
    private final String INSURANCE_DATE_ENDING = "Auta z kończącym się ubezpieczeniem";
    private final String INSURANCE_DATE_ENDING_BY_MONTH = "Auta z kończącym się ubezpieczeniem w miesiącu...";
    private final String SERVICE_DATE_ENDING = "Auta z kończącym się przeglądem";
    private final String SERVICE_DATE_ENDING_BY_MONTH = "Auta z kończącym się przeglądem w miesiącu...";
    private final String CARS_BY_BRAND = "Znajdź auto według marki";
    private final String CARS_BY_MODEL = "Znajdź auto według modelu";
    private final String SORT_BY_BRAND = "Posortuj auta według marki";
    private final String ADD_CAR = "Dodaj auto";
    private final String DELETE_CAR = "Usuń auto";

    private List<String> options = List.of(INSURANCE_DATE_ENDING, INSURANCE_DATE_ENDING_BY_MONTH, SERVICE_DATE_ENDING,
            SERVICE_DATE_ENDING_BY_MONTH, CARS_BY_BRAND, CARS_BY_MODEL, SORT_BY_BRAND, ADD_CAR, DELETE_CAR);

    public void initialize() {
        optionsListView.setItems(FXCollections.observableArrayList(options));
        optionsListView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            System.out.println(optionsListView.getSelectionModel().getSelectedItems().toString());
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("["+INSURANCE_DATE_ENDING+"]")) {
                initializeTable(FXCollections.observableList(carService.getCarsWithInsuranceDateExpiring()));
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("["+INSURANCE_DATE_ENDING_BY_MONTH+"]")) {
                initializeTable(FXCollections.observableList(carService.getCarsWithInsuranceDateExpiringByMonth(11)));
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("["+SERVICE_DATE_ENDING+"]")) {
                initializeTable(FXCollections.observableList(carService.getCarsWithServiceDateExpiring()));
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("["+SERVICE_DATE_ENDING_BY_MONTH+"]")) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/carsApp/javaFx/monthInput.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setScene(new Scene(root));
                stage.show();
                monthInputPaneController.getMonthTextField()/////////////////////////
                initializeTable(FXCollections.observableList(carService.getCarsWithServiceDateExpiringByMonth(11)));
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("["+CARS_BY_BRAND+"]")) {
                initializeTable(FXCollections.observableList(carService.getCarsByBrand("renault")));
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("["+CARS_BY_MODEL+"]")) {
                initializeTable(FXCollections.observableList(carService.getCarsByModel("clio")));
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("["+SORT_BY_BRAND+"]")) {
                initializeTable(FXCollections.observableList(carService.getCarsSortedByBrandName()));
            }
        });
    }

    private void initializeTable(ObservableList<Car> carsData) {
        carsTablePaneController.getCarsTable().getItems().clear();
        carsTablePaneController.getCarsTable().getItems().addAll(carsData);
        carsTablePaneController.getBrandColumn().setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getBrand()));
        carsTablePaneController.getModelColumn().setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getModel()));
        carsTablePaneController.getYearOfManufactureColumn().setCellValueFactory(car -> new SimpleStringProperty(String.valueOf(car.getValue().getManufactureYear())));
        carsTablePaneController.getServiceDateColumn().setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getServiceDate().toString()));
        carsTablePaneController.getInsuranceDateColumn().setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getInsuranceDate().toString()));
        carsTablePaneController.getLicenseNumberColumn().setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getLicenseNumber()));
    }
}

