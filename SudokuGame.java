import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SudokuSolver {
    private int[][] board;
    private static final int SIZE = 9;
    private static final int EMPTY = 0;

    public SudokuSolver(int[][] initialBoard) {
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = initialBoard[i][j];
            }
    }
}

    public boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValidMove(row, col, num)) {
                            board[row][col] = num;
                            if (solve()) {
                                return true;
                            }
                            board[row][col] = EMPTY;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidMove(int row, int col, int num) {
        return !isInRow(row, num) && !isInColumn(col, num) && !isInBox(row - row % 3, col - col % 3, num);
    }

    private boolean isInRow(int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInColumn(int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + boxStartRow][col + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[][] getBoard() {
        return board;
    }
}

public class SudokuGame extends JFrame {
    private JTextField[][] cells;
    private static final int SIZE = 9;
    private static final int EMPTY = 0;

    public SudokuGame() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 400));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(SIZE, SIZE));
        cells = new JTextField[SIZE][SIZE];

        // Create the text fields for the Sudoku cells
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 20));
                mainPanel.add(cells[row][col]);
            }
        }

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        add(solveButton, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    private void solveSudoku() {
        int[][] board = new int[SIZE][SIZE];

        // Read the values from the text fields and populate the board
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String value = cells[row][col].getText();
                if (value.isEmpty()) {
                    board[row][col] = EMPTY;
                } else {
                    board[row][col] = Integer.parseInt(value);
                }
            }
        }

        SudokuSolver solver = new SudokuSolver(board);
        if (solver.solve()) {
            // Update the text fields with the solved values
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    cells[row][col].setText(Integer.toString(solver.getBoard()[row][col]));
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No solution exists.", "Sudoku Solver", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SudokuGame();
            }
        });
    }
}
