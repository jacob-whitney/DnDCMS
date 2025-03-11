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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class CharacterApp {
    private CharacterList characterList = new CharacterList();
    private Stage primaryStage;
    private Group root = new Group();
    private ObservableList rootList = root.getChildren();
    private int sceneWidth = 1600;
    private int sceneHeight = 900;
    private TextFlow errorMessage = new TextFlow();

    // Constructors
    public CharacterApp(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Scene Show Methods
    /**
     * method: showCharactersListScene
     * parameters: list
     * return: void
     * purpose: Shows scene of list of characters
     */
    public void showCharacterListScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), getCharacterTable(), getCharacterInteractButtons());
        Scene scene = new Scene(flowPane, sceneWidth, sceneHeight);
        primaryStage.setTitle("D&D Character Creator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * method: showNewCharactersScene
     * parameters: list
     * return: void
     * purpose: Shows scene of new Character form
     */
    public void showNewCharacterScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), errorMessage, getCharacterForm());
        Scene scene = new Scene(flowPane, sceneWidth, sceneHeight);
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
        flowPane.getChildren().addAll(getHeader());
        Scene scene = new Scene(flowPane, sceneWidth, sceneHeight);
        primaryStage.setTitle("Import Characters");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * method: showUpdateCharacterScene
     * parameters: list
     * return: void
     * purpose: Shows scene of updating a character
     */
    public void showUpdateCharacterScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), getCharacterForm());
        Scene scene = new Scene(flowPane, sceneWidth, sceneHeight);
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
        flowPane.getChildren().addAll(getHeader());
        Scene scene = new Scene(flowPane, sceneWidth, sceneHeight);
        primaryStage.setTitle("Delete Character");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Get Nodes
    /**
     * method: getHeader
     * parameters: none
     * return: Text
     * purpose: Create elements for header of every scene
     */
    public Text getHeader() {
        Text title = new Text();
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
        ObservableList<Character> observableCharacterList = FXCollections.observableArrayList();

        if (characterList.getListSize() > 0) {
            for (int i = 0; i < characterList.getListSize(); i++) {
                Character c = characterList.getCharacter(i);
                observableCharacterList.add(c);
            }
        }

        TableView<Character> tableView = new TableView<>();

        TableColumn<Character, Number> idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Character, String> nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Character, String> classificationColumn = new TableColumn("Class");
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));
        TableColumn<Character, String> raceColumn = new TableColumn("Race");
        raceColumn.setCellValueFactory(new PropertyValueFactory<>("race"));
        TableColumn<Character, String> strColumn = new TableColumn("Str");
        strColumn.setCellValueFactory(new PropertyValueFactory<>("str"));
        TableColumn<Character, String> dexColumn = new TableColumn("Dex");
        dexColumn.setCellValueFactory(new PropertyValueFactory<>("dex"));
        TableColumn<Character, String> conColumn = new TableColumn("Con");
        conColumn.setCellValueFactory(new PropertyValueFactory<>("con"));

        tableView.getColumns().addAll(idColumn, nameColumn, classificationColumn, raceColumn, strColumn, dexColumn, conColumn);
        tableView.setItems(observableCharacterList);
        return tableView;
    }

    /**
     * method: getCharacterInteractButtons
     * parameters: list
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
     * parameters: list
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
        ToggleGroup classGroup = new ToggleGroup();
        RadioButton classBarbRB = new RadioButton("Barbarian");
        classBarbRB.setToggleGroup(classGroup);
        RadioButton classFightRB = new RadioButton("Fighter");
        classFightRB.setToggleGroup(classGroup);
        RadioButton classRangRB = new RadioButton("Ranger");
        classRangRB.setToggleGroup(classGroup);
        RadioButton classRogRB = new RadioButton("Rogue");
        classRogRB.setToggleGroup(classGroup);
        RadioButton classSorcRB = new RadioButton("Sorcer");
        classSorcRB.setToggleGroup(classGroup);
        RadioButton classWarlRB = new RadioButton("Warlock");
        classWarlRB.setToggleGroup(classGroup);
        RadioButton classWizRB = new RadioButton("Wizard");
        classWizRB.setToggleGroup(classGroup);

        Label raceLabel = new Label("Race");
        ToggleGroup raceGroup = new ToggleGroup();
        RadioButton raceDwarfRB = new RadioButton("Dwarf");
        raceDwarfRB.setToggleGroup(raceGroup);
        RadioButton raceDragRB = new RadioButton("Dragonborn");
        raceDragRB.setToggleGroup(raceGroup);
        RadioButton raceElfRB = new RadioButton("Elf");
        raceElfRB.setToggleGroup(raceGroup);
        RadioButton raceGnomeRB = new RadioButton("Gnome");
        raceGnomeRB.setToggleGroup(raceGroup);
        RadioButton raceHalfRB = new RadioButton("Halfling");
        raceHalfRB.setToggleGroup(raceGroup);
        RadioButton raceHumanRB = new RadioButton("Human");
        raceHumanRB.setToggleGroup(raceGroup);

        Button customAction  = new Button("Roll Stats");

        Label strLabel = new Label("Strength");
        TextField strField = new TextField();
        Label dexLabel = new Label("Dexterity");
        TextField dexField = new TextField();
        Label conLabel = new Label("Constitution");
        TextField conField = new TextField();

        Button submit = new Button("Submit");
        submit.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (
                        validateId(idField.getText()) &&
                        validateName(nameField.getText()) &&
                        validateToggleGroup("Class", classGroup) &&
                        validateToggleGroup("Race", raceGroup) &&
                        validateAbilityScore("Strength", strField.getText()) &&
                        validateAbilityScore("Dexterity", dexField.getText()) &&
                        validateAbilityScore("Constitution", conField.getText())
                ){
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    RadioButton classSelected = (RadioButton) classGroup.getSelectedToggle();
                    String classification = classSelected.getText();
                    RadioButton raceSelected = (RadioButton) raceGroup.getSelectedToggle();
                    String race = raceSelected.getText();
                    int str = Integer.parseInt(strField.getText());
                    int dex = Integer.parseInt(dexField.getText());
                    int con = Integer.parseInt(conField.getText());

                    characterList.addCharacter(new Character(id, name, classification, race, str, dex, con));
                    showCharacterListScene();
                }

            }
        }));

        Button cancel = new Button("Cancel");
        cancel.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showCharacterListScene();
            }
        }));
        FlowPane buttonPane = new FlowPane(Orientation.HORIZONTAL);
        buttonPane.getChildren().addAll(submit, cancel);

        FlowPane formPane = new FlowPane(Orientation.VERTICAL);
        formPane.getChildren().addAll(
                idLabel,
                idField,
                nameLabel,
                nameField,
                classLabel,
                classBarbRB,
                classFightRB,
                classRangRB,
                classRogRB,
                classSorcRB,
                classWarlRB,
                classWizRB,
                raceLabel,
                raceDwarfRB,
                raceDragRB,
                raceElfRB,
                raceGnomeRB,
                raceHalfRB,
                raceHumanRB,
                customAction,
                strLabel,
                strField,
                dexLabel,
                dexField,
                conLabel,
                conField,
                buttonPane
                );

        return formPane;
    }

    // Validation
    /**
     * method: validateId
     * parameters: id
     * return: boolean
     * purpose: Confirms that ID is a valid integer
     */
    public boolean validateId(String id) {
        int listId = 0;
        int listSize = characterList.getListSize();
        Text dupId = new Text("> Invalid ID: " + id + ", already exists, please enter a new one");
        Text posInt = new Text("> Invalid ID: Please enter a positive integer");
        Text nonIntId = new Text("> Invalid ID: Please enter an integer");
        errorMessage.getChildren().clear();

        // Check that it's integer
        try {
            int intId = Integer.parseInt(id);
            if (intId > 0) {
                if (listSize > 0) {
                    for (int i = 0; i < listSize; i++) {
                        listId = characterList.getCharacter(i).getId();
                        if (listId == intId) {
                            errorMessage.getChildren().add(dupId);
                            return false;
                        }
                    }
                }
                return true;
            } else {
                errorMessage.getChildren().add(posInt);
                return false;
            }
        } catch (NumberFormatException e) {
            errorMessage.getChildren().add(nonIntId);
            return false;
        }
    }

    /**
     * method: validateName
     * parameters: name
     * return: boolean
     * purpose: Confirms that Name is a valid string
     */
    public boolean validateName(String name) {
        String listName = "";
        int listSize = characterList.getListSize();
        Text dupName = new Text("> Invalid Name: " + name + ", already exists, please enter a new one");
        Text moreChar = new Text("> Invalid Name: Please enter a name with 2 characters or more");
        Text lessChar = new Text("> Invalid Name: Please enter a name with 50 characters or fewer");
        errorMessage.getChildren().clear();

        if (name.length() < 2) {
            errorMessage.getChildren().add(moreChar);
            return false;
        } else if (name.length() > 50) {
            errorMessage.getChildren().add(lessChar);
            return false;
        }
        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                listName = characterList.getCharacter(i).getName();
                if (listName.equals(name)) {
                    errorMessage.getChildren().add(dupName);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * method: validateToggleGroup
     * parameters: attribute, toggleGroup
     * return: boolean
     * purpose: Confirms that toggleGroups
     * have a radio button selected
     */
    public boolean validateToggleGroup(String attribute, ToggleGroup toggleGroup) {
        Text unselected = new Text("> Invalid " + attribute + ": Please select one");
        errorMessage.getChildren().clear();

        if (toggleGroup.getSelectedToggle() == null) {
            errorMessage.getChildren().add(unselected);
            return false;
        } else {
            return true;
        }
    }

    /**
     * method: validateAbilityScore
     * parameters: ability, score
     * return: int
     * purpose: Confirms that Ability Score
     * is valid integer
     */
    public boolean validateAbilityScore(String ability, String score) {
        Text nonInt = new Text("> Invalid " + ability + ": Please enter an integer");
        Text tooSmall = new Text("> Invalid " + ability + ": Please enter a score of 3 or more");
        Text tooBig = new Text("> Invalid " + ability + ": Please enter a score of 20 or less");
        errorMessage.getChildren().clear();
        try {
            int intScore = Integer.parseInt(score);
            if (intScore < 3) {
                errorMessage.getChildren().add(tooSmall);
                return false;
            } else if (intScore > 20) {
                errorMessage.getChildren().add(tooBig);
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            errorMessage.getChildren().add(nonInt);
            return false;
        }
    }
}
