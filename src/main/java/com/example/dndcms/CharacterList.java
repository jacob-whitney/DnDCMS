/**
 * Jacob Whitney
 * Software Development I
 * March 12, 2025
 * CharacterList.java
 * Description: manages list of Character objects
 * to be viewed or edited.
 */

package com.example.dndcms;

// Imports
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CharacterList {
    // Attributes
    private List<Character> characters;
    private static final Scanner sc = new Scanner(System.in);

    // Constructor
    public CharacterList(List<Character> characters) {
        this.characters = new ArrayList<>(characters);
    }

    public CharacterList() {
        this.characters = new ArrayList<>();
    }

    // Custom Methods
    /**
     * method: getCharacter
     * parameters: index
     * return: Character
     * purpose: Gets Character object
     *            by index number
     */
    public Character getCharacter(int index) {
        if (!characters.isEmpty() && index >= 0 && index < characters.size()) {
            return characters.get(index);
        } else {
            System.out.println("This list is empty");
            return null;
        }
    }

    /**
     * method: getCharacterList
     * parameters: none
     * return: String
     * purpose: Prints existing list of Characters
     */
    public String getCharacterList() {
        if (!characters.isEmpty()) {
            String charList = "";
            for (int i = 0; i < characters.size(); i++) {
                Character c = characters.get(i);
                charList = charList + c.getId()             + " | ";
                charList = charList + c.getName()           + " | ";
                charList = charList + c.getClassification() + " | ";
                charList = charList + c.getRace() + " |  ";
                charList = charList + c.getStr()            + "  |  ";
                charList = charList + c.getDex()            + "  |  ";
                charList = charList + c.getCon()            + "\n";
            }
            return charList;
        } else {
            return "List is empty";
        }
    }

    /**
     * method: getListSize
     * parameters: none
     * return: int
     * purpose: Gets number of records
     *            in Character list
     */
    public int getListSize() {
        return characters.size();
    }

    /**
     * method: addCharacter
     * parameters: character
     * return: List<Character>
     * purpose: Adds Character to list
     */
    public List<Character> addCharacter( Character character) {
        characters.add(character);
        return characters;
    }

    /**
     * method: deleteCharacter
     * parameters: id
     * return: List<Character>
     * purpose: Deletes Character from list
     */
    public void deleteCharacter(String id) {
        int deleteId = Integer.parseInt(id);
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getId() == deleteId) {
                characters.remove(i);
                break;
            }
        }
    }

    /**
     * method: updateCharacter
     * parameters: id, attribute, value, list
     * return: List<Character>
     * purpose: Updates one attribute for
     *            any Character in list
     */
    public List<Character> updateCharacter(String id, String attribute, String value, CharacterList list) {
        for (Character c : characters) {
            if (c.getId() == Integer.parseInt(id)) {
                boolean loop = true;
                while (loop) {
                    switch (attribute) {
                        case "id", "ID":
                            int updateId = 0;
                            if (IPO.getValidId(value, list)) {
                                updateId = Integer.parseInt(value);
                            }
                            c.setId(updateId);
                            System.out.println("Character " + id + "'s ID updated to " + updateId);
                            loop = false;
                            break;
                        case "name", "Name":
                            String updateName = IPO.getValidName(value, list);
                            c.setName(updateName);
                            System.out.println("Character " + id + "'s Name updated to " + updateName);
                            loop = false;
                            break;
                        case "class", "Class":
                            String updateClassification = IPO.getValidClassification(value);
                            c.setClassification(updateClassification);
                            System.out.println("Character " + id + "'s Class updated to " + updateClassification);
                            loop = false;
                            break;
                        case "race", "Race":
                            String updateRace = IPO.getValidRace(value);
                            c.setRace(updateRace);
                            System.out.println("Character " + id + "'s Race updated to " + updateRace);
                            loop = false;
                            break;
                        case "str", "Str", "strength", "Strength":
                            int updateStr = IPO.getValidAbilityScore("str", value);
                            c.setStr(updateStr);
                            System.out.println("Character " + id + "'s Strength updated to " + updateStr);
                            loop = false;
                            break;
                        case "dex", "Dex", "dexterity", "Dexterity":
                            int updateDex = IPO.getValidAbilityScore("dex", value);
                            c.setDex(updateDex);
                            System.out.println("Character " + id + "'s Dexterity updated to " + updateDex);
                            loop = false;
                            break;
                        case "con", "Con", "constitution", "Constitution":
                            int updateCon = IPO.getValidAbilityScore("con", value);
                            c.setCon(updateCon);
                            System.out.println("Character " + id + "'s Constitution updated to " + updateCon);
                            loop = false;
                            break;
                        default:
                            System.out.println("> The attribute you entered is invalid, please try again");
                            System.out.println(IPO.printAttributes());
                            System.out.print("Attribute: ");
                            attribute = sc.nextLine();
                    }
                }

            }
        }
        return characters;
    }

}

