package connectfour;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class ConnectFourGUI {

    private int rows;
    private int columns;
    private JLabel[][] grid;
    private JFrame frame;
    private JPanel gridPanel;
    private JPanel buttonPanel;
    private JLabel turnLabel;
    private char currentPlayer;
    private ImageIcon playerXDisc;
    private ImageIcon playerODisc;

     /**
     * Constructor for the ConnectFourGUI class.
     * Initializes the game window and GUI components, sets up the board, and displays the interface.
     * Sets up players' disc icons, window properties, and the main frame layout.
     */
    public ConnectFourGUI() {
        selectGridSize();
        currentPlayer = 'X';

        playerXDisc = new ImageIcon("C:\\Users\\YOUSEF\\Desktop\\uni\\IV\\prog tech\\prac\\assignment2\\ConnectFour\\src\\connectfour\\player1.png");
        playerODisc = new ImageIcon("C:\\Users\\YOUSEF\\Desktop\\uni\\IV\\prog tech\\prac\\assignment2\\ConnectFour\\src\\connectfour\\player2.png");
        playerXDisc = scaleImageIcon(playerXDisc, 90, 100);
        playerODisc = scaleImageIcon(playerODisc, 90, 100);

        frame = new JFrame("Connect Four");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            int confirm = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to exit the game?", 
                "Exit Confirmation", 
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        });
        
        turnLabel = new JLabel("Player " + currentPlayer + "'s turn");
        frame.add(turnLabel, BorderLayout.SOUTH);

        gridPanel = new JPanel(new GridLayout(rows, columns));
        buttonPanel = new JPanel(new GridLayout(1, columns));

        for (int i = 0; i < columns; i++) {
            JButton button = new JButton("Drop");
            final int column = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dropDisc(column);
                }
            });
            buttonPanel.add(button);
        }

        gridPanel = new JPanel(new GridLayout(rows, columns));
        buttonPanel = new JPanel(new GridLayout(1, columns));

        for (int i = 0; i < columns; i++) {
            JButton button = new JButton("Drop");
            final int column = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dropDisc(column);
                }
            });
            buttonPanel.add(button);
        }

        grid = new JLabel[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = new JLabel(" ", JLabel.CENTER);
                grid[i][j].setOpaque(true);
                grid[i][j].setBackground(java.awt.Color.BLUE);
                grid[i][j].setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
                gridPanel.add(grid[i][j]);
            }
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem resetMenuItem = new JMenuItem("Reset");
        resetMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGrid();
            }
        });
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to exit the game?", 
                "Exit Confirmation", 
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        });
        menu.add(resetMenuItem);
        menu.add(exitMenuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);

        frame.setSize(1024, 1024);
        frame.setVisible(true);
    }

     /**
     * Scales an ImageIcon to the specified width and height.
     *
     * @param icon  the ImageIcon to be scaled
     * @param width the desired width of the scaled icon
     * @param height the desired height of the scaled icon
     * @return a new ImageIcon scaled to the specified dimensions
     */
    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
    
     /**
     * Handles the action of dropping a disc into a column when a player clicks a drop button.
     * Places the current player's disc in the lowest empty row in the specified column,
     * checks for a win or draw condition, and updates the turn label.
     *
     * @param column the column where the disc should be dropped
     */
    private void dropDisc(int column) {
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][column].getIcon() == null) {
                grid[row][column].setIcon(currentPlayer == 'X' ? playerXDisc : playerODisc);
                
                if (checkWin(row, column)) {
                    JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
                    resetGrid();
                } else if (checkDraw()) {
                    JOptionPane.showMessageDialog(frame, "It's a draw!");
                    resetGrid();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    turnLabel.setText("Player " + currentPlayer + "'s turn");
                }
                return;
            }
        }
        
        JOptionPane.showMessageDialog(frame, "This column is full!!!! Please choose another column:)");
    }

     /**
     * Checks if placing a disc at the specified row and column results in a win.
     * Calls checkDirection to check four possible directions (horizontal, vertical, and two diagonals).
     *
     * @param row the row of the placed disc
     * @param column the column of the placed disc
     * @return true if the current player has won, false otherwise
     */
    private boolean checkWin(int row, int column) {
        return checkDirection(row, column, 1, 0) ||
               checkDirection(row, column, 0, 1) || 
               checkDirection(row, column, 1, 1) ||
               checkDirection(row, column, 1, -1);
    }

     /**
     * Checks if the game board is in a draw state by confirming that all cells are filled
     * and no player has won.
     *
     * @return true if the game is a draw, false otherwise
     */ 
    private boolean checkDraw() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j].getIcon() == null) {
                    return false;
                }
            }
        }
        return true;
    }

     /**
     * Checks a specific direction from a given row and column for four consecutive discs
     * of the same type. Calls countInDirection to count discs in both directions from the starting cell.
     *
     * @param row the row of the starting cell
     * @param column the column of the starting cell
     * @param deltaRow the change in row to check each consecutive cell
     * @param deltaColumn the change in column to check each consecutive cell
     * @return true if four consecutive discs of the same type are found, false otherwise
     */
    private boolean checkDirection(int row, int column, int deltaRow, int deltaColumn) {
        int count = 1;
        count += countInDirection(row, column, deltaRow, deltaColumn);
        count += countInDirection(row, column, -deltaRow, -deltaColumn);
        return count >= 4;
    }

     /**
     * Counts consecutive discs of the same type in a specified direction, starting from a given cell.
     * Moves in the direction specified by deltaRow and deltaColumn until a different disc or an
     * out-of-bounds cell is encountered.
     *
     * @param row the row of the starting cell
     * @param column the column of the starting cell
     * @param deltaRow the change in row for each consecutive cell
     * @param deltaColumn the change in column for each consecutive cell
     * @return the count of consecutive discs in the specified direction
     */
    private int countInDirection(int row, int column, int deltaRow, int deltaColumn) {
        int count = 0;
        ImageIcon currentIcon = currentPlayer == 'X' ? playerXDisc : playerODisc;
        int newRow = row + deltaRow;
        int newCol = column + deltaColumn;

        while (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < columns) {
            if (grid[newRow][newCol].getIcon() != null && grid[newRow][newCol].getIcon().equals(currentIcon)) {
                count++;
            } else {
                break;
            }
            newRow += deltaRow;
            newCol += deltaColumn;
        }

        return count;
    }

     /**
     * Resets the game grid by clearing all cells and resetting the current player.
     * Updates the turn label to indicate the start of a new game.
     */
    private void resetGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j].setIcon(null);
            }
        }
        currentPlayer = 'X';
        turnLabel.setText("Player " + currentPlayer + "'s turn");
    }

     /**
     * Displays a dialog to allow the player to select the grid size.
     * Sets the number of rows and columns based on the chosen size.
     * Exits the game if the dialog is closed without a selection.
     */
   private void selectGridSize() {
    String[] options = {"8x5", "10x6", "12x7"};
    
    String message = "Select grid size:";

    JOptionPane optionPane = new JOptionPane(
        message, 
        JOptionPane.QUESTION_MESSAGE, 
        JOptionPane.DEFAULT_OPTION, 
        null, 
        options, 
        options[0]
    );

    JDialog dialog = optionPane.createDialog("Grid Size");
    
    dialog.setSize(400, 200); 
    dialog.setLocationRelativeTo(frame); 
    dialog.setVisible(true);

    String selectedSize = (String) optionPane.getValue();
    if (selectedSize == null) {
        System.exit(0);
        return; 
    }

    switch (selectedSize) {
        case "8x5":
            rows = 5;
            columns = 8;
            break;
        case "10x6":
            rows = 6;
            columns = 10;
            break;
        case "12x7":
            rows = 7;
            columns = 12;
            break;
    }
    }
   
    public static void main(String[] args) {
        new ConnectFourGUI();
    }
}
