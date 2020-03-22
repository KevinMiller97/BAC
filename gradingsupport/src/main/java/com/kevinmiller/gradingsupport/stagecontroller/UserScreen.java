package com.kevinmiller.gradingsupport.stagecontroller;

import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UserScreen {

	@SuppressWarnings("unused")
	private static Screen screen = null;

	public static void init(Stage stage) {

		Rectangle bounds = new Rectangle(300, 700); // TODO properly initialize from prop file
		bounds.setX(85);
		bounds.setY(150);

		screen = new Screen(stage, bounds, "UserScreen");
		stage.show();
	}

}
