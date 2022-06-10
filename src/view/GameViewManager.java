package view;


import java.util.Random;

import javax.imageio.event.IIOReadWarningListener;
import javax.sound.midi.MidiChannel;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SHIP;
import model.SmallInfoLabel;

public class GameViewManager {
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private Stage menuStage;
	private ImageView ship;
	
	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 800;
	
	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	private boolean isUpKeyPressed;
	private boolean isDownKeyPressed;
	
	private int angle;
	
	private AnimationTimer gameTimer;
	
	private GridPane gridPane1;
	private GridPane gridPane2;
	private static final String BACKGROUND_IMAGE = "view/resources/darkPurple.png";
	private static final String METEOR_BROWN_IMAGE = "view/resources/meteorBrown.png";
	private static final String METEOR_GREY_IMAGE = "view/resources/meteorGrey.png";
	private static final String LIGHT_IMAGE = "view/resources/bolt_gold.png";
	
	
	private ImageView[] brownMeteors;
	private ImageView[] greyMeteors;
 	
	private Random randomPositionGenerator;
	
	private ImageView star;
	private ImageView light;
	
	private SmallInfoLabel pointsLabel;
	private ImageView[] playerLifes;
	private int playerLife;
	private int points;
	private final static String GOLD_STAR_IMAGE = "view/resources/star_gold.png";
	
	// we will consider every objects as a circle
	
	private final static int STAR_RADIUS = 12;
	private final static int SHIP_RADIUS = 27;
	private final static int METEOR_RADIUS = 20;
	
	
	
	public GameViewManager() {
		initializeStage();
		createKeyListeners();
		randomPositionGenerator = new Random();
	}

