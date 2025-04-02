/**
 * Software Development I
 * April 18, 2025
 * Character.java
 * @author Jacob Whitney
 */

package com.example.dndcms;

/**
 * Description: contains attributes and methods for
 * creating and updating Character objects.
 */
public class Character {
    // Attributes
    private int id;
    private String name;
    private String classification;
    private String race;
    private int str;
    private int dex;
    private int con;

    // Constructor
    /**
     * constructor: Character
     * purpose: initialize a character object with
     * all attributes passed as parameters.
     * @param id Unique integer to identify Character
     * @param name Unique name to identify Character
     * @param classification Describes the type of Character and their skills
     * @param race Describes the look and features of the Character
     * @param str Measures Character ability with strength
     * @param dex Measures Character ability with dexterity
     * @param con Measures Character ability with constitution
     */
    public Character(
            int id,
            String name,
            String classification,
            String race,
            int str,
            int dex,
            int con) {
        this.id = id;
        this.name = name;
        this.classification = classification;
        this.race = race;
        this.str = str;
        this.dex = dex;
        this.con = con;
    }

    // Getters and Setters
    /**
     * ID getter
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * ID setter
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Name getter
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Class getter
     * @return classification
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Class setter
     * @param classification
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }

    /**
     * Race getter
     * @return race
     */
    public String getRace() {
        return race;
    }

    /**
     * Race setter
     * @param race
     */
    public void setRace(String race) {
        this.race = race;
    }

    /**
     * Strength getter
     * @return str
     */
    public int getStr() {
        return str;
    }

    /**
     * Strength setter
     * @param str
     */
    public void setStr(int str) {
        this.str = str;
    }

    /**
     * Dexterity getter
     * @return dex
     */
    public int getDex() {
        return dex;
    }

    /**
     * Dexterity setter
     * @param dex
     */
    public void setDex(int dex) {
        this.dex = dex;
    }

    /**
     * Constitution getter
     * @return
     */
    public int getCon() {
        return con;
    }

    /**
     * Constitution setter
     * @param con
     */
    public void setCon(int con) {
        this.con = con;
    }
}
