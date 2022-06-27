package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private Button btExit;

	@FXML
	void openBreadth(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("BreadthScene.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Breadth First Algoritm");
		primaryStage.show();
		Stage stage2 = (Stage) btExit.getScene().getWindow();
		stage2.close();
	}
	
    @FXML
    void openDepth(ActionEvent event) throws IOException {
    	Stage primaryStage = new Stage();
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("DepthScene.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Deapth First Algoritm");
		primaryStage.show();
		Stage stage2 = (Stage) btExit.getScene().getWindow();
		stage2.close();
    }
    
   
    @FXML
    void openA(ActionEvent event) throws IOException {
    	Stage primaryStage = new Stage();
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("AstarScene.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("A* Algoritm");
		primaryStage.show();
		Stage stage2 = (Stage) btExit.getScene().getWindow();
		stage2.close();
    }
	
	@FXML
	void Exit(ActionEvent event) {
		System.exit(0);
	}
}
