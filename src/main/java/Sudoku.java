import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Sudoku {

    private static final int BOARD_SIZE = 9;
    private static final int BOX_SIZE = 3;
    private static final int NO_VALUE = 0;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = BOARD_SIZE;
    private static final int BOARD_START_INDEX = 0;
    private static final String EMPTY_CELL = "x";
    private static final String SEPARATOR = ",";

    public int[][] readBoard(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String line = br.readLine();
            int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
            int rowNum = 0;
            while (line != null) {
                board[rowNum] = parseLine(line);
                rowNum++;
                line = br.readLine();
            }
            return board;
        } finally {
            br.close();
        }
    }

    private int[] parseLine(String line) {
        String[] row = line.replaceAll(EMPTY_CELL, String.valueOf(NO_VALUE)).split(SEPARATOR);
        return Arrays.stream(row).mapToInt(Integer::parseInt).toArray();
    }

    public boolean solve(int[][] board) {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                if (board[row][column] == NO_VALUE) {
                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
                        board[row][column] = k;
                        if (checkRowConstraint(board, row)
                                && checkColumnConstraint(board, column)
                                && checkBoxConstraint(board, row, column)
                                && solve(board)) {
                            return true;
                        }
                        board[row][column] = NO_VALUE;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRowConstraint(int[][] board, int row) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
            if (!checkConstraint(board, row, constraint, column)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumnConstraint(int[][] board, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            if (!checkConstraint(board, row, constraint, column)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkBoxConstraint(int[][] board, int row, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        int boxRowStart = (row / BOX_SIZE) * BOX_SIZE;
        int boxRowEnd = boxRowStart + BOX_SIZE;

        int boxColumnStart = (column / BOX_SIZE) * BOX_SIZE;
        int boxColumnEnd = boxColumnStart + BOX_SIZE;

        for (int r = boxRowStart; r < boxRowEnd; r++) {
            for (int c = boxColumnStart; c < boxColumnEnd; c++) {
                if (!checkConstraint(board, r, constraint, c))
                    return false;
            }
        }
        return true;
    }

    private boolean checkConstraint(int[][] board, int row, boolean[] constraint, int column) {
        if (board[row][column] != NO_VALUE) {
            if (!constraint[board[row][column] - 1]) {
                constraint[board[row][column] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }

    public void printBoard(int[][] board) {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                System.out.print(board[row][column] + " ");
            }
            System.out.println();
        }
    }
}
