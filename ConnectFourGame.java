package connectfourgame;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * connect four - a two-player console game.
 * players take turns dropping pieces into a 6x7 grid.
 * the first player to connec1t 4 pieces in a row wins.
 * supports horizontal, vertical and diagonal win conditions.
 *
 * @author melihkaanmetek
 */
public class ConnectFourGame {

    //number of rows on the board - standard connect four has 6 rows
    private static final int rows = 6;
    //number of columns on the board - standard connect four has 7 columns
    private static final int cols = 7;
    //how many consecutive pieces are needed to win
    private static final int winLength = 4;

    //represents an empty cell on the board
    private static final int empty = 0;
    //represents a piece belonging to player 1
    private static final int playerOne = 1;
    //represents a piece belonging to player 2
    private static final int playerTwo = 2;

    //game status: the game is still in progress
    private static final int ongoing = 1;
    //game status: no more moves available and no winner - it's a draw
    private static final int draw = 0;
    //game status: one of the players has connected 4 pieces
    private static final int win = 2;

    public static void main(String[] args) {
        //create a single scanner object to read all user input throughout the game
        Scanner input = new Scanner(System.in);

        //show the welcome screen before starting the first game
        printWelcome();

        //this boolean controls whether the players want to play another round
        boolean playAgain = true;

        //keep starting new games until the players choose to stop
        while (playAgain) {
            //run one full game from start to finish
            playGame(input);
            //after the game ends, ask if they want to play again
            playAgain = askPlayAgain(input);
        }
        //say goodbye after the players are done
        System.out.println("\nthanks for playing! goodbye.");
        //close the scanner to release system resources
        input.close();
    }
    /**
     *displays the welcome banner and basic instructions to the players.
     *called once at the very beginning of the program.
     */
    private static void printWelcome() {
        //top border of the welcome banner
        System.out.println("==================================");
        //game title displayed in the center
        System.out.println("    welcome to connect four!");
        //bottom border of the banner
        System.out.println("==================================");
        //tell players which symbol they will use during the game
        System.out.println("  player 1: x   |   player 2: o");
        //remind players of the main goal of the game
        System.out.println("  connect 4 pieces in a row to win");
        //separator before the game begins
        System.out.println("==================================\n");
    }
    /**
     *controls the flow of a single game.
     *creates a fresh board, alternates turns between players,
     *and loops until the game is either won or drawn.
     */
    private static void playGame(Scanner input) {
        //create a new empty board for this game session
        //the board is a 2d array where each cell holds 0, 1 or 2
        int[][] board = new int[rows][cols];
        // player one always goes first at the start of each game
        boolean isPlayerOne = true;
        //start with the game in progress - will change when someone wins or draws
        int status = ongoing;

        //show the empty board before any moves are made
        printBoard(board);

        //keep playing turns until the game is over
        while (status == ongoing) {
            //determine which player is taking this turn based on the boolean flag
            int currentPlayer = isPlayerOne ? playerOne : playerTwo;

            //ask the current player to pick a column and validate their input
            int column = getPlayerChoice(input, currentPlayer);

            //try to place the piece in the chosen column
            //if the column is full, dropPiece returns false and we skip switching turns
            if (!dropPiece(board, column, currentPlayer)) {
                //notify the player their chosen column has no room left
                System.out.println("this column is full! try a different one.");
                // kip the rest of this iteration so the same player tries again
                continue;
            }

            //show the updated board after the piece was placed
            printBoard(board);

            //check if the game is over after this move
            status = checkGameStatus(board);

            // if the status changed to win, announce the winner
            if (status == win) {
                System.out.println(">>> player " + currentPlayer + " wins! <<<");
            }
            //if the status changed to draw, announce the tie
            else if (status == draw) {
                System.out.println(">>> it's a draw! the board is full. <<<");
            }

            //flip the turn to the other player for the next iteration
            isPlayerOne = !isPlayerOne;
        }
    }
    /**
     * asks the current player to enter a column number.
     * keeps looping until the player enters a valid integer within the allowed range.
     * handles non-integer input gracefully using try-catch.
     * @param input        the shared scanner for reading keyboard input
     * @param player       the number of the current player (1 or 2)
     * @return             a valid column number between 1 and cols (inclusive)
     */
    private static int getPlayerChoice(Scanner input, int player) {
        // loop forever until a valid input is received and returned
        while (true) {
            // prompt the player to enter their column choice
            System.out.print("player " + player + ", choose a column (1-" + cols + "): ");
            try {
                // attempt to read an integer from the keyboard
                int choice = input.nextInt();

                // check that the chosen column is within the valid board range
                if (choice >= 1 && choice <= cols) {
                    // valid choice - return it so the game can continue
                    return choice;
                }
                // the number was outside the allowed range - tell the player
                System.out.println("please enter a number between 1 and " + cols + ".");
            } catch (InputMismatchException e) {
                // the player typed something that is not a number (e.g. a letter)
                System.out.println("invalid input! please enter a number.");
                // clear the bad input from the scanner buffer so the next read works
                input.nextLine();
            }
        }
    }
    /**
     * places a piece into the chosen column by simulating gravity.
     * the piece falls to the lowest available row in that column.
     * @param board    the current state of the game board
     * @param column   the column chosen by the player (1-indexed)
     * @param player   the player number whose piece is being placed
     * @return         true if the piece was placed successfully, false if the column is full
     */
    private static boolean dropPiece(int[][] board, int column, int player) {
        // convert from 1-indexed (user-facing) to 0-indexed (array-facing)
        int col = column - 1;

        //start from the bottom row and move upward to simulate gravity
        for (int row = rows - 1; row >= 0; row--) {
            //check if this cell is currently unoccupied
            if (board[row][col] == empty) {
                //place the player's piece in the lowest empty cell
                board[row][col] = player;
                // signal success - the piece was placed
                return true;
            }
        }
        //if we reach here, every row in this column was occupied - the column is full
        return false;
    }
    /**
     *renders the current board state to the console.
     *displays column numbers at the top for easy reference.
     *uses x for player 1 and o for player 2.  the current state of the game board
     */
    private static void printBoard(int[][] board) {
        //add a blank line before the board for visual spacing
        System.out.println();

        //print column numbers as a header row so players know which column is which
        System.out.print("  ");
        for (int j = 1; j <= cols; j++) {
            //print each column number with consistent spacing to align with the grid
            System.out.print(" " + j + "  ");
        }
        //move to the next line after the header
        System.out.println();

        //loop through each row from top to bottom
        for (int i = 0; i < rows; i++) {
            //start each row with a left border character
            System.out.print("|");
            //loop through each column in this row
            for (int j = 0; j < cols; j++) {
                //if this cell belongs to player 1, print x
                if (board[i][j] == playerOne) System.out.print(" x |");
                //if this cell belongs to player 2, print o
                else if (board[i][j] == playerTwo) System.out.print(" o |");
                //if the cell is empty, print a blank space
                else System.out.print("   |");
            }
            //move to the next line after finishing this row
            System.out.println();

            //print a horizontal separator line between rows
            System.out.println("-----------------------------");
        }
        //add a blank line after the board for visual spacing
        System.out.println();
    }
    /**
     * checks the entire board to determine the current game status.
     * tests all four win directions using a shared helper method.
     * also checks for a draw when the board is completely full.
     *
     * @param board    the current state of the game board
     * @return         win if someone has 4 in a row, draw if board is full, ongoing otherwise
     */
    private static int checkGameStatus(int[][] board) {
        //define all four directions as (rowDelta, colDelta) pairs
        //each pair tells checkLine how to step through consecutive cells
        int[][] directions = {
            {0, 1},   //horizontal: move right along the same row
            {1, 0},   //vertical: move down along the same column
            {1, 1},   //diagonal: move down-right (top-left to bottom-right)
            {1, -1}   //diagonal: move down-left (top-right to bottom-left)
        };

        //check every cell on the board as a potential starting point for a winning line
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //skip empty cells - a winning line must start from a player's piece
                if (board[i][j] == empty) continue;
                //try each of the four directions from this starting cell
                for (int[] dir : directions) {
                    //if 4 consecutive matching pieces are found, someone has won
                    if (checkLine(board, i, j, dir[0], dir[1])) {
                        return win;
                    }
                }
            }
        }
        //if nobody won, check if the board is completely full (draw condition)
        //only need to check the top row - if it's full, every column below is also full
        for (int j = 0; j < cols; j++) {
            //if any cell in the top row is empty, the game is still ongoing
            if (board[0][j] == empty) return ongoing;
        }

        //every cell in the top row was filled and no one won - it's a draw
        return draw;
    }
    /**
     * checks whether there are winLength consecutive matching pieces
     * starting at (row, col) and moving in the direction (dRow, dCol).
     * returns false immediately if a boundary would be crossed.
     * @param board    the current state of the game board
     * @param row      the starting row index
     * @param col      the starting column index
     * @param dRow     how much to move the row index at each step
     * @param dCol     how much to move the column index at each step
     * @return         true if winLength matching pieces are found in this direction
     */
    private static boolean checkLine(int[][] board, int row, int col, int dRow, int dCol) {
        //store the piece value at the starting position to compare against neighbours
        int player = board[row][col];

        //check the next (winLength - 1) cells in the given direction
        for (int k = 1; k < winLength; k++) {
            //calculate the row index of the next cell in this direction
            int newRow = row + k * dRow;
            //calculate the column index of the next cell in this direction
            int newCol = col + k * dCol;

            //if the next cell is outside the board boundaries, no win is possible here
            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                return false;
            }

            //if the next cell does not belong to the same player, the line is broken
            if (board[newRow][newCol] != player) {
                return false;
            }
        }
        //all winLength cells matched the same player - this is a winning line
        return true;
    }
    /**
     *asks the players if they want to start a new game after the current one ends.
     *only accepts yes, y, no or n as valid answers (case-insensitive).
     *the shared scanner for reading keyboard input
     *true if the players want to play again, false if they want to quit
     */
    private static boolean askPlayAgain(Scanner input) {
        //consume the leftover newline character from the previous nextInt() call
        //without this, the next nextLine() would immediately return an empty string
        input.nextLine();

        //keep asking until a valid yes or no response is given
        while (true) {
            //prompt the players to make their decision
            System.out.print("\nplay again? (yes/no): ");

            //read the full line, strip whitespace from both ends, convert to lowercase
            //this makes the check case-insensitive and ignores accidental spaces
            String answer = input.nextLine().trim().toLowerCase();

            //accept both the full word "yes" and the shorthand "y"
            if (answer.equals("yes") || answer.equals("y")) return true;
            //accept both the full word "no" and the shorthand "n"
            if (answer.equals("no") || answer.equals("n")) return false;

            //if neither condition matched, the input was not recognised - ask again
            System.out.println("please enter 'yes' or 'no'.");
        }
    }
}