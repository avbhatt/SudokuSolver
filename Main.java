package com.adit;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;

public class Main {

    public static void printGrid(Cell[][] grid, Boolean possible){
        for (int i = 0; i < 9; i++){
            if (i % 3 == 0) {
                System.out.print("-------------------------\n");
            }
            for (int j  = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                if (possible){
                    String top = grid[i][j].getValue() + " ";
                    if (grid[i][j].getPossible() != null){
                        top += "{" + grid[i][j].getPossible() + "} ";
                    }
                    System.out.print(top);
                }
                else {
                    System.out.print(grid[i][j].getValue() + " ");
                }

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

    public static HashSet<Integer> possibles(Cell[][] grid, ArrayList<LinkedHashSet<Integer>> rows, ArrayList<LinkedHashSet<Integer>> cols, ArrayList<LinkedHashSet<Integer>> boxes, int row, int col){
        HashSet<Integer> possible = new HashSet<>();
        int box = getBox(row, col);

        for (int i = 1; i < 10; i++){
            if (!(rows.get(row).contains(i)) && !(cols.get(col).contains(i)) && !(boxes.get(box).contains(i))){
                possible.add(i);
            }
        }
        return possible;
    }


    public static void solve(Cell[][] grid, ArrayList<LinkedHashSet<Integer>> rows, ArrayList<LinkedHashSet<Integer>> cols, ArrayList<LinkedHashSet<Integer>> boxes){
        while (true) {
            int improve = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    HashSet<Integer> temp = new HashSet<>();
                    if (!grid[i][j].getSet()) {
                        temp = possibles(grid, rows, cols, boxes, i, j);
                        grid[i][j].setPossible(temp);
                    }
                    if (temp.size() == 1){
                        int val = temp.iterator().next();
                        grid[i][j].setValue(val);
                        rows.get(i).add(val);
                        cols.get(j).add(val);
                        boxes.get(getBox(i,j)).add(val);
                        grid[i][j].setPossible(null);
                        grid[i][j].setSet(true);
                        improve++;
                    }
                }
            }
            if (improve == 0){
                break;
            }
        }
    }

    public static void main(String[] args) {
        /*Cell[][] grid = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Cell(1);
            }
        }*/
        Cell[][] grid = {
                {new Cell(2),new Cell(4),new Cell(0),new Cell(0),new Cell(0),new Cell(9),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(1),new Cell(0),new Cell(0),new Cell(0),new Cell(3),new Cell(0),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(0),new Cell(0),new Cell(2),new Cell(2),new Cell(6),new Cell(0),new Cell(0),new Cell(3),new Cell(0)},
                {new Cell(0),new Cell(0),new Cell(4),new Cell(4),new Cell(0),new Cell(0),new Cell(3),new Cell(0),new Cell(0)},
                {new Cell(5),new Cell(0),new Cell(1),new Cell(1),new Cell(8),new Cell(3),new Cell(7),new Cell(0),new Cell(9)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(7),new Cell(8),new Cell(0),new Cell(0)},
                {new Cell(0),new Cell(7),new Cell(0),new Cell(0),new Cell(1),new Cell(5),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(2),new Cell(0),new Cell(0),new Cell(0),new Cell(1)},
                {new Cell(0),new Cell(0),new Cell(3),new Cell(3),new Cell(0),new Cell(0),new Cell(0),new Cell(8),new Cell(7)}};
        /*{{new Cell(2),new Cell(0),new Cell(8),new Cell(3),new Cell(0),new Cell(0),new Cell(0),new Cell(6),new Cell(1)},
                {new Cell(0),new Cell(7),new Cell(1),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(5)},
                {new Cell(0),new Cell(3),new Cell(0),new Cell(6),new Cell(7),new Cell(0),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(4),new Cell(0),new Cell(0),new Cell(7),new Cell(5),new Cell(0),new Cell(6),new Cell(1),new Cell(8)},
                {new Cell(0),new Cell(6),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(5),new Cell(0)},
                {new Cell(1),new Cell(8),new Cell(5),new Cell(0),new Cell(6),new Cell(4),new Cell(0),new Cell(0),new Cell(3)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(9),new Cell(2),new Cell(0),new Cell(8),new Cell(0)},
                {new Cell(8),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(5),new Cell(9),new Cell(0)},
                {new Cell(7),new Cell(4),new Cell(0),new Cell(0),new Cell(0),new Cell(6),new Cell(1),new Cell(0),new Cell(2)}};*/
        printGrid(grid, false);

        ArrayList<LinkedHashSet<Integer>> rows = new ArrayList<>();
        ArrayList<LinkedHashSet<Integer>> cols = new ArrayList<>();
        ArrayList<LinkedHashSet<Integer>> boxes = new ArrayList<>(9);
        for (int j = 0; j < 9; j++) {
            List<Integer> trow = new ArrayList<>();
            Arrays.asList(grid[j]).forEach( (cell) -> trow.add(cell.getValue()));
            rows.add(new LinkedHashSet<>(trow));
            LinkedHashSet<Integer> tcol = new LinkedHashSet<>();
            for (int i = 0; i < 9; i++) {
                tcol.add(grid[i][j].getValue());
                try {
                    int box = getBox(j, i);
                    (boxes.get(box)).add(grid[j][i].getValue());
                } catch(IndexOutOfBoundsException e) {
                    LinkedHashSet<Integer> t = new LinkedHashSet<>();
                    t.add(grid[j][i].getValue());
                    boxes.add(t);
                }
            }
            cols.add(tcol);
        }
        solve(grid, rows, cols, boxes);
        printGrid(grid, true);
    }
}
