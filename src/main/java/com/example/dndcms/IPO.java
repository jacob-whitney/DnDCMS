/**
 * Jacob Whitney
 * Software Development I
 * February 23, 2025
 * Menu.java
 * --Description: Manages CLI menu and user interactions
 */

package com.example.dndcms;

// Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class IPO {
    private static final Scanner sc = new Scanner(System.in);

    // User Input Methods
    /**
     * method: inputFilePath
     * parameters: none
     * return: String
     * purpose: User inputs path to file they want to import
     */
    public static String inputFilePath() {
        System.out.println("All rows in the file must follow the following format:");
        System.out.println("ID, Name, Class, Race, Strength, Dexterity, Constitution");
        System.out.println("For example: 1002, Eldrin Moonshadow, Rogue, Elf, 12, 19, 10");
        System.out.println();
        System.out.println("Enter the file path (ex. resources/chars.txt) you want to import");
        System.out.print("Filepath: ");
        return sc.nextLine();
    }

    /**
     * method: inputAttribute
     * parameters: attribute
     * return: String
     * purpose: User is instructed to input a specific
     *          Character attribute
     */
    public static String inputAttribute(String attribute) {
        boolean loop = true;
        while (loop) {
            switch (attribute) {
                case "id":
                    System.out.print("ID: ");
                    loop = false;
                    break;
                case "name":
                    System.out.print("Name: ");
                    loop = false;
                    break;
                case "classification":
                    System.out.println();
                    System.out.println("Choose from one of the following classes.");
                    System.out.println("  1. Barbarian");
                    System.out.println("  2. Fighter");
                    System.out.println("  3. Ranger");
                    System.out.println("  4. Rogue");
                    System.out.println("  5. Sorcerer");
                    System.out.println("  6. Warlock");
                    System.out.println("  7. Wizard");
                    System.out.println();
                    System.out.print("Class: ");
                    loop = false;
                    break;
                case "race":
                    System.out.println();
                    System.out.println("Choose from one of the following races.");
                    System.out.println("  1. Dwarf");
                    System.out.println("  2. Dragonborn");
                    System.out.println("  3. Elf");
                    System.out.println("  4. Gnome");
                    System.out.println("  5. Halfling");
                    System.out.println("  6. Human");
                    System.out.println();
                    System.out.print("Race: ");
                    loop = false;
                    break;
                case "str":
                    System.out.print("Strength: ");
                    loop = false;
                    break;
                case "dex":
                    System.out.print("Dexterity: ");
                    loop = false;
                    break;
                case "con":
                    System.out.print("Constitution: ");
                    loop = false;
                    break;
                default:
                    System.out.println("Not a valid attribute, try again");
            }
        }
        return sc.nextLine();
    }

    // Process Methods
    /**
     * method: readFile
     * parameters: filepath
     * return: ArrayList<String>
     * purpose: Determine if passed filepath
     *            is a valid file and if so,
     *            read it into an ArrayList of
     *            Strings
     */
    public static ArrayList<String> readFile(String filepath) {
        ArrayList<String> data = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(new File(filepath));

            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("> File not found");
        }
        return data;
    }

    /**
     * method: parseAttributesFromString
     * parameters: line, list
     * return: Character
     * purpose: Parses a String from an imported file
     *            into attributes and returns a new Character
     */
    public static Character parseAttributesFromString(String line, CharacterList list) {
        String[] attributes = line.split(", ");
        int id = 0;
        if (getValidId(attributes[0], list)) {
            id = Integer.parseInt(attributes[0]);
        }
        String name = getValidName(attributes[1], list);
        String classification = getValidClassification(attributes[2]);
        String race = getValidRace(attributes[3]);
        int str = getValidAbilityScore("str", attributes[4]);
        int dex = getValidAbilityScore("dex", attributes[5]);
        int con = getValidAbilityScore("con", attributes[6]);

        System.out.print(id + " | " + name + " | " + classification + " | ");
        System.out.println(race + " |  " + str + "  |  " + dex + "  |  " + con);

        return new Character(id, name, classification, race, str, dex, con);
    }

    // Validation Methods
    /**
     * method: validateImportedString
     * parameters: line
     * return: boolean
     * purpose: Confirms that String from imported
     *            file has 7 attributes exactly
     */
    public static boolean validateImportedString(String line) {
        String[] data = line.split(", ");
        if (data.length != 7) {
            System.out.println("> Exactly 7 attributes must be present, this line will be skipped: ");
            System.out.println("'" + line + "'");
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
        // Check that it's integer
        try {
            int intId = Integer.parseInt(id);
            if (intId > 0) {
                return true;
            } else {
                System.out.println("> Please enter a positive integer");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("> Please enter an integer");
            return false;
        }
    }

    /**
     * method: validateName
     * parameters: name
     * return: boolean
     * purpose: Confirms that name is not too short
     *            or long
     */
    public static boolean validateName(String name) {
        if (name.length() < 2) {
            System.out.println("> Please enter a name with 2 characters or more");
            return false;
        } else if (name.length() > 50) {
            System.out.println("> Please enter a name with 50 characters or less");
            return false;
        }
        return true;
    }

    /**
     * method: validateAbilityScore
     * parameters: score
     * return: boolean
     * purpose: Confirms that score is valid integer
     *            between 3 and 20 inclusive
     */
    public static boolean validateAbilityScore(String score) {
        try {
            int intScore = Integer.parseInt(score);
            if (intScore < 3) {
                System.out.println("> Please enter a score of 3 or more");
                return false;
            } else if (intScore > 20) {
                System.out.println("> Please enter a score of 20 or less");
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("> Please enter an integer");
            return false;
        }
    }

    /**
     * method: checkIdExists
     * parameters: id, list
     * return: boolean
     * purpose: Confirms passed ID exists in
     *            current list
     */
    public static boolean checkIdExists(String id, CharacterList list) {
        int intId = Integer.parseInt(id);
        int listId = 0;
        int listSize = list.getListSize();
        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                listId = list.getCharacter(i).getId();
                if (listId == intId) {
                    return true;
                }
            }
        }
        System.out.println("> This ID does not exist in the Character list, try again");
        return false;
    }

    // Check for Duplication Methods
    /**
     * method: checkDuplicateIds
     * parameters: id, list
     * return: boolean
     * purpose: Checks that passed ID does not
     *            exist in current list
     */
    public static boolean checkDuplicateIds(String id, CharacterList list) {
        int intId = Integer.parseInt(id);
        int listId = 0;
        int listSize = list.getListSize();
        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                listId = list.getCharacter(i).getId();
                if (listId == intId) {
                    System.out.println("> The ID, " + id + ", already exists, please enter a new one");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * method: checkDuplicateNames
     * parameters: name, list
     * return: boolean
     * purpose: Checks that passed name does not
     *            exist in current list
     */
    public static boolean checkDuplicateNames(String name, CharacterList list) {
        String listName = "";
        int listSize = list.getListSize();
        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                listName = list.getCharacter(i).getName();
                if (listName.equals(name)) {
                    System.out.println("> The name, " + name + ", already exists, please enter a new one");
                    return false;
                }
            }
        }
        return true;
    }

    // Custom Get Methods
    /**
     * method: getValidId
     * parameters: id, list
     * return: int
     * purpose: Returns ID value that has been
     *            validated completely and is ready
     *            to be submitted to new Character
     */
    public static boolean getValidId(String id, CharacterList list) {
        if (validateId(id)) {
            if (checkDuplicateIds(id, list)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * method: getValidName
     * parameters: name, list
     * return: String
     * purpose: Returns name value that has been
     *            validated completely and is ready
     *            to be submitted to a new Character
     */
    public static String getValidName(String name, CharacterList list) {
        while (true) {
            if (validateName(name)) {
                if (checkDuplicateNames(name, list)) {
                    break;
                } else {
                    name = inputAttribute("name");
                }
            } else {
                name = inputAttribute("name");
            }
        }

        return name;
    }

    /**
     * method: getValidClassification
     * parameters: classification
     * return: String
     * purpose: Returns class value that has been
     *            validated completely and is ready
     *            to be submitted to a new Character
     */
    public static String getValidClassification(String classification) {
        boolean loop = true;
        while (loop) {
            switch (classification) {
                case "1", "Barbarian", "barbarian":
                    classification = "Barbarian";
                    loop = false;
                    break;
                case "2", "Fighter", "fighter":
                    classification = "Fighter";
                    loop = false;
                    break;
                case "3", "Ranger", "ranger":
                    classification = "Ranger";
                    loop = false;
                    break;
                case "4", "Rogue", "rogue":
                    classification = "Rogue";
                    loop = false;
                    break;
                case "5", "Sorcerer", "sorcerer":
                    classification = "Sorcerer";
                    loop = false;
                    break;
                case "6", "Warlock", "warlock":
                    classification = "Warlock";
                    loop = false;
                    break;
                case "7", "Wizard", "wizard":
                    classification = "Wizard";
                    loop = false;
                    break;
                default:
                    System.out.println("> Invalid class");
                    classification = inputAttribute("classification");
            }
        }
        return classification;
    }

    /**
     * method: getValidRace
     * parameters: race
     * return: String
     * purpose: Returns race value that's
     *            validated completely and ready
     *            to submit to new Character
     */
    public static String getValidRace(String race) {
        boolean loop = true;
        while (loop) {
            switch (race) {
                case "1", "Dwarf", "dwarf":
                    race = "Dwarf";
                    loop = false;
                    break;
                case "2", "Dragonborn", "dragonborn":
                    race = "Dragonborn";
                    loop = false;
                    break;
                case "3", "Elf", "elf":
                    race = "Elf";
                    loop = false;
                    break;
                case "4", "Gnome", "gnome":
                    race = "Gnome";
                    loop = false;
                    break;
                case "5", "Halfling", "halfling":
                    race = "Halfling";
                    loop = false;
                    break;
                case "6", "Human", "human":
                    race = "Human";
                    loop = false;
                    break;
                default:
                    System.out.println("> Invalid race");
                    race = inputAttribute("race");
            }
        }
        return race;
    }

    /**
     * method: getValidAbilityScore
     * parameters: ability, score
     * return: int
     * purpose: Returns ability score that's
     *            validated completely and ready
     *            to submit to a new Character
     */
    public static int getValidAbilityScore(String ability, String score) {
        while (true) {
            if (validateAbilityScore(score)) {
                break;
            } else {
                score = inputAttribute(ability);
            }
        }
        return Integer.parseInt(score);
    }

    /**
     * method: getRandomAbilityScores
     * parameters: none
     * return: int
     * purpose: Generates random number between
     *            3 and 20 inclusive
     */
    public static int getRandomAbilityScore() {
        Random rand = new Random();
        return rand.nextInt(18) + 3;
    }

    // Print Methods
    /**
     * method: printMainMenu
     * parameters: none
     * return: String
     * purpose: Prints main menu with instructions
     */
    public static String printMainMenu() {

        return """
        \s
        Menu
        ----------------------------------------------------------------
        i - Import a file of Characters
        p - Print a list of created Characters
        c - Create a Character manually
        d - Delete a Character
        u - Update a Character
        r - Roll random ability scores for a Character
        q - Quit
       \s
        Enter the letter of the option you want to use:\s""";
    }

    /**
     * method: printCharHeadings
     * parameters: none
     * return: String
     * purpose: Prints label headings for
     *            Character records
     */
    public static String printCharHeadings() {
        return """
        ID    | Name           | Class    | Race   | Str | Dex | Con
        -----------------------------------------------------------------""";
    }

    /**
     * method: printAttributes
     * parameters: none
     * return: String
     * purpose: Prints available attributes
     *            for user to select from
     */
    public static String printAttributes() {
        return """
        ----------------------------------------------------------------
        ID
        Name
        Class
        Race
        Strength
        Dexterity
        Constitution
        \s""";
    }
}

