import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class represents the game Colored-NxM-tile puzzle, this game has an NxM board with
 * numbers from 1 to N * M-1 and one blank, each non-empty tile has a color - red, green or black.
 * The tiles are arranged in some initial order and the goal in this game is to find the cheapest number of actions that result from the initial order to the final arrangement,
 * in the goal state all numbers are arranged from 1 to N * M-1 from left to right and bottom-up when the empty tile is in the lower right corner of the board.
 * Black tiles cannot be moved, sliding red tiles cost 30, and green tiles cost 1.
 * @author Ori Ben-Hamo
 *
 */
public class Ex1 {
	static boolean withOpen = false, withTime = false;
	static String algo;

	/**
	 * Reads the input of the game from a file.
	 * @return the initial state of the game.
	 * @throws FileNotFoundException
	 */
	private static State readInput() throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader("input.txt"));
		algo = in.nextLine();
		String time = in.nextLine();
		String open = in.nextLine();
		String dimension = in.nextLine();
		String black = in.nextLine();
		String red = in.nextLine();
		if (time.equals("with time"))
			withTime = true;
		if (open.equals("with open"))
			withOpen = true;
		int rowsNum = Integer.parseInt(dimension.substring(0, dimension.indexOf('x')));
		int columnsNum = Integer.parseInt(dimension.substring(dimension.indexOf('x') + 1));
		ArrayList<String> strBlack = new ArrayList<String>();
		ArrayList<Integer> blackList = new ArrayList<Integer>();
		if (black.length() > 7) { //Checks if the list of blacks is not empty, assume there is a space after "Black:" and before the beginning of the list.
			black = black.substring(7);
			strBlack = new ArrayList<String>(Arrays.asList(black.split(",")));
			for (String string : strBlack) 
				blackList.add(Integer.parseInt(string));
		}
		ArrayList<String> strRed = new ArrayList<String>();
		ArrayList<Integer> redList = new ArrayList<Integer>();
		if (red.length() > 5) { //Checks if the list of reds is not empty, assume there is a space after "Red:" and before the beginning of the list.
			red = red.substring(5);
			strRed = new ArrayList<String>(Arrays.asList(red.split(",")));
			for (String string : strRed) 
				redList.add(Integer.parseInt(string));
		}
		String [][] strBoard = new String[rowsNum][columnsNum];
		for (int i = 0; i < rowsNum; i++) {
			strBoard[i]=in.nextLine().split(",");
		}
		in.close();
		int [][] board = new int [rowsNum][columnsNum];
		for (int i = 0; i < rowsNum; i++) {
			for (int j = 0; j < columnsNum; j++) {
				if (strBoard[i][j].equals("_")) {
					board[i][j] = 0; // value 0 for the empty place ("_")
					continue;
				}
				board[i][j] = Integer.parseInt(strBoard[i][j]);
			}
			
		}
		State start = new State(rowsNum, columnsNum, board, blackList, redList); //Creates the initial state
		return start;
	}
	
	/**
	 * Writes the output to a file.
	 * @param summary summary of the results of the game.
	 * @throws IOException
	 */
	private static void saveToFile(String summary) throws IOException {
		try {
			PrintWriter pw = new PrintWriter("output.txt");
			pw.write(summary);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		State start = readInput();
		State goal = start.goal();
		Solver solver = new Solver(algo);
		SearchInfo info = solver.solve(start, goal, withOpen);
		saveToFile(info.summary(withTime));
	}
}
