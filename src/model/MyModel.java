package model;

import model.interpeter.Interpreter;
import model.interpeter.commands.Command;
import model.interpeter.commands.ConnectCommand;
import model.interpeter.commands.OpenDataServerCommand;
import model.interpeter.commands.assests.DataWriterClient;
import model.server_side.MySerialServer;
import model.server_side.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class MyModel extends Observable {

    Server calcServer; //server to calc best path
    Interpreter aircraftControl; //incharge of controlling the aircraft by joystick/script

    public MyModel() {
        calcServer=new MySerialServer();
        aircraftControl=new Interpreter("./scripts/simulators_vars.txt");
        Command openServer=new OpenDataServerCommand();
        openServer.execute(new ArrayList<>(Arrays.asList("openDataServer", "5400", "10")),0);
    }
    public void runScript(String[] lines){
        aircraftControl.lexer(lines);
        aircraftControl.parser();
    }

    public void controlElevatorAileron(double elevator, double aileron){
        DataWriterClient.out.println("set /controls/flight/elevator "+elevator);
        DataWriterClient.out.println("set /controls/flight/aileron "+aileron);
        DataWriterClient.out.flush();
    }
    public void controlRudder(double rudder) {
        DataWriterClient.out.println("set /controls/flight/rudder "+rudder);
        DataWriterClient.out.flush();
    }
    public void controlThrottle(double throttle){
        DataWriterClient.out.println("set /controls/engines/current-engine/throttle "+throttle);
        DataWriterClient.out.flush();
    }
    public void connectToSim(String ip, String port){
        int c=new ConnectCommand().execute(new ArrayList<String>(Arrays.asList("connect", ip, port)),0);
    }
}
