import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class State {
	private int[][] mat; //Represents the board for this state
	private int n;
	private int m;
	private int x; //Holds the x-coordinate (row) of the empty place ('_'/0)
	private int y; //Holds the y-coordinate (col) of the empty place ('_'/0)
	private ArrayList<Integer> black;
	private ArrayList<Integer> red;
//	private Hashtable<Integer, Boolean> black;
//	private Hashtable<Integer, Boolean> red;
	private State parent;
	private String move; //The step taken to reach this state
	private int moveID; //Value between 0-3, Indicates the direction of the step that created this state (0-left,1-up,2-right,3-down), useful for A*, IDA* and DFBnB for local priority ordering
	private int count; //Holds the amount of vertices created up to this state
	private int cost; //Holds the required cost up to this state
	int iteration; //Holds the iteration in the algorithm that created this state, is used to sort vertices with equal f values, useful for A*, IDA* and DFBnB for priority ordering
	int out; //for IDA* and DFBnB, 1 marks "out" 
	private int[] row = { 0, -1, 0, 1 };
	private int[] col = { -1, 0, 1, 0 };

	/** Constructor for the initial state */
	public State(int n, int m, int[] start, ArrayList<Integer> black, ArrayList<Integer> red) {
		mat = new int[n][m];
		this.n = n;
		this.m = m;
		parent = null;
		move = "";
		moveID=0; // maybe -1?
		cost = 0;
		count = 1; // should be 0??
		iteration=0;
		out=0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				mat[i][j] = start[i * m + j];
				mat[i][j] = start[i * m + j];
				if (mat[i][j] == 0) {
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

	/** Copy constructor */
	public State(State other) { // Init parent?
		parent = other.getParent();
		move = ""; // don't sure about that!!
		n = other.getN();
		m = other.getM();
		x = other.getX();
		y = other.getY();
		out=out;
		mat = new int[n][m];
		int[][] temp = other.getMat();
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				this.mat[i][j] = temp[i][j];
			}
		}
		mat[x][y] = 0;
		this.black = new ArrayList<Integer>();
		for (Integer num : other.getBlack()) {
			this.black.add(num);
		}
		this.red = new ArrayList<Integer>();
		for (Integer num : other.getRed()) {
			this.red.add(num);
		}
	}

	/** Constructor for the goal state */
	public State(int n, int m) {
		parent = null;
		move = "";
		mat = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				mat[i][j] = i * m + j + 1;
				mat[i][j] = i * m + j + 1; // why two times??
			}
		}
		mat[n - 1][m - 1] = 0;
		x = n - 1;
		y = m - 1;
	}
	
	public State cutOff(int n, int m) {
		State cutOff = new State(n,m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				cutOff.setValue(i, j, 0);
//				cutOff.getMat()[i][j]=0;		
//				mat[i][j] = i * m + j + 1;
			}
		}
		return cutOff;
	}
	
	public State fail(int n, int m) {
		State fail = new State(n,m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				fail.setValue(i, j, -1);
//				mat[i][j] = i * m + j + 1;
			}
		}
		return fail;
	}

	public String toString() {
		String s = "";
		for (int[] row : mat)
			s += Arrays.toString(row) + "\n";
		return s;
	}

	public boolean IsLegal(int row, int column) {
		if (!(row >= 0 && row < n && column >= 0 && column < m))
			return false;
		if (black.contains(mat[row][column]))
			return false;
		return true;
	}

	public boolean IsParent(int row, int column) {
		int x =getX();
		int y= getY();
		setCoordinate(x, y, row, column);
		if (equals(getParent())) {
			setCoordinate(row, column, x, y);
			return true;
		}
		setCoordinate(row, column, x, y);
		return false;
	}

//	public boolean allIsLegal(int row, int column) {
//		for (int i = 0; i < 4; i++) {
//			if(!(IsLegal(x+this.row[i], y+col[i])))
//				return false;
//		}
//		return true;
//	}

	public int getN() {
		return n;
	}

	public int getM() {
		return m;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int[][] getMat() {
		return mat;
	}

	public ArrayList<Integer> getBlack() {
		return black;
	}

	public ArrayList<Integer> getRed() {
		return red;
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public int getMoveID() {
		return moveID;
	}

	public void setMoveID(int moveID) {
		this.moveID = moveID;
	}

	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCount() {
		return count;
	}

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

	public int getCoordinate(int x, int y) {
		return mat[x][y];
	}

	public void setCoordinate(int x1, int y1, int x2, int y2) {
		int tmp = mat[x1][y1];
		mat[x1][y1] = mat[x2][y2];
		mat[x2][y2] = tmp;
		x = x2;
		y = y2;
	}
	
	public void setValue(int x, int y, int value) { // maybe it is better to overload setCoordinate..
		mat[x][y] = value;
	}

	public boolean isRed(int x, int y) {
		if (red.contains(mat[x][y]))
			return true;
		return false;
	}

	public boolean equals(State s) {
		if (s == null)
			return false; // what if "this" is null too???
		int[][] tmp = s.getMat();
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[0].length; j++) {
				if (mat[i][j] != tmp[i][j])
					return false;
			}
		}
		return true;
	}

	public String path() {
		if (parent == null)
			return move;
		return parent.path() + move;
	}
	
	public int heuristic() {	// what to do with the black tiles? infinite value?
		int manhattanDistanceSum = 0;
		for (int x = 0; x < n; x++) // x-dimension, traversing rows (i)
		    for (int y = 0; y < m; y++) { // y-dimension, traversing cols (j)
		        int value = mat[x][y]; // tiles array contains board elements
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
	
	public int f() {
		int f = getCost()+heuristic();
		return f;
	}

}
