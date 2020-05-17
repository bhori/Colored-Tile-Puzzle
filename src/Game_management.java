import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game_management {

	static private int[] row = { 0, -1, 0, 1 };
	static private int[] col = { -1, 0, 1, 0 };

	public static void saveToFile(String path, String num, String cost, String time) throws IOException {

		try {
			PrintWriter pw = new PrintWriter("output.txt");
			pw.write(path + "\n" + num + "\n" + cost + "\n" + time);
			pw.close();
		} catch (IOException e) {
			throw new IOException("ERR:can't write in file: saveToFile");
		}

	}

	public static String BFS(State start, State goal) throws IOException {
		long startTime = System.nanoTime();
		int count = 0;
//		Queue L = new 
		Queue<State> q = new LinkedList<State>();
//		Hashtable<Integer, State> h = new Hashtable<Integer, State>();
		q.add(start);
//		h.put(start.getId(), start);
//		Hashtable<Integer, State> c = new Hashtable<Integer, State>();
		while (!(q.isEmpty())) {
			State node = q.remove();
//			c.put(node.getId(), node);
			count++;
			for (int i = 0; i < 4; i++) {
				if (node.IsLegal(node.getX() + row[i], node.getY() + col[i])) {
					State s = new State(node);
					count++;
					s.setParent(node);
					if (s.equals(node.getParent()))
						continue;
					int x1 = node.getX();
					int y1 = node.getY();
					int x2 = x1 + row[i];
					int y2 = y1 + col[i];
					if (i == 0) // should to improve this four conditions!!
						s.setMove(s.getCoordinate(x2, y2) + "R-");
					if (i == 1)
						s.setMove(s.getCoordinate(x2, y2) + "D-");
					if (i == 2)
						s.setMove(s.getCoordinate(x2, y2) + "L-");
					if (i == 3)
						s.setMove(s.getCoordinate(x2, y2) + "U-");
					if (s.isRed(x2, y2)) {
						s.setCost(node.getCost() + 30);
					} else {
						s.setCost(node.getCost() + 1);
					}
					s.setCoordinate(x1, y1, x2, y2);
//					if (!(c.containsKey(s.getId()) && !(h.containsKey(s.getId())))) {
						if (s.equals(goal)) {
							s.setMove(s.getMove().substring(0, 2));
//						return s.path()+", count: "+count+", cost: "+s.getCost();
							String path = s.path();
							String num = "Num: " + count;
							String value = "Cost: " + s.getCost();
							long endTime = System.nanoTime();
							long totalTime = endTime - startTime;

							saveToFile(path, num, value, "" + totalTime);
							return null;
						}
//					}
					q.add(s);
//					h.put(s.getId(), s);
				}
			}
		}
		String path = "no path";
		String num = "Num: " + count;
		saveToFile(path, num, "", "");
		return "no path";
	}

	public static void main(String[] args) throws IOException {
		boolean withOpen = false, withTime = false, blacks = false, reds = false;
		Scanner in = new Scanner(new FileReader("input.txt")); // maybe i need to write "/input.txt" with "/"
		String algo = in.nextLine();
		String time = in.nextLine();
		String open = in.nextLine();
		String dimension = in.nextLine();
		String black = in.nextLine();
		String red = in.nextLine();
		if (time.contains("with"))
			withTime = true;
		if (open.contains("with"))
			withOpen = true;
		int n = Integer.parseInt(dimension.substring(0, dimension.indexOf('x')));
		int m = Integer.parseInt(dimension.substring(dimension.indexOf('x') + 1));
		ArrayList<String> black2 = new ArrayList<String>();
		ArrayList<Integer> black3 = new ArrayList<Integer>();
		if (black.length() > 7) {
			blacks = true;
			black = black.substring(7);
			black2 = new ArrayList<String>(Arrays.asList(black.split(",")));
			for (String string : black2) {
				black3.add(Integer.parseInt(string));
			}
			System.out.println(black3.toString());
		}
		ArrayList<String> red2 = new ArrayList<String>();
		ArrayList<Integer> red3 = new ArrayList<Integer>();
		if (red.length() > 5) {
			reds = true;
			red = red.substring(5);
			red2 = new ArrayList<String>(Arrays.asList(red.split(",")));
			for (String string : red2) {
				red3.add(Integer.parseInt(string));
			}
			System.out.println(red3.toString());
//			red3 = new ArrayList<String>(Arrays.asList(Integer.parseInt(red.split(","))));
//			System.out.println(red2);
		}
//		System.out.println(red);
		String content = "";
		while (in.hasNextLine()) {
			content += in.nextLine();
			if (in.hasNextLine())
				content += ",";
		}
		in.close();
		String[] s = content.split(",");
		int[] nums = new int[s.length];
		Node[] nodes = new Node[s.length];
		for (int i = 0; i < nums.length; i++) {
			if (s[i].equals("_")) {
				nums[i] = 0; // value 0 for the empty place ("_")
				continue;
			}
			nums[i] = Integer.parseInt(s[i]);
		}
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == -1) {
				nodes[i] = new Node(-1, '_');
			} else {
				if (blacks && black2.contains("" + nums[i])) {
					nodes[i] = new Node(nums[i], 'b');
				} else if (reds && red2.contains("" + nums[i])) {
					nodes[i] = new Node(nums[i], 'r');
				} else {
					nodes[i] = new Node(nums[i], 'g');
				}
			}
		}
		State start = new State(n, m, nums, black3, red3);
		State goal = new State(n, m);
		System.out.println(start);
//		System.out.println(start.IsLegal(2, 4));
//		System.out.println(start.allIsLegal(1, 3));
		System.out.println();
		System.out.println(goal);
		System.out.println(BFS(start, goal));
	}

}
