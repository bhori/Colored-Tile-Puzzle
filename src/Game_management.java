import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game_management {
	
	static private int[] row = { 0, -1, 0, 1 };
	static private int[] col = { -1, 0, 1, 0 };


	
	public static String BFS(State start, State goal){
//		Queue L = new 
		for (int i = 0; i < 4; i++) {
			if(start.IsLegal(start.getX()+row[i], start.getY()+col[i])) {
				State s = new State(start);
				int x1=start.getX();
				int y1= start.getY();
				s.setCoordinate(x1, y1, x1+row[i], y1+col[i]);
			}
		}
		return "finish";
	}
	

	public static void main(String[] args) throws FileNotFoundException {
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
		System.out.println(BFS(start,goal));
	}

}
