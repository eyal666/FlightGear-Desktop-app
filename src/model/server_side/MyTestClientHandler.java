package model.server_side;

import java.io.*;

public class MyTestClientHandler implements ClientHandler  {
	
	private Solver<String, String> solver;
	private CacheManager<String, String> cm;
	
	public MyTestClientHandler(CacheManager<String, String> cm, Solver<String, String> solver) {
		this.cm=cm;
		this.solver=solver;
	}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		
		BufferedReader inputFromClient=new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter outputToClient=new PrintWriter(outToClient);
		
		try {
			String problem;
			String solution;
			while(!(problem=inputFromClient.readLine()).equals("end")) {
				if(cm.isExist(problem)) {
					solution=cm.getSolution(problem);
				}
				else {
					solution=solver.solve(problem);
					cm.saveSolution(problem, solution);
				}
				outputToClient.println(solution);
				outputToClient.flush();
			}	
		} catch (IOException e) {e.printStackTrace();}
	}



}
