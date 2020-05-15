import java.util.ArrayList;
import java.util.Arrays;

public class State {
	private int[][] mat;
	private int n;
	private int m;
	private int x;
	private int y;
	private ArrayList<Integer> black;
	private ArrayList<Integer> red;
	private int[] row = { 0, -1, 0, 1 };
	private int[] col = { -1, 0, 1, 0 };

	/** Constructor for the start state */
	public State(int n, int m, int[] start, ArrayList<Integer> black, ArrayList<Integer> red) {
		mat = new int[n][m];
		this.n = n;
		this.m = m;
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
	public State(State other) {
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
	}

	/** Constructor for the goal state */
	public State(int n, int m) {
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

	public int[][] getMat() {
		return mat;
	}

	public ArrayList<Integer> getBlack() {
		return black;
	}

	public ArrayList<Integer> getRed() {
		return red;
	}
	
	public void setCoordinate(int x1, int y1, int x2, int y2) {
		int tmp=mat[x1][y1];
		mat[x1][y1]=mat[x2][y2];
		mat[x2][y2]=tmp;
	}

}
