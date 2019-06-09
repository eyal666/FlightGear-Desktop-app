package model.server_side;

import java.awt.*;

public class MyMatrix {

    public double [][] matrix;
    public State<Point> initialState, goalState;
    public int rows, columns;

    public MyMatrix(double[][] matrix, State<Point> initialState, State<Point> goalState, int rows, int columns) {
        this.matrix = matrix;
        this.initialState = initialState;
        this.goalState = goalState;
        this.rows = rows;
        this.columns = columns;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public State<Point> getInitialState() {
        return initialState;
    }

    public void setInitialState(State<Point> initialState) {
        this.initialState = initialState;
    }

    public State<Point> getGoalState() {
        return goalState;
    }

    public void setGoalState(State<Point> goalState) {
        this.goalState = goalState;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
