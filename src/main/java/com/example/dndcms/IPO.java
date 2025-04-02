/**
 * Software Development I
 * April 18, 2025
 * IPO.java
 * @author Jacob Whitney
 */

package com.example.dndcms;

// Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

/**
 * Description: Manages CLI menu and user interactions
 */
public class IPO {
    private static final Scanner sc = new Scanner(System.in);

    // User Input Methods
    /**
     * method: inputAttribute
     * purpose: User is instructed to input a specific
     * Character attribute
     * @param attribute Identifies which Character attribute will be input
     * @return String that the user inputs
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

    // Validation Methods
    /**
     * method: validateId
     * purpose: Confirms that ID is a valid integer
     * @param id Unique integer that is being validated
     * @return true or false if id is valid or not
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
     * purpose: Confirms that name is not too short
     * or long
     * @param name Unique String that is being validated
     * @return true or false if name is valid or not
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
     * purpose: Confirms that score is valid integer
     * between 3 and 20 inclusive
     * @param score Integer that must be a valid ability score
     * @return true or false if score is valid
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

    // Check for Duplication Methods
    /**
     * method: checkDuplicateIds
     * purpose: Checks that passed ID does not
     * exist in current list
     * @param id Unique integer being checked against all existing list IDs
     * @param list CharacterList with IDs to be checked against new ID
     * @return true or false if the new ID is unique or not
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
     * purpose: Checks that passed name does not
     * exist in current list
     * @param name Unique String being checked against all existing list names
     * @param list CharacterList with names to be checked against new name
     * @return true or false if the new name is unique or not
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
     * purpose: Returns true or false if
     * ID value is validated completely and
     * is ready to be submitted to new Character
     * @param id Unique integer to be run through all ID validation
     * @param list CharacterList to check new ID against
     * @return true or false if ID passes all validation
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
     * purpose: Returns name value that has been
     * validated completely and is ready
     * to be submitted to a new Character
     * @param name Unique String to be run through all name validation
     * @param list CharacterList to check new name against
     * @return String of name that is valid
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
     * purpose: Returns class value that has been
     * validated completely and is ready
     * to be submitted to a new Character
     * @param classification Character class to be checked for validation
     * @return String of class that is valid
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
     * purpose: Returns race value that's
     * validated completely and ready
     * to submit to new Character
     * @param race Character race to be checked for validation
     * @return String of race that is valid
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
     * purpose: Returns ability score that's
     * validated completely and ready
     * to submit to a new Character
     * @param ability Identify which ability is being validated
     * @param score Integer value to be checked for validation
     * @return Score that is valid
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
     * purpose: Generates random number between
     * 3 and 20 inclusive
     * @return Score that is a random integer within valid constraints
     */
    public static int getRandomAbilityScore() {
        Random rand = new Random();
        return rand.nextInt(18) + 3;
    }

    // Print Methods
    /**
     * method: printAttributes
     * purpose: Prints available attributes
     * for user to select from
     * @return String of all attributes to be printed to the console
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

