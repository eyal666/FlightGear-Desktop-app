package model.server_side;

public class ServerMain {

    public static void main(String[] args){
        Server s=new MySerialServer();
        s.start(5404, new MyClientHandler());
    }
}
