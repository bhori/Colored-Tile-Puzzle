import java.util.Comparator;

/**
 * This class represents a comparator for a comparison of two states.
 * The comparison is done according to several criteria by levels:
 * First, compare with the value of the f() function (Which is the cost up to this state plus the value of the heuristic function of this state),
 * the state with the lower value will appear first.
 * If it is equal then compare by the discovery time of the two states,
 * that is, compares the iterations in which the two states were created,
 * if it is equal compare in the order of the development of the states in the same iteration, the state created first will appear first.
 * @author Ori Ben-Hamo
 *
 */
public class State_Comparator implements Comparator<State> {

	/**
	 * Compare between two states.
	 */
	@Override
	public int compare(State s1, State s2) {
		int f1 = s1.f();
		int f2 = s2.f();
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
