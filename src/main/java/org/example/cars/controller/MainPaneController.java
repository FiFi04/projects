package org.example.cars.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.cars.model.Car;
import org.example.cars.service.CarService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainPaneController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private VBox mainAreaVBox;
    @FXML
    private ListView<String> optionsListView;
    @FXML
    private CarsTablePaneController carsTablePaneController;

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
    private final String CARS_TABLE_PANE_PATH = "/carsApp/javaFx/carsTablePane.fxml";
    private final String INPUT_PANE_PATH = "/carsApp/javaFx/inputPane.fxml";
    private final String CAR_ADD_PANE_PATH = "/carsApp/javaFx/carAddPane.fxml";
    private final String POPUP_PANE_PATH = "/carsApp/javaFx/PopupPane.fxml";

    private List<String> options = List.of(INSURANCE_DATE_ENDING, INSURANCE_DATE_ENDING_BY_MONTH, SERVICE_DATE_ENDING,
            SERVICE_DATE_ENDING_BY_MONTH, CARS_BY_BRAND, CARS_BY_MODEL, SORT_BY_BRAND, ADD_CAR, DELETE_CAR);

    public void initialize() {
        optionsListView.setItems(FXCollections.observableArrayList(options));
        optionsListView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            mainAreaVBox.getChildren().clear();
            carsTablePaneController = (CarsTablePaneController) getController(CARS_TABLE_PANE_PATH);
            System.out.println(optionsListView.getSelectionModel().getSelectedItems().toString());
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("[" + INSURANCE_DATE_ENDING + "]")) {
                initializeTable(FXCollections.observableList(carService.getCarsWithInsuranceDateExpiring()));
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("[" + INSURANCE_DATE_ENDING_BY_MONTH + "]")) {
                InputPaneController controller = (InputPaneController) getController(INPUT_PANE_PATH);
                controller.getConfirmationButton().addEventHandler(ActionEvent.ACTION, actionEvent -> {
                    Integer month = Integer.parseInt(controller.getTextField().getText());
                    initializeTable(FXCollections.observableList(carService.getCarsWithInsuranceDateExpiringByMonth(month)));
                });
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("[" + SERVICE_DATE_ENDING + "]")) {
                initializeTable(FXCollections.observableList(carService.getCarsWithServiceDateExpiring()));
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("[" + SERVICE_DATE_ENDING_BY_MONTH + "]")) {
                InputPaneController controller = (InputPaneController) getController(INPUT_PANE_PATH);
                controller.getConfirmationButton().addEventHandler(ActionEvent.ACTION, actionEvent -> {
                    Integer month = Integer.parseInt(controller.getTextField().getText());
                    initializeTable(FXCollections.observableList(carService.getCarsWithServiceDateExpiringByMonth(month)));
                });
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("[" + CARS_BY_BRAND + "]")) {
                InputPaneController controller = (InputPaneController) getController(INPUT_PANE_PATH);
                controller.getInfoLabel().setText("Marka");
                controller.getConfirmationButton().addEventHandler(ActionEvent.ACTION, actionEvent -> {
                    String brand = controller.getTextField().getText();
                    initializeTable(FXCollections.observableList(carService.getCarsByBrand(brand)));
                });
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("[" + CARS_BY_MODEL + "]")) {
                InputPaneController controller = (InputPaneController) getController(INPUT_PANE_PATH);
                controller.getInfoLabel().setText("Model");
                controller.getConfirmationButton().addEventHandler(ActionEvent.ACTION, actionEvent -> {
                    String model = controller.getTextField().getText();
                    initializeTable(FXCollections.observableList(carService.getCarsByModel(model)));
                });
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("[" + SORT_BY_BRAND + "]")) {
                initializeTable(FXCollections.observableList(carService.getCarsSortedByBrandName()));
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("[" + ADD_CAR + "]")) {
                CarAddPaneController controller = (CarAddPaneController) getController(CAR_ADD_PANE_PATH);
                initializeTable(FXCollections.observableList(carService.getCarsSortedByBrandName()));
                controller.getAddButton().addEventHandler(ActionEvent.ACTION, actionEvent -> {
                    Car car = createCar(controller);
                    try {
                        carService.add(car);
                        showPopupWindow("Auto zostało dodane do listy");
                    } catch (Exception e) {
                        showPopupWindow(e.getMessage());
                    }
                });
            }
            if (optionsListView.getSelectionModel().getSelectedItems().toString().equals("[" + DELETE_CAR + "]")) {
                InputPaneController inputPaneController = (InputPaneController) getController(INPUT_PANE_PATH);
                initializeTable(FXCollections.observableList(carService.getCarsSortedByBrandName()));
                inputPaneController.getConfirmationButton().setText("Usuń");
                inputPaneController.getInfoLabel().setText("Wprowadź nr rejestracyjny lub wybierz auto z listy:");
                List<Car> cars = new ArrayList<>();
                carsTablePaneController.getCarsTable().addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEventCarTable ->
                    cars.add(carsTablePaneController.getCarsTable().getSelectionModel().getSelectedItems().get(0))
                );
                inputPaneController.getConfirmationButton().addEventHandler(ActionEvent.ACTION, actionEvent ->
                        System.out.println(deleteCar(inputPaneController, cars))
                );
            }
        });
    }

    private Car createCar (CarAddPaneController controller) {
        String brand = controller.getBrandTextField().getText();
        String model = controller.getModelTextField().getText();
        Integer manufactureYear = Integer.parseInt(controller.getProdYearTextField().getText());
        LocalDate serviceDate = controller.getServiceDatePicker().getValue();
        LocalDate insuranceDate = controller.getInsuranceDatePicker().getValue();
        String licencePlate = controller.getLicencePlateTextField().getText();
        return new Car(brand, model, manufactureYear, serviceDate, insuranceDate, licencePlate);
    }

    private boolean deleteCar(InputPaneController inputPaneController, List<Car> cars) {
        if(inputPaneController.getTextField().getText().isEmpty()) {
            carService.delete(cars.get(cars.size()-1));
            return true;
        } else {
            String carLicense = inputPaneController.getTextField().getText();
            for (Car car : carService.getCarsSortedByBrandName()) {
                if (car.getLicenseNumber().equals(carLicense)) {
                    carService.delete(car);
                    return true;
                }
            }
        }
        return false;
    }

    private void showPopupWindow(String message) {
        Stage stage = new Stage();
        VBox root = null;
        PopupPaneController controller;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(POPUP_PANE_PATH));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller.getMessageLabel().setText(message);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("INFO");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        controller.getConfirmationButton().addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent ->
                stage.close()
        );
    }

    private PaneController getController(String path) {
        PaneController controller;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            controller = loader.getController();
            mainAreaVBox.getChildren().add(0, root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return controller;
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

