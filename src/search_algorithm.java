
/**
 * This interface represents a search algorithm that can solve the game Colored-Tile-Puzzle.
 * @author Ori Ben-Hamo
 *
 */
public interface search_algorithm {
	
	/**
	 * Solves the game.
	 * @param start the initial state.
	 * @param goal the goal state.
	 * @param withOpen indicates whether to print the open list in each iteration of the algorithm or not.
	 * @return the information of the search result.
	 */
	public SearchInfo solve(State start, State goal, boolean withOpen);
	
}
