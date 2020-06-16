import java.util.Hashtable;

/**
 * This class represents a recursive DFID algorithm with loop-avoidance.
 * Note that the algorithm does not necessarily find the cheapest path but rather finds the shortest path.
 * This class implements the search_algorithm interface.
 * @author Ori Ben-Hamo
 *
 */
public class DFID_search implements search_algorithm{
	private SearchInfo info;
	private State cutOff;
	private State fail;
	static int count = 1;
	
	/**
	 * Creates cutOff state, i.e. all tiles are '0'.
	 * @param start the state from which the cutOff state is produced.
	 * @return cutOff state.
	 */
	public State cutOff(State start) {
		State cutOff = new State(start);
		int n = cutOff.getBoard().length;
		int m= cutOff.getBoard()[0].length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				cutOff.setCoordinate(i, j, 0);
			}
		}
		return cutOff;
	}

	/**
	 * Creates fail state, i.e. all tiles are '-1'.
	 * @param start the state from which the fail state is produced.
	 * @return fail state.
	 */
	public State fail(State start) {
		State fail = new State(start);
		int n = fail.getBoard().length;
		int m= fail.getBoard()[0].length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				fail.setCoordinate(i, j, -1);
			}
		}
		return fail;
	}
	
	/**
	 * Run the search on the given game and returns its result.
	 */
	@Override
	public SearchInfo solve(State start, State goal, boolean withOpen) {
		start.setCount(1);
		if (!start.blackInPlace()) {
			start.setMove("no path");
			info = new SearchInfo(start, count);
			return info;
		}
		cutOff = cutOff(start);
		fail = fail(start);
		State result = null;
		for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
			Hashtable<String, State> openList = new Hashtable<String, State>();
			result = limitedDFS(start, goal, depth, openList, withOpen);
			if (!(result.equals(cutOff))) {
				info = new SearchInfo(result, count);
				return info;
			}
		}
		return null;
	}
	
	/**
	 * Recursive DFS algorithm that is limited in the depth of the search.
	 * @param current the state we are comparing to the goal state or going to expand.
	 * @param goal the goal state.
	 * @param limit the limit of the depth of the search.
	 * @param openList the open list.
	 * @return the goal state if reached, cutOff if reached limit, fail if the goal state is not found
	 */
	private State limitedDFS(State current, State goal, int limit, Hashtable<String, State> openList, boolean withOpen) {
		boolean isCutOff;
		State result = null;
		if (current.isGoal(goal)) {
			String move = current.getMove();
			move = move.substring(0, move.indexOf('-'));
			current.setMove(move);
			return current;
		} else if (limit == 0) {
			return cutOff;
		} else {
			openList.put(current.toString(), current);
			isCutOff = false;
			for (int i = 0; i < 4; i++) {
				State son = current.createSon(i);
				if (son != null) {
					count++;
					son.setCount(count);
					if (openList.containsKey(son.toString()))
						continue;
					result = limitedDFS(son, goal, limit - 1, openList, withOpen);
					if (result.equals(cutOff)) {
						isCutOff = true;
					} else if (!(result.equals(fail))) {
						return result;
					}
				}
			}
			if (withOpen)
				printOpenList(openList);
			openList.remove(current.toString());
			if (isCutOff) {
				return cutOff;
			} else {
				fail.setMove("no path");
				fail.setCount(count);
				return fail;
			}
		}
	}

	/**
	 * Prints the open list.
	 * @param openList the required list to be printed.
	 */
	private void printOpenList(Hashtable<String, State> openList) {
		for (String state : openList.keySet()) {
			System.out.println(state);
		}
		System.out.println("**************************");
	}

}