	private void createKeyListeners() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				}
				else if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = true;
				}
				else if(event.getCode() == KeyCode.UP) {
					isUpKeyPressed = true;
				}
				else if(event.getCode() == KeyCode.DOWN) {
					isDownKeyPressed = true;
				}
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.UP) {
					isUpKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.DOWN) {
					isDownKeyPressed = false;
				}
			}
			
		});
	}

	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		
	}
	
	public void createNewGame(Stage menuStage, SHIP choosenShip) {
		this.menuStage = menuStage; 
		this.menuStage.hide();
		createBackground();
		createShip(choosenShip);
		createGameElements(choosenShip);
		createGameLoop();
		
		
		gameStage.show();
	}
	
	private void createGameElements(SHIP choosenShip) {
		playerLife = 2;
		star = new ImageView(GOLD_STAR_IMAGE);
		light = new ImageView(LIGHT_IMAGE);
		setNewElementPos(star);
		gamePane.getChildren().add(star);
		pointsLabel = new SmallInfoLabel("POINTS : 00");
		pointsLabel.setLayoutX(460);
		pointsLabel.setLayoutY(20);
		gamePane.getChildren().add(pointsLabel);
		playerLifes = new ImageView[3];
		
		gamePane.getChildren().add(light);
		
		for(int i = 0; i < playerLifes.length; i++) {
			playerLifes[i] = new ImageView(choosenShip.getUrlLife());
			playerLifes[i].setLayoutX(455 + (50*i));
			playerLifes[i].setLayoutY(80);
			gamePane.getChildren().add(playerLifes[i]);
 		}
		
		
		
		brownMeteors = new ImageView[3];
		for(int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i] = new ImageView(METEOR_BROWN_IMAGE);
			setNewElementPos(brownMeteors[i]);
			gamePane.getChildren().add(brownMeteors[i]);
		}
		
		greyMeteors = new ImageView[3];
		for(int i = 0; i < greyMeteors.length; i++) {
			greyMeteors[i] = new ImageView(METEOR_GREY_IMAGE);
			setNewElementPos(greyMeteors[i]);
			gamePane.getChildren().add(greyMeteors[i]);
		}
	}
	
	private void moveGameElements() {
		
		star.setLayoutY(star.getLayoutY()+ 5);
		
		
		for(int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY() + 7);
			brownMeteors[i].setRotate(brownMeteors[i].getRotate() + 4);
		}
		
		for(int i = 0; i < greyMeteors.length; i++) {
			greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY() + 7);
			greyMeteors[i].setRotate(greyMeteors[i].getRotate() + 4);
		}
	}
	
	private void checkIfElementsAreBehindTheShipAndRelocate() {
		
		if(star.getLayoutY() > 1200){
			setNewElementPos(star);
		}
		for(int i = 0; i < brownMeteors.length; i++) {
			if(brownMeteors[i].getLayoutY() > 900) {
				setNewElementPos(brownMeteors[i]);
			}
		}
		
		for(int i = 0; i < greyMeteors.length; i++) {
			if(greyMeteors[i].getLayoutY() > 900) {
				setNewElementPos(greyMeteors[i]);
			}
		}
	}
	
	private void setNewElementPos(ImageView image) {
		image.setLayoutX(randomPositionGenerator.nextInt(370));
		image.setLayoutY(-(randomPositionGenerator.nextInt(3200) + 600));
	}
	
	private void setElementInsideScene(ImageView image) {
		image.setLayoutX(randomPositionGenerator.nextInt(600));
		image.setLayoutY(randomPositionGenerator.nextInt(800));
	}
	
	private void createShip(SHIP ChoosenShip) {
		ship = new ImageView(ChoosenShip.getUrl());
		ship.setLayoutX(GAME_WIDTH/2 - 50);
		ship.setLayoutY(GAME_HEIGHT-90);
		
		gamePane.getChildren().add(ship);
		
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			int k = 0;
			@Override
			public void handle(long now) {
				
				k = (k+1) % 100;
				System.out.println(k);
				moveBackground();
				moveGameElements();
				checkIfElementsAreBehindTheShipAndRelocate();
				checkIfElementCollided();
				moveShip();
				if(k == 0) showLight();

				
			}

		};
		
		gameTimer.start();
	}
	
	private void showLight() {
		setElementInsideScene(light);
	}
	
	private void moveShip() {
		if(isLeftKeyPressed && !isRightKeyPressed) {
			if(angle > -30) {
				angle -= 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() > -20) {
				ship.setLayoutX(ship.getLayoutX() - 3);
			}
		}
		else if(!isLeftKeyPressed && isRightKeyPressed) {
			if(angle < 30) {
				angle += 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() < GAME_WIDTH-80) {
				ship.setLayoutX(ship.getLayoutX() + 3); 
			}
		}
		else if(!isLeftKeyPressed && !isRightKeyPressed) {
			if(angle < 0) {
				angle += 5;
			}
			else if(angle > 0) {
				angle -= 5;
			}
			
			ship.setRotate(angle);
		}
		else if(isLeftKeyPressed && isRightKeyPressed) {
			if(angle < 0) {
				angle += 5;
			}
			else if(angle > 0) {
				angle -= 5;
			}
			
			ship.setRotate(angle);
		}
		if(isUpKeyPressed && !isDownKeyPressed) {
			if(ship.getLayoutY() > 0) {
				ship.setLayoutY(ship.getLayoutY()-3);
			}
		}
		if(!isUpKeyPressed && isDownKeyPressed) {
			if(ship.getLayoutY() < GAME_HEIGHT-85) {
				ship.setLayoutY(ship.getLayoutY()+3);
			}
		}
	}
	
	private void createBackground() {
		gridPane1 = new GridPane();
		gridPane2 = new GridPane();
		
		
		for(int i = 0; i < 12; i++) {
			ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
			ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
			GridPane.setConstraints(backgroundImage1, i%3, i/3);
			GridPane.setConstraints(backgroundImage2, i%3, i/3);
			gridPane1.getChildren().add(backgroundImage1);
			gridPane2.getChildren().add(backgroundImage2);
		}
		
		gridPane2.setLayoutY(-1024);
		

		gamePane.getChildren().addAll(gridPane1, gridPane2);
	}
	
	private void moveBackground() {
		gridPane1.setLayoutY(gridPane1.getLayoutY()+1);
		gridPane2.setLayoutY(gridPane2.getLayoutY()+1);
		
		if(gridPane1.getLayoutY() >= 1024) {
			gridPane1.setLayoutY(-1024);
		}
		
		if(gridPane2.getLayoutY() >= 1024) {
			gridPane2.setLayoutY(-1024);
		}
	}
	
	private void checkIfElementCollided() {
		if(SHIP_RADIUS + STAR_RADIUS > calculateDistance(ship.getLayoutX()+49, ship.getLayoutY() + 37, star.getLayoutX()+15, star.getLayoutY()+15)) {
			setNewElementPos(star);
			points++;
			String textToSet = "POINTS: ";
			if(points < 10) {
				textToSet = textToSet + "0";
			}
			pointsLabel.setText(textToSet + points);
		}
		
		
		for(int i = 0; i < brownMeteors.length; i++) {
			if(SHIP_RADIUS + METEOR_RADIUS > calculateDistance(ship.getLayoutX()+49, ship.getLayoutY() + 37, brownMeteors[i].getLayoutX()+20, brownMeteors[i].getLayoutY()+20)) {
				setNewElementPos(brownMeteors[i]);
				removeLife();
			}
		}
		
		for(int i = 0; i < greyMeteors.length; i++) {
            if(SHIP_RADIUS + METEOR_RADIUS > calculateDistance(ship.getLayoutX()+49, ship.getLayoutY() + 37, greyMeteors[i].getLayoutX()+20, greyMeteors[i].getLayoutY()+20)) {
                setNewElementPos(greyMeteors[i]);
                removeLife();
            }
        }
	}
	
	
	private void removeLife() {
		gamePane.getChildren().remove(playerLifes[playerLife]);
		playerLife--;
		
		if(playerLife < 0) {
			gameStage.close();
			gameTimer.stop();
			menuStage.show();
		}
	}
	
	private double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt( Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) );
	}
	
}
