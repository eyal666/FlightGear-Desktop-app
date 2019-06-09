package viewModel;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.MyModel;

import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {

    MyModel m;
    public DoubleProperty joyStickX, joyStickY, throttle, rudder; //value of the joyStick position

    public ViewModel(MyModel m) {
        this.m = m;
        joyStickY=new SimpleDoubleProperty();
        joyStickX=new SimpleDoubleProperty();
        throttle=new SimpleDoubleProperty();
        rudder=new SimpleDoubleProperty();
    }

    public void runScriptVm(String script){
        m.runScript(script.split("\n"));
    }
    public void controlElevatorAileronVm(){
        double elevatorVal;
        double aileronVal;
        elevatorVal=Double.min(0-(joyStickY.doubleValue()/60), 1);
        aileronVal=Double.min(joyStickX.doubleValue()/60, 1);
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
    @Override
    public void update(Observable o, Object arg) {

    }
}
