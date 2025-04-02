package com.example.dndcms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Jacob Whitney
 * Software Development I
 * April 18, 2025
 * DnDCMSController.java
 * Description: manages controller for JavaFX
 * application
 */
public class DnDCMSController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}