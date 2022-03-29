package com.company;

public class Stats {
    public int dmgUpper;
    public int dmgLower;
    public int defense;

    public Stats(int dmgUpper, int dmgLower, int defense) {
        this.dmgUpper = dmgUpper;
        this.dmgLower = dmgLower;
        this.defense = defense;
    }

    public int getDmgUpper() {
        return dmgUpper;
    }

    public void setDmgUpper(int dmgUpper) {
        this.dmgUpper = dmgUpper;
    }

    public int getDmgLower() {
        return dmgLower;
    }

    public void setDmgLower(int dmgLower) {
        this.dmgLower = dmgLower;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
