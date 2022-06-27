package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class DepthController implements Initializable {

	@FXML
	private Button btExit;

	@FXML
	private ComboBox<Node> cbStart;

	@FXML
	private ComboBox<Node> cbGoal;

	@FXML
	private TextArea tfPath;

	@FXML
	private Label lDistance;

	@FXML
	private TextArea tfGoal;

	@FXML
	private TextArea tfNumber;

	@FXML
	private AnchorPane ap;
	
    @FXML
    private TextArea tfPath1;
    
	@FXML
	private AnchorPane ap1;

	private int flag1;
	private int flagHeuristic=0;
	private int index;

	Node startNode;
	ArrayList<Node> endGoals = new ArrayList<>();
	ArrayList<TableNode> endGoals2 = new ArrayList<>();

	int Hvalues[];
	Node cities[];

	private Stack<TableNode> stack;
	private TableNode arr[];

	Graph g;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		start();
	}

	public void start() {
		Stop[] stops = new Stop[] { new Stop(0, Color.YELLOW), new Stop(1, Color.BLUE) };
		LinearGradient lgcolors = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		BackgroundFill bgfill = new BackgroundFill(lgcolors, CornerRadii.EMPTY, Insets.EMPTY);
		ap.setBackground(new Background(bgfill));
		g = new Graph(20, 72);
		cities = new Node[20];
		Hvalues = new int[20];
		arr = new TableNode[20];
		stack = new Stack<TableNode>();
		flag1 = 0;
		ap1.setVisible(false);
		File f = new File("input1.txt");
		Scanner sc;

		try {
			sc = new Scanner(f);
			int NumOfNodes = 0;
			NumOfNodes = 20;
			String split[];
			for (int i = 0; i < NumOfNodes; i++) {
				System.out.println(i);
				Circle c;
				split = sc.nextLine().split(";");
				cities[i] = new Node(Integer.parseInt(split[0]), split[1], Integer.parseInt(split[2]),
						Integer.parseInt(split[3]));
				cities[i].setImage(new File("location.png"));
				ImageView iv = new ImageView();
				Image image = new Image(cities[i].getImage().toURI().toString());
				iv.setImage(image);

				g.addNode(cities[i]);

				c = new Circle();
				c.setRadius(6);
				c.setLayoutX(Integer.parseInt(split[2]));
				c.setLayoutY(Integer.parseInt(split[3]));

				iv.setLayoutX(Integer.parseInt(split[2]) - 15);
				iv.setLayoutY(Integer.parseInt(split[3]) - 15);

				iv.maxHeight(10);
				iv.maxWidth(10);
				iv.prefHeight(10);
				iv.prefWidth(10);

				c.setFill(Color.GRAY);
				Label l = new Label(split[1]);
				l.setStyle("-fx-font-weight: bold;");
				l.setLayoutY(c.getLayoutY() - 10);
				l.setLayoutX(c.getLayoutX() + 8);
				ap.getChildren().addAll(l, iv);

				Node n2 = cities[i];

				iv.setOnMouseClicked(e -> {
					if (flag1 == 0) {
						File file2 = new File("locationGreen.png");
						Image image2 = new Image(file2.toURI().toString());
						iv.setImage(image2);
						flag1 = 1;
						System.out.println(n2.name);
						startNode = n2;
						cbStart.setValue(n2);
					} else if (flag1 == 1) {
						File file2 = new File("locationRed.png");
						Image image2 = new Image(file2.toURI().toString());
						iv.setImage(image2);
						endGoals.add(n2);
						tfGoal.appendText(n2 + "; ");
					}
				});
			}

			sc.close();

			for (int i = 0; i < 20; i++) {
				cbStart.getItems().addAll(cities[i]);
			}
			try {
				for (int i = 20; i < 40; i++) {
					cbStart.getItems().remove(20);
				}
			} catch (Exception ex) {

			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if(flagHeuristic==1) {				// Car Distance
			System.out.println("flagcar==1");
			index=3;
		}else if(flagHeuristic==0) {		// Walking Distance
			System.out.println("flagwalk==1");
			index=4;
		}

		File f2 = new File("input2.txt");

		try {
			Scanner sc2 = new Scanner(f2);
			String[] split;
			Edge e;
			for (int i = 0; i < 72; i++) {
				split = sc2.nextLine().split(";");
				e = new Edge(cities[Integer.parseInt(split[0])], cities[Integer.parseInt(split[1])],
						Integer.parseInt(split[index]));
				g.addEdge(e);
			}
			sc2.close();
		} catch (Exception ex) {

		}

	}

	@FXML
	void Exit(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void Back(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("Scene.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		Stage stage2 = (Stage) btExit.getScene().getWindow();
		stage2.close();
	}

	int randomCount = 0;

	@FXML
	void RandomGoal(ActionEvent event) {

		if (randomCount == 0) {
			randomCount = (int) (Math.random() * (5) + 1);
			tfNumber.setText(randomCount + "");
		}
		endGoals.clear();
		String temp = "";
		int arr[] = new int[10];
		for (int j = 0; j < 10; j++) {
			arr[j] = -1;
		}

		for (int i = 0; i < randomCount;) {
			int r = (int) (Math.random() * (20));
			int flag = 0;
			if (r == startNode.getId()) {
				flag = 1;
			}
			for (int j = 0; j < 10; j++) {
				if (r == arr[j]) {
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				arr[i] = r;
				endGoals.add(cities[r]);
				i++;
				temp += " " + cities[r] + ";";

			}
		}
		int r = (int) (Math.random() * (20));

		tfGoal.setText(temp);
	}

	@FXML
	void RandomStart(ActionEvent event) {
		int r = (int) (Math.random() * (20));

		cbStart.setValue(cities[r]);
	}

	@FXML
	void RandomCount(ActionEvent event) {
		randomCount = (int) (Math.random() * (5) + 1);
		tfNumber.setText(randomCount + "");

	}

//	@FXML
//	void combGoal(ActionEvent event) {
//		ImageView iv;
//		Image image;
//
//		if (endGoals.get(0) != null) {
//			endGoals.get(0).setImage(new File("location.png"));
//			iv = new ImageView();
//			image = new Image(endGoals.get(0).getImage().toURI().toString());
//			iv.setImage(image);
//			ap.getChildren().add(iv);
//
//			iv.maxHeight(10);
//			iv.maxWidth(10);
//			iv.prefHeight(10);
//			iv.prefWidth(10);
//
//			iv.setLayoutX(endGoals.get(0).getX() - 15);
//			iv.setLayoutY(endGoals.get(0).getY() - 15);
//		}
//
//		endGoals.add(cbGoal.getValue());
//
//		endGoals.get(0).setImage(new File("locationRed.png"));
//		iv = new ImageView();
//		image = new Image(endGoals.get(0).getImage().toURI().toString());
//		iv.setImage(image);
//		ap.getChildren().add(iv);
//
//		iv.maxHeight(10);
//		iv.maxWidth(10);
//		iv.prefHeight(10);
//		iv.prefWidth(10);
//
//		iv.setLayoutX(endGoals.get(0).getX() - 15);
//		iv.setLayoutY(endGoals.get(0).getY() - 15);
//
//	}

	@FXML
	void combStart(ActionEvent event) {

		ImageView iv;
		Image image;
		if (startNode != null) {
			startNode.setImage(new File("location.png"));
			iv = new ImageView();
			image = new Image(startNode.getImage().toURI().toString());
			iv.setImage(image);
			ap.getChildren().add(iv);

			iv.maxHeight(10);
			iv.maxWidth(10);
			iv.prefHeight(10);
			iv.prefWidth(10);

			iv.setLayoutX(startNode.getX() - 15);
			iv.setLayoutY(startNode.getY() - 15);
		}

		startNode = cbStart.getValue();
System.out.println(startNode+"345 startNode");
		startNode.setImage(new File("locationGreen.png"));
		iv = new ImageView();
		image = new Image(startNode.getImage().toURI().toString());
		iv.setImage(image);
		ap.getChildren().add(iv);

		iv.maxHeight(10);
		iv.maxWidth(10);
		iv.prefHeight(10);
		iv.prefWidth(10);

		iv.setLayoutX(startNode.getX() - 15);
		iv.setLayoutY(startNode.getY() - 15);

	}

	Node Goal;

	@FXML
	void Run(ActionEvent event) {

		ap1.setVisible(true);
		int count = 0;

		int loops = endGoals.size();
		System.out.println(loops+"371 loops");
		int distance = 0;
		ArrayList<Node> endGoalsRed = new ArrayList<>();

		for (int y = 0; y < loops; y++) {
			endGoalsRed.add(endGoals.get(y));
			System.out.println(endGoals.get(y)+"endGoals.get(y)           377");
		}

		System.out.println("start is: " + startNode);

		System.out.println("\n the remaining goals are \n");
		for (int u = 0; u < endGoals2.size(); u++) {
			System.out.println(endGoals2.get(u).getV());
		}

		System.out.println("--------");

		stack.clear();
		stack.push(new TableNode(startNode, 0));
		for (int i = 0; i < 20; i++) {
			arr[i] = new TableNode(cities[i], 0, false);
			System.out.println(arr[i].toString()+"arr[i]        394");
		}

		for (int i = 0; i < 20; i++)
			System.out.println(arr[i].getV().getName() + " " + arr[i].getDistance() + arr[i].isKnown());

		while (!stack.isEmpty()) {
			int ind = stack.pop().getV().getId();
			arr[ind].setKnown(true);
			tfPath1.appendText(" => "+arr[ind].getV()+"\n");

			System.out.println(arr[ind].getV().getName() + " " + arr[ind].getDistance());
			System.out.println();
			for (int l = 0; l < endGoals.size(); l++) {
				if (endGoals.get(l).getId() == (arr[ind].getV().getId())) {
					System.out.println(endGoals.get(l).getId()+arr[ind].getV().getId()+"arr[ind].getV().getId()      endGoals.get(l).getId()     409");
					System.out.println(count+"count        410");
					count++;
					break;
				}
			}
			if (count == loops) {
				break;
			}

			System.out.println(arr[ind].getV().getName() + " :-");
			for (int i = 1; i < g.getAdj_list()[ind].size(); i++) {
				System.out.println(g.getAdj_list()[ind].get(i).getEnd().getName() + " "
						+ arr[g.getAdj_list()[ind].get(i).getEnd().getId()].getDistance());
				int ind2 = g.getAdj_list()[ind].get(i).getEnd().getId();
				if (!arr[ind2].isKnown()) {
					arr[ind2].setPrevious(arr[ind].getV());
					stack.push(arr[ind2]);
					arr[ind2].setKnown(true);
				}
			}
		}
		
		for (int u=0 ; u<loops; u++) {

		TableNode temp = arr[endGoals.get(u).getId()];
		ArrayList<Node> path = new ArrayList<Node>();
		while (temp.getPrevious() != null ) {
			for (int i = 0; i < g.getEList().length; i++) {
				if (g.getEList()[i].getStart().getId() == temp.getV().getId()
						&& g.getEList()[i].getEnd().getId() == temp.getPrevious().getId()) {
					distance += g.getEList()[i].getDistance();
					System.out.println(g.getEList()[i].getDistance()+"g.getEList()[i].getDistance()                441");
				}
			}
			System.out.println(temp.getPrevious().getName());
			path.add(temp.getV());
			temp = arr[temp.getPrevious().getId()];
		}
		lDistance.setText(String.valueOf(distance));
		path.add(startNode);

		System.out.println();

		Line l = new Line();
		l.setStartX(startNode.getX());
		
		l.setStartY(startNode.getY());
		
		for (int i = path.size() - 1; i >= 0; i--) {
			l.setEndX(path.get(i).getX());
			l.setEndY(path.get(i).getY());
			l.setStrokeWidth(3);
			l.setOpacity(0.70);
			ap1.getChildren().add(l);
			l = new Line();
			l.setStartX(path.get(i).getX());
			l.setStartY(path.get(i).getY());

			ImageView iv;
			Image image;

			if (i != path.size() - 1 && i != 0) {

				path.get(i).setImage(new File("locationOrange.png"));

				iv = new ImageView();
				image = new Image(path.get(i).getImage().toURI().toString());
				iv.setImage(image);
				ap.getChildren().add(iv);

				iv.maxHeight(10);
				iv.maxWidth(10);
				iv.prefHeight(10);
				iv.prefWidth(10);

				iv.setLayoutX(path.get(i).getX() - 15);
				iv.setLayoutY(path.get(i).getY() - 15);

			}
			tfPath.appendText(" => " + path.get(i).getName() + ".\n");
		}
		tfPath.appendText(" ========\n");
		}
		System.out.println(distance);

		startNode = Goal;
		System.out.println("the new start is" + startNode);
		endGoals.remove(Goal);
		endGoals2.clear();

		for (	int y = 0; y < loops; y++) {
			endGoalsRed.get(y).setImage(new File("locationRed.png"));

			ImageView iv;
			Image image;

			iv = new ImageView();
			image = new Image(endGoalsRed.get(y).getImage().toURI().toString());
			iv.setImage(image);
			ap.getChildren().add(iv);

			iv.maxHeight(10);
			iv.maxWidth(10);
			iv.prefHeight(10);
			iv.prefWidth(10);

			iv.setLayoutX(endGoalsRed.get(y).getX() - 15);
			iv.setLayoutY(endGoalsRed.get(y).getY() - 15);
		}

	}

	@FXML
	void Clear(ActionEvent event) {
		ap1.setVisible(false);
		tfPath.clear();
		tfPath1.clear();
		lDistance.setText("");
		ap1.getChildren().clear();
		tfNumber.clear();
		tfGoal.clear();
		endGoals.clear();
		flagHeuristic=0;
		start();

	}
	@FXML
    void Car(ActionEvent event) {
		flagHeuristic=1;
		start();
		

    }  @FXML
    void Wlaking(ActionEvent event) {
    	flagHeuristic=0;
    	start();

    }
}
