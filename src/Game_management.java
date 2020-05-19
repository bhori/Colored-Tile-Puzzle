import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game_management {
	private static boolean withOpen = false, withTime = false, blacks = false, reds = false;

	public static String summary(State n, double totalTime) {
		String path = n.path();
		String num = "Num: " + n.getCount();
		String value = "Cost: " + n.getCost();
		String time="";
		if(withTime)
			time = new DecimalFormat("0.000").format(totalTime) + " seconds";
		if (path.equals("no path"))
			return path + "\n" + num;
//		String value = "Cost: " + n.getCost();
		return path + "\n" + num + "\n" + value + "\n"+time;
	}

	public static void saveToFile(String summary) throws IOException {

		try {
			PrintWriter pw = new PrintWriter("output.txt");
			pw.write(summary);
			pw.close();
		} catch (IOException e) {
			throw new IOException("ERR:can't write in file: saveToFile");
		}

	}

	public static String DFID(State start, State goal) throws IOException {
		for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
			Hashtable<Integer, State> h = new Hashtable<Integer, State>();
			String result = limitedDFS(start, goal, depth, h);
		}
		return "no path";
	}

	private static String limitedDFS(State n, State goal, int depth, Hashtable<Integer, State> h) {
		if (n.equals(goal)) {
		}
		return "";
	}

	public static State readInput() throws FileNotFoundException {
//		boolean withOpen = false, withTime = false, blacks = false, reds = false;
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
//			blacks = true;
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
//			reds = true;
			red = red.substring(5);
			red2 = new ArrayList<String>(Arrays.asList(red.split(",")));
			for (String string : red2) {
				red3.add(Integer.parseInt(string));
			}
			System.out.println(red3.toString());
		}
		String content = "";
		while (in.hasNextLine()) {
			content += in.nextLine();
			if (in.hasNextLine())
				content += ",";
		}
		in.close();
		String[] s = content.split(",");
		int[] nums = new int[s.length];
		for (int i = 0; i < nums.length; i++) {
			if (s[i].equals("_")) {
				nums[i] = 0; // value 0 for the empty place ("_")
				continue;
			}
			nums[i] = Integer.parseInt(s[i]);
		}
		State start = new State(n, m, nums, black3, red3);
		return start;
	}

	public static void main(String[] args) throws IOException {
		State start = readInput();
		State goal = new State(start.getN(), start.getM());
		System.out.println(start);
		System.out.println();
		System.out.println(goal);
		/**		should be here switch case for the algorithm	 **/
		long startTime = System.nanoTime();
//		start = Algorithms.BFS(start, goal);
		start = Algorithms.DFID(start, goal);
		System.out.println("success!");
		long endTime = System.nanoTime();
		double totalTime = (endTime - startTime) / 1000000000.0;
		String summary = summary(start, totalTime);
		saveToFile(summary);
	}

}
