package com.adit;

import java.util.HashSet;
import java.util.Stack;

/*
Need a stack to hold guessPossible sets
peek into set to analyze
pop off to go back to previous guessPossible */
public class Cell {
    private int value;
    private Boolean set;
    private HashSet<Integer> possible;
    private int guessValue;
    private Boolean guessSet;
    private Stack<HashSet<Integer>> guesses;
    //private HashSet<Integer> guessPossible;

    public Cell(int val){
        value = val;
        if (val != 0)
            set = true;
        else set = false;
        guesses = new Stack<>();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        this.set = true;
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

    public int getGuessValue() {
        return guessValue;
    }

    public void setGuessValue(int guessValue) {
        this.guessValue = guessValue;
        this.guessSet = true;
    }

    public Boolean getGuessSet() {
        if (guessSet == null)
            guessSet = false;
        return guessSet;
    }

   /* public HashSet<Integer> getGuessPossible() {
        return guessPossible;
    }

    public void setGuessPossible(HashSet<Integer> guessPossible) {
        this.guessPossible = guessPossible;
    }*/

    public HashSet<Integer> getGuess() {
        if (guesses.empty()){
            return null;
        }
        else return guesses.peek();
    }

    public void addGuess(HashSet<Integer> guess) {
        this.guesses.push(guess);
    }

    public Boolean emptyStack() {
        return this.guesses.empty();
    }

    public void resetGuessPossible(){
        this.guesses.pop();
    }

    public void resetGuess() {
        this.guessValue = 0;
        this.guessSet = false;
    }
}
