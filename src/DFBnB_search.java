import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class DFBnB_search {
	private static int[] row = { 0, 1, 0, -1 };
	private static int[] col = { 1, 0, -1, 0 };

	/**
	 * Computes the number of tiles in the board that are not black (excluding the empty tile), used for the initial threshold.
	 * @param start - the start state of the game.
	 * @return the number of tiles in the board that are not black (excluding the empty tile).
	 */
	private static int numberOfTiles(State start) {
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
	private static int factorial(int tilesNumber) {
		int res = 1, i;
		for (i = 2; i <= tilesNumber; i++)
			res *= i;
		return res;
	}

	public static State DFBnB(State start, State goal) {
		int count = 1;
		if (!start.blackInPlace()) {
			start.setMove("no path");
			start.setCount(count);
			return start;
		}
		Stack<State> stack = new Stack<State>();
		Hashtable<String, State> h = new Hashtable<String, State>();
		stack.push(start);
		h.put(start.toString(), start);
		int tilesNumber = numberOfTiles(start);
		int t = Math.min(factorial(tilesNumber), Integer.MAX_VALUE);
//		int t = 200;
//		int count = 1;
		start.setCount(count);
		State result = null;
		while (!(stack.isEmpty())) {
			State node = stack.pop();
			if (node.getOut() == 1) {
				h.remove(node.toString());
			} else {
				node.setOut(1);
				stack.push(node);
				ArrayList<State> children = new ArrayList<State>();
				for (int i = 0; i < 4; i++) {
					int x1 = node.getX();
					int y1 = node.getY();
					int x2 = x1 + row[i];
					int y2 = y1 + col[i];
					if (node.IsLegal(x2, y2) && !(node.IsParent(x2, y2))) {
						State s = new State(node);
						count++;
						s.setParent(node);
						switch (i) {
						case 0:
							s.setMove(s.getCoordinate(x2, y2) + "L-");
							break;
						case 1:
							s.setMove(s.getCoordinate(x2, y2) + "U-");
							break;
						case 2:
							s.setMove(s.getCoordinate(x2, y2) + "R-");
							break;
						case 3:
							s.setMove(s.getCoordinate(x2, y2) + "D-");
							break;
						default:
							break;
						}
						s.setMoveID(i);
						if (s.isRed(x2, y2)) {
							s.setCost(node.getCost() + 30);
						} else {
							s.setCost(node.getCost() + 1);
						}
						s.replace(x1, y1, x2, y2);
						children.add(s);
					}
				}
				State_Comperator sort = new State_Comperator();
				children.sort(sort);
				for (int i = 0; i < children.size(); i++) {
					State state = children.get(i);
					if (state.f() >= t) {
						while (i < children.size())
							children.remove(i);
//					} else if (h.containsKey(state.toString())) {
					} else if (h.get(state.toString()) != null) {
						State old = h.get(state.toString());
						if (old.getOut() == 1) {
							children.remove(state);
							i--;
						} else {
							if (old.f() <= state.f()) {
								children.remove(state);
								i--;
							} else {
								stack.remove(old);
								h.remove(old.toString());
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
					h.put(children.get(i).toString(), children.get(i));
				}
			}
		}
		if (result == null) {
			start.setMove("no path");
			result = start;
		}
		result.setCount(count);
		return result;
	}
}
