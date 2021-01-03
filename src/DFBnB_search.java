import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
/**
 * This class represents a DFBnB algorithm with loop-avoidance.
 * This class implements the search_algorithm interface.
 * @author Ori Ben-Hamo
 *
 */
public class DFBnB_search implements search_algorithm{
	private SearchInfo info;

	/**
	 * Computes the number of tiles in the board that are not black (excluding the empty tile), used for the initial threshold.
	 * @param start - the start state of the game.
	 * @return the number of tiles in the board that are not black (excluding the empty tile).
	 */
	private int numberOfTiles(State start) {
		int[][] board = start.getBoard();
		int tiles = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if ((board[i][j] != 0) && !(start.getBlack().contains(board[i][j]))) {
					tiles++;
				}
			}
		}
		return tiles;
	}

	/**
	 * Computes the factorial of the number of tiles in the board that are not black (excluding the empty tile), used for the initial threshold.
	 * @param tilesNumber - the number of tiles in the board that are not black (excluding the empty tile).
	 * @return the factorial of tilesNumber.
	 */
	private int factorial(int tilesNumber) {
		int res = 1, i;
		for (i = 2; i <= tilesNumber; i++)
			res *= i;
		return res;
	}

	/**
	 * Runs the search on the given game and returns its result.
	 */
	@Override
	public SearchInfo solve(State start, State goal, boolean withOpen) {
		int count = 1;
		if (!start.blackInPlace()) {
			start.setMove("no path");
			start.setCount(count);
			info = new SearchInfo(start, count);
			return info;
		}
		Stack<State> stack = new Stack<State>();
		Hashtable<String, State> openList = new Hashtable<String, State>();
		stack.push(start);
		openList.put(start.toString(), start);
		int tilesNumber = numberOfTiles(start);
		int t = Math.min(factorial(tilesNumber), Integer.MAX_VALUE);
		start.setCount(count);
		State result = null;
		while (!(stack.isEmpty())) {
			if (withOpen)
				printOpenList(openList);
			State node = stack.pop();
			if (node.getOut() == 1) {
				openList.remove(node.toString());
			} else {
				node.setOut(1);
				stack.push(node);
				ArrayList<State> children = node.createChildren();
				count += children.size();
				State_Comparator sort = new State_Comparator();
				children.sort(sort);
				for (int i = 0; i < children.size(); i++) {
					State state = children.get(i);
					if (state.f() >= t) {
						while (i < children.size())
							children.remove(i);
					} else if (openList.get(state.toString()) != null) {
						State old = openList.get(state.toString());
						if (old.getOut() == 1) {
							children.remove(state);
							i--;
						} else {
							if (old.f() <= state.f()) {
								children.remove(state);
								i--;
							} else {
								stack.remove(old);
								openList.remove(old.toString());
							}
						}
					} else if (state.isGoal(goal)) {
						t = state.f();
						String move = state.getMove();
						move = move.substring(0, move.indexOf('-'));
						state.setMove(move);
						state.setCount(count);
						result = state;
						while (i < children.size())
							children.remove(i);
					}
				}
				for (int i = children.size() - 1; i >= 0; i--) {
					stack.push(children.get(i));
					openList.put(children.get(i).toString(), children.get(i));
				}
			}
		}
		if (result == null) {
			start.setMove("no path");
			result = start;
		}
		result.setCount(count);
		info = new SearchInfo(result, count);
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
