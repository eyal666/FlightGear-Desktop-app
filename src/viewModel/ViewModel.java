package viewModel;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.MyModel;
import model.test.MyInterpreter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
//        try {
//            BufferedReader inFromScript=new BufferedReader(new FileReader(script));
//            String line;
//            List<String> lines=new ArrayList<>();
//            while((line=inFromScript.readLine())!=null){
//                lines.add(line);
//            }
//            inFromScript.close();
//            m.runScript(lines.toArray(new String[0]));
//        } catch (FileNotFoundException e) {} catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public void controlElevatorAileronVm(){
        double elevatorVal=Double.min(0-(joyStickY.doubleValue()/100), 1);
        double aileronVal=Double.min(joyStickX.doubleValue()/100, 1);
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
    @Override
    public void update(Observable o, Object arg) {

    }
}
