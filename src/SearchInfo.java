import java.text.DecimalFormat;

/**
 * This class represents the information obtained as a result of the algorithm's search,
 * i.e. the path, the number of vertices created, the path cost, and the running time of the algorithm.
 * @author Ori Ben-Hamo
 *
 */
public class SearchInfo {
	private String path;
	private int numOfStates;
	private int cost;
	private double time;
	
	public SearchInfo(State goal, int count) {
		path = path(goal);
		numOfStates = count;
		cost = goal.getCost();
	}
	
	/**
	 * Find the path of the game.
	 * @param goal the last state in the game.
	 * @return the path from the initial state to the 'goal'.
	 */
	private String path(State goal) {
		if (goal.getParent() == null || goal.getMove().equals("no path"))
			return goal.getMove();
		return path(goal.getParent()) + goal.getMove();
	}
	
	/**
	 * Summarizes all the information into one neat string
	 * @param withTime indicates whether to include the runtime in the summary.
	 * @return a string containing a summary of all the information about the result of the search.
	 */
	public String summary(boolean withTime) {
		String StatesNumber = "Num: " + numOfStates;
		String totalCost = "Cost: " + cost;
		if (path.equals("no path"))
			return path + "\n" + StatesNumber;
		if (withTime) {
			String totalTime = new DecimalFormat("0.000").format(time) + " seconds";
			return path + "\n" + StatesNumber + "\n" + totalCost + "\n" + totalTime;
		}
		return path + "\n" + StatesNumber + "\n" + totalCost;
	}
	
	/**
	 * Returns the path of the game.
	 * @return the path of the game.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Updates the path of the game.
	 * @param path the new path.
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Updates the number of states which generated.
	 * @param numOfStates the number of states which generated.
	 */
	public void setNumOfStates(int numOfStates) {
		this.numOfStates = numOfStates;
	}
	
	/**
	 * Returns the cost of the path.
	 * @return the cost of the path.
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Updates the cost of the path.
	 * @param cost the new cost.
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * Updates the time it took for the game.
	 * @param time the time it took for the game.
	 */
	public void setTime(double time) {
		this.time = time;
	}
}
