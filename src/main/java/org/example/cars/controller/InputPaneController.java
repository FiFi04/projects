package org.example.cars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InputPaneController implements PaneController{

    @FXML
    private Button confirmationButton;

    @FXML
    private Label infoLabel;

    @FXML
    private TextField monthTextField;

    public void initialize() {
    }

    public Button getConfirmationButton() {
        return confirmationButton;
    }
    public Label getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabel(Label infoLabel) {
        this.infoLabel = infoLabel;
    }

    public TextField getTextField() {
        return monthTextField;
    }


}

