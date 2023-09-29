package org.example.cars.controller;

import java.util.List;

final class ControllerUtils {
    static final String INSURANCE_DATE_ENDING = "Auta z kończącym się ubezpieczeniem";
    static final String INSURANCE_DATE_ENDING_BY_MONTH = "Auta z kończącym się ubezpieczeniem w miesiącu...";
    static final String SERVICE_DATE_ENDING = "Auta z kończącym się przeglądem";
    static final String SERVICE_DATE_ENDING_BY_MONTH = "Auta z kończącym się przeglądem w miesiącu...";
    static final String CARS_BY_BRAND = "Znajdź auto według marki";
    static final String CARS_BY_MODEL = "Znajdź auto według modelu";
    static final String SORT_BY_BRAND = "Posortuj auta według marki";
    static final String ADD_CAR = "Dodaj auto";
    static final String DELETE_CAR = "Usuń auto";
    static final String CARS_TABLE_PANE_PATH = "/carsApp/javaFx/carsTablePane.fxml";
    static final String INPUT_PANE_PATH = "/carsApp/javaFx/inputPane.fxml";
    static final String MONTH_INPUT_PANE_PATH = "/carsApp/javaFx/monthSelectPane.fxml";
    static final String CAR_ADD_PANE_PATH = "/carsApp/javaFx/carAddPane.fxml";
    static final String POPUP_PANE_PATH = "/carsApp/javaFx/PopupPane.fxml";

    static final String POPUP_DELETE_PANE_PATH = "/carsApp/javaFx/PopupDeletePane.fxml";
    static final List<Integer> MONTHS = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);


    private ControllerUtils() {
    }
}
