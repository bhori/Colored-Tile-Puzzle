import java.util.Hashtable;

public class DFID_search implements search_algorithm{
	private State cutOff;
	private State fail;
	static int count = 1; // this is good???
	
	private State limitedDFS(State node, State goal, int limit, Hashtable<String, State> h) {
		boolean isCutOff;
		State result = null;
//		State cutOff = node.cutOff(node.getN(), node.getM()); // should update counter??
		fail = node.fail();
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
				State son = node.createSon(i);
				if (son != null) {
					count++;
					son.setCount(count);
					if (h.containsKey(son.toString()))
						continue;
					result = limitedDFS(son, goal, limit - 1, h);
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

	@Override
	public State solve(State start, State goal) {
		start.setCount(1);
		if (!start.blackInPlace()) {
			start.setMove("no path");
			return start;
		}
		cutOff = start.cutOff(); // should update counter??
		State result = null;
		for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
			Hashtable<String, State> h = new Hashtable<String, State>();
			result = limitedDFS(start, goal, depth, h);
			if (!(result.equals(cutOff)))
				return result;
		}
		return null;
	}
	
//	public static State DFID(State start, State goal) throws IOException {
//	start.setCount(1);
//	if (!start.blackInPlace()) {
//		start.setMove("no path");
//		return start;
//	}
//	State cutOff = start.cutOff(start.getN(), start.getM()); // should update counter??
//	State result = null;
//	for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
//		Hashtable<String, State> h = new Hashtable<String, State>();
//		result = limitedDFS(start, goal, depth, h);
//		if (!(result.equals(cutOff)))
//			return result;
//	}
//	return null;
//}
	
	/** part of old version of limited_dfs **/
//	int x1 = node.getX();
//	int y1 = node.getY();
//	int x2 = x1 + row[i];
//	int y2 = y1 + col[i];
//	if (node.IsLegal(x2, y2) && !(node.IsParent(x2, y2))) {
//		State s = new State(node);
//		count++;
//		s.setCount(count);
//		s.setParent(node);
//		switch (i) {
//		case 0:
//			s.setMove(s.getCoordinate(x2, y2) + "L-");
//			break;
//		case 1:
//			s.setMove(s.getCoordinate(x2, y2) + "U-");
//			break;
//		case 2:
//			s.setMove(s.getCoordinate(x2, y2) + "R-");
//			break;
//		case 3:
//			s.setMove(s.getCoordinate(x2, y2) + "D-");
//			break;
//		default:
//			break;
//		}
//		if (s.isRed(x2, y2)) {
//			s.setCost(node.getCost() + 30);
//		} else {
//			s.setCost(node.getCost() + 1);
//		}
//		s.replace(x1, y1, x2, y2);
//		if (h.containsKey(s.toString()))
//			continue;
//		result = limitedDFS(s, goal, limit - 1, h);
//		if (result.equals(cutOff)) {
//			isCutOff = true;
//		} else if (!(result.equals(fail))) {
//			return result;
//		}
//	}

}
