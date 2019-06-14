package model;

import model.interpeter.Interpreter;
import model.interpeter.commands.Command;
import model.interpeter.commands.ConnectCommand;
import model.interpeter.commands.OpenDataServerCommand;
import model.interpeter.commands.assests.DataWriterClient;
import model.server_side.MyClientHandler;
import model.server_side.MySerialServer;
import model.server_side.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class MyModel extends Observable {

    Server calcServer; //server to calc best path
    Interpreter aircraftControl; //incharge of controlling the aircraft by joystick/script
    int calcServerPort;
    PrintWriter outTocalcServer;
    BufferedReader inFromCalcServer;
    String [][] matrix;
    String path;
    String ipForCalcServer;
    int portForCalcServer;


    public MyModel(int calcServerPort) {
        this.calcServerPort = calcServerPort;
        calcServer = new MySerialServer();
        aircraftControl = new Interpreter("./scripts/simulators_vars.txt");
        Command openServer = new OpenDataServerCommand();
        openServer.execute(new ArrayList<>(Arrays.asList("openDataServer", "5400", "10")), 0);
        calcServer.start(calcServerPort, new MyClientHandler());
        getAircraftPosition();
    }

    public void runScript(String[] lines){
        new Thread(()->{
            aircraftControl.lexer(lines);
            aircraftControl.parser();
        }).start();
    }
    public void controlElevatorAileron(double elevator, double aileron){
        System.out.println(elevator+" , "+aileron);
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
        new ConnectCommand().execute(new ArrayList<String>(Arrays.asList("connect", ip, port)),0);
    }
    public String connectToCalcServer(String ip, String port, String [][] matrix, String init, String goal){
        ipForCalcServer=ip;
        portForCalcServer=Integer.parseInt(port);
        this.matrix=matrix;
        return getPathFromCalcServer(init, goal);
    }
    public String getPathFromCalcServer (String init, String goal){
        try {
            Socket theServer=new Socket(ipForCalcServer, portForCalcServer);
            System.out.println("connected to calc server");
            outTocalcServer=new PrintWriter(theServer.getOutputStream());
            inFromCalcServer=new BufferedReader(new InputStreamReader(theServer.getInputStream()));
        } catch (IOException e) {}
        int i, j;
        System.out.println("sending problem...");
        for(i=0;i<matrix.length;i++){
            System.out.print("\t");
            for(j=0;j<matrix[i].length-1;j++){
                outTocalcServer.print(matrix[i][j]+",");
                //System.out.print(matrix[i][j]+",");
            }
            outTocalcServer.println(matrix[i][j]);
            //System.out.println(matrix[i][j]);
        }
        outTocalcServer.println("end");
        outTocalcServer.println(init);
        outTocalcServer.println(goal);
        outTocalcServer.flush();
        System.out.println("\tend\n\t"+init+"\n\t"+goal);
        System.out.println("\tproblem sent, waiting for solution...");
        try {
            this.path=inFromCalcServer.readLine();
        } catch (IOException e) {}
        System.out.println("\tsolution received");
        return path;
    }
    public void getAircraftPosition(){
        new Thread(()->{
            while(true) {
                String[] pos = new String[3];
                pos[0] = String.valueOf(Interpreter.symbolTable.get("/position/latitude-deg").getValue());
                pos[1] = String.valueOf(Interpreter.symbolTable.get("/position/longitude-deg").getValue());
                pos[2] = String.valueOf(Interpreter.symbolTable.get("/instrumentation/heading-indicator/indicated-heading-deg").getValue());
                //System.out.println("model: "+pos);
                this.setChanged();
                this.notifyObservers(pos);
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {}
            }
        }).start();
    }
}
