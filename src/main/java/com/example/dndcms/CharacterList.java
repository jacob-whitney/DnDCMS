/**
 * Software Development I
 * April 18, 2025
 * CharacterList.java
 * @author Jacob Whitney
 */

package com.example.dndcms;

// Imports
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description: manages list of Character objects
 * to be viewed or edited.
 */
public class CharacterList {
    // Attributes
    private List<Character> characters;
    private static final Scanner sc = new Scanner(System.in);

    // Constructor

    /**
     * constructor: CharacterList
     * purpose: initialize list object that
     * contains Character objects
     * @param characters List of created characters to be
     *                   added to the CharacterList object
     */
    public CharacterList(List<Character> characters) {
        this.characters = new ArrayList<>(characters);
    }

    /**
     * constructor: CharacterList
     * purpose: initialize list object that
     * does not contain any Character objects
     */
    public CharacterList() {
        this.characters = new ArrayList<>();
    }

    // Custom Methods
    /**
     * method: getCharacter
     * purpose: Gets Character object
     *            by index number
     * @param index Character's object index to be retrieved by
     * @return Character identified by its object index
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
     * method: getListSize
     * purpose: Gets number of records
     * in Character list
     * @return Integer showing size of list
     */
    public int getListSize() {
        return characters.size();
    }

    /**
     * method: addCharacter
     * purpose: Adds Character to list
     * @param character Object to added to CharacterList
     * @return CharacterList with new Character added
     */
    public List<Character> addCharacter( Character character) {
        characters.add(character);
        return characters;
    }

    /**
     * method: deleteCharacter
     * purpose: Deletes Character from list
     * @param id Unique integer to identify Character to delete
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
     * method: deleteAllCharacters
     * purpose: Deletes Character from list
     */
    public void deleteAllCharacters() {
        characters.clear();
    }

    /**
     * method: updateCharacter
     * purpose: Updates one attribute for
     * any Character in list
     * @param id Unique integer to identify Character
     * @param attribute Character attribute to be updated
     * @param value What will attribute be updated to
     * @param list CharacterList to hold updated Character
     * @return CharacterList that contains Character to be updated
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

