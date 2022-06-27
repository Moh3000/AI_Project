package application;

public class Edge {
	
	Node start;
	Node end;
	int distance;
	
	public Edge(Node start, Node end, int distance) {
		super();
		this.start = start;
		this.end = end;
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}
	
	
	
}