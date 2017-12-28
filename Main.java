package com.adit;

import java.util.*;

public class Main {

    private static void printGrid(Cell[][] grid, Boolean possible){
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

    public static void printGrid(Cell[][] grid){
        for (int i = 0; i < 9; i++){
            if (i % 3 == 0) {
                System.out.print("-------------------------\n");
            }
            for (int j  = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                String top;
                if (grid[i][j].getSet())
                    top = grid[i][j].getValue() + " ";
                else if (grid[i][j].getGuessSet())
                    top = grid[i][j].getGuessValue() + " ";
                else
                    top = grid[i][j].getValue() + " " ;
                if (grid[i][j].getGuess() != null){
                    top += "{" + grid[i][j].getGuess() + "} ";
                }
                else if (grid[i][j].getPossible() != null)
                    top += "{" + grid[i][j].getPossible() + "} ";
                System.out.print(top);


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

    private static int getBox(int j, int i){
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

    private static HashSet<Integer> possibles(Cell[][] grid, ArrayList<LinkedHashSet<Integer>> rows, ArrayList<LinkedHashSet<Integer>> cols, ArrayList<LinkedHashSet<Integer>> boxes, int row, int col){
        HashSet<Integer> possible = new HashSet<>();
        int box = getBox(row, col);

        for (int i = 1; i < 10; i++){
            if (!(rows.get(row).contains(i)) && !(cols.get(col).contains(i)) && !(boxes.get(box).contains(i))){
                possible.add(i);
            }
        }
        return possible;
    }

    private static void reset(Cell[][] grid, ArrayList<LinkedHashSet<Integer>> rows, ArrayList<LinkedHashSet<Integer>> cols, ArrayList<LinkedHashSet<Integer>> boxes, ArrayList<ArrayList<Integer>> changesPossibles, ArrayList<ArrayList<Integer>> changesConstraints){
        for (ArrayList<Integer> changesPossible : changesPossibles) {
            grid[changesPossible.get(0)][changesPossible.get(1)].resetGuessPossible();
        }
        for (ArrayList<Integer> changesConstraint : changesConstraints) {
            int i = changesConstraint.get(0);
            int j = changesConstraint.get(1);
            int val = changesConstraint.get(2);
            grid[i][j].resetGuess();
            rows.get(i).remove(val);
            cols.get(j).remove(val);
            boxes.get(getBox(i, j)).remove(val);
        }
    }

    private static void guess(Cell[][] grid, ArrayList<LinkedHashSet<Integer>> rows, ArrayList<LinkedHashSet<Integer>> cols, ArrayList<LinkedHashSet<Integer>> boxes, int[] g){
        Iterator<Integer> potentialIterator;
        if (grid[g[0]][g[1]].emptyStack())
            potentialIterator = grid[g[0]][g[1]].getPossible().iterator();
        else potentialIterator = grid[g[0]][g[1]].getGuess().iterator();
        finish:
        while (potentialIterator.hasNext()) {
            ArrayList<ArrayList<Integer>> changesPossibles = new ArrayList<>();
            ArrayList<ArrayList<Integer>> changesConstraints = new ArrayList<>();
            int potential = potentialIterator.next();
            grid[g[0]][g[1]].setGuessValue(potential);
            rows.get(g[0]).add(potential);
            cols.get(g[1]).add(potential);
            boxes.get(getBox(g[0],g[1])).add(potential);
            changesConstraints.add(new ArrayList<>(Arrays.asList(g[0], g[1], potential)));
            cycle:
            while (true) {
                //printGrid(grid);
                int improve = 0;
                int[] bestGuess = {-1, -1, 9};
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        HashSet<Integer> initial;
                        if (grid[i][j].emptyStack()) {
                            initial = grid[i][j].getPossible();
                        } else {
                            initial = grid[i][j].getGuess();
                        }
                        HashSet<Integer> temp;
                        if (!(grid[i][j].getSet() || grid[i][j].getGuessSet())) {
                            temp = possibles(grid, rows, cols, boxes, i, j);
                            if (initial.size() > temp.size()) {
                                grid[i][j].addGuess(temp);
                                changesPossibles.add(new ArrayList<>(Arrays.asList(i, j)));
                            }
                            if (temp.size() != 1 && temp.size() < bestGuess[2]) {
                                bestGuess[2] = temp.size();
                                bestGuess[1] = j;
                                bestGuess[0] = i;
                            }

                            if (temp.size() == 1) {
                                int val = temp.iterator().next();
                                grid[i][j].setGuessValue(val);
                                rows.get(i).add(val);
                                cols.get(j).add(val);
                                boxes.get(getBox(i, j)).add(val);
                                changesConstraints.add(new ArrayList<>(Arrays.asList(i, j, val)));
                                improve++;
                            } else if (temp.size() == 0) {
                                // Dead end, guess was incorrect
                                //printGrid(grid);
                                reset(grid, rows, cols, boxes, changesPossibles, changesConstraints);
                                //printGrid(grid);
                                break cycle;
                            }
                        }
                    }
                }
                if (improve == 0) {
                    if (bestGuess[0] != -1) {
                        //printGrid(grid);
                        guess(grid, rows, cols, boxes, bestGuess);
                        if (!grid[0][0].getSolved())
                            reset(grid, rows, cols, boxes, changesPossibles, changesConstraints);
                        //printGrid(grid);
                        break;
                    }
                    else {
                        grid[0][0].setSolved();
                        for (int i = 0; i < 9; i++){
                            for (int j = 0; j < 9; j++){
                                if (grid[i][j].getValue() == 0)
                                    grid[i][j].setValue(grid[i][j].getGuessValue());
                            }
                        }
                        break finish;
                    }
                }
            }
        }
    }

    private static void solve(Cell[][] grid, ArrayList<LinkedHashSet<Integer>> rows, ArrayList<LinkedHashSet<Integer>> cols, ArrayList<LinkedHashSet<Integer>> boxes){
        while (true) {
            int improve = 0;
            int[] bestGuess = {-1,-1,9};
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    HashSet<Integer> temp = new HashSet<>();
                    if (!grid[i][j].getSet()) {
                        temp = possibles(grid, rows, cols, boxes, i, j);
                        grid[i][j].setPossible(temp);
                        if (temp.size() < bestGuess[2]){
                            bestGuess[2] = temp.size();
                            bestGuess[1] = j;
                            bestGuess[0] = i;
                        }
                    }
                    if (temp.size() == 1){
                        int val = temp.iterator().next();
                        grid[i][j].setValue(val);
                        rows.get(i).add(val);
                        cols.get(j).add(val);
                        boxes.get(getBox(i,j)).add(val);
                        grid[i][j].setPossible(null);
                        improve++;
                    }
                }
            }
            if (improve == 0){
                if (bestGuess[0] != -1){
                    //printGrid(grid, true);
                    guess(grid, rows, cols, boxes, bestGuess);
                }
                break;
            }
        }
    }

    public static void main(String[] args) {
        Cell[][] grid = {
              /*{new Cell(0),new Cell(2),new Cell(0),new Cell(0),new Cell(0),new Cell(4),new Cell(9),new Cell(0),new Cell(3)},
                {new Cell(0),new Cell(6),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(8)},
                {new Cell(3),new Cell(0),new Cell(4),new Cell(0),new Cell(9),new Cell(5),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(9),new Cell(4),new Cell(2),new Cell(6),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(7)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(5),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(8),new Cell(6),new Cell(4),new Cell(1)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(7),new Cell(8),new Cell(0),new Cell(1),new Cell(0),new Cell(4)},
                {new Cell(6),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(7),new Cell(0)},
                {new Cell(4),new Cell(0),new Cell(1),new Cell(5),new Cell(0),new Cell(0),new Cell(0),new Cell(2),new Cell(0)}};*/
             /* {new Cell(2),new Cell(0),new Cell(8),new Cell(3),new Cell(0),new Cell(0),new Cell(0),new Cell(6),new Cell(1)},
                {new Cell(0),new Cell(7),new Cell(1),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(5)},
                {new Cell(0),new Cell(3),new Cell(0),new Cell(6),new Cell(7),new Cell(0),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(4),new Cell(0),new Cell(0),new Cell(7),new Cell(5),new Cell(0),new Cell(6),new Cell(1),new Cell(8)},
                {new Cell(0),new Cell(6),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(5),new Cell(0)},
                {new Cell(1),new Cell(8),new Cell(5),new Cell(0),new Cell(6),new Cell(4),new Cell(0),new Cell(0),new Cell(3)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(9),new Cell(2),new Cell(0),new Cell(8),new Cell(0)},
                {new Cell(8),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(5),new Cell(9),new Cell(0)},
                {new Cell(7),new Cell(4),new Cell(0),new Cell(0),new Cell(0),new Cell(6),new Cell(1),new Cell(0),new Cell(2)}};*/
             /*{new Cell(0),new Cell(9),new Cell(0),new Cell(0),new Cell(0),new Cell(7),new Cell(1),new Cell(0),new Cell(2)},
                {new Cell(2),new Cell(0),new Cell(1),new Cell(0),new Cell(3),new Cell(4),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(0),new Cell(4),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(6)},
                {new Cell(1),new Cell(2),new Cell(6),new Cell(3),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(7)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(4),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(6),new Cell(9),new Cell(2),new Cell(5)},
                {new Cell(3),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(6),new Cell(0)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(7),new Cell(5),new Cell(0),new Cell(2),new Cell(0),new Cell(8)},
                {new Cell(8),new Cell(0),new Cell(5),new Cell(2),new Cell(0),new Cell(0),new Cell(0),new Cell(9),new Cell(0)}};*/
                {new Cell(0),new Cell(0),new Cell(5),new Cell(0),new Cell(0),new Cell(3),new Cell(6),new Cell(0),new Cell(0)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(5),new Cell(0),new Cell(0),new Cell(0),new Cell(4)},
                {new Cell(0),new Cell(2),new Cell(8),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(3)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(7),new Cell(0),new Cell(9),new Cell(6)},
                {new Cell(0),new Cell(4),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(7),new Cell(0)},
                {new Cell(6),new Cell(1),new Cell(0),new Cell(4),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(7),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(2),new Cell(3),new Cell(0)},
                {new Cell(9),new Cell(0),new Cell(0),new Cell(0),new Cell(2),new Cell(0),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(0),new Cell(0),new Cell(2),new Cell(8),new Cell(0),new Cell(0),new Cell(9),new Cell(0),new Cell(0)}};
        System.out.println("Confirm Input:");
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

        System.out.println("Solution:");
        printGrid(grid, false);
    }
}
