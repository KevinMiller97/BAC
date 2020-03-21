package com.kevinmiller.gradingsupport.stagecontroller;

import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UserScreen {

	private static Screen screen = null;

	public static void init(Stage stage) {
		screen = new Screen(stage, new Rectangle(800, 600), "UserScreen"); // TODO adapt res
		stage.show();
	}

}
