package model.interpeter.commands.assests;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class DataWriterClient {

    public int port;
    public String ip;
    public static PrintWriter out;

    public DataWriterClient(String ip, int port) {
        this.port = port;
        this.ip = ip;
    }

    public void runClient() {
        try {
            Socket theServer = new Socket(ip, port);
            System.out.println("Connected to server...");
            out = new PrintWriter(theServer.getOutputStream());
        } catch (IOException e) {}

    }


}
