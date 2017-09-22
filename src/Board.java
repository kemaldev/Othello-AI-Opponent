import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    private int[][] boardSquares;
    private int[][] boardSquarePositions;
    private StringBuilder boardView;
    private Scanner scanner;

    public Board() {

        scanner = new Scanner(System.in);

        // Setting up the initial state of the board.
        // 1 = 'X', 2 = 'O' , 0 = Empty square
        boardSquares = new int[4][4];


        boardSquares[0][3] = 1;
        boardSquares[3][2] = 1;
        boardSquares[1][2] = 2;
        boardSquares[2][1] = 2;
        boardSquares[3][1] = 2;




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

    public void makeMove() {
        System.out.println("Make a move: ");
        String rowAndColumnText = scanner.nextLine();
        String[] rowAndColumn = rowAndColumnText.split(",");
        int row = Integer.parseInt(rowAndColumn[0]);
        int column = Integer.parseInt(rowAndColumn[1]);
        if (!validMove(row, column, 1)) {
            System.out.println("Sorry but that move is not allowed, try again!");
            makeMove();
        } else {
            boardSquares[row][column] = 1;
        }
    }

    public void flipCheckers(ArrayList<int[]> rowsAndCols, int player) {
        for(int[] position : rowsAndCols) {
            boardSquares[position[0]][position[1]] = player;
        }
    }

    public boolean validMove(int row, int col, int player) {
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
            for (int j = 0; j < boardSquares.length - 1; j++) {
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
                                if (boardSquares[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player);
                                    validMove = true;
                                } else if(boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                                //If the next square is an opponents piece
                            } else if (boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
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
                                if (boardSquares[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player);
                                    validMove = true;
                                } else if(boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if (boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
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
                        if (currRow < 0 || currCol > boardSquares.length - 1) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (boardSquares[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player);
                                    validMove = true;
                                } else if(boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if (boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
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
                        if (currCol > boardSquares.length - 1) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (boardSquares[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player);
                                    validMove = true;
                                } else if(boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if (boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
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
                        if (currRow > boardSquares.length - 1 || currCol > boardSquares.length - 1) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (boardSquares[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player);
                                    validMove = true;
                                } else if(boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if (boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
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
                        if (currRow > boardSquares.length - 1) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (boardSquares[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player);
                                    validMove = true;
                                } else if(boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if (boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
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
                        if (currRow > boardSquares.length - 1 || currCol < 0) {
                            break direction;
                        } else {
                            if (adjacentFound) {
                                if (boardSquares[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player);
                                    validMove = true;
                                } else if(boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if (boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
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
                                if (boardSquares[currRow][currCol] == player) {
                                    flipCheckers(piecesToFlip, player);
                                    validMove = true;
                                } else if(boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
                                    int[] position = new int[2];
                                    position[0] = currRow;
                                    position[1] = currCol;
                                    piecesToFlip.add(position);
                                }
                            } else if (boardSquares[currRow][currCol] != 0 || boardSquares[currRow][currCol] != player) {
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
