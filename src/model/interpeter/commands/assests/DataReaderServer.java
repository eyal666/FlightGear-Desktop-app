package model.interpeter.commands.assests;

import model.interpeter.Interpreter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class DataReaderServer {

    int port;
    int rate;
    public static volatile boolean stop;
    public  ArrayList<String> varNames;
    public DataReaderServer(int port,int rate) {
        stop = false;
        this.port=port;
        this.rate=rate;
        this.varNames=new ArrayList<>();
//        try {
//            Scanner s=new Scanner(new BufferedReader(new FileReader("./scripts/simulator_vars.txt")));
//            while(s.hasNext()){
//                varNames.add(s.next());
//            }
//        } catch (FileNotFoundException e) {}
        addVars();
        for(String s : varNames) {
            Interpreter.symbolTable.put(s, new Variable(0.0, s));
        }
    }
    public void runServer() {
        try {
            ServerSocket server=new ServerSocket(port);
            Socket client=server.accept();
            System.out.println("client connected...");
            Interpreter.flag=true;
            BufferedReader input=new BufferedReader(new InputStreamReader(client.getInputStream()));
            int i;
            while(!stop) {
                i=0;
                String[] inputFromClient = input.readLine().split(",");
                for(String s: inputFromClient) {
                    Interpreter.symbolTable.get(varNames.get(i)).setValue(Double.parseDouble(s));
                    i++;
                }
                Thread.sleep(rate);
            }
            input.close();
            client.close();
            server.close();
        } catch (IOException | InterruptedException e) {}

    }
    public static void close(){
        stop=true;
    }
    public void addVars(){
        varNames.add("/instrumentation/airspeed-indicator/indicated-speed-kt");
        varNames.add("/instrumentation/altimeter/indicated-altitude-ft");
        varNames.add("/instrumentation/altimeter/pressure-alt-ft");
        varNames.add("/instrumentation/attitude-indicator/indicated-pitch-deg");
        varNames.add("/instrumentation/attitude-indicator/indicated-roll-deg");
        varNames.add("/instrumentation/attitude-indicator/internal-pitch-deg");
        varNames.add("/instrumentation/attitude-indicator/internal-roll-deg");
        varNames.add("/instrumentation/encoder/indicated-altitude-ft");
        varNames.add("/instrumentation/encoder/pressure-alt-ft");
        varNames.add("/instrumentation/gps/indicated-altitude-ft");
        varNames.add("/instrumentation/gps/indicated-ground-speed-kt");
        varNames.add("/instrumentation/gps/indicated-vertical-speed");
        varNames.add("/instrumentation/heading-indicator/indicated-heading-deg");
        varNames.add("/instrumentation/magnetic-compass/indicated-heading-deg");
        varNames.add("/instrumentation/slip-skid-ball/indicated-slip-skid");
        varNames.add("/instrumentation/turn-indicator/indicated-turn-rate");
        varNames.add("/instrumentation/vertical-speed-indicator/indicated-speed-fpm");
        varNames.add("/controls/flight/aileron");
        varNames.add("/controls/flight/elevator");
        varNames.add("/controls/flight/rudder");
        varNames.add("/controls/flight/flaps");
        varNames.add("/controls/engines/current-engine/throttle");
        varNames.add("/engines/engine/rpm");
        varNames.add("/controls/flight/speedbrake");
        varNames.add("/position/latitude-deg");
        varNames.add("/position/longitude-deg");

    }
}
