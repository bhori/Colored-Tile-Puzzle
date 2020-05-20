import java.util.Comparator;

public class State_Comperator implements Comparator<State>{

	@Override
	public int compare(State s1, State s2) {
		int f1 = s1.getCost()+s1.heuristic();
		int f2 = s2.getCost()+s2.heuristic();
		int diff = f1-f2;
		return diff;
	}

}
