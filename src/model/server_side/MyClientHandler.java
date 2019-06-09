package model.server_side;

import java.awt.*;
import java.io.*;

public class MyClientHandler implements ClientHandler {

	private Solver<Searchable, String> solver;
	private CacheManager<Searchable, String> cm;

	public MyClientHandler() {
		this.solver = new SolverSearcher(new BestFirstSearch<String>());
		this.cm = new FileCacheManager<>();
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {

		BufferedReader inputFromClient=new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter outputToClient=new PrintWriter(outToClient);

		try {
			Searchable problem;
			String solution;
			String line;
			String [] stringMatrix;
			int j=0;
			int rows=0, columns=0;

			line=inputFromClient.readLine();
			stringMatrix=line.split(",");
			columns=stringMatrix.length;
			rows=columns;
			double [][] matrix=new double[rows][columns];

			while(!line.equals("end")) {
				for(int i=0; i<columns; i++){
					matrix[j][i]=Double.parseDouble(stringMatrix[i]);
				}
				j++;
				line=inputFromClient.readLine();
				stringMatrix=line.split(",");
			}

			String[] recInitial=inputFromClient.readLine().split(",");
			Point initialPoint=new Point(Integer.parseInt(recInitial[0]),Integer.parseInt(recInitial[1]));
			String[] recGoal=inputFromClient.readLine().split(",");
			Point goalPoint=new Point(Integer.parseInt(recGoal[0]),Integer.parseInt(recGoal[1]));

			problem=new SearchableMatrix(new MyMatrix(matrix, new State<>(initialPoint), new State<>(goalPoint), rows, columns));

			if(cm.isExist(problem)) {
				solution=cm.getSolution(problem);
			}
			else {
				solution=solver.solve(problem);
				cm.saveSolution(problem, solution);
			}
			outputToClient.println(solution);
			outputToClient.flush();

		} catch (IOException e) {e.printStackTrace();}
	}

}
