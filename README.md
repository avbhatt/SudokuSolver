# SudokuSolver
A Java program that will take in an unsolved Sudoku puzzle and output the solved puzzle.
## Usage
Manually specify Sudoku grid in main function, 0 represents empty space.
E.g.  
``` 
Cell[][] grid = {
              {new Cell(0),new Cell(2),new Cell(0),new Cell(0),new Cell(0),new Cell(4),new Cell(9),new Cell(0),new Cell(3)},
                {new Cell(0),new Cell(6),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(8)},
                {new Cell(3),new Cell(0),new Cell(4),new Cell(0),new Cell(9),new Cell(5),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(9),new Cell(4),new Cell(2),new Cell(6),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(7)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0)},
                {new Cell(5),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(8),new Cell(6),new Cell(4),new Cell(1)},
                {new Cell(0),new Cell(0),new Cell(0),new Cell(7),new Cell(8),new Cell(0),new Cell(1),new Cell(0),new Cell(4)},
                {new Cell(6),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(0),new Cell(7),new Cell(0)},
                {new Cell(4),new Cell(0),new Cell(1),new Cell(5),new Cell(0),new Cell(0),new Cell(0),new Cell(2),new Cell(0)}};
```

## Future Features:
- GUI to enter grid and/or read from text file
- Command line solver
- Executable jar
- Incremental hints
- Mobile App
