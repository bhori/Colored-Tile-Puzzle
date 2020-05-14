
public class Node {
	private int num;
	private char color;

	Node(int num, char color) {
		this.num = num;
		this.color = color;
	}
	
	Node(Node n) {
		this.num = n.getNum();
		this.color = n.getColor();
	}

	public int getNum() {
		return num;
	}

//	public void setNum(int num) {
//		this.num = num;
//	}

	public char getColor() {
		return color;
	}
	public String toString() {
		return "("+num+","+color+")";
	}

//	public void setColor(char color) {
//		this.color = color;
//	}
	
	
}
