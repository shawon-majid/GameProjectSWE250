package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class SmallInfoLabel extends Label{
	private final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	private final static String BACKGROUND_IMAGE = "/view/resources/blue_info_panel.png";
	
	public SmallInfoLabel(String text) {
		setPrefWidth(130);
		setPrefHeight(50);
		
		BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		
		setBackground(new Background(backgroundImage));
		setAlignment(Pos.CENTER_LEFT);
		setPadding(new Insets(10, 10, 10, 10));
		setText(text);
		setLabelFont();
		
		
	}
	
	private void setLabelFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 15));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", 15));
		}
	}
}
