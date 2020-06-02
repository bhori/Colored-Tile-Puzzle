import java.util.Hashtable;
import java.util.PriorityQueue;

public class A_star_search {
	private static int[] row = { 0, 1, 0, -1 };
	private static int[] col = { 1, 0, -1, 0 };

	public static State A_Star(State start, State goal, boolean withOpen) {
		int count = 1;
		PriorityQueue<State> queue = new PriorityQueue<State>(new State_Comperator());
		Hashtable<String, State> openList = new Hashtable<String, State>();
		queue.add(start);
		openList.put(start.toString(), start);
		Hashtable<String, State> closedList = new Hashtable<String, State>();
		int iteration = 0;
		while (!(queue.isEmpty())) {
			iteration++;
//			State node = queue.poll(); // maybe should use q.pool??
//			openList.remove(node.toString());
			if (withOpen) {
				System.out.println("iteration " + iteration + " open list:\n");
				for (String state : openList.keySet()) {
					System.out.println(state);
				}
				System.out.println();
//				System.out.println("iteration "+iteration+" open list:\n\n" + openList.keySet() + "\n");
			}
			State node = queue.poll(); // maybe should use q.pool??
			openList.remove(node.toString());
			if (node.isGoal(goal)) { // what about the start node? it don't have "move" field for substring...
				String move = node.getMove();
				move = move.substring(0, move.indexOf('-'));
				node.setMove(move);
				node.setCount(count);
				return node;
			}
			closedList.put(node.toString(), node);
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
					s.setIteration(iteration);
//					if (!(closedList.containsKey(s.toString()) && !(openList.containsKey(s.toString())))) {
					if (closedList.get(s.toString()) == null && openList.get(s.toString()) == null) {
						queue.add(s);
						openList.put(s.toString(), s);
					} else if (openList.containsKey(s.toString())) {
						State old = openList.get(s.toString());
						if (old.getCost() > s.getCost()) {
//						if (old.f() > s.f()) {
							queue.remove(old); // don't sure about that!!
							openList.remove(old.toString());
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
}
