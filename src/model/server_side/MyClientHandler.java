package model.server_side;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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
			int rows=0, columns=0;
			ArrayList<String []> arr=new ArrayList<>();
			line=inputFromClient.readLine();
			stringMatrix=line.split(",");
			while(!line.equals("end")) {
				arr.add(stringMatrix);
				line = inputFromClient.readLine();
				stringMatrix = line.split(",");
			}
			rows=arr.size();
			columns=arr.get(0).length;
			double [][] matrix=new double[rows][columns];
			for(int i=0; i<rows; i++){
				for (int j=0; j<columns; j++){
					matrix[i][j]=Double.parseDouble(arr.get(i)[j]);
				}
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
