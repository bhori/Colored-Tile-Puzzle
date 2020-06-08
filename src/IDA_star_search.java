import java.util.Hashtable;
import java.util.Stack;

public class IDA_star_search implements search_algorithm {

	@Override
	public State solve(State start, State goal) {
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
						for (int i = 0; i < 4; i++) {
							State son = node.createSon(i);
							if (son == null)
								continue;
							count++;
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
								son.setMove(move);
								son.setCount(count);
								return son;
							}
							stack.push(son);
							h.put(son.toString(), son);
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

	
//	public static State IDA_Star(State start, State goal) {
//	int count = 1;
//	if (start.blackInPlace()) {
//		Stack<State> stack = new Stack<State>();
//		Hashtable<String, State> h = new Hashtable<String, State>();
//		int threshold = start.heuristic();
//		start.setCount(count);
//		while (threshold < Integer.MAX_VALUE) {
//			int min = Integer.MAX_VALUE;
//			start.setOut(0);
//			stack.push(start);
//			h.put(start.toString(), start);
//			while (!(stack.isEmpty())) {
//				State node = stack.pop();
//				if (node.getOut() == 1) {
//					h.remove(node.toString());
//				} else {
//					node.setOut(1);
//					stack.push(node);
//					for (int i = 0; i < 4; i++) {
//						int x1 = node.getX();
//						int y1 = node.getY();
//						int x2 = x1 + row[i];
//						int y2 = y1 + col[i];
//						if (node.IsLegal(x2, y2) && !(node.IsParent(x2, y2))) {
//							State s = new State(node);
//							count++;
//							s.setParent(node);
//							switch (i) {
//							case 0:
//								s.setMove(s.getCoordinate(x2, y2) + "L-");
//								break;
//							case 1:
//								s.setMove(s.getCoordinate(x2, y2) + "U-");
//								break;
//							case 2:
//								s.setMove(s.getCoordinate(x2, y2) + "R-");
//								break;
//							case 3:
//								s.setMove(s.getCoordinate(x2, y2) + "D-");
//								break;
//							default:
//								break;
//							}
//							s.setMoveID(i);
//							if (s.isRed(x2, y2)) {
//								s.setCost(node.getCost() + 30);
//							} else {
//								s.setCost(node.getCost() + 1);
//							}
//							s.replace(x1, y1, x2, y2);
//							if (s.f() > threshold) {
//								min = Math.min(min, s.f());
//								continue;
//							}
//							if (h.get(s.toString()) != null && h.get(s.toString()).getOut() == 1)
//								continue;
//							if (h.get(s.toString()) != null && h.get(s.toString()).getOut() != 1) {
//								State old = h.get(s.toString());
//								if (old.f() > s.f()) {
//									stack.remove(old);
//									h.remove(old.toString());
//								} else {
//									continue;
//								}
//							}
//							if (s.isGoal(goal)) {
//								String move = s.getMove();
//								move = move.substring(0, move.indexOf('-'));
//								s.setMove(move);
//								s.setCount(count);
//								return s;
//							}
//							stack.push(s);
//							h.put(s.toString(), s);
//						}
//					}
//				}
//			}
//			threshold = min;
//		}
//	}
//	start.setMove("no path");
//	start.setCount(count);
//	return start;
//}
}
