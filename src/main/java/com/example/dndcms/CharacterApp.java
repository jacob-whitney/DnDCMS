/**
 * Jacob Whitney
 * Software Development I
 * March 12, 2025
 * CharacterApp.java
 * Description: contains attributes and methods for
 * managing JavaFX scenes for GUI
 */

package com.example.dndcms;

// Imports
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class CharacterApp {
    private Stage primaryStage;

    // Constructors
    public CharacterApp(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Scene methods
    /**
     * method: setDefaultScene
     * parameters: none
     * return: void
     * purpose: Sets defaults of all scenes like headers and footers
     */
    public void setDefaultScene() {
        Text h1 = new Text();
        h1.setX(50);
        h1.setY(100);
        h1.setText("Dungeons & Dragons\nCharacter Creator");

        Group root = new Group();
        ObservableList list = root.getChildren();
        list.add(h1);
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("style.css");

        primaryStage.setTitle("D&D Character Creator");
        primaryStage.setScene(scene);
    }

    /**
     * method: showCharactersListScene
     * parameters: none
     * return: void
     * purpose: Shows scene of list of characters
     */
    public void showCharacterListScene() {
        setDefaultScene();

        primaryStage.show();
    }
}
