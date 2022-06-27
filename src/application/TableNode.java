package application;

public class TableNode implements Comparable<TableNode> {
	private Node City;
	private boolean Known;
	private int Distance;
	private Node Previous;

	public TableNode(Node city, int distance) {
		super();
		City = city;
		Distance = distance;
	}
	
	public TableNode(Node city, int distance , boolean x) {
		super();
		City = city;
		Distance = distance;
		Known = x;
	}
	
	public TableNode(Node city) {
		super();
		City = city;
	}

	public Node getV() {
		return City;
	}

	public boolean isKnown() {
		return Known;
	}

	public void setKnown(boolean known) {
		Known = known;
	}

	public int getDistance() {
		return Distance;
	}

	public void setDistance(int distance) {
		Distance = distance;
	}

	public Node getPrevious() {
		return Previous;
	}

	public void setPrevious(Node previous) {
		Previous = previous;
	}

	@Override
	public int compareTo(TableNode t) {
		if (this.getDistance() > t.getDistance()) {
			return 1;
		} else if (this.getDistance() < t.getDistance()) {
			return -1;
		}
		return 0;
	}
}
