import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a BFS algorithm, in this implementation to improve the search we used closed list and open list for loop-avoidance.
 * Note that the algorithm does not necessarily find the cheapest path but rather finds the shortest path.
 * This class implements the search_algorithm interface.
 * @author Ori Ben-Hamo
 *
 */
public class BFS_search implements search_algorithm{
	private SearchInfo info;

	/**
	 * Run the search on the given game and returns its result.
	 */
	@Override
	public SearchInfo solve(State start, State goal, boolean withOpen) {
		int count = 1;
		info = new SearchInfo(start, count);
		if (start.blackInPlace()) {
			Queue<State> queue = new LinkedList<State>();
			Hashtable<String, State> openList = new Hashtable<String, State>();
			queue.add(start);
			openList.put(start.toString(), start);
			Hashtable<String, State> closedList = new Hashtable<String, State>();
			while (!(queue.isEmpty())) {
				if (withOpen)
					printOpenList(openList); 
				State node = queue.poll(); 
				openList.remove(node.toString());
				closedList.put(node.toString(), node);
				for (int i = 0; i < 4; i++) {
					State son = node.createSon(i);
					if (son != null) {
						count++;
						if (closedList.get(son.toString()) == null && openList.get(son.toString()) == null) {
							if (son.isGoal(goal)) {
								String move = son.getMove();
								move = move.substring(0, move.indexOf('-'));
								son.setMove(move);
								info = new SearchInfo(son, count);
								return info;
							}
							queue.add(son);
							openList.put(son.toString(), son);
						}
					}
				}
			}
		}
		info.setPath("no path");
		info.setNumOfStates(count);
		return info;
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
