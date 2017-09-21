package com.adit;

import java.util.HashSet;

public class Cell {
    private int value;
    private Boolean set;
    private HashSet<Integer> possible;
    public Cell(int val){
        value = val;
        if (val != 0)
            set = true;
        else set = false;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSet(Boolean set) {
        this.set = set;
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
