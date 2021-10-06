import java.io.IOException;

public class SudokuResolver {

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                Sudoku sudoku = new Sudoku();
                int[][] board = sudoku.readBoard(args[0]);
                sudoku.solve(board);
                sudoku.printBoard(board);
            } else {
                System.out.println("Please, provide a file name in arguments.");
            }
        } catch (IOException ex) {
            System.out.println("Error occurred while reading matrix.");
        }
    }
}
