package model.server_side;

public interface Server {

	public void runServer(int port, ClientHandler ch) throws Exception;
	public void start(int port, ClientHandler ch);
	public void stop();
}
