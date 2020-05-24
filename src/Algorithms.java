import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Algorithms {
	private static int[] row = { 0, 1, 0, -1 };
	private static int[] col = { 1, 0, -1, 0 };

	public static State BFS(State start, State goal, boolean withOpen) {
		int count = 1;
		Queue<State> queue = new LinkedList<State>();
		Hashtable<String, State> openList = new Hashtable<String, State>();
//		String open_list="";
		queue.add(start);
		openList.put(start.toString(), start);
		Hashtable<String, State> closedList = new Hashtable<String, State>();
		while (!(queue.isEmpty())) {
			State node = queue.remove(); // maybe should use q.pool??
			openList.remove(node.toString());
			if (withOpen) {
				System.out.println("open list:\n\n" + openList.keySet() + "\n");
//				for (String string : openList.keySet()) {
//					System.out.println(string);
////					open_list+="\n"+string;
//				}
//				System.out.println();
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
//					if (s.equals(node.getParent()))
//						continue;
//					if(node.getParent()!=null && s.toString()==node.getParent().toString()) //s.toString().equals(node.getParent().toString()
//						continue;
//					count++;
//					int x1 = node.getX();
//					int y1 = node.getY();
//					int x2 = x1 + row[i];
//					int y2 = y1 + col[i];
					if (i == 0) // should to improve this four conditions!!
						s.setMove(s.getCoordinate(x2, y2) + "L-");
					if (i == 1)
						s.setMove(s.getCoordinate(x2, y2) + "U-");
					if (i == 2)
						s.setMove(s.getCoordinate(x2, y2) + "R-");
					if (i == 3)
						s.setMove(s.getCoordinate(x2, y2) + "D-");
					if (s.isRed(x2, y2)) {
						s.setCost(node.getCost() + 30);
					} else {
						s.setCost(node.getCost() + 1);
					}
					s.setCoordinate(x1, y1, x2, y2);
//					if (node.getParent() != null && s.toString().equals(node.getParent().toString())) // s.toString()==node.getParent().toString()
//						continue;
//					count++;
					if (!(closedList.containsKey(s.toString()) && !(openList.containsKey(s.toString())))) {
//					if (c.get(s.toString())==null && h.get(s.toString())==null) {
						if (s.equals(goal)) {
							String move = s.getMove();
							move = move.substring(0, move.indexOf('-'));
							s.setMove(move);
//						return s.path()+", count: "+count+", cost: "+s.getCost();
//							String path = s.path();
//							String num = "Num: " + count;
//							String value = "Cost: " + s.getCost();
//							String summary = summary(s, count, s.getCost());
//							long endTime = System.nanoTime();
//							double totalTime = (endTime - startTime)/1000000000.0;
//							summary+="\n"+totalTime;
//							saveToFile(summary);
							s.setCount(count);
							return s;
						}
					}
					queue.add(s);
					openList.put(s.toString(), s);
				}
			}
		}
//		String path = "no path";
//		String num = "Num: " + count;
//		String summary = path+"\n"+num;
//		saveToFile(summary);
		start.setMove("no path");
		start.setCount(count);
		return start;
	}

	public static State DFID(State start, State goal) throws IOException {
		start.setCount(1);
//		static int count = 1;
		State cutOff = start.cutOff(start.getN(), start.getM()); // should update counter??
		State result = null;
		for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
			Hashtable<String, State> h = new Hashtable<String, State>();
			result = limitedDFS(start, goal, depth, h);
			if (!(result.equals(cutOff)))
				return result;
		}
//		if(!(result.equals(cutOff)))
//			return result;
		return null;
	}

	static int count = 1; // this is good???

	private static State limitedDFS(State node, State goal, int limit, Hashtable<String, State> h) {
//		static int count = 1;
		boolean isCutOff;
		State result = null;
		State cutOff = node.cutOff(node.getN(), node.getM()); // should update counter??
		State fail = node.fail(node.getN(), node.getM());
//		if (node.equals(goal) && node.getMove().length() > 0) { // maybe the second condition is not necessary
		if (node.equals(goal)) {
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
//					s.setCount(node.getCount()+1);
					s.setCount(count);
					s.setParent(node);
//					if (s.equals(node.getParent()))
//						continue;
//					if(node.getParent()!=null && s.toString()==node.getParent().toString()) //s.toString().equals(node.getParent().toString()
//						continue;
//					count++;
//					int x1 = node.getX();
//					int y1 = node.getY();
//					int x2 = x1 + row[i];
//					int y2 = y1 + col[i];
					if (i == 0) // should to improve this four conditions!!
						s.setMove(s.getCoordinate(x2, y2) + "L-");
					if (i == 1)
						s.setMove(s.getCoordinate(x2, y2) + "U-");
					if (i == 2)
						s.setMove(s.getCoordinate(x2, y2) + "R-");
					if (i == 3)
						s.setMove(s.getCoordinate(x2, y2) + "D-");
					if (s.isRed(x2, y2)) {
						s.setCost(node.getCost() + 30);
					} else {
						s.setCost(node.getCost() + 1);
					}
					s.setCoordinate(x1, y1, x2, y2);
//					s.setCount(count);
//					if (node.getParent() != null && s.toString().equals(node.getParent().toString())) // s.toString()==node.getParent().toString()
//						continue;
//					count++;
					if (h.containsKey(s.toString()))
						continue;
					result = limitedDFS(s, goal, limit - 1, h);
//					State cutOff = s.cutOff(s.getN(), s.getM()); // should update counter??
//					State fail = s.fail(s.getN(), s.getM());
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
//		if(isCutOff) {
//			return cutOff;
//		}else {
//			return fail;
//		}
//		return null;
	}

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
			State node = queue.poll(); // maybe should use q.pool??
			openList.remove(node.toString());
			if (withOpen) {
				System.out.println("open list:\n\n" + openList.keySet() + "\n");
			}
			if (node.equals(goal)) { // what about the start node? it don't have "move" field for substring...
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
					if (!(closedList.containsKey(s.toString()) && !(openList.containsKey(s.toString())))) {
						queue.add(s);
						openList.put(s.toString(), s);
					} else if (openList.containsKey(s.toString())) {
						State old = openList.get(s.toString());
						if (old.getCost() > s.getCost()) {
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
