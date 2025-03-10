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
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DnDCMSApp extends Application {
    private CharacterApp characterApp;

    @Override
    public void start(Stage primaryStage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(DnDCMSApp.class.getResource("dndcms-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();*/

        characterApp = new CharacterApp(primaryStage);
        characterApp.showCharacterListScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}