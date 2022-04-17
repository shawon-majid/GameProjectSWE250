package view;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.SpaceRunnerButton;

public class ViewManager {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	public ViewManager() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createButtons();
	}
	
	private void createButtons() {
		// TODO Auto-generated method stub
		SpaceRunnerButton button = new SpaceRunnerButton("Click Me!");
		button.setLayoutX(WIDTH/2 - button.getPrefWidth()/2);
		button.setLayoutY(HEIGHT/2 - button.getPrefHeight()/2);
		mainPane.getChildren().add(button);
	
	}

	public Stage getMainStage() {
		return mainStage;
	}
}
