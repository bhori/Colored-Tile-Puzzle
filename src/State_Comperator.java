import java.util.Comparator;

public class State_Comperator implements Comparator<State> {

	@Override
	public int compare(State s1, State s2) {
		int f1 = s1.getCost() + s1.heuristic();
		int f2 = s2.getCost() + s2.heuristic();
		if (f1 != f2) {
			int diff = f1 - f2;
			return diff;
		}else if(s1.getIteration()!=s2.getIteration()) {
			return s1.getIteration()-s2.getIteration();
		}else {
			return s1.getMoveID()-s2.getMoveID();
		}
	}

}
