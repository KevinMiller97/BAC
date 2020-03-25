package com.kevinmiller.gradingsupport;

import java.io.IOException;

import com.kevinmiller.gradingsupport.stagecontroller.UserScreen;

import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationLauncher extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		UserScreen.init(stage);
	}

	public static void main(String[] args) {
		launch();
	}

}