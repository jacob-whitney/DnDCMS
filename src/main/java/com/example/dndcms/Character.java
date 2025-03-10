/**
 * Jacob Whitney
 * Software Development I
 * March 12, 2025
 * Character.java
 * Description: contains attributes and methods for
 * creating and updating Character objects.
 */

package com.example.dndcms;

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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getDex() {
        return dex;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public int getCon() {
        return con;
    }

    public void setCon(int con) {
        this.con = con;
    }
}
