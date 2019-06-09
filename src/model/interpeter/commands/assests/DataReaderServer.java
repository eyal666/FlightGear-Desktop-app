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
        try {
            Scanner s=new Scanner(new BufferedReader(new FileReader("./scripts/simulator_vars.txt")));
            while(s.hasNext()){
                varNames.add(s.next());
            }
        } catch (FileNotFoundException e) {}
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
}
