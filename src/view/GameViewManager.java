package view;


import java.util.Random;

import javax.imageio.event.IIOReadWarningListener;
import javax.sound.midi.MidiChannel;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
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
	private static final String HORIZONTAL_LASER = "view/resources/laserHorizonotal.png";
	private static final String VERTICAL_LASER = "view/resources/laserVertical.png";
	private static final String LIGHT_IMAGE = "view/resources/bolt_gold.png";
	
	private ImageView[] brownMeteors;
	private ImageView[] greyMeteors;
 	
	private Random randomPositionGenerator;
	
	private ImageView[] stars;
	private ImageView light1, light2;
	private ImageView horizontalLaser;
	private ImageView verticalLaser;
	
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
		
		
		
		horizontalLaser = new ImageView(HORIZONTAL_LASER);
		verticalLaser = new ImageView(VERTICAL_LASER);
		light1 = new ImageView(LIGHT_IMAGE);
		light2 = new ImageView(LIGHT_IMAGE);
		
		
	
		gamePane.getChildren().add(horizontalLaser);
		gamePane.getChildren().add(verticalLaser);
		gamePane.getChildren().add(light1);
		gamePane.getChildren().add(light2);
		
		
		horizontalLaser.setVisible(false);
		verticalLaser.setVisible(false);
		light1.setVisible(false);
		light2.setVisible(false);
		
		
		pointsLabel = new SmallInfoLabel("POINTS : 00");
		pointsLabel.setLayoutX(460);
		pointsLabel.setLayoutY(20);
		gamePane.getChildren().add(pointsLabel);
		playerLifes = new ImageView[3];
		
		
		
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
		
		stars = new ImageView[10];
		for(int i = 0; i < stars.length; i++) {
			stars[i] = new ImageView(GOLD_STAR_IMAGE);
			setNewElementPos(stars[i]);
			gamePane.getChildren().add(stars[i]);
		}
		
	}
	
	private void moveGameElements() {
		
		
		
		for(int i = 0; i < stars.length; i++) {
			stars[i].setLayoutY(stars[i].getLayoutY()+ 5);
		}
		
		
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
		
		
		for(int i = 0; i < stars.length; i++) {
			if(stars[i].getLayoutY() > 1200) {
				setNewElementPos(stars[i]);
			}
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
	
	
	private void setHorizontalLaserPosition(ImageView image, int laserY) {
		image.setLayoutY(laserY);
		image.setLayoutX(0);
	}
	
	private void setVerticalLaserPosition(ImageView image, int laserX) {
		image.setLayoutY(0);
		image.setLayoutX(laserX);
	}
	
	
	private void createShip(SHIP ChoosenShip) {
		ship = new ImageView(ChoosenShip.getUrl());
		ship.setLayoutX(GAME_WIDTH/2 - 50);
		ship.setLayoutY(GAME_HEIGHT-90);
		
		gamePane.getChildren().add(ship);
		
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			int k = 1;
			int laserX, laserY;
			@Override
			public void handle(long now) {
				
				k = (k+1) % 500;
				
				System.out.println(k); // this will be deleted
				
				moveBackground();
				moveGameElements();
				checkIfElementsAreBehindTheShipAndRelocate();
				checkIfElementCollided();
				checkIfHorizontalLaserCollided();
				checkIfVerticalLaserCollided();
				moveShip();
				
				if(k == 0) showLight(laserX, laserY);
				if(k == 100) hideLight();
				if(k == 400) {
					laserX = randomPositionGenerator.nextInt(GAME_WIDTH); 
					laserY = randomPositionGenerator.nextInt(GAME_HEIGHT);
					showCaution(laserX, laserY);
				}
				
			}

		};
		
		gameTimer.start();
	}
	
	private void showCaution(int laserX, int laserY) {
		light1.setVisible(true);
		light1.setLayoutX(laserX);
		light1.setLayoutY(10);
		
		light2.setVisible(true);
		light2.setLayoutX(10);
		light2.setLayoutY(laserY);
	}
	

	
	private void hideLight() {
		setHorizontalLaserPosition(horizontalLaser, -100);
		setVerticalLaserPosition(verticalLaser, -100);
		
		// although upper code hides the laser from user i still set visibility of laser to false
		horizontalLaser.setVisible(false);
		verticalLaser.setVisible(false);
	}
	
	private void showLight(int laserX, int laserY) {
		light1.setVisible(false);
		light2.setVisible(false);
		
		showHorizontalLaser(laserY);
		showVerticalLaser(laserX);
	}
	
	private void showHorizontalLaser(int laserY) {
		horizontalLaser.setVisible(true);
		setHorizontalLaserPosition(horizontalLaser, laserY);
	}
	
	private void showVerticalLaser(int laserX) {
		verticalLaser.setVisible(true);
		setVerticalLaserPosition(verticalLaser, laserX);
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
	
	private void checkIfHorizontalLaserCollided() {
		if(ship.getLayoutY()+SHIP_RADIUS >= horizontalLaser.getLayoutY() && ship.getLayoutY()+SHIP_RADIUS <= horizontalLaser.getLayoutY()+30) {
			removeLife();
			
			// we can hide Horizontal laser here to avoid gameOver for laser collision
			// hideLight();
		}
	}
	
	private void checkIfVerticalLaserCollided() {
		if(ship.getLayoutX()+SHIP_RADIUS >= verticalLaser.getLayoutX() && ship.getLayoutX()+SHIP_RADIUS <= verticalLaser.getLayoutX()+30) {
			removeLife();
			
			// we can hide vertical laser here to avoid gameOver for laser collision
			// hideLight();
		}
	}
	
	private void checkIfElementCollided() {
		
		for(int i = 0; i < stars.length; i++) {
			if(SHIP_RADIUS + STAR_RADIUS > calculateDistance(ship.getLayoutX()+49, ship.getLayoutY() + 37, stars[i].getLayoutX()+15, stars[i].getLayoutY()+15)) {
				setNewElementPos(stars[i]);
				points++;
				String textToSet = "POINTS: ";
				if(points < 10) {
					textToSet = textToSet + "0";
				}
				pointsLabel.setText(textToSet + points);
			}
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
