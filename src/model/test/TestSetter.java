package model.test;

import model.server_side.MyClientHandler;
import model.server_side.MySerialServer;
import model.server_side.Server;

public class TestSetter {
	

	static Server s; 
	
	public static void runServer(int port) {

		s=new MySerialServer(); // initialize
		try {
			s.start(port, new MyClientHandler());
		}catch (Exception e) {e.printStackTrace();}
	}

	public static void stopServer() {
		s.stop();
	}
	

}
