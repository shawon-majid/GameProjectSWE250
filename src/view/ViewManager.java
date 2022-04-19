package view;


import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.InfoLabel;
import model.SHIP;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;




public class ViewManager {
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTON_START_X = 100;
	private final static int MENU_BUTTON_START_Y = 150;
	
	private SpaceRunnerSubScene creditSubScene;
	private SpaceRunnerSubScene helpSubScene;
	private SpaceRunnerSubScene scoresSubScene;
	private SpaceRunnerSubScene shipChooserSubScene;
	
	private SpaceRunnerSubScene sceneToHide;
	
	
	List <SpaceRunnerButton> menuButtons;
	List <ShipPicker> shipList;
	
	private SHIP choosenShip; 
	
	public ViewManager() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		menuButtons = new ArrayList<>();
		createSubScenes();
		createButtons();
		createLogo();
		createBackground();
	}
	
	private void createSubScenes() {
		creditSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(creditSubScene);
		
		helpSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(helpSubScene);
		
		scoresSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(scoresSubScene);
		
		createShipChooserSubScene();
			
	}
	
	private void createShipChooserSubScene() {
		shipChooserSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(shipChooserSubScene);
		
		
		InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUE SHIP");
		chooseShipLabel.setLayoutX(110);
		chooseShipLabel.setLayoutY(25);
		
		shipChooserSubScene.getPane().getChildren().add(chooseShipLabel);
		shipChooserSubScene.getPane().getChildren().add(createShipToChoose());
		shipChooserSubScene.getPane().getChildren().add(createButtonToStart());
		
	}
	
	private HBox createShipToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		shipList = new ArrayList<>();
		for(SHIP ship : SHIP.values()) {
			
			ShipPicker shipToPick = new ShipPicker(ship);
			box.getChildren().add(shipToPick);
			shipList.add(shipToPick);
			shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					for(ShipPicker ship: shipList) {
						ship.setIsCircleChoosen(false);
					}
					shipToPick.setIsCircleChoosen(true);
					choosenShip = shipToPick.getShip();
				}
				
			});
		}
		box.setLayoutX(300 - (118*2));
		box.setLayoutY(100);
		return box;
	}

	private void addMenuButton(SpaceRunnerButton button) {
		button.setLayoutX(MENU_BUTTON_START_X);
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size()*100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
		createLogo();
	}
	
	private void createButtons() {
		createStartButton();
		createScoresButton();
		createHelpButton();
		createCreditsButton();
		createExitButton();
	}
	
	private void showSubScene(SpaceRunnerSubScene subScene) {
		if(sceneToHide != null) {
			sceneToHide.moveSubScene();
		}
		subScene.moveSubScene();
		sceneToHide = subScene;
	}
	
	private void createStartButton() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
		addMenuButton(startButton);
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				showSubScene(shipChooserSubScene);
			}
		});
	}
	
	private void createScoresButton() {
		SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
		addMenuButton(scoresButton);
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(scoresSubScene);
			}
		});
	}
	
	private void createHelpButton() {
		SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
		addMenuButton(helpButton);
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				showSubScene(helpSubScene);
			}
		});
	}
	
	private void createCreditsButton() {
		SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
		addMenuButton(creditsButton);
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditSubScene);
			}
		});
	}
	
	private void createExitButton() {
		SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
		addMenuButton(exitButton);
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				mainStage.close();
			}
		});
	}
	
	
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/darkPurple.png", 250, 250, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
	
	private void createLogo() {
		ImageView logo = new ImageView("view/resources/spacerunner.png");
		logo.setLayoutX(400);
		logo.setLayoutY(50);
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
			
		});
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
			}
			
		});
		
		mainPane.getChildren().add(logo);
	}
	
	private SpaceRunnerButton createButtonToStart() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("START");
		startButton.setLayoutX(350);
		startButton.setLayoutY(300);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(choosenShip != null) {
					GameViewManager game = new GameViewManager();
					game.createNewGame(mainStage, choosenShip);
				}
				else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText(null);
					alert.setContentText("You did not choose your ship!");
					alert.show();
				}
				
				
			}
			
		});
		
		return startButton;
	}

	public Stage getMainStage() {
		return mainStage;
	}
}
