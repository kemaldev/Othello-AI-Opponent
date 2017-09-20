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

        boardSquares[1][1] = 2;
        boardSquares[1][2] = 1;
        boardSquares[2][1] = 1;
        boardSquares[2][2] = 2;

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
        for(int i = 0; i < boardSquares.length; i++) {
            for(int j = 0; j < boardSquares[i].length; j++) {
                if(boardSquares[i][j] == 1) {
                    boardView.setCharAt(boardSquarePositions[i][j], 'X');
                } else if(boardSquares[i][j] == 2) {
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
        if(!validMove(row, column, 1)) {
            System.out.println("Sorry but that move is not allowed, try again!");
            makeMove();
        } else {
            boardSquares[row][column] = 1;
        }

        flipCheckers(row, column);
    }

    public void flipCheckers(int row, int col) {

    }

    public boolean validMove(int row, int col, int player) {
        //If the spot has already been occupied.
        if(boardSquares[row][col] != 0) {
            return false;
        }

        //If we're checking player X's move.
        if(player == 1){
            boolean adjacentFound;
            int currRow;
            int currCol;

            //For each direction search if there's opponent-pieces in between the move and earlier player moves.
            for(int i = 0; i < 8; i++) {
                adjacentFound = false;
                currRow = row;
                currCol = col;
                direction: for(int j = 0; j < boardSquares.length - 1; j++) {
                    //0 = north-west
                    //1 = north
                    //2 = north-east
                    //3 = east
                    //4 = south-east
                    //5 = south
                    //6 = south-west
                    //7 = west
                    switch(i) {
                        case 0:
                            currRow--;
                            currCol--;
                            if(currRow < 0 || currCol < 0) {
                                break direction;
                            } else {
                                if(adjacentFound) {
                                    if(boardSquares[currRow][currCol] == 1) {
                                        return true;
                                    }
                                } else if(boardSquares[currRow][currCol] == 2) {
                                    adjacentFound = true;
                                } else {
                                    break direction;
                                }
                            }
                            break;
                        case 1:
                            currRow--;
                            if(currRow < 0) {
                                break direction;
                            } else {
                                if(adjacentFound) {
                                    if(boardSquares[currRow][currCol] == 1) {
                                        return true;
                                    }
                                } else if(boardSquares[currRow][currCol] == 2) {
                                    adjacentFound = true;
                                } else {
                                    break direction;
                                }
                            }
                            break;
                        case 2:
                            currRow--;
                            currCol++;
                            if(currRow < 0 || currCol > boardSquares.length - 1) {
                                break direction;
                            } else {
                                if(adjacentFound) {
                                    if(boardSquares[currRow][currCol] == 1) {
                                        return true;
                                    }
                                } else if(boardSquares[currRow][currCol] == 2) {
                                    adjacentFound = true;
                                } else {
                                    break direction;
                                }
                            }
                            break;
                        case 3:
                            currCol++;
                            if(currCol > boardSquares.length - 1) {
                                break direction;
                            } else {
                                if(adjacentFound) {
                                    if(boardSquares[currRow][currCol] == 1) {
                                        return true;
                                    }
                                } else if(boardSquares[currRow][currCol] == 2) {
                                    adjacentFound = true;
                                } else {
                                    break direction;
                                }
                            }
                            break;
                        case 4:
                            currRow++;
                            currCol++;
                            if(currRow > boardSquares.length - 1 || currCol> boardSquares.length - 1) {
                                break direction;
                            } else {
                                if(adjacentFound) {
                                    if(boardSquares[currRow][currCol] == 1) {
                                        return true;
                                    }
                                } else if(boardSquares[currRow][currCol] == 2) {
                                    adjacentFound = true;
                                } else {
                                    break direction;
                                }
                            }
                            break;
                        case 5:
                            currRow++;
                            if(currRow > boardSquares.length - 1) {
                                break direction;
                            } else {
                                if(adjacentFound) {
                                    if(boardSquares[currRow][currCol] == 1) {
                                        return true;
                                    }
                                } else if(boardSquares[currRow][currCol] == 2) {
                                    adjacentFound = true;
                                } else {
                                    break direction;
                                }
                            }
                            break;
                        case 6:
                            currRow++;
                            currCol--;
                            if(currRow > boardSquares.length - 1 || currCol < 0) {
                                break direction;
                            } else {
                                if(adjacentFound) {
                                    if(boardSquares[currRow][currCol] == 1) {
                                        return true;
                                    }
                                } else if(boardSquares[currRow][currCol] == 2) {
                                    adjacentFound = true;
                                } else {
                                    break direction;
                                }
                            }
                            break;
                        case 7:
                            currCol--;
                            if(currCol < 0) {
                                break direction;
                            } else {
                                if(adjacentFound) {
                                    if(boardSquares[currRow][currCol] == 1) {
                                        return true;
                                    }
                                } else if(boardSquares[currRow][currCol] == 2) {
                                    adjacentFound = true;
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

        } else {
            //O

        }
        return false;
    }

}
