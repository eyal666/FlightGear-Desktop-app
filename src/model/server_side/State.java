package model.server_side;

public class State<T> {
    private T state;
    private double cost;
    private State<T> cameFrom;

    public State(T state){
        this.state=state;
    }

    @Override
    //hashes state by converting to string
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public boolean equals(Object s) {
       return this.state.equals(((State<T>)s).getState());
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public State<T> getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(State<T> cameFrom) {
        this.cameFrom = cameFrom;
    }
}
