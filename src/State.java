import java.util.ArrayList;

public class State {

    ArrayList<Action> actions;
    int[][] boardSquares;
    int value;

    public State(int[][] boardSquares) {
        this.boardSquares = boardSquares;
        actions = new ArrayList<>();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public State() {
        actions = new ArrayList<>();
    }

    public void addActionAndItsState(Action action, State state) {
        action.setState(state);
        actions.add(action);
    }

    public int calcUtility(int player) {
        int myPieces = 0;
        int opponentPieces = 0;
        for(int i = 0; i < boardSquares.length; i++) {
            for(int j = 0; j < boardSquares[i].length; j++) {
                if(boardSquares[i][j] == player) {
                    myPieces++;
                } else if(boardSquares[i][j] != player && boardSquares[i][j] != 0) {
                    opponentPieces++;
                }
            }
        }

        return myPieces - opponentPieces;
    }

    public Action getActionWithValue(int value) {
        for(Action action : actions) {
            if(action.getState().getValue() == value) {
                return action;
            }
        }

        return null;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public int[][] getBoardSquares() {
        return boardSquares;
    }

    public void setBoardSquares(int[][] boardSquares) {
        this.boardSquares = boardSquares;
    }



}
