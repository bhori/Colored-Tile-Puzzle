import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class DFBnB_search {
	private static int[] row = { 0, 1, 0, -1 };
	private static int[] col = { 1, 0, -1, 0 };

	private static void removeFromStack(Stack<State> stack, State state) {
		Stack<State> tmp = new Stack<State>();
		while (!(stack.isEmpty())) {
			State s = (State) stack.pop();
			if (s.equals(state))
				break;
			tmp.push(s);
		}
		while (!(tmp.isEmpty())) {
			stack.push(tmp.pop());
		}

	}

	public static State DFBnB(State start, State goal) {
		Stack<State> stack = new Stack<State>();
		Hashtable<String, State> h = new Hashtable<String, State>();
		stack.push(start);
		h.put(start.toString(), start);
		int t = Integer.MAX_VALUE;
//		int t = 200;
		int count = 1;
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
						s.setCoordinate(x1, y1, x2, y2);
						children.add(s);
					}
				}
				State_Comperator sort = new State_Comperator();
				children.sort(sort);
				for (int i=0;i<children.size();i++) {
					State state = children.get(i);
					if (state.f() >= t) {
						while (i < children.size())
							children.remove(i);
//					} else if (h.containsKey(state.toString())) {
					} else if (h.get(state.toString())!=null) {
						State old = h.get(state.toString());
						if (old.getOut() == 1) {
							children.remove(state);
						} else {
							if (old.f() <= state.f()) {
								children.remove(state);
							} else {
								removeFromStack(stack, old); //this function is neccesary? maybe pop is enough...
//								stack.pop();
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
