package model.server_side;

public interface CacheManager<Problem, Solution> {
	
	public boolean isExist(Problem problem);
	public Solution getSolution(Problem problem);
	public void saveSolution(Problem problem, Solution solution);

}
