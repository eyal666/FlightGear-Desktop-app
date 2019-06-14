package viewModel;


import javafx.beans.property.*;
import model.MyModel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class ViewModel extends Observable implements Observer {

    MyModel m;
    public DoubleProperty joyStickX, joyStickY, throttle, rudder; //value of the joyStick position
    public StringProperty path;

    public ViewModel(MyModel m) {
        this.m = m;
        joyStickY=new SimpleDoubleProperty();
        joyStickX=new SimpleDoubleProperty();
        throttle=new SimpleDoubleProperty();
        rudder=new SimpleDoubleProperty();
        path=new SimpleStringProperty();
    }
    public void runScriptVm(String script){
        m.runScript(script.split("\n"));
    }
    public void controlElevatorAileronVm(){
        double elevatorVal;
        double aileronVal;
        elevatorVal=-joyStickY.doubleValue()/70;//Double.min(0-(joyStickY.doubleValue()/70), 1);
        aileronVal=joyStickX.doubleValue()/70;//Double.min(joyStickX.doubleValue()/70, 1);
        System.out.println(elevatorVal+" , "+aileronVal);
        m.controlElevatorAileron(elevatorVal, aileronVal);
    }
    public void controlRudderVm(){
        m.controlRudder(rudder.doubleValue());
    }
    public void controlThrottleVm(){
        m.controlThrottle(throttle.doubleValue());
    }
    public void connectToSimVM(String ip, String port){
        m.connectToSim(ip, port);
    }
    public void connectToCalcServerVm(String ip, String port, double [][] matrix, Point init, Point goal){
        String[][] matrixAsString=new String[matrix.length][matrix[0].length];
        String initPointAsString=init.x+","+init.x;
        String goalPointAsString=goal.x+","+goal.y;
        for(int i=0;i<matrix.length;i++)
            for(int j=0;j<matrix[i].length;j++)
                matrixAsString[i][j]=String.valueOf(matrix[i][j]);
        m.connectToCalcServer(ip, port,matrixAsString,initPointAsString, goalPointAsString);
    }
    public void getPathFromCalcServerVm( Point init, Point goal){
        String initPointAsString=init.x+","+init.y;
        String goalPointAsString=goal.x+","+goal.y;
        m.getPathFromCalcServer(initPointAsString,goalPointAsString);
    }
    @Override
    public void update(Observable o, Object arg) {
        path.setValue(m.getPath());
        notifyObservers();
    }
}
