package com.example.dndcms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DnDCMSController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}