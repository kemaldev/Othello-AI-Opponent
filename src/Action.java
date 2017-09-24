/**
 * The class that represents an action of a state.
 */
public class Action {

    private int row;
    private int col;
    private State state;


    /**
     * Constructor that initializes the row and the column for the action.
     * @param row
     * @param col
     */
    public Action(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the result of the action.
     * @return
     */
    public State getState() {
        return state;
    }

    /**
     * Sets the result of the action
     * @param state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Method that gets the column from the action.
     * @return
     */
    public int getCol() {
        return col;
    }

    /**
     * Method that sets the column for the action
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Method that gets the row from the action.
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     * Method that sets the row for the action.
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }
}
