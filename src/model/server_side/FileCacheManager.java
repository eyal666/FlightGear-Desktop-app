package model.server_side;

import java.io.*;

public class FileCacheManager<Problem, Solution> implements CacheManager<Problem, Solution>{
	
	
	public FileCacheManager() {}

	@Override
	public boolean isExist(Problem problem) {
		
		return new File(problem.toString()+".txt").exists();
	}


	@SuppressWarnings({ "unchecked" })
	@Override
	public Solution getSolution(Problem problem) {
		try {
			Solution sol;
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File(problem.toString()+".txt")));
			sol=(Solution) ois.readObject();
			ois.close();
			return sol;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public void saveSolution(Problem problem, Solution solution) {
		
		try {
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File(problem.toString()+".txt")));
			oos.writeObject(solution);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
