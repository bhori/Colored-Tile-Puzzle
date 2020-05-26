import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class BFS_search {
	private static int[] row = { 0, 1, 0, -1 };
	private static int[] col = { 1, 0, -1, 0 };

	public static State BFS(State start, State goal, boolean withOpen) {
		int count = 1;
		Queue<State> queue = new LinkedList<State>();
		Hashtable<String, State> openList = new Hashtable<String, State>();
		queue.add(start);
		openList.put(start.toString(), start);
		Hashtable<String, State> closedList = new Hashtable<String, State>();
		while (!(queue.isEmpty())) {
			State node = queue.remove(); // maybe should use q.pool??
			openList.remove(node.toString());
			if (withOpen) {
				System.out.println("open list:\n\n" + openList.keySet() + "\n");
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
					if (s.isRed(x2, y2)) {
						s.setCost(node.getCost() + 30);
					} else {
						s.setCost(node.getCost() + 1);
					}
					s.setCoordinate(x1, y1, x2, y2);
					if (!(closedList.containsKey(s.toString()) && !(openList.containsKey(s.toString())))) {
//					if (c.get(s.toString())==null && h.get(s.toString())==null) {
						if (s.equals(goal)) {
							String move = s.getMove();
							move = move.substring(0, move.indexOf('-'));
							s.setMove(move);
							s.setCount(count);
							return s;
						}
					}
					queue.add(s);
					openList.put(s.toString(), s);
				}
			}
		}
		start.setMove("no path");
		start.setCount(count);
		return start;
	}
}
