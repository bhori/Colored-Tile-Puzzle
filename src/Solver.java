
/**
 * This class is responsible for managing the game which includes running the appropriate algorithm and returning the result of the game.
 * @author Ori Ben-Hamo
 *
 */
public class Solver {
	private search_algorithm algo;
	private SearchInfo info;
	
	/**
	 * Constructor for selecting the requested algorithm for the game
	 * @param algo the requested algorithm for the game.
	 */
	public Solver(String algo) {
		switch (algo) {
		case "BFS":
			this.algo = new BFS_search();
			break;
		case "DFID":
			this.algo = new DFID_search();
			break;
		case "A*":
			this.algo = new A_star_search();
			break;
		case "IDA*":
			this.algo = new IDA_star_search();
			break;
		case "DFBnB":
			this.algo = new DFBnB_search();
			break;
		default:
			break;
		}
	}
	
	/**
	 * Runs the game and returns its result.
	 * @param start the initial state of the game.
	 * @param goal the goal state of the game.
	 * @param withOpen indicates whether to print the open list in each iteration of the algorithm.
	 * @return the result of the game.
	 */
	public SearchInfo solve(State start, State goal, boolean withOpen){
		long startTime = System.nanoTime();
		info = algo.solve(start, goal, withOpen);
		long endTime = System.nanoTime();
		double totalTime = (endTime - startTime) / 1000000000.0;
		info.setTime(totalTime);
		return info;
	}

}
