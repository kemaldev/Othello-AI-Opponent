import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The board of the game and its rules.
 */

public class Board {

    private int[][] boardSquares;
    private int[][] boardSquarePositions;
    private int depth;
    private int nodes;
    private HashMap<int[][], State> states;
    private StringBuilder boardView;
    private Scanner scanner;

    /**
     * Constructor which initializes all the needed variables, positions and pieces on the board.
     */
    public Board() {

        scanner = new Scanner(System.in);
        states = new HashMap<>();

        // Setting up the initial state of the board.
        // 1 = 'X', 2 = 'O' , 0 = Empty square
        boardSquares = new int[4][4];

        boardSquares[1][1] = 2;
        boardSquares[2][2] = 2;
        boardSquares[1][2] = 1;
        boardSquares[2][1] = 1;

        //Setting up all of the board-squares positions.
        boardSquarePositions = new int[4][4];
        boardSquarePositions[0][0] = 97;
        boardSquarePositions[0][1] = 108;
        boardSquarePositions[0][2] = 119;
        boardSquarePositions[0][3] = 130;
        boardSquarePositions[1][0] = 281;
        boardSquarePositions[1][1] = 292;
        boardSquarePositions[1][2] = 303;
        boardSquarePositions[1][3] = 314;
        boardSquarePositions[2][0] = 465;
        boardSquarePositions[2][1] = 476;
        boardSquarePositions[2][2] = 487;
        boardSquarePositions[2][3] = 498;
        boardSquarePositions[3][0] = 649;
        boardSquarePositions[3][1] = 660;
        boardSquarePositions[3][2] = 671;
        boardSquarePositions[3][3] = 682;

        boardView = new StringBuilder();
        boardView.append(
                "---------------------------------------------\n" +
                        "|          |          |          |          |\n" +
                        "|          |          |          |          |\n" +
                        "|          |          |          |          |\n" +
                        "---------------------------------------------\n" +
                        "|          |          |          |          |\n" +
                        "|          |          |          |          |\n" +
                        "|          |          |          |          |\n" +
                        "---------------------------------------------\n" +
                        "|          |          |          |          |\n" +
                        "|          |          |          |          |\n" +
                        "|          |          |          |          |\n" +
                        "---------------------------------------------\n" +
                        "|          |          |          |          |\n" +
                        "|          |          |          |          |\n" +
                        "|          |          |          |          |\n" +
                        "---------------------------------------------");

    }

    /**
     * Method that prints out the current state of the board.
     */
    public void printBoard() {
        for (int i = 0; i < boardSquares.length; i++) {
            for (int j = 0; j < boardSquares[i].length; j++) {
                if (boardSquares[i][j] == 1) {
                    boardView.setCharAt(boardSquarePositions[i][j], 'X');
                } else if (boardSquares[i][j] == 2) {
                    boardView.setCharAt(boardSquarePositions[i][j], 'O');
                }
            }
        }
        System.out.println(boardView);
    }

    /**
     * Prints out the nodes and depth of the last search made.
     */
    public void printDepthAndNodes() {
        System.out.println("Depth of search: " + depth + "\nNodes examined: " + nodes);
    }

    /**
     * Method that makes a move for the player.
     */
    public void makeMove() {
        System.out.println("Make a move: ");
        String rowAndColumnText = scanner.nextLine();
        String[] rowAndColumn = rowAndColumnText.split(",");
        try {
            int row = Integer.parseInt(rowAndColumn[0]);
            int column = Integer.parseInt(rowAndColumn[1]);

            if(row >= boardSquares.length || column >= boardSquares.length) {
                System.out.println("You've selected a square that doesn't exist, try again!");
                makeMove();
            } else if (!validMove(row, column, 1, boardSquares)) {
                System.out.println("Sorry but that move is not allowed, try again!");
                makeMove();
            } else {
                boardSquares[row][column] = 1;
            }
        } catch(NumberFormatException ex) {
            System.out.println("Error: wrong input. Use the following format: 0,0");
            makeMove();
        }
    }

    /**
     * Method that makes a move for the AI.
     */
    public void aiMove() {
        Action action = alphaBetaSearch(new State(boardSquares));
        if(validMove(action.getRow(), action.getCol(), 2, boardSquares)) {
            boardSquares[action.getRow()][action.getCol()] = 2;
        }
    }

    /**
     * Method that flips the pieces on the board if it should.
     * @param rowsAndCols The rows and columns.
     * @param player The player that's currently going to make a move.
     * @param boardToFlip The board we're going to flip pieces on.
     */
    public void flipCheckers(ArrayList<int[]> rowsAndCols, int player, int[][] boardToFlip) {
        for(int[] position : rowsAndCols) {
            boardToFlip[position[0]][position[1]] = player;
        }
    }

