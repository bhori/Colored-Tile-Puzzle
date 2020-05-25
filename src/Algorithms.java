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
//					s.setCoordinate(x1, y1, x2, y2);
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

	public static State IDA_Star(State start, State goal) {
		Stack<State> stack = new Stack<State>();
		Hashtable<String, State> h = new Hashtable<String, State>();
		int threshold = start.heuristic();
		int count = 1;
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
						int x1 = node.getX();
						int y1 = node.getY();
						int x2 = x1 + row[i];
						int y2 = y1 + col[i];
						if (node.IsLegal(x2, y2) && !(node.IsParent(x2, y2))) {
							State s = new State(node);
//							s.setCoordinate(x1, y1, x2, y2);
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
//							int f = s.getCost()+s.heuristic();
							if (s.f() > threshold) {
								min = Math.min(min, s.f());
								continue;
							}
							if (h.containsKey(s.toString())) {
								State old = h.get(s.toString());
								if (old.getOut() == 1) {
									continue;
								} else {
									if (old.f() > s.f()) {
//										stack.pop();
										removeFromStack(stack, old);
										h.remove(old.toString());
									} else {
										continue;
									}
								}
							}
							if (s.equals(goal)) {
								String move = s.getMove();
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
		start.setMove("no path");
		start.setCount(count);
		return start;
	}

	public static void removeFromStack(Stack<State> stack, State state) {
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
//						s.setCoordinate(x1, y1, x2, y2);
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
//				Iterator<State> iterChild = children.iterator();
//				while (iterChild.hasNext()) {
//					State child = iterChild.next();
//					if (child.f() >= t) {
//						iterChild.remove(); // Dont sure about that!!
//					}
//				}
				for (int i=0;i<children.size();i++) {
					State state = children.get(i);
					if (state.f() >= t) {
//						int i = children.indexOf(state);
						while (i < children.size())
							children.remove(i);
					} else if (h.containsKey(state.toString())) {
						State old = h.get(state.toString());
						if (old.getOut() == 1) {
							children.remove(state);
						} else {
							if (old.f() <= state.f()) {
								children.remove(state);
							} else {
								removeFromStack(stack, old);
								h.remove(old.toString());
							}
						}
					} else if (state.equals(goal)) {
						t = state.f();
						String move = state.getMove();
						move = move.substring(0, move.indexOf('-'));
						state.setMove(move);
						state.setCount(count);
						result = state;
//						int i = children.indexOf(state);
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
//			start.setCount(count);
			result = start;
		}
		result.setCount(count);
		return result;
	}

}
