import java.util.HashMap;
import java.util.Hashtable;
import java.util.Stack;

/**
 * This class represents a IDA* algorithm with loop-avoidance.
 * This class implements the search_algorithm interface.
 * @author Ori Ben-Hamo
 *
 */
public class IDA_star_search implements search_algorithm {
	private SearchInfo info;

	/**
	 * Runs the search on the given game and returns its result.
	 */
	@Override
	public SearchInfo solve(State start, State goal, boolean withOpen) {
		int count = 1;
		info = new SearchInfo(start, count);
		if (start.blackInPlace()) {
			Stack<State> stack = new Stack<State>();
			HashMap<String, State> openList = new HashMap<String, State>();
			int threshold = start.heuristic();
			start.setCount(count);
			while (threshold != Integer.MAX_VALUE) {
				int min = Integer.MAX_VALUE;
				start.setOut(0);
				stack.clear();
				openList.clear();
				stack.push(start);
				openList.put(start.toString(), start);
				while (!(stack.isEmpty())) {
					if (withOpen)
						printOpenList(openList);
					State node = stack.pop();
					if (node.getOut() == 1) {
						openList.remove(node.toString(), node);
					} else {
						node.setOut(1);
						stack.push(node);
						for (int i = 0; i < 4; i++) {
							State son = node.createSon(i);
							if (son == null)
								continue;
							count++;
							if (son.f() > threshold) {
								min = Math.min(min, son.f());
								continue;
							}
							if (openList.get(son.toString()) != null && openList.get(son.toString()).getOut() == 1)
								continue;
							if (openList.get(son.toString()) != null && openList.get(son.toString()).getOut() != 1) {
								State old = openList.get(son.toString());
								if (old.f() > son.f()) {
									stack.remove(old);
									openList.remove(old.toString(), old);
								} else {
									continue;
								}
							}
							if (son.isGoal(goal)) {
								String move = son.getMove();
								move = move.substring(0, move.indexOf('-'));
								son.setMove(move);
								info = new SearchInfo(son, count);
								return info;
							}
							stack.push(son);
							openList.put(son.toString(), son);
						}
					}
				}
				threshold = min;
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
	private void printOpenList(HashMap<String, State> openList) {
		for (String state : openList.keySet()) {
			System.out.println(state);
		}
		System.out.println("**************************");
	}

}
