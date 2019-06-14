package model.server_side;

import java.awt.*;
import java.util.ArrayList;

public class SearchableMatrix implements Searchable<Point> {

    MyMatrix matrix;

    public SearchableMatrix(MyMatrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public State getInitialState() {
        return matrix.initialState;
    }

    @Override
    public State getGoalState() {
        return matrix.goalState;
    }

    @Override
    public ArrayList<State<Point>> getAllPossibleStates(State<Point> s) {
        ArrayList<State<Point>> successors=new ArrayList<>();
        addIfInbound((int)s.getState().getX()+1, (int)s.getState().getY(), s, successors);
        addIfInbound((int)s.getState().getX()-1, (int)s.getState().getY(), s, successors);
        addIfInbound((int)s.getState().getX(), (int)s.getState().getY()+1, s, successors);
        addIfInbound((int)s.getState().getX(), (int)s.getState().getY()-1, s, successors);
        return successors;
    }
    private void addIfInbound(int row, int column,State<Point> s, ArrayList<State<Point>> successors){
        if(row>=0 && row<matrix.rows && column>=0 && column<matrix.columns) {
            State<Point> state=new State<>(new Point(row, column));
            state.setCameFrom(s);
            state.setCost(s.getCost()+matrix.getMatrix()[row][column]);
            successors.add(state);
        }

    }
    @Override
    public String backTrace(State goal){
        State<Point> current=goal;
        State<Point> camefrom;
        String directions="";
        while(!current.equals(this.getInitialState())){
            camefrom=current.getCameFrom();
            if(current.getState().getY()==camefrom.getState().getY()+1)
                directions="Right,"+directions;
              //  trace.addFirst("Down,");
            if(current.getState().getY()==camefrom.getState().getY()-1)
                directions="Left,"+directions;
               // trace.addFirst("Up,");
            if(current.getState().getX()==camefrom.getState().getX()+1)
                directions="Down,"+directions;
               // trace.addFirst("Right,");
            if(current.getState().getX()==camefrom.getState().getX()-1)
                directions="Up,"+directions;

            current=current.getCameFrom();
        }
       return directions.substring(0, directions.length()-1);

    }
    @Override
    public String toString(){
        return matrix.matrix.length+" , "+matrix.getInitialState().getState().x+matrix.getInitialState().getState().y
                +matrix.getGoalState().getState().x+matrix.getGoalState().getState().y;
    }
}
