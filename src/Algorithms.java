import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Algorithms {
	private static int[] row = { 0, 1, 0, -1 };
	private static int[] col = { 1, 0, -1, 0 };

	public static State BFS(State start, State goal, boolean withOpen) {
		int count = 1;
		if (start.blackInPlace()) {
			Queue<State> queue = new LinkedList<State>();
			Hashtable<String, State> openList = new Hashtable<String, State>();
			queue.add(start);
			openList.put(start.toString(), start);
			Hashtable<String, State> closedList = new Hashtable<String, State>();
			int iteration = 1; // Used for printing the open list
			while (!(queue.isEmpty())) {
				if (withOpen) {
					System.out.println("iteration " + iteration + " open list:\n");
					for (String state : openList.keySet()) {
						System.out.println(state);
					}
					System.out.println();
				}
				State node = queue.poll(); // maybe should use q.pool??
				openList.remove(node.toString());
				iteration++;
				closedList.put(node.toString(), node);
//				ArrayList<State> children = node.createChildren();
//				for (int i=0;i<children.size();i++) {
//					State s = children.get(i);
//					if (closedList.get(s.toString()) == null && openList.get(s.toString()) == null) {
//						if (s.isGoal(goal)) {
//							String move = s.getMove();
//							move = move.substring(0, move.indexOf('-'));
//							s.setMove(move);
//							s.setCount(count);
//							return s;
//						}
//						queue.add(s);
//						openList.put(s.toString(), s);
//					}
//				}
				for (int i = 0; i < 4; i++) {
					State s = node.createSon(i);
					if (s != null) {
						count++;
						if (closedList.get(s.toString()) == null && openList.get(s.toString()) == null) {
							if (s.isGoal(goal)) {
								String move = s.getMove();
								move = move.substring(0, move.indexOf('-'));
								s.setMove(move);
								s.setCount(count);
								return s;
							}
							queue.add(s);
							openList.put(s.toString(), s);
						}
					}
				}
			}
		}
		start.setMove("no path");
		start.setCount(count);
		return start;
	}

	public static State DFID(State start, State goal) throws IOException {
		start.setCount(1);
		if (!start.blackInPlace()) {
			start.setMove("no path");
			return start;
		}
		State cutOff = start.cutOff(start.getN(), start.getM()); // should update counter??
		State result = null;
		for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
			Hashtable<String, State> h = new Hashtable<String, State>();
			result = limitedDFS(start, goal, depth, h);
			if (!(result.equals(cutOff)))
				return result;
		}
		return null;
	}

	static int count = 1; // this is good???

	private static State limitedDFS(State node, State goal, int limit, Hashtable<String, State> h) {
		boolean isCutOff;
		State result = null;
		State cutOff = node.cutOff(node.getN(), node.getM()); // should update counter??
		State fail = node.fail(node.getN(), node.getM());
		if (node.isGoal(goal)) {
			String move = node.getMove();
			move = move.substring(0, move.indexOf('-'));
			node.setMove(move);
			return node;
		} else if (limit == 0) {
			return cutOff;
		} else {
			h.put(node.toString(), node);
			isCutOff = false;
			for (int i = 0; i < 4; i++) {
				int x1 = node.getX();
				int y1 = node.getY();
				int x2 = x1 + row[i];
				int y2 = y1 + col[i];
				if (node.IsLegal(x2, y2) && !(node.IsParent(x2, y2))) {
					State s = new State(node);
					count++;
					s.setCount(count);
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
					if (s.isRed(x2, y2)) {
						s.setCost(node.getCost() + 30);
					} else {
						s.setCost(node.getCost() + 1);
					}
					s.replace(x1, y1, x2, y2);
					if (h.containsKey(s.toString()))
						continue;
					result = limitedDFS(s, goal, limit - 1, h);
					if (result.equals(cutOff)) {
						isCutOff = true;
					} else if (!(result.equals(fail))) {
						return result;
					}
				}
			}
			h.remove(node.toString());
			if (isCutOff) {
				return cutOff;
			} else {
				fail.setMove("no path");
				fail.setCount(count);
				return fail;
			}
		}
	}

	public static State A_Star(State start, State goal, boolean withOpen) {
		int count = 1;
		if (start.blackInPlace()) {
			PriorityQueue<State> queue = new PriorityQueue<State>(new State_Comperator());
			Hashtable<String, State> openList = new Hashtable<String, State>();
			queue.add(start);
			openList.put(start.toString(), start);
			Hashtable<String, State> closedList = new Hashtable<String, State>();
			int iteration = 0;
			while (!(queue.isEmpty())) {
				iteration++;
				if (withOpen) {
					System.out.println("iteration " + iteration + " open list:\n");
					for (String state : openList.keySet()) {
						System.out.println(state);
					}
					System.out.println();
				}
				State node = queue.poll();
				openList.remove(node.toString());
				if (node.isGoal(goal)) { // what about the start node? it don't have "move" field for substring...
					String move = node.getMove();
					move = move.substring(0, move.indexOf('-'));
					node.setMove(move);
					node.setCount(count);
					return node;
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
							queue.remove(old); // don't sure about that!!
							openList.remove(old.toString());
							queue.add(son);
							openList.put(son.toString(), son);
						}
					}
				}
			}
		}
		start.setMove("no path");
		start.setCount(count);
		return start;
	}

	public static State IDA_Star(State start, State goal) {
		int count = 1;
		if (start.blackInPlace()) {
			Stack<State> stack = new Stack<State>();
			Hashtable<String, State> h = new Hashtable<String, State>();
			int threshold = start.heuristic();
			start.setCount(count);
			while (threshold < Integer.MAX_VALUE) {
				int min = Integer.MAX_VALUE;
				start.setOut(0);
				stack.push(start);
				h.put(start.toString(), start);
				while (!(stack.isEmpty())) {
					State node = stack.pop();
					if (node.getOut() == 1) {
						h.remove(node.toString());
					} else {
						node.setOut(1);
						stack.push(node);
						ArrayList<State> children = node.createChildren();
						count += children.size();
						for (int i = 0; i < children.size(); i++) {
							State son = children.get(i);
								if (son.f() > threshold) {
									min = Math.min(min, son.f());
									continue;
								}
								if (h.get(son.toString()) != null && h.get(son.toString()).getOut() == 1)
									continue;
								if (h.get(son.toString()) != null && h.get(son.toString()).getOut() != 1) {
									State old = h.get(son.toString());
									if (old.f() > son.f()) {
										stack.remove(old);
										h.remove(old.toString());
									} else {
										continue;
									}
								}
								if (son.isGoal(goal)) {
									String move = son.getMove();
									move = move.substring(0, move.indexOf('-'));
									s.setMove(move);
									s.setCount(count);
									return s;
								}
								stack.push(s);
								h.put(s.toString(), s);
							}
						}
					}
				}
				threshold = min;
			}
		}
		start.setMove("no path");
		start.setCount(count);
		return start;
	}

	/**
	 * Computes the number of tiles in the board that are not black (excluding the
	 * empty tile), used for the initial threshold.
	 * 
	 * @param start - the start state of the game.
	 * @return the number of tiles in the board that are not black (excluding the
	 *         empty tile).
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
	 * Computes the factorial of the number of tiles in the board that are not black
	 * (excluding the empty tile), used for the initial threshold.
	 * 
	 * @param tilesNumber - the number of tiles in the board that are not black
	 *                    (excluding the empty tile).
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
