package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipPicker extends VBox{
	ImageView circleImage;
	ImageView shipImage;
	
	private String circleNotChoosen = "view/resources/shipchooser/grey_circle.png";
	private String circleChoosen = "view/resources/shipchooser/yellow_boxTick.png";
	
	private SHIP ship;
	
	private boolean isCircleChoosen;
	
	public ShipPicker(SHIP ship) {
		circleImage = new ImageView(circleNotChoosen);
		shipImage = new ImageView(ship.getUrl());
		this.ship = ship;
		isCircleChoosen = false; 
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().add(circleImage);
		this.getChildren().add(shipImage);
	}
	
	public SHIP getShip() {
		return ship;
	}
	
	public boolean getIsCircleChoosen() {
		return isCircleChoosen;
	}
	
	public void setIsCircleChoosen(boolean isCircleChoosen) {
		this.isCircleChoosen = isCircleChoosen;
		String imageToSet = this.isCircleChoosen ? circleChoosen : circleNotChoosen;
		
		circleImage.setImage(new Image(imageToSet)); 
	}
	
}
