import java.util.ArrayList;
import java.util.Arrays;

public class State {
	private int[][] board; //Represents the board for this state
	private int n;
	private int m;
	private int x; //Holds the x-coordinate (row) of the empty place ('_'/0)
	private int y; //Holds the y-coordinate (col) of the empty place ('_'/0)
	private ArrayList<Integer> black;
	private ArrayList<Integer> red;
//	private Hashtable<Integer, Boolean> black;
//	private Hashtable<Integer, Boolean> red;
	private State parent;
	private String move; //The step is taken to reach this state
	private int moveID=-1; //Value between 0-3, Indicates the direction of the step that created this state (0-left,1-up,2-right,3-down), useful for A*, IDA* and DFBnB for local priority ordering
	private int count; //Holds the amount of vertices created up to this state
	private int cost; //Holds the required cost up to this state
	int iteration; //Holds the iteration in the algorithm that created this state, is used to sort vertices with equal f values, useful for A*, IDA* and DFBnB for priority ordering
	int out; //for IDA* and DFBnB, 1 marks "out" 

//	/** Constructor for the initial state */
	/**
	 * Constructor for the initial state
	 * @param n the number of rows in the board.
	 * @param m the number of columns in the board.
	 * @param start list of the numbers by their location in the board.
	 * @param black list of the black tiles in the board.
	 * @param red list of the red tiles in the board.
	 */
	public State(int n, int m, int[] start, ArrayList<Integer> black, ArrayList<Integer> red) {
		board = new int[n][m];
		this.n = n;
		this.m = m;
		parent = null;
		move = "";
		moveID=-1; // maybe -1?
		cost = 0;
		count = 1; // should be 0??
		iteration=0;
		out=0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				board[i][j] = start[i * m + j];
				board[i][j] = start[i * m + j];
				if (board[i][j] == 0) {
					x = i;
					y = j;
				}
			}
		}
		this.black = new ArrayList<Integer>();
		for (Integer num : black) {
			this.black.add(num);
		}
		this.red = new ArrayList<Integer>();
		for (Integer num : red) {
			this.red.add(num);
		}
	}

//	/** Copy constructor */
	/**
	 * Copy constructor
	 * @param other - the copied state.
	 */
	public State(State other) { // Init parent?
		parent = other.getParent();
		move = ""; // don't sure about that!!
		n = other.getN();
		m = other.getM();
		x = other.getX();
		y = other.getY();
		out=0;
		board = new int[n][m];
		int[][] temp = other.getBoard();
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				this.board[i][j] = temp[i][j];
			}
		}
		board[x][y] = 0;
		this.black = new ArrayList<Integer>();
		for (Integer num : other.getBlack()) {
			this.black.add(num);
		}
		this.red = new ArrayList<Integer>();
		for (Integer num : other.getRed()) {
			this.red.add(num);
		}
	}

