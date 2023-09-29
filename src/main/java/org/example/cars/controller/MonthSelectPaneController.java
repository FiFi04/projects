package org.example.cars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class MonthSelectPaneController {
        @FXML
        private Button confirmationButton;

        @FXML
        private Label infoLabel;

        @FXML
        private ChoiceBox<Integer> monthsChoiceBox;

        public Button getConfirmationButton() {
                return confirmationButton;
        }

        public Label getInfoLabel() {
                return infoLabel;
        }

        public ChoiceBox<Integer> getMonthsChoiceBox() {
                return monthsChoiceBox;
        }

}
