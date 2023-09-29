package org.example.cars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PopupDeletePaneController {
        @FXML
        private Button cancelButton;

        @FXML
        private Button confirmationButton;

        @FXML
        private Label messageLabel;


        public void initialize() {

        }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public Button getConfirmationButton() {
        return confirmationButton;
    }

    public void setConfirmationButton(Button confirmationButton) {
        this.confirmationButton = confirmationButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }
}


