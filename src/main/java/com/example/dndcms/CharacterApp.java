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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class CharacterApp {
    private Stage primaryStage;
    private Group root = new Group();
    private ObservableList rootList = root.getChildren();
    private int width = 800;
    private int height = 600;

    // Constructors
    public CharacterApp(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Scene Show Methods
    /**
     * method: showCharactersListScene
     * parameters: none
     * return: void
     * purpose: Shows scene of list of characters
     */
    public void showCharacterListScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), getCharacterTable(), getCharacterInteractButtons());
        Scene scene = new Scene(flowPane, width, height);
        primaryStage.setTitle("D&D Character Creator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * method: showNewCharactersScene
     * parameters: none
     * return: void
     * purpose: Shows scene of new Character form
     */
    public void showNewCharacterScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), getCharacterForm());
        Scene scene = new Scene(flowPane, width, height);
        primaryStage.setTitle("Create New Character");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * method: showImportCharactersScene
     * parameters: none
     * return: void
     * purpose: Shows scene of importing new characters
     */
    public void showImportCharactersScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), getCharacterForm());
        Scene scene = new Scene(flowPane, width, height);
        primaryStage.setTitle("Import Characters");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * method: showUpdateCharacterScene
     * parameters: none
     * return: void
     * purpose: Shows scene of updating a character
     */
    public void showUpdateCharacterScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), getCharacterForm());
        Scene scene = new Scene(flowPane, width, height);
        primaryStage.setTitle("Update Character");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * method: showDeleteCharacterScene
     * parameters: none
     * return: void
     * purpose: Shows scene of updating a character
     */
    public void showDeleteCharacterScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), getCharacterForm());
        Scene scene = new Scene(flowPane, width, height);
        primaryStage.setTitle("Delete Character");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Custom Methods
    /**
     * method: getHeader
     * parameters: none
     * return: Text
     * purpose: Create elements for header of every scene
     */
    public Text getHeader() {
        Text title = new Text();
        title.setX(50);
        title.setY(100);
        title.setText("Dungeons & Dragons\nCharacter Creator");
        return title;
    }

    /**
     * method: getCharacterTable
     * parameters: none
     * return: TableView
     * purpose: Create elements for list of characters
     * within a table
     */
    public TableView getCharacterTable() {
        Label tableLabel = new Label("Character Table");

        TableView<Character> tableView = new TableView<>();

        TableColumn<Character, String> idColumn = new TableColumn("ID");
        TableColumn<Character, String> name = new TableColumn("Name");
        TableColumn<Character, String> classification = new TableColumn("Class");
        TableColumn<Character, String> race = new TableColumn("Race");
        TableColumn<Character, String> str = new TableColumn("Str");
        TableColumn<Character, String> dex = new TableColumn("Dex");
        TableColumn<Character, String> con = new TableColumn("Con");

        tableView.getColumns().addAll(idColumn, name, classification, race, str, dex);
        return tableView;
    }

    /**
     * method: getCharacterInteractButtons
     * parameters: none
     * return: FlowPane
     * purpose: Create row of buttons for users
     * to interact with characters
     */
    public FlowPane getCharacterInteractButtons() {
        Button create = new Button("Create");
        create.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showNewCharacterScene();
            }
        }));
        Button importing = new Button("Import");
        importing.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showImportCharactersScene();
            }
        }));
        Button update = new Button("Update");
        update.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showUpdateCharacterScene();
            }
        }));
        Button delete = new Button("Delete");
        delete.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showDeleteCharacterScene();
            }
        }));

        FlowPane buttonPane = new FlowPane(Orientation.HORIZONTAL);
        buttonPane.getChildren().addAll(create, importing, update, delete);

        return buttonPane;
    }

    /**
     * method: getCharacterForm
     * parameters: none
     * return: FlowPane
     * purpose: Create row of buttons for users
     * to submit changes to a character or cancel
     */
    public FlowPane getCharacterForm() {
        Label idLabel = new Label("ID");
        TextField idField = new TextField();
        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();
        Label classLabel = new Label("Class");
        TextField classField = new TextField();
        Label raceLabel = new Label("Race");
        TextField raceField = new TextField();

        Button customAction  = new Button("Roll Stats");

        Label strLabel = new Label("Strength");
        TextField strField = new TextField();
        Label dexLabel = new Label("Dexterity");
        TextField dexField = new TextField();
        Label conLabel = new Label("Constitution");
        TextField conField = new TextField();

        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");
        cancel.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showCharacterListScene();
            }
        }));
        FlowPane buttonPane = new FlowPane(Orientation.HORIZONTAL);
        buttonPane.getChildren().addAll(submit, cancel);

        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(
                idLabel,
                idField,
                nameLabel,
                nameField,
                classLabel,
                classField,
                raceLabel,
                raceField,
                customAction,
                strLabel,
                strField,
                dexLabel,
                dexField,
                conLabel,
                conField,
                buttonPane
                );

        return flowPane;
    }
}