//	/** Constructor for the goal state */
	/**
	 * Constructor for the goal state
	 * @param n - the number of rows in the board.
	 * @param m - the number of columns in the board.
	 */
	public State(int n, int m) {
		parent = null;
		move = "";
		board = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				board[i][j] = i * m + j + 1;
				board[i][j] = i * m + j + 1; // why two times??
			}
		}
		board[n - 1][m - 1] = 0;
		x = n - 1;
		y = m - 1;
	}
	
	public State cutOff(int n, int m) {
		State cutOff = new State(n,m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				cutOff.setCoordinate(i, j, 0);
//				cutOff.getboard()[i][j]=0;		
//				board[i][j] = i * m + j + 1;
			}
		}
		return cutOff;
	}
	
	public State fail(int n, int m) {
		State fail = new State(n,m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				fail.setCoordinate(i, j, -1);
//				board[i][j] = i * m + j + 1;
			}
		}
		return fail;
	}
	
	/** Returns a string representing the board of this state */
	public String toString() {
		String s = "";
		for (int[] row : board)
			s += Arrays.toString(row) + "\n";
		return s;
	}
	
	/**
	 * Checks whether the operator is valid, which means it does not exceed out of board limits and does not move a black tile.
	 * @param row - the row is given by the operator.
	 * @param column - the column is given by the operator.
	 * @return true if the operator is valid, else return false.
	 */
	public boolean IsLegal(int row, int column) {
		if (!(row >= 0 && row < n && column >= 0 && column < m))
			return false;
		if (black.contains(board[row][column]))
			return false;
		return true;
	}
	
	/**
	 * Checks whether the operator creates the parent state.
	 * @param row - the row is given by the operator.
	 * @param column - the column is given by the operator.
	 * @return 'true' if the operator creates the parent state, else return false.
	 */
	public boolean IsParent(int row, int column) {
		int x =getX();
		int y= getY();
		replace(x, y, row, column);
		if (equals(getParent())) {
			replace(row, column, x, y);
			return true;
		}
		replace(row, column, x, y);
		return false;
	}
	
	/**
	 * Checks whether all black tiles are in the right place, otherwise there is no solution to the game for this state.
	 * @return true if all black tiles are in the right place, else return false.
	 */
	public boolean blackInPlace() {
		if(black.size()==0)
			return true;
		for (Integer tile_number : black) {
			int targetX = (tile_number - 1) / m; // expected x-coordinate (row)
            int targetY = (tile_number - 1) % m; // expected y-coordinate (col)
            if(board[targetX][targetY]!=tile_number)
            	return false;
		}
		return true;
	}
	
	/**
	 * @return Number of rows in the board.
	 */
	public int getN() {
		return n;
	}	
	
	/**
	 * @return Number of columns in the board
	 */
	public int getM() {
		return m;
	}
	
	/**
	 * @return the x-coordinate (row) of the empty place ('_'/0).
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return the y-coordinate (column) of the empty place ('_'/0).
	 */
	public int getY() {
		return y;
	}
	
