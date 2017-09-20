public class Main {

    public static void main(String[] args) {
        Board board = new Board();

        System.out.println("Welcome to this Othello game. 'O' represents white pieces and 'X' represents black pieces. \n" +
                "You as a player will play as the black pieces ('X') and the computer you're facing will play as white pieces ('O') \n \n" +
                "In order to make a move enter the tile you would like to place your piece on, example: \n\n" +
                "I want to place the piece in the upper left corner, I write: 0,0 in the console window. The square right of that is 0,1 etc." );
        board.printBoard();

        board.makeMove();

        board.printBoard();

    }
}