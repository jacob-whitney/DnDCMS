/**
 * Jacob Whitney
 * Software Development I
 * March 12, 2025
 * DnDCMSApp.java
 * Description: Users can create Characters to be played in their
 * Dungeons & Dragon's games. Each Character has unique
 * attributes. The program adheres to the following requirements:
 * - Character class holds at least 6 attributes
 * - User must be able to perform CRUD operations
 * - User must be able to perform a custom action: Roll ability score stats
 * - User must never be able to crash the program by entering invalid data
 * - The program only exits when the user chooses to
 */

package com.example.dndcms;

// Imports
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class DnDCMSApp extends Application {
    private CharacterApp characterApp;

    @Override
    public void start(Stage primaryStage) throws IOException {

        characterApp = new CharacterApp(primaryStage);
        characterApp.showCharacterListScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}