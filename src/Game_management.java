import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game_management {

	public static void main(String[] args) throws FileNotFoundException {
		boolean withOpen = false, withTime = false, blacks = false, reds = false;
//		boolean blacks = false;
//		boolean reds = false;
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
//		System.out.println(n + "\t " + m);
//		System.out.println(algo);
//		System.out.println(time);
//		System.out.println(open);
//		System.out.println(dimension);
//		System.out.println(black);
		ArrayList<String> black2 = new ArrayList<String>();
		if (black.length() > 7) {
			blacks = true;
			black = black.substring(7);
			black2 = new ArrayList<String>(Arrays.asList(black.split(",")));
			System.out.println(black2);
		}
//		System.out.println(black.length());
//		System.out.println(black);
//		System.out.println(red);
		ArrayList<String> red2 = new ArrayList<String>();
		if (red.length() > 5) {
			reds = true;
			red = red.substring(5);
			red2 = new ArrayList<String>(Arrays.asList(red.split(",")));
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
//		System.out.println(content);
		String[] s = content.split(",");
//		System.out.println(Arrays.toString(s));
//		System.out.println(s[0]);
		int[] nums = new int[s.length];
		Node[] nodes = new Node[s.length];
		for (int i = 0; i < nums.length; i++) {
			if (s[i].equals("_")) {
				nums[i] = -1;
//				System.out.println(nums[i]);
				continue;
			}
			nums[i] = Integer.parseInt(s[i]);
//			System.out.println(nums[i]);
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
		State start = new State(n, m, nodes);
		System.out.println(start);

	}

}
