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

import static org.example.cars.controller.ControllerUtils.*;

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

    private List<String> options = List.of(INSURANCE_DATE_ENDING, INSURANCE_DATE_ENDING_BY_MONTH, SERVICE_DATE_ENDING,
            SERVICE_DATE_ENDING_BY_MONTH, CARS_BY_BRAND, CARS_BY_MODEL, SORT_BY_BRAND, ADD_CAR, DELETE_CAR);

    public void initialize() {
        optionsListView.setItems(FXCollections.observableArrayList(options));
        optionsListView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mainAreaVBox.getChildren().size() > 1) {
                mainAreaVBox.getChildren().remove(0);
            }
            System.out.println(optionsListView.getSelectionModel().getSelectedItems().toString());
            if (isOptionChosen(INSURANCE_DATE_ENDING)) {
                initializeTable(FXCollections.observableList(carService.getCarsWithInsuranceDateExpiring()));
            }
            if (isOptionChosen(INSURANCE_DATE_ENDING_BY_MONTH)) {
                MonthSelectPaneController controller = (MonthSelectPaneController) getController(MONTH_INPUT_PANE_PATH);
                controller.getMonthsChoiceBox().setItems(FXCollections.observableArrayList(MONTHS));
                findCarsByDateExpiring(controller, INSURANCE_DATE_ENDING_BY_MONTH);
            }
            if (isOptionChosen(SERVICE_DATE_ENDING)) {
                initializeTable(FXCollections.observableList(carService.getCarsWithServiceDateExpiring()));
            }
            if (isOptionChosen(SERVICE_DATE_ENDING_BY_MONTH)) {
                MonthSelectPaneController controller = (MonthSelectPaneController) getController(MONTH_INPUT_PANE_PATH);
                controller.getMonthsChoiceBox().setItems(FXCollections.observableArrayList(MONTHS));
                findCarsByDateExpiring(controller, SERVICE_DATE_ENDING_BY_MONTH);
            }
            if (isOptionChosen(CARS_BY_BRAND)) {
                InputPaneController inputPaneController = (InputPaneController) getController(INPUT_PANE_PATH);
                inputPaneController.getInfoLabel().setText("Marka");
                findCarsByBrandModel(inputPaneController, CARS_BY_BRAND);
            }
            if (isOptionChosen(CARS_BY_MODEL)) {
                InputPaneController inputPaneController = (InputPaneController) getController(INPUT_PANE_PATH);
                inputPaneController.getInfoLabel().setText("Model");
                findCarsByBrandModel(inputPaneController, CARS_BY_MODEL);
            }
            if (isOptionChosen(SORT_BY_BRAND)) {
                initializeTable(FXCollections.observableList(carService.getCarsSortedByBrandName()));
            }
            if (isOptionChosen(ADD_CAR)) {
                CarAddPaneController controller = (CarAddPaneController) getController(CAR_ADD_PANE_PATH);
                initializeTable(FXCollections.observableList(carService.getCarsSortedByBrandName()));
                addCarToDatabase(controller);
            }
            if (isOptionChosen(DELETE_CAR)) {
                InputPaneController inputPaneController = (InputPaneController) getController(INPUT_PANE_PATH);
                initializeTable(FXCollections.observableList(carService.getCarsSortedByBrandName()));
                inputPaneController.getConfirmationButton().setText("Usuń");
                inputPaneController.getInfoLabel().setText("Wprowadź nr rejestracyjny lub wybierz auto z listy:");
                deleteCarFromDatabase(inputPaneController);
            }
        });
    }

    private void deleteCarFromDatabase(InputPaneController inputPaneController) {
        List<Car> cars = new ArrayList<>();
        carsTablePaneController.getCarsTable().addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEventCarTable ->
                cars.add(carsTablePaneController.getCarsTable().getSelectionModel().getSelectedItems().get(0))
        );
        inputPaneController.getConfirmationButton().addEventHandler(ActionEvent.ACTION, actionEvent ->
                System.out.println(deleteCar(inputPaneController, cars))
        );
    }

    private void addCarToDatabase(CarAddPaneController controller) {
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

    private void findCarsByDateExpiring(MonthSelectPaneController controller, String option) {
        controller.getConfirmationButton().addEventHandler(ActionEvent.ACTION, actionEvent -> {
            Integer month = controller.getMonthsChoiceBox().getValue();
            List<Car> cars = option.equals(INSURANCE_DATE_ENDING_BY_MONTH) ? carService.getCarsWithInsuranceDateExpiringByMonth(month) :
                    carService.getCarsWithServiceDateExpiringByMonth(month);
            initializeTable(FXCollections.observableList(cars));
        });
    }

    private void findCarsByBrandModel(InputPaneController controller, String option) {
        controller.getConfirmationButton().addEventHandler(ActionEvent.ACTION, actionEvent -> {
            String textField = controller.getTextField().getText();
            List<Car> cars = option.equals(CARS_BY_BRAND) ? carService.getCarsByBrand(textField) : carService.getCarsByModel(textField);
            initializeTable(FXCollections.observableList(cars));
        });
    }

    private boolean isOptionChosen(String option) {
        return optionsListView.getSelectionModel().getSelectedItems().toString().equals("[" + option + "]");
    }

    private Car createCar(CarAddPaneController controller) {
        String brand = controller.getBrandTextField().getText();
        String model = controller.getModelTextField().getText();
        Integer manufactureYear = Integer.parseInt(controller.getProdYearTextField().getText());
        LocalDate serviceDate = controller.getServiceDatePicker().getValue();
        LocalDate insuranceDate = controller.getInsuranceDatePicker().getValue();
        String licencePlate = controller.getLicencePlateTextField().getText().toUpperCase();
        return new Car(brand, model, manufactureYear, serviceDate, insuranceDate, licencePlate);
    }

    private boolean deleteCar(InputPaneController inputPaneController, List<Car> cars) {
        if (inputPaneController.getTextField().getText().isEmpty()) {
            if (shouldDeletePopupWindow(cars.get(cars.size() - 1))) {
                carService.delete(cars.get(cars.size() - 1));
                return true;
            }
        } else {
            String carLicense = inputPaneController.getTextField().getText();
            for (Car car : carService.getCarsSortedByBrandName()) {
                if (car.getLicenseNumber().equalsIgnoreCase(carLicense)) {
                    if (shouldDeletePopupWindow(car)) {
                        carService.delete(car);
                        return true;
                    }
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

    private boolean shouldDeletePopupWindow(Car car) {
        Stage stage = new Stage();
        VBox root = null;
        PopupDeletePaneController controller;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(POPUP_DELETE_PANE_PATH));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("INFO");
        stage.initModality(Modality.APPLICATION_MODAL);
        controller.getConfirmationButton().addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    controller.setDeleteStatus(true);
                    stage.close();
                }
        );
        controller.getCancelButton().addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    controller.setDeleteStatus(false);
                    stage.close();
                }
        );
        stage.showAndWait();
        return controller.isDelete();
    }

    private Object getController(String path) {
        Object controller;
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
        carsTablePaneController.getCarsTable().setItems(carsData);
        carsTablePaneController.getBrandColumn().setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getBrand()));
        carsTablePaneController.getModelColumn().setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getModel()));
        carsTablePaneController.getYearOfManufactureColumn().setCellValueFactory(car -> new SimpleStringProperty(String.valueOf(car.getValue().getManufactureYear())));
        carsTablePaneController.getServiceDateColumn().setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getServiceDate().toString()));
        carsTablePaneController.getInsuranceDateColumn().setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getInsuranceDate().toString()));
        carsTablePaneController.getLicenseNumberColumn().setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getLicenseNumber()));
    }
}

