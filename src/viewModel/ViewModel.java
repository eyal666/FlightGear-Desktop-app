package viewModel;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.MyModel;

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
    public void getPathFromCalcServerVm(){
        path.setValue(m.getPath());

    }
    public void connectToCalcServerVm(String ip, String port){
        Random r=new Random() ;
        String[][] matrix=new String[4][4];
        for(int i=0;i<matrix.length;i++)
            for(int j=0;j<matrix[i].length;j++)
                matrix[i][j]=String.valueOf(100+r.nextInt(101));
        m.connectToCalcServer(ip, port,matrix,"0,0", "3,3");
        getPathFromCalcServerVm();
    }

    @Override
    public void update(Observable o, Object arg) {
//        if(o.equals(m)){
//            getPathFromCalcServerVm();
//        }
    }
}
