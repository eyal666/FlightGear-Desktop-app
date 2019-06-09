package model.server_side;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class BestFirstSearch<Solution> extends CommonSearcher {

    @Override
    public Solution search(Searchable s) {
        openList.add(s.getInitialState());
        HashSet<State> closedSet=new HashSet<>();

        while(openList.size()>0){
            State n= (State) openList.poll();
            closedSet.add(n);

            if(n.equals(s.getGoalState()))
                return (Solution)s.backTrace(n);

            ArrayList<State> succesors=s.getAllPossibleStates(n);
            for(State state : succesors){
                if(!closedSet.contains(state)&& !openList.contains(state)){
                    state.setCameFrom(n);
                    openList.add(state);
                }
                else if(openList.contains(state)){
                    updateTobetterPath(state);
                }
            }
        }
        return null;
    }
    private void updateTobetterPath(State s) {
        LinkedList<State> q = new LinkedList<>();
        State temp;
        while (!openList.isEmpty()) {
            temp = (State)openList.poll();
            if(temp.equals(s)&& s.getCost()<temp.getCost()){
                temp.setCost(s.getCost());
                temp.setCameFrom(s.getCameFrom());
            }
            q.add(temp);
        }
        openList.addAll(q);
    }

    public static void main(String[] args){

        double[][] mat={{123,187,175,190},
                {57,23,142,169},
                {154,81,109,127},
                {138,50,60,19}};
        Searchable<Point> matrix=new SearchableMatrix(new MyMatrix(mat, new State<>(new Point(0,0)), new State<>(new Point(3,3)),4,4));
        BestFirstSearch<String> bfs=new BestFirstSearch<>();
        String trace=bfs.search(matrix);
        System.out.println(trace);

    }
}
