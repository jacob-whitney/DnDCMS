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
import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.dndcms.IPO.*;

public class CharacterApp {
    private static CharacterList characterList = new CharacterList();
    private static Stage primaryStage;
    private static Group root = new Group();
    private ObservableList rootList = root.getChildren();
    private static int sceneWidth = 1600;
    private static int sceneHeight = 900;
    private static TextFlow errorMessage = new TextFlow();

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
    public static void showCharacterListScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), errorMessage, getCharacterTable());
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
    public static void showNewCharacterScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), errorMessage, getCharacterForm(null));
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
    public static void showImportCharactersScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), errorMessage, getImportForm());
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
    public static void showUpdateCharacterScene(Character character) {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), errorMessage, getCharacterForm(character));
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
    public static void showDeleteCharacterScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader());
        Scene scene = new Scene(flowPane, sceneWidth, sceneHeight);
        primaryStage.setTitle("Delete Character");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Get Nodes
    /**
     * method: getStyles
     * parameters: none
     * return: void
     * purpose: Get styles for all scenes
     */
    public static void getStyles() {
        root.setStyle("-fx-font-size: 16px;");
    }
    /**
     * method: getHeader
     * parameters: none
     * return: Text
     * purpose: Create elements for header of every scene
     */
    public static Text getHeader() {
        Text title = new Text();
        title.setText("Dungeons & Dragons\nCharacter Creator");
        return title;
    }

    /**
     * method: getCharacterTable
     * parameters: none
     * return: FlowPane
     * purpose: Create elements for list of characters
     * within a table
     */
    public static FlowPane getCharacterTable() {
        errorMessage.getChildren().clear();

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
                errorMessage.getChildren().clear();
                Character selectedCharacter = tableView.getSelectionModel().getSelectedItem();
                if (selectedCharacter != null) {
                    showUpdateCharacterScene(selectedCharacter);
                } else {
                    Text noneSelected = new Text("> No character selected\n");
                    errorMessage.getChildren().add(noneSelected);
                }


            }
        }));
        Button delete = new Button("Delete");
        delete.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                errorMessage.getChildren().clear();
                Character selectedChar = (Character) tableView.getSelectionModel().getSelectedItem();
                if (selectedChar != null) {
                    String id = String.valueOf(selectedChar.getId());
                    characterList.deleteCharacter(id);
                    showCharacterListScene();
                } else {
                    Text noneSelected = new Text("> No character selected\n");
                    errorMessage.getChildren().add(noneSelected);
                }
                //showDeleteCharacterScene();
            }
        }));

        FlowPane buttonPane = new FlowPane(Orientation.HORIZONTAL);
        buttonPane.getChildren().addAll(create, importing, update, delete);

        FlowPane tablePane = new FlowPane(Orientation.VERTICAL);
        tablePane.getChildren().addAll(tableView, buttonPane);

        return tablePane;
    }

    /**
     * method: getCharacterForm
     * parameters: list
     * return: FlowPane
     * purpose: Create form for getting Character
     * attributes via user input
     */
    public static FlowPane getCharacterForm(Character character) {
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
        Label strLabel = new Label("Strength");
        TextField strField = new TextField();
        Label dexLabel = new Label("Dexterity");
        TextField dexField = new TextField();
        Label conLabel = new Label("Constitution");
        TextField conField = new TextField();

        Button customAction  = new Button("Roll Stats");
        customAction.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                strField.setText(String.valueOf(IPO.getRandomAbilityScore()));
                dexField.setText(String.valueOf(IPO.getRandomAbilityScore()));
                conField.setText(String.valueOf(IPO.getRandomAbilityScore()));

            }
        }));

        FlowPane buttonPane = new FlowPane(Orientation.HORIZONTAL);

        // Creating new character
        if (character == null) {
            Button submit = new Button("Submit");
            submit.setOnMouseClicked((new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    errorMessage.getChildren().clear();
                    if (
                            validateId(idField.getText()) &&
                            validateName(nameField.getText()) &&
                            validateToggleGroup("Class", classGroup) &&
                            validateToggleGroup("Race", raceGroup) &&
                            validateAbilityScore("Strength", strField.getText()) &&
                            validateAbilityScore("Dexterity", dexField.getText()) &&
                            validateAbilityScore("Constitution", conField.getText())
                    ) {
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

            buttonPane.getChildren().addAll(submit, cancel);

        // Updating existing character
        } else {
            idField.setText(String.valueOf(character.getId()));
            nameField.setText(character.getName());
            switch (character.getClassification()) {
                case "Barbarian":
                    classGroup.selectToggle(classBarbRB);
                    break;
                case "Fighter":
                    classGroup.selectToggle(classFightRB);
                    break;
                case "Ranger":
                    classGroup.selectToggle(classRangRB);
                    break;
                case "Rogue":
                    classGroup.selectToggle(classRogRB);
                    break;
                case "Sorcer":
                    classGroup.selectToggle(classSorcRB);
                    break;
                case "Warlock":
                    classGroup.selectToggle(classWarlRB);
                    break;
                case "Wizard":
                    classGroup.selectToggle(classWizRB);
                    break;
                default:
                    Text invChar = new Text("> Invalid Class: Select a new one\n");
                    errorMessage.getChildren().add(invChar);
            }
            switch (character.getRace()) {
                case "Dwarf":
                    raceGroup.selectToggle(raceDwarfRB);
                    break;
                case "Dragonborn":
                    raceGroup.selectToggle(raceDragRB);
                    break;
                case "Elf":
                    raceGroup.selectToggle(raceElfRB);
                    break;
                case "Gnome":
                    raceGroup.selectToggle(raceGnomeRB);
                    break;
                case "Halfling":
                    raceGroup.selectToggle(raceHalfRB);
                    break;
                case "Human":
                    raceGroup.selectToggle(raceHumanRB);
                    break;
                default:
                    Text invRace = new Text("> Invalid Race: Select a new one\n");
                    errorMessage.getChildren().add(invRace);
            }
            strField.setText(String.valueOf(character.getStr()));
            dexField.setText(String.valueOf(character.getDex()));
            conField.setText(String.valueOf(character.getCon()));

            Button update = new Button("Update");
            update.setOnMouseClicked((new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    errorMessage.getChildren().clear();
                    if (!String.valueOf(character.getId()).equals(idField.getText())) {
                        if (validateId(idField.getText())) {
                            character.setId(Integer.parseInt(idField.getText()));
                        }
                    }
                    if (!character.getName().equals(nameField.getText())) {
                        if (validateName(nameField.getText())) {
                            character.setName(nameField.getText());
                        }
                    }
                    RadioButton classSelected = (RadioButton) classGroup.getSelectedToggle();
                    if (!character.getClassification().equals(classSelected.getText())) {
                        if (validateClass(classSelected.getText())) {
                            character.setClassification(classSelected.getText());
                        }
                    }
                    RadioButton raceSelected = (RadioButton) raceGroup.getSelectedToggle();
                    if (!character.getRace().equals(raceSelected.getText())) {
                        if (validateRace(raceSelected.getText())) {
                            character.setRace(raceSelected.getText());
                        }
                    }
                    if (!String.valueOf(character.getStr()).equals(strField.getText())) {
                        if (validateAbilityScore("Strength", strField.getText())) {
                            character.setStr(Integer.parseInt(strField.getText()));
                        }
                    }
                    if (!String.valueOf(character.getDex()).equals(dexField.getText())) {
                        if (validateAbilityScore("Dexterity", dexField.getText())) {
                            character.setDex(Integer.parseInt(dexField.getText()));
                        }
                    }
                    if (!String.valueOf(character.getCon()).equals(conField.getText())) {
                        if (validateAbilityScore("Constitution", conField.getText())) {
                            character.setCon(Integer.parseInt(conField.getText()));
                        }
                    }
                }
            }));

            Button exit = new Button("Exit");
            exit.setOnMouseClicked((new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    showCharacterListScene();
                }
            }));
            buttonPane.getChildren().addAll(update, exit);
        }

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

    /**
     * method: getImportForm
     * parameters: none
     * return: MenuBar
     * purpose: Create form for users to
     * import text file of Characters
     */
    public static FlowPane getImportForm() {
        Text invFile = new Text("> No valid file selected, try again");
        errorMessage.getChildren().clear();

        Label fileLabel = new Label("Upload");

        Text fileName = new Text("No file selected");

        Menu fileMenu = new Menu("Choose File");
        MenuItem item = new MenuItem("Open Text File");
        fileMenu.getItems().add(item);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files (*.txt", "*.txt"));
        item.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                errorMessage.getChildren().clear();
                int success = 0;
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile != null) {
                    String charLine = "";
                    fileName.setText(selectedFile.getName());
                    ArrayList<String> fileData = readFile(selectedFile);
                    for (int i = 0; i < fileData.size(); i++) {
                        charLine = fileData.get(i);
                        if (validateImportedString(charLine)) {
                            String[] attributes = charLine.split(", ");
                            if (
                                    validateId(attributes[0]) &&
                                    validateName(attributes[1]) &&
                                    validateClass(attributes[2]) &&
                                    validateRace(attributes[3]) &&
                                    validateAbilityScore("Strength", attributes[4]) &&
                                    validateAbilityScore("Dexterity", attributes[5]) &&
                                    validateAbilityScore("Constitution", attributes[6])
                            ){
                                characterList.addCharacter(parseAttributesFromString(charLine));
                                success++;
                            } else {
                                Text invString = new Text("> for the following line:\n\t" + charLine + "\n");
                                errorMessage.getChildren().add(invString);
                            }
                        } else {
                            Text invLine = new Text("> Exactly 7 attributes must be present, this line will be skipped:\n\t" + charLine + "\n");
                            errorMessage.getChildren().add(invLine);
                        }
                    }
                } else {
                    errorMessage.getChildren().add(invFile);
                }
                Text successImport = new Text("* " +success + " characters imported!\n");
                errorMessage.getChildren().add(successImport);
            }
        });

        MenuBar chooseFileButton = new MenuBar(fileMenu);
        chooseFileButton.setTranslateX(3);
        chooseFileButton.setTranslateY(3);

        FlowPane buttonPane = new FlowPane(Orientation.HORIZONTAL);
        buttonPane.getChildren().addAll(chooseFileButton, fileName);

        Button backButton = new Button("Go Back");
        backButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showCharacterListScene();
            }
        }));

        FlowPane formPane = new FlowPane(Orientation.VERTICAL);
        formPane.getChildren().addAll(
                fileLabel,
                buttonPane,
                backButton
        );

        return formPane;
    }

    // Processor Methods
    /**
     * method: readFile
     * parameters: file
     * return: ArrayList<String>
     * purpose: Read a file into an ArrayList of
     * Strings
     */
    public static ArrayList<String> readFile(File file) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                fileData.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("> File not found");
        }
        return fileData;
    }

    /**
     * method: parseAttributesFromString
     * parameters: line
     * return: Character
     * purpose: Parses a String from an imported file
     *            into attributes and returns a new Character
     */
    public static Character parseAttributesFromString(String line) {
        String[] attributes = line.split(", ");

        int id = Integer.parseInt(attributes[0]);
        String name = attributes[1];
        String classification = attributes[2];
        String race = attributes[3];
        int str = Integer.parseInt(attributes[4]);
        int dex = Integer.parseInt(attributes[5]);
        int con = Integer.parseInt(attributes[6]);

        return new Character(id, name, classification, race, str, dex, con);
    }

    // Validation Methods
    /**
     * method: validateImportedString
     * parameters: line
     * return: boolean
     * purpose: Confirms that String from imported
     * file has 7 attributes exactly
     */
    public static boolean validateImportedString(String line) {
        String[] data = line.split(", ");
        if (data.length != 7) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * method: validateId
     * parameters: id
     * return: boolean
     * purpose: Confirms that ID is a valid integer
     */
    public static boolean validateId(String id) {
        int listId = 0;
        int listSize = characterList.getListSize();
        Text dupId = new Text("> Invalid ID: " + id + ", already exists, please enter a new one\n");
        Text posInt = new Text("> Invalid ID: Please enter a positive integer\n");
        Text nonIntId = new Text("> Invalid ID: Please enter an integer\n");
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
    public static boolean validateName(String name) {
        String listName = "";
        int listSize = characterList.getListSize();
        Text dupName = new Text("> Invalid Name: " + name + ", already exists, please enter a new one\n");
        Text moreChar = new Text("> Invalid Name: Please enter a name with 2 characters or more\n");
        Text lessChar = new Text("> Invalid Name: Please enter a name with 50 characters or fewer\n");
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
     * method: validateClass
     * parameters: classification
     * return: boolean
     * purpose: Confirms that Class is a valid string
     */
    public static boolean validateClass(String classification) {
        Text invClass = new Text("> Invalid Class: Choose Barbarian, Fighter, Ranger, Rogue, Sorcerer, Warlock, or Wizard\n");
        errorMessage.getChildren().clear();

        if (
                classification.equals("Barbarian") ||
                classification.equals("Fighter") ||
                classification.equals("Ranger") ||
                classification.equals("Rogue") ||
                classification.equals("Sorcerer") ||
                classification.equals("Warlock") ||
                classification.equals("Wizard")
        ) {
            return true;
        } else {
            errorMessage.getChildren().add(invClass);
            return false;
        }
    }

    /**
     * method: validateRace
     * parameters: race
     * return: boolean
     * purpose: Confirms that Race is a valid string
     */
    public static boolean validateRace(String race) {
        Text invRace = new Text("> Invalid Race: Choose Dwarf, Dragonborn, Elf, Gnome, Halflin, or Human\n");
        errorMessage.getChildren().clear();

        if (
                race.equals("Dwarf") ||
                race.equals("Dragonborn") ||
                race.equals("Elf") ||
                race.equals("Gnome") ||
                race.equals("Halfling") ||
                race.equals("Human")
        ) {
            return true;
        } else {
            errorMessage.getChildren().add(invRace);
            return false;
        }
    }

    /**
     * method: validateAbilityScore
     * parameters: ability, score
     * return: int
     * purpose: Confirms that Ability Score
     * is valid integer
     */
    public static boolean validateAbilityScore(String ability, String score) {
        Text nonInt = new Text("> Invalid " + ability + ": Please enter an integer\n");
        Text tooSmall = new Text("> Invalid " + ability + ": Please enter a score of 3 or more\n");
        Text tooBig = new Text("> Invalid " + ability + ": Please enter a score of 20 or less\n");
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

    /**
     * method: validateToggleGroup
     * parameters: attribute, toggleGroup
     * return: boolean
     * purpose: Confirms that toggleGroups
     * have a radio button selected
     */
    public static boolean validateToggleGroup(String attribute, ToggleGroup toggleGroup) {
        Text unselected = new Text("> Invalid " + attribute + ": Please select one\n");
        errorMessage.getChildren().clear();

        if (toggleGroup.getSelectedToggle() == null) {
            errorMessage.getChildren().add(unselected);
            return false;
        } else {
            return true;
        }
    }
}
