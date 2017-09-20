package com.adit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Main {

    public static void printGrid(Cell[][] grid){
        for (int i = 0; i < 9; i++){
            if (i % 3 == 0) {
                System.out.print("-------------------------\n");
            }
            for (int j  = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(grid[i][j].getValue() + " {" + grid[i][j].getPossible() + "} ");
                if (j == 8) {
                    System.out.print("|");
                }
            }
            System.out.print("\n");
            if (i == 8) {
                System.out.print("-------------------------\n");
            }
        }
    }

    public static int getBox(int j, int i){
        if (j < 3) {
            if (i < 3) {
                return 0;
            }
            else if (i < 6){
                return 1;
            }
            else {
                return 2;
            }
        }
        else if (j < 6) {
            if (i < 3) {
                return 3;
            }
            else if (i < 6){
                return 4;
            }
            else {
                return 5;
            }
        }
        else {
            if (i < 3) {
                return 6;
            }
            else if (i < 6){
                return 7;
            }
            else {
                return 8;
            }
        }
    }

    public static HashSet<Integer> possibles(Cell[][] grid, ArrayList<LinkedHashSet<Cell>> rows, ArrayList<LinkedHashSet<Cell>> cols, ArrayList<LinkedHashSet<Cell>> boxes, int row, int col){
        HashSet<Integer> possible = new HashSet<>();
        int box = getBox(row, col);

        for (int i = 1; i < 10; i++){
            if (!(rows.get(row).contains(i)) && !(cols.get(col).contains(i)) && !(boxes.get(box).contains(i))){
                possible.add(i);
            }
        }
        return possible;
    }


    public static void solve(Cell[][] grid, ArrayList<LinkedHashSet<Cell>> rows, ArrayList<LinkedHashSet<Cell>> cols, ArrayList<LinkedHashSet<Cell>> boxes){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].getSet()) {
                    grid[i][j].setPossible(possibles(grid, rows, cols, boxes, i, j));
                }
            }
        }
        printGrid(grid);
    }

    public static void main(String[] args) {
        /*Cell[][] grid = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Cell(1);
            }
        }*/
        Cell[][] grid = {{new Cell(2),new Cell(0),new Cell(8),new Cell(3),new Cell(0),new Cell(0),new Cell(0),new Cell(6),new Cell(1)},
                {new Cell(0),new Cell(7),new Cell(1),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(5)},
                {new Cell(0),new Cell(3),new Cell(0),new Cell(6),new Cell(7),new Cell(0),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(4),new Cell(0),new Cell(0),new Cell(7),new Cell(5),new Cell(0),new Cell(6),new Cell(1),new Cell(8)},
                {new Cell(0),new Cell(6),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(5),new Cell(0)},
                {new Cell(1),new Cell(8),new Cell(5),new Cell(0),new Cell(6),new Cell(4),new Cell(0),new Cell(0),new Cell(3)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(9),new Cell(2),new Cell(0),new Cell(8),new Cell(0)},
                {new Cell(8),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(5),new Cell(9),new Cell(0)},
                {new Cell(7),new Cell(4),new Cell(0),new Cell(0),new Cell(0),new Cell(6),new Cell(1),new Cell(0),new Cell(2)}};
        printGrid(grid);

        ArrayList<LinkedHashSet<Cell>> rows = new ArrayList<>();
        ArrayList<LinkedHashSet<Cell>> cols = new ArrayList<>();
        ArrayList<LinkedHashSet<Cell>> boxes = new ArrayList<>(9);
        for (int j = 0; j < 9; j++) {
            rows.add(new LinkedHashSet<>(Arrays.asList(grid[j])));
            LinkedHashSet<Cell> temp = new LinkedHashSet<>();
            for (int i = 0; i < 9; i++) {
                temp.add(grid[i][j]);
                try {
                    int box = getBox(j, i);
                    (boxes.get(box)).add(grid[j][i]);
                } catch(IndexOutOfBoundsException e) {
                    LinkedHashSet<Cell> t = new LinkedHashSet<>();
                    t.add(grid[j][i]);
                    boxes.add(t);
                }
            }
            cols.add(temp);
        }
        System.out.println(rows);
        System.out.println(cols);
        System.out.print(boxes);

        solve(grid, rows, cols, boxes);
    }
}
