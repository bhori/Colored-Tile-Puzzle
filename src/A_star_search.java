import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 * This class represents a A* algorithm, in this implementation to improve the search we used closed list and open list for loop-avoidance.
 * This class implements the search_algorithm interface.
 * @author Ori Ben-Hamo
 *
 */
public class A_star_search implements search_algorithm{
	private SearchInfo info;

	/**
	 * Runs the search on the given game and returns its result.
	 */
	@Override
	public SearchInfo solve(State start, State goal, boolean withOpen) {
		int count = 1;
		info = new SearchInfo(start, count);
		if (start.blackInPlace()) {
			PriorityQueue<State> queue = new PriorityQueue<State>(new State_Comparator());
			Hashtable<String, State> openList = new Hashtable<String, State>();
			queue.add(start);
			openList.put(start.toString(), start);
			Hashtable<String, State> closedList = new Hashtable<String, State>();
			int iteration = 0;
			while (!(queue.isEmpty())) {
				iteration++;
				if (withOpen)
					printOpenList(openList);
				State node = queue.poll();
				openList.remove(node.toString());
				if (node.isGoal(goal)) {
					String move = node.getMove();
					move = move.substring(0, move.indexOf('-'));
					node.setMove(move);
					info = new SearchInfo(node, count);
					return info;
				}
				closedList.put(node.toString(), node);
				ArrayList<State> children = node.createChildren();
				count += children.size();
				for (int i = 0; i < children.size(); i++) {
					State son = children.get(i);
					son.setIteration(iteration);
					if (closedList.get(son.toString()) == null && openList.get(son.toString()) == null) {
						queue.add(son);
						openList.put(son.toString(), son);
					} else if (openList.containsKey(son.toString())) {
						State old = openList.get(son.toString());
						if (old.getCost() > son.getCost()) {
							queue.remove(old);
							openList.remove(old.toString());
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
