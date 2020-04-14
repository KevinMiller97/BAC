package com.kevinmiller.gradingsupport.stagecontroller;

import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.json.JSONObject;

import com.kevinmiller.gradingsupport.fxgui.FXBaseApplication;
import com.kevinmiller.gradingsupport.json.JSONUtil;
import com.kevinmiller.gradingsupport.utility.PropertiesHelper;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UserScreen {

	private static Stage stage;
	private static Rectangle bounds;

	private static FXBaseApplication base = new FXBaseApplication(false);

	public static void init(Stage stage) {
		UserScreen.stage = stage;
		int x, y;
		bounds = new Rectangle(300, 700);
		try {
			x = Integer.parseInt(PropertiesHelper.loadProperty("fx.x"));
			y = Integer.parseInt(PropertiesHelper.loadProperty("fx.y"));
			bounds = new Rectangle(x, y);
			bounds.setX(Integer.parseInt(PropertiesHelper.loadProperty("startingPositionX")));
			bounds.setY(Integer.parseInt(PropertiesHelper.loadProperty("startingPositionY")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		configure();
		stage.show();
	}

	private static void configure() {
		Scene scene = new Scene(base);
//		scene.getStylesheets().add(e) TODO

		stage.setX(bounds.getX());
		stage.setY(bounds.getY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
		stage.setScene(scene);
		stage.setOpacity(0.82);
		stage.setOnCloseRequest(close -> {
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e) {
				System.err.println(e.getMessage());
			}
		});

		GlobalScreen.addNativeKeyListener(new GlobalKeyListener(stage));
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			// TODO display in application
			System.err.println("There was a problem registering the native hook, key combinations will not work");
			System.err.println(ex.getMessage());
		}
	}

	public static void reload(JSONObject configuration) {
		Platform.runLater(() -> {
			UserScreen.base = new FXBaseApplication(true);
			// checks if the file is valid and also displays the student's name/id in the
			// application
			base.getStartSection().getStudentNameProperty().set(configuration.getString(JSONUtil.studentnameTerm));
			base.getStartSection().getStudentIdProperty().set(configuration.getString(JSONUtil.studentidTerm));
			stage.setScene(new Scene(base));
		});
	}

	public static FXBaseApplication getBaseApplication() {
		return base;
	}

	public static Stage getStage() {
		return stage;
	}
}
