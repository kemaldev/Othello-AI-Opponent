public class Action {

    private int row;
    private int col;
    private State state;


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Action(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
