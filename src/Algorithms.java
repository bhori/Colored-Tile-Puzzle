
public class Algorithms {
	private int[] row = { 0, -1, 0, 1 };
	private int[] col = { -1, 0, 1, 0 };


	
	public String BFS(State start, State goal){
//		Queue L = new 
		for (int i = 0; i < 4; i++) {
			if(start.IsLegal(start.getX()+row[i], start.getY()+col[i])) {
				State s = new State(start);
				int x1=start.getX();
				int y1= start.getY();
				s.setCoordinate(x1, y1, x1+row[i], y1+col[i]);
			}
		}
		return "";
	}

}
