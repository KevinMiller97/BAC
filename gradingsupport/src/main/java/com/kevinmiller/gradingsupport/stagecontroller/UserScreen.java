package com.kevinmiller.gradingsupport.stagecontroller;

import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.json.JSONObject;

import com.kevinmiller.gradingsupport.fxgui.FXBaseApplication;
import com.kevinmiller.gradingsupport.fxgui.controls.segment.finaloverview.FinalOverview;
import com.kevinmiller.gradingsupport.json.JSONReader;
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
			updateFooterMessage(true,
					"There was a problem registering the native hook, key combinations will not work");
			ex.printStackTrace();
		}
	}

	public static void reload(JSONObject configuration, String message) {
		JSONReader.reset();
		Platform.runLater(() -> {
			UserScreen.base = new FXBaseApplication(true);
			// checks if the file is valid and also displays the student's name/id in the
			// application
			if (configuration.has(JSONUtil.studentFirstnameTerm) && configuration.has(JSONUtil.studentLastnameTerm)) {
				base.getStartSection().getStudentNameProperty()
						.set(configuration.getString(JSONUtil.studentFirstnameTerm) + " "
								+ configuration.getString(JSONUtil.studentLastnameTerm));
				base.getStartSection().setStudentFirstName(configuration.getString(JSONUtil.studentFirstnameTerm));
				base.getStartSection().setStudentLastName(configuration.getString(JSONUtil.studentLastnameTerm));
			}
			if (configuration.has(JSONUtil.studentidTerm)) {
				base.getStartSection().getStudentIdProperty().set(configuration.getString(JSONUtil.studentidTerm));
			}
			if (configuration.has(JSONUtil.feedbackTerm))
				FinalOverview.setFeedback(configuration.getString(JSONUtil.feedbackTerm));
			stage.setScene(new Scene(base));

			updateFooterMessage(message.startsWith("Error"), message);
		});
	}

	public static void focus() {
		base.requestFocus();
	}

	public static void updateFooterMessage(boolean warning, String message) {
		base.updateMessage(warning, message);
	}

	public static FXBaseApplication getBaseApplication() {
		return base;
	}

	public static Stage getStage() {
		return stage;
	}
}
