/**
 * The main class that runs the actual game.
 */
public class Main {

    public static void main(String[] args) {
        Board board = new Board();

        System.out.println("Welcome to this Othello game. 'O' represents white pieces and 'X' represents black pieces. \n" +
                "You as a player will play as the black pieces ('X') and the computer you're facing will play as white pieces ('O') \n \n" +
                "In order to make a move enter the tile you would like to place your piece on, example: \n\n" +
                "I want to place the piece in the upper left corner, I write: 0,0 in the console window. The square right of that is 0,1 etc." );
        board.printBoard();

        int player = 1;
        while(true) {
            if(board.gameOver(player))
                break;
            board.makeMove();
            board.printBoard();

            player = 2;
            if(board.gameOver(player))
                break;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The computer choses the following move:");
            board.aiMove();
            board.printBoard();

            player = 1;
        }

        System.out.println("Game over!" + "\n");
        System.out.println(board.playerWon());

    }
}