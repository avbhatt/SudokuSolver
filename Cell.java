package com.adit;

import java.util.HashSet;

public class Cell {
    private int value;
    private Boolean set;
    private HashSet<Integer> possible;
    public Cell(int val){
        value = val;
        set = true;
    }

    public int getValue() {
        return value;
    }

    public Boolean getSet() {
        return set;
    }

    public void setPossible(HashSet<Integer> possible) {
        this.possible = possible;
    }

    public HashSet<Integer> getPossible() {
        return possible;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
