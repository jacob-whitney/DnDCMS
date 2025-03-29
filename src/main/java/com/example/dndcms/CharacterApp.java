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
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

import static com.example.dndcms.IPO.*;

public class CharacterApp {
    private static CharacterList characterList = new CharacterList();
    private static Stage primaryStage;
    private static Group root = new Group();
    private static int sceneWidth = 1600;
    private static int sceneHeight = 900;
    private static TextFlow errorMessage = new TextFlow();
    private static String ip;
    private static String dbUsername;
    private static String dbPassword;
    private static boolean connected = false;

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
     * parameters: none
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
     * method: showConnectDBScene
     * parameters: none
     * return: void
     * purpose: Shows scene of new Character form
     */
    public static void showConnectDBScene() {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(getHeader(), errorMessage, getDBForm());
        Scene scene = new Scene(flowPane, sceneWidth, sceneHeight);
        primaryStage.setTitle("Create New Character");
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

    // Get Nodes
    /**
     * method: getHeader
     * parameters: none
     * return: Text
     * purpose: Create elements for header of every scene
     */
    public static Text getHeader() {
        Text header = new Text();
        if (connected == true) {
            header.setText("Dungeons & Dragons\nCharacter Creator\n\nConnected to Database");
        } else {
            header.setText("Dungeons & Dragons\nCharacter Creator\n\nNo Database Connection Found");
        }
        return header;
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

        Button connectDB = new Button("Connect to Database");
        connectDB.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showConnectDBScene();
            }
        }));
        Button create = new Button("Create");
        create.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showNewCharacterScene();
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
        if (connected) {
            buttonPane.getChildren().addAll(connectDB, create, update, delete);
        } else {
            buttonPane.getChildren().add(connectDB);
        }

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

                        insertCharsToDB(id, name, classification, race, str, dex, con);
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
     * method: getDBForm
     * parameters: list
     * return: FlowPane
     * purpose: Create form for getting Database
     * connection details via user input
     */
    public static FlowPane getDBForm() {
        Label ipAddressLabel = new Label("Server IP Address");
        TextField ipAddressField = new TextField();
        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        FlowPane buttonPane = new FlowPane(Orientation.HORIZONTAL);

        // Submit connection to Database
        Button connect = new Button("Connect");
        connect.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                errorMessage.getChildren().clear();
                if (!passwordField.getText().equals("")) {
                    if (validateIpAddress(ipAddressField.getText()) && validateUsername(usernameField.getText())) {
                        ip = ipAddressField.getText();
                        dbUsername = usernameField.getText();
                        dbPassword = passwordField.getText();

                        // Connect to server using credentials
                        if (initialDBConnect(ip, dbUsername, dbPassword)) {
                            String sql = """
                                CREATE TABLE IF NOT EXISTS characters (
                                    id INT PRIMARY KEY NOT NULL,
                                    name VARCHAR(50) NOT NULL,
                                    class VARCHAR(50),
                                    race VARCHAR(50),
                                    str INT,
                                    dex INT,
                                    con INT
                                );""";
                            updateDB(sql);

                            // Import start characters
                            ArrayList<String> startChars = getStartChars();
                            String charLine = "";
                            for (int i = 0; i < startChars.size(); i++) {
                                charLine = startChars.get(i);
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
                                        insertCharsToDB(
                                                Integer.parseInt(attributes[0]),
                                                attributes[1],
                                                attributes[2],
                                                attributes[3],
                                                Integer.parseInt(attributes[4]),
                                                Integer.parseInt(attributes[5]),
                                                Integer.parseInt(attributes[6])
                                        );
                                    } else {
                                        Text invString = new Text("> for the following line:\n\t" + charLine + "\n");
                                        errorMessage.getChildren().add(invString);
                                    }
                                } else {
                                    Text invLine = new Text("> Exactly 7 attributes must be present, this line will be skipped:\n\t" + charLine + "\n");
                                    errorMessage.getChildren().add(invLine);
                                }
                            }
                            connected = true;
                            showCharacterListScene();
                        } else {
                            connected = false;
                        }
                    }
                } else {
                    Text emptyPassword = new Text("> Password field is empty. Please enter a valid password\n");
                    errorMessage.getChildren().add(emptyPassword);
                }
            }
        }));

        Button cancel = new Button("Cancel");
        cancel.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showCharacterListScene();
            }
        }));

        buttonPane.getChildren().addAll(connect, cancel);

        FlowPane formPane = new FlowPane(Orientation.VERTICAL);
        formPane.getChildren().addAll(
                ipAddressLabel,
                ipAddressField,
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
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

    /**
     * method: initialDBConnect
     * parameters: boolean
     * return: ip, un, pw, query
     * purpose: Using a server IP Address, username,
     * password, and query, connect to the user's MySQL database
     * and make updates
     */
    public static boolean initialDBConnect(String ip, String un, String pw) {
        String jdbcAddress = "jdbc:mysql://" + ip + ":3306";
        try(Connection conn = DriverManager.getConnection(jdbcAddress, un, pw);
            Statement stmt = conn.createStatement();) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS dndcms;");
            return true;
        } catch (SQLException e) {
            Text noConnect = new Text("> Could not connect to database, try again");
            errorMessage.getChildren().add(noConnect);
            throw new RuntimeException(e);
        }
    }

    /**
     * method: queryDB
     * parameters: ResultSet
     * return: ip, un, pw, query
     * purpose: Using a server IP Address, username,
     * password, and query, connect to the user's MySQL database
     * and query for results
     */
    public static ResultSet queryDB(String query) {
        String jdbcAddress = "jdbc:mysql://" + ip + ":3306/dndcms";
        try(Connection conn = DriverManager.getConnection(jdbcAddress, dbUsername, dbPassword);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {
            return rs;
        } catch (SQLException e) {
            Text noConnect = new Text("> Could not connect to database, try again");
            errorMessage.getChildren().add(noConnect);
            throw new RuntimeException(e);
        }
    }

    /**
     * method: updateDB
     * parameters: query
     * return: boolean
     * purpose: Using a server IP Address, username,
     * password, and query, connect to the user's MySQL database
     * and make updates
     */
    public static boolean updateDB(String query) {
        String jdbcAddress = "jdbc:mysql://" + ip + ":3306/dndcms";
        try(Connection conn = DriverManager.getConnection(jdbcAddress, dbUsername, dbPassword);
            Statement stmt = conn.createStatement();) {
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            Text noConnect = new Text("> Could not connect to database, try again");
            errorMessage.getChildren().add(noConnect);
            throw new RuntimeException(e);
        }
    }

    /**
     * method: insertCharstoDB
     * parameters: id, name, classification, race,
     * str, dex, con
     * return: boolean
     * purpose: Import a character's attributes
     * into the database
     */
    public static boolean insertCharsToDB(int id, String name, String classification, String race, int str, int dex, int con) {
        String sql = "INSERT INTO characters VALUES (";
        sql += id + ", '";
        sql += name + "', '";
        sql += classification + "', '";
        sql += race + "', ";
        sql += str + ", ";
        sql += dex + ", ";
        sql += con + ")";

        if (updateDB(sql)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method: updatCharInDB
     * parameters: id, name, classification, race,
     * str, dex, con
     * return: boolean
     * purpose: Import a character's attributes
     * into the database
     */
    public static boolean updateCharInDB(int id, String attribute, String value) {
        String sql = "UPDATE characters SET ";
        sql += attribute + " = '" + value + "' ";
        sql += "WHERE id = " + id;

        if (updateDB(sql)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method: getStartChars
     * parameters: none
     * return: ArrayList<String>
     * purpose: Get ArrayList of start characters
     */
    public static ArrayList<String> getStartChars() {
        ArrayList<String> fileData = new ArrayList<>();
        fileData.add("1001, Thrain Ironfist, Barbarian, Dwarf, 18, 14, 16");
        fileData.add("1002, Eldrin Moonshadow, Rogue, Elf, 12, 19, 10");
        fileData.add("1003, Soren Blackflame, Warlock, Dragonborn, 15, 13, 9");
        fileData.add("1004, Mira Willowbrook, Ranger, Halfling, 14, 17, 12");
        fileData.add("1005, Orin Deepdelver, Fighter, Dwarf, 19, 10, 18");
        fileData.add("1006, Sylas Starfire, Wizard, Elf, 10, 16, 8");
        fileData.add("1007, Kaida Stormscale, Sorcerer, Dragonborn, 13, 18, 11");
        fileData.add("1008, Finn Copperkettle, Rogue, Gnome, 9, 14, 7");
        fileData.add("1009, Rowan Swiftblade, Fighter, Human, 17, 12, 14");
        fileData.add("1010, Zephyr Duskrun, Ranger, Elf, 11, 19, 13");
        fileData.add("1011, Baldric Stonehelm, Barbarian, Dwarf, 20, 12, 15");
        fileData.add("1012, Lirien Dawnwhisper, Wizard, Elf, 8, 17, 6");
        fileData.add("1013, Cassia Thornbrook, Warlock, Halfling, 14, 9, 10");
        fileData.add("1014, Drogan Ashenmaw, Sorcerer, Dragonborn, 16, 15, 12");
        fileData.add("1015, Jareth Windrider, Fighter, Human, 18, 11, 17");
        fileData.add("1016, Elowen Lightfoot, Rogue, Halfling, 7, 16, 8");
        fileData.add("1017, Magnus Ironbark, Warlock, Gnome, 13, 14, 10");
        fileData.add("1018, Tavian Stormborn, Ranger, Human, 15, 18, 14");
        fileData.add("1019, Brakka Flamebeard, Sorcerer, Dwarf, 17, 12, 15");
        fileData.add("1020, Nyx Shadowvale, Wizard, Gnome, 10, 19, 7");

        return fileData;
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

    /**
     * method: validateIpAddress
     * parameters: ipAddress
     * return: boolean
     * purpose: Confirms that IP Address is a valid
     */
      public static boolean validateIpAddress(String ipAddress) {
        errorMessage.getChildren().clear();

        try {
            InetAddress.getByName(ipAddress);
            return true;
        } catch (Exception e) {
            Text invIp = new Text("> Invalid IP Address: Cannot connect to \"" + ipAddress + "\". Please try again\n");
            errorMessage.getChildren().add(invIp);
            return false;
        }
    }

    /**
     * method: validateUsername
     * parameters: username
     * return: boolean
     * purpose: Confirms that a username is a valid
     */
    public static boolean validateUsername(String username) {
        errorMessage.getChildren().clear();

        // Checks that username meets MySQL requirements
        // - 1-32 characters long
        // - No special character except '_' or '%'
        // - No spaces
        String regex = "^[a-zA-Z0-9_]{1,32}$";
        if (username.matches(regex)) {
            return true;
        } else {
            Text invUsername = new Text("> Invalid Username: \"" + username + "\" is not a valid MySQL username. Try again\n");
            errorMessage.getChildren().add(invUsername);
            return false;
        }
    }
}
