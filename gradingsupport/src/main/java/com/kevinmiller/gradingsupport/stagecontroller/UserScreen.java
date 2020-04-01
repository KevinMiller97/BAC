package com.kevinmiller.gradingsupport.stagecontroller;

import java.io.IOException;

import com.kevinmiller.gradingsupport.utility.PropertiesHelper;

import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UserScreen {

	@SuppressWarnings("unused")
	private static Screen screen = null;

	public static void init(Stage stage) {
		int x, y;
		Rectangle bounds = new Rectangle(300, 700);
		try {
			x = Integer.parseInt(PropertiesHelper.loadProperty("fx.x"));
			y = Integer.parseInt(PropertiesHelper.loadProperty("fx.y"));
			bounds = new Rectangle(x, y);
			bounds.setX(Integer.parseInt(PropertiesHelper.loadProperty("startingPositionX")));
			bounds.setY(Integer.parseInt(PropertiesHelper.loadProperty("startingPositionY")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		screen = new Screen(stage, bounds, "UserScreen");
		stage.show();
	}

}