//	/**
//	 * @param x - the new x-coordinate (row) of the empty place ('_'/0).
//	 */
//	public void setX(int x) {
//		this.x = x;
//	}
//	
//	/**
//	 * @param y - the new y-coordinate (column) of the empty place ('_'/0).
//	 */
//	public void setY(int y) {
//		this.y = y;
//	}
	
	/**
	 * @return the board of this state.
	 */
	public int[][] getBoard() {
		return board;
	}
	
	/**
	 * @return the list of the black tiles.
	 */
	public ArrayList<Integer> getBlack() {
		return black;
	}
	
	/**
	 * @return the list of the red tiles.
	 */
	public ArrayList<Integer> getRed() {
		return red;
	}
	
	/**
	 * @return the parent of this state (the state in which the operator operated to reach this state).
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * Updates the parent of this state (the state in which the operator operated to reach this state).
	 * @param parent - the parent of this state.
	 */
	public void setParent(State parent) {
		this.parent = parent;
	}

	/**
	 * @return the step is taken to reach this state.
	 */
	public String getMove() {
		return move;
	}

	/**
	 * Updates the step is taken to reach this state.
	 * @param move
	 */
	public void setMove(String move) {
		this.move = move;
	}

	public int getMoveID() {
		return moveID;
	}

	public void setMoveID(int moveID) {
		this.moveID = moveID;
	}

	/**
	 * @return the cost of the path up to this state.
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Updates the cost of the path up to this state.
	 * @param cost - the cost of the path up to this state.
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * @return the number of vertices produced up to this state.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Updates the number of vertices produced up to this state.
	 * @param count - the number of vertices produced up to this state.
	 */
	public void setCount(int count) {
		this.count = count;
	}

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	public int getOut() {
		return out;
	}

	public void setOut(int out) {
		this.out = out;
	}

	/**
	 * Return the number in the board that is in the place of the received coordinates.
	 * @param x - the x-coordinate (row).
	 * @param y - the y-coordinate (column).
	 * @return the number in the board that is in the place of the received coordinates.
	 */
	public int getCoordinate(int x, int y) {
		return board[x][y];
	}

	/**
	 * Updates the value found in the board in the received coordinates with the given value.
	 * @param x - the x-coordinate (row).
	 * @param y - the y-coordinate (column).
	 * @param value - the new value in the board in the given coordinates. 
	 */
	public void setCoordinate(int x, int y, int value) { // maybe it is better to overload setCoordinate..
		board[x][y] = value;
	}

	/**
	 * Replaces the value in the board at position x1,y1 with the value at position x2,y2.
	 * @param x1 - the x-coordinate of the first position (row).
	 * @param y1 - the y-coordinate of the first position (column).
	 * @param x2 - the x-coordinate of the second position (row).
	 * @param y2 - the y-coordinate of the second position (column).
	 */
	public void replace(int x1, int y1, int x2, int y2) {
		int tmp = board[x1][y1];
		board[x1][y1] = board[x2][y2];
		board[x2][y2] = tmp;
		x = x2;
		y = y2;
	}
	
	/**
	 * Checks whether this tile is red.
	 * @param x - the x-coordinate (row) of tile.
	 * @param y - the y-coordinate (column) of tile.
	 * @return true if board[x][y] is red, otherwise return false
	 */
	public boolean isRed(int x, int y) {
		if (red.contains(board[x][y]))
			return true;
		return false;
	}
	
	/**
	 * Checks whether this is the goal state.
	 * @param goal - the goal state.
	 * @return true if this is the goal state, else return false.
	 */
	public boolean isGoal(State goal) {
		return equals(goal);
	}
	
	/**
	 * Checks whether the boards of this state and 's' are equal.
	 * @param s - the state compared to this state.
	 * @return true if the boards are equal, otherwise return false
	 */
	public boolean equals(State s) {
		if (s == null)
			return false; // what if "this" is null too???
		int[][] tmp = s.getBoard();
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[0].length; j++) {
				if (board[i][j] != tmp[i][j])
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Computes the path from the start state to this state.
	 * @return the path from the start state to this state.
	 */
	public String path() {
		if (parent == null)
			return move;
		return parent.path() + move;
	}
	
	/**
	 * Computes the heuristic function of the state
	 * @return the value of the heuristic function
	 */
	public int heuristic() {	// what to do with the black tiles? infinite value?
		int manhattanDistanceSum = 0;
		for (int x = 0; x < n; x++) // x-dimension, traversing rows (i)
		    for (int y = 0; y < m; y++) { // y-dimension, traversing cols (j)
		        int value = board[x][y]; // tiles array contains board elements
		        if (value != 0 && !(black.contains(value))) { // we don't compute MD for element 0
		            int targetX = (value - 1) / m; // expected x-coordinate (row)
		            int targetY = (value - 1) % m; // expected y-coordinate (col)
		            int dx = x - targetX; // x-distance to expected coordinate
		            int dy = y - targetY; // y-distance to expected coordinate
//		            int targetX;
//		            int targetY;
//					if(value%m==0) {
//						targetX = value/m -1;
//						targetY = m-1;
//					} else {
//						targetX = value/m;
//						targetY = value%m -1;
//					}
//					 int dx = x - targetX; // x-distance to expected coordinate
//			         int dy = y - targetY; // y-distance to expected coordinate
		            if(isRed(x, y)) {
		            	dx=dx*30;
		            	dy=dy*30;	
		            }
		            manhattanDistanceSum += Math.abs(dx) + Math.abs(dy); 
		        } 
		    }
		return manhattanDistanceSum;
	}
	
	/**
	 * Computes the 'f' function of the state which is the sum of the heuristic function and the cost till this state
	 * @return the value of the 'f' function
	 */
	public int f() {
		int f = getCost()+heuristic();
		return f;
	}

}
