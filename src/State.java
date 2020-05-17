import java.util.ArrayList;
import java.util.Arrays;

public class State {
	private int[][] mat;
//	private int id;
	private int n;
	private int m;
	private int x;
	private int y;
	private ArrayList<Integer> black;
	private ArrayList<Integer> red;
	private State parent;
	private String move;
	private int cost;
	private int[] row = { 0, -1, 0, 1 };
	private int[] col = { -1, 0, 1, 0 };
//	public static int size=0;

	/** Constructor for the start state */
	public State(int n, int m, int[] start, ArrayList<Integer> black, ArrayList<Integer> red) {
//		id=++size;
		mat = new int[n][m];
		this.n = n;
		this.m = m;
		parent = null;
		move="";
		cost=0;
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
		System.out.println(this.black.toString());
		System.out.println(this.red.toString());
	}
	
//	/** Constructor for the middle state */
//	public State(int n, int m, int[][] mat, int x, int y, ArrayList<Integer> black, ArrayList<Integer> red) {
//		this.n=n;
//		this.m=m;
//		this.mat = new int[n][m];
//		for (int i = 0; i < mat.length; i++) {
//			for (int j = 0; j < mat[0].length; j++) {
//				this.mat[i][j]=mat[i][j];
//			}
//		}
//	}
	
	/** Copy constructor */
	public State(State other) { // Init parent?
//		id=++size; //maybe it's not good (design)
		parent=other.getParent();
		move=""; // don't sure about that!!
		n=other.getN();
		m=other.getM();
		x=other.getX();
		y=other.getY();
		mat = new int [n][m];
		int [][] temp = other.getMat();
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				this.mat[i][j]=temp[i][j];
			}
		}
		mat[x][y]=0;
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
		move="";
		mat = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				mat[i][j] = i * m + j + 1;
				mat[i][j] = i * m + j + 1;
			}
		}
		mat[n - 1][m - 1] = 0;
		x = n - 1;
		y = m - 1;
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
	
//	public int getId() {
//		return id;
//	}

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
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCoordinate(int x, int y) {
		return mat[x][y];
	}

	public void setCoordinate(int x1, int y1, int x2, int y2) {
		int tmp=mat[x1][y1];
		mat[x1][y1]=mat[x2][y2];
		mat[x2][y2]=tmp;
		x=x2;
		y=y2;
	}
	
	public boolean isRed(int x, int y) {
		if (red.contains(mat[x][y]))
			return true;
		return false;
	}
	
	public boolean equals(State s) {
		if(s==null)
			return false; //what if "this" is null too???
		int [][] tmp = s.getMat();
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[0].length; j++) {
				if(mat[i][j]!=tmp[i][j])
					return false;
			}
		}
		return true;
	}

	public String path() {
		if(parent==null)
			return move;
		return parent.path()+move;
	}

}
