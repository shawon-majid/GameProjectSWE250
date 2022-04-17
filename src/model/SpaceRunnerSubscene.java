package model;

import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

public class SpaceRunnerSubscene extends SubScene{

	private final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	private final static String BACKGROUND_IMAGE = "model/resources/yellow_panel.png";
	
	public SpaceRunnerSubscene() {
		super(new AnchorPane(), 600, 400);
		prefWidth(600);
		prefHeight(400);
		
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 400, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		
		AnchorPane root2 = (AnchorPane)this.getRoot();
		root2.setBackground(new Background(image));
	}

}
