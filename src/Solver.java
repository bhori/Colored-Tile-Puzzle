
public class Solver {
	private search_algorithm algo;
	
	public Solver(String algo) {
		switch (algo) {
		case "BFS":
			this.algo = new BFS_search();
			break;
		case "DFID":
			this.algo = new DFID_search();
			break;
		case "A*":
			this.algo = new A_star_search();
			break;
		case "IDA*":
			this.algo = new IDA_star_search();
			break;
		case "DFBnB":
			this.algo = new DFBnB_search();
			break;
		default:
			break;
		}
	}
	
	public State solve(State start, State goal){
		return algo.solve(start, goal);
	}

}
