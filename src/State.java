import java.util.ArrayList;

/**
 * The class that represents a State.
 */
public class State {

    ArrayList<Action> actions;
    int[][] boardSquares;
    int value;

    /**
     * Constructor that initializes a state with its board and its action-list.
     * @param boardSquares
     */
    public State(int[][] boardSquares) {
        this.boardSquares = boardSquares;
        actions = new ArrayList<>();
    }

    /**
     * Method that returns the states value.
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * Method that sets the states value.
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }


    /**
     * Method that adds an action and the actions resulting state to this state.
     * @param action
     * @param state
     */
    public void addActionAndItsState(Action action, State state) {
        action.setState(state);
        actions.add(action);
    }

    /**
     * Method that calculates the utility for the current state.
     * @param player
     * @return
     */
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

    /**
     * Method that gets the action with a specific value
     * @param value
     * @return
     */
    public Action getActionWithValue(int value) {
        for(Action action : actions) {
            if(action.getState().getValue() == value) {
                return action;
            }
        }

        return null;
    }

    /**
     * Method that gets all of the actions the state has.
     * @return
     */
    public ArrayList<Action> getActions() {
        return actions;
    }

    /**
     * Method that sets the actions the state has.
     * @param actions
     */
    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    /**
     * Method that gets the states current board-values.
     * @return
     */
    public int[][] getBoardSquares() {
        return boardSquares;
    }

    /**
     * Method that sets the board-values on the state.
     * @param boardSquares
     */
    public void setBoardSquares(int[][] boardSquares) {
        this.boardSquares = boardSquares;
    }



}
