import java.util.Arrays;

public class State {
	private Node [][] state;
	private Node [][] goal;
	
	State(int n, int m, Node [] start){
		state = new Node[n][m];
		goal = new Node[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				state[i][j] = new Node(start[i*m+j]);
//				state[i][j] = new Node(start[i*m+j]);
			}
		}
		for (int i = 0; i < start.length; i++) {
			if(start[i].getNum()==-1) {
				goal[n-1][m-1]=new Node(start[i]);
				continue;
			}
			int row = (start[i].getNum()-1)/m;
			int column = (start[i].getNum()-1)%m;
			goal[row][column]=new Node(start[i]);
		}
		
	}
	
//	State(int n, int m){
//		state = new Node[n][m];
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < m; j++) {
//				state[i][j] = new Node(start[i*m+j]);
//			}
//		}
//	}
	
	public String toString() {
		String s1= "";
		String s2= "";
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				s1+=state[i][j].toString();
				s2+=goal[i][j].toString();
			}
			s1+="\n";
			s2+="\n";
		}
		return s1+"\n *** goal ***\n\n"+s2;
	}
}