    /**
     * Method that gets the available moves from the current state.
     * @param boardState The current state we're going to calculate the available moves from.
     * @param player The player that's supposed to make a move.
     * @return
     */
    public State getAvailableMoves(int[][] boardState, int player) {
        State state = new State(boardState);
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j] == 0) {
                    int[][] newState = new int[boardState.length][];
                    for (int k = 0; k < boardState.length; k++) {
                        newState[k] = Arrays.copyOf(boardState[k], boardState[k].length);
                    }
                    if (validMove(i, j, player, newState)) {
                        newState[i][j] = player;
                        state.addActionAndItsState(new Action(i, j), new State(newState));

                    }
                }
            }
        }
        return state;
    }

    /**
     * Method that decides whether the game is over or not.
     * @param player The current player that's supposed to make a move.
     * @return
     */
    public boolean gameOver(int player) {
        State state = getAvailableMoves(boardSquares, player);
        return terminalTest(state);
    }

    /**
     * If a player has won the game return a string with the player that won and his score.
     * @return
     */
    public String playerWon() {
        int player = 0;
        int computer = 0;
        for(int i = 0; i < boardSquares.length; i++) {
            for(int j = 0; j < boardSquares[i].length; j++) {
                if(boardSquares[i][j] == 1) {
                    player++;
                } else if(boardSquares[i][j] != player && boardSquares[i][j] != 0) {
                    computer++;
                }
            }
        }

        if(player > computer) {
            return "Congratulations, you won with the score " + player + " - " + computer + "!";
        }

        return "Sorry, but the computer beat you this time with the score " + computer + " - " + player + "!";
    }

    /**
     * Tests whether we're in a terminal node or not.
     * @param state The current state we're testing.
     * @return
     */
    public boolean terminalTest(State state) {
        if(state.getActions() != null){
            if(state.getActions().size() == 0) {
                return true;
            }
        }

        for(int i = 0; i < state.boardSquares.length; i++) {
            for(int j = 0; j < state.boardSquares[i].length; j++) {
                if(state.boardSquares[i][j] == 0) {
                    return false; //As long as there's spots free and there are actions left it's not a terminal node.
                }
            }
        }

        return true;
    }

    /**
     * The alpha-beta pruning search algorithm.
     * @param state From where we should start searching in the game tree.
     * @return Returns the best choice the AI can make from the calculations.
     */
    public Action alphaBetaSearch(State state) {
        depth = 0;
        nodes = 0;
        state = getAvailableMoves(state.getBoardSquares(), 2);
        int v = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
        state = states.get(state.getBoardSquares());
        states.clear();
        return state.getActionWithValue(v); //Get the action with the value v and return it.
    }

    /**
     * Method that is part of the alpha-beta pruning search algorithm, recursive together with the minValue().
     * @param state The current state
     * @param a The alpha value
     * @param b The beta value
     * @return
     */
    public int maxValue(State state, int a, int b) {
        //If we're at a leaf-node, a.k.a. winning/losing state.
        if(terminalTest(state) || depth > 30) {
            int utility = state.calcUtility(2);
            state.setValue(utility);
            states.put(state.getBoardSquares(), state);
            return utility;
        }

        int v = Integer.MIN_VALUE;

        state = getAvailableMoves(state.getBoardSquares(), 2);
        states.put(state.getBoardSquares(), state);
        ArrayList<Action> actions = state.getActions();
        depth++;

        for(Action action : actions) {
            nodes++;

            //Min function has to be able to see if the actions result has any actions.
            action.setState(getAvailableMoves(action.getState().getBoardSquares(), 2));
            v = Math.max(v, minValue(action.getState(), a, b));
            if(v >= b) {
                action.getState().setValue(v);
                return v;
            }
            a = Math.max(a, v);
        }

        state.setValue(v);
        states.put(state.getBoardSquares(), state);
        return v;
    }

    /**
     * Method that is part of the alpha-beta pruning search algorithm, recursive together with the maxValue().
     * @param state The current state
     * @param a The alpha value
     * @param b The beta value
     * @return
     */
    public int minValue(State state, int a, int b) {
        if(terminalTest(state) || depth > 30) {
            int utility = state.calcUtility(2);
            state.setValue(utility);
            states.put(state.getBoardSquares(), state);
            return utility;
        }
        int v = Integer.MAX_VALUE;

        state = getAvailableMoves(state.getBoardSquares(), 1);
        states.put(state.getBoardSquares(), state); // Add the state with its actions to the map.
        ArrayList<Action> actions = state.getActions();
        depth++;

        for(Action action : actions) {
            nodes++;

            action.setState(getAvailableMoves(action.getState().getBoardSquares(), 1));

            v = Math.min(v, maxValue(action.getState(), a, b));
            if(v <= a) {
                action.getState().setValue(v);
                states.put(action.getState().getBoardSquares(), action.getState());
                return v;
            }
            b = Math.min(b, v);
        }

        state.setValue(v);
        states.put(state.getBoardSquares(), state);
        return v;
    }

    /**
     * A method that returns whether a move is valid or not and flips the pieces that should be flipped if it's true.
     * @param row The row we're checking.
     * @param col The column we're checking.
     * @param player The current player that's supposed to make a move.
     * @param board The board we're looking at.
     * @return Returns whether the move is valid or not.
     */
    public boolean validMove(int row, int col, int player, int[][] board) {
        //If the spot has already been occupied.
        if (boardSquares[row][col] != 0) {
            return false;
        }

        ArrayList<int[]> piecesToFlip = new ArrayList<>();
        boolean adjacentFound;
        boolean validMove = false;
        int currRow;
        int currCol;

        //For each direction search if there's opponent-pieces in between the move and earlier player moves.
        for (int i = 0; i < 8; i++) {
            adjacentFound = false;
            currRow = row;
            currCol = col;
            direction:
            for (int j = 0; j < board.length - 1; j++) {
                //0 = north-west
                //1 = north
                //2 = north-east
                //3 = east
                //4 = south-east
                //5 = south
                //6 = south-west
                //7 = west
                switch (i) {
                    case 0:
                        currRow--;
                        currCol--;

                        //If we reach the edge we want to break out of the loop.
                        if (currRow < 0 || currCol < 0) {
                            break direction;
                        } else {
                            //If the next square was an oppnents piece
                            if (adjacentFound) {
                                //If we've found a square with our own piece
                                if (board[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player, board);
                                    validMove = true;
                                } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                                //If the next square is an opponents piece
                            } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                piecesToFlip.clear();
                                adjacentFound = true;
                                int[] position = new int[2];
                                position[0] = currRow;
                                position[1] = currCol;
                                piecesToFlip.add(position);
                                //If the next square is our own piece or an empty space we know we can skip to check this direction.
                            } else {
                                break direction;
                            }
                        }
                        break;
                    case 1:
                        currRow--;
                        if (currRow < 0) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (board[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player, board);
                                    validMove = true;
                                } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                piecesToFlip.clear();
                                adjacentFound = true;
                                int[] position = new int[2];
                                position[0] = currRow;
                                position[1] = currCol;
                                piecesToFlip.add(position);
                            } else {
                                break direction;
                            }
                        }
                        break;
                    case 2:
                        currRow--;
                        currCol++;
                        if (currRow < 0 || currCol > board.length - 1) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (board[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player, board);
                                    validMove = true;
                                } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                piecesToFlip.clear();
                                adjacentFound = true;
                                int[] position = new int[2];
                                position[0] = currRow;
                                position[1] = currCol;
                                piecesToFlip.add(position);
                            } else {
                                break direction;
                            }
                        }
                        break;
                    case 3:
                        currCol++;
                        if (currCol > board.length - 1) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (board[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player, board);
                                    validMove = true;
                                } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if (board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                piecesToFlip.clear();
                                adjacentFound = true;
                                int[] position = new int[2];
                                position[0] = currRow;
                                position[1] = currCol;
                                piecesToFlip.add(position);
                            } else {
                                break direction;
                            }
                        }
                        break;
                    case 4:
                        currRow++;
                        currCol++;
                        if (currRow > board.length - 1 || currCol > board.length - 1) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (board[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player, board);
                                    validMove = true;
                                } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                piecesToFlip.clear();
                                adjacentFound = true;
                                int[] position = new int[2];
                                position[0] = currRow;
                                position[1] = currCol;
                                piecesToFlip.add(position);
                            } else {
                                break direction;
                            }
                        }
                        break;
                    case 5:
                        currRow++;
                        if (currRow > board.length - 1) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (board[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player, board);
                                    validMove = true;
                                } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                piecesToFlip.clear();
                                adjacentFound = true;
                                int[] position = new int[2];
                                position[0] = currRow;
                                position[1] = currCol;
                                piecesToFlip.add(position);
                            } else {
                                break direction;
                            }
                        }
                        break;
                    case 6:
                        currRow++;
                        currCol--;
                        if (currRow > board.length - 1 || currCol < 0) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (board[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player, board);
                                    validMove = true;
                                } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                piecesToFlip.clear();
                                adjacentFound = true;
                                int[] position = new int[2];
                                position[0] = currRow;
                                position[1] = currCol;
                                piecesToFlip.add(position);
                            } else {
                                break direction;
                            }
                        }
                        break;
                    case 7:
                        currCol--;
                        if (currCol < 0) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (board[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player, board);
                                    validMove = true;
                                } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if(board[currRow][currCol] != 0 && board[currRow][currCol] != player) {
                                piecesToFlip.clear();
                                adjacentFound = true;
                                int[] position = new int[2];
                                position[0] = currRow;
                                position[1] = currCol;
                                piecesToFlip.add(position);
                            } else {
                                break direction;
                            }
                        }
                        break;
                    default:
                        System.out.println("[ERR]: Unknown direction chosen in the validation of the move.");
                }
            }
        }
        return validMove;
    }

}
