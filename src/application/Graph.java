
package application;

import java.util.LinkedList;

import javafx.scene.shape.Circle;

public class Graph {
	private Node NList[];
	private Edge EList[];
	private LinkedList<Edge> adj_list[];
	private int NumOfNodes;
	private int NumOfEdges;
	private static int i = 0;
	private static int j = 0;

	public Graph(int v, int e) {
		super();
		NumOfNodes = v;
		NumOfEdges = e;
		NList = new Node[NumOfNodes];
		EList = new Edge[NumOfEdges];
		adj_list = new LinkedList[NumOfNodes];
		i=0;
		j=0;
	}

	public void addNode(Node v) {
		NList[i] = v;
		adj_list[i] = new LinkedList();
		adj_list[i++].addFirst(new Edge(v, v, 0));
	}

	public void addEdge(Edge e) {
		EList[j++] = e;

		adj_list[e.getStart().getId()].addLast(e);
	}

	public int getNumOfVerticies() {
		return NumOfNodes;
	}

	public int getNumOfEdges() {
		return NumOfEdges;
	}

	public LinkedList<Edge>[] getAdj_list() {
		return adj_list;
	}

	public void setAdj_list(LinkedList<Edge>[] adj_list) {
		this.adj_list = adj_list;
	}

	public Node[] getVList() {
		return NList;
	}

	public void setVList(Node[] vList) {
		NList = vList;
	}

	public Edge[] getEList() {
		return EList;
	}

	public void setEList(Edge[] eList) {
		EList = eList;
	}
}
