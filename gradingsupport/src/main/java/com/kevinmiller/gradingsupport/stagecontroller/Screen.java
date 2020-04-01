package com.kevinmiller.gradingsupport.stagecontroller;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.kevinmiller.gradingsupport.fxgui.FXBaseApplication;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Screen {

	private final FXBaseApplication base = new FXBaseApplication();

	final Stage stage;
	private final Rectangle bounds;
	private final String name;

	Screen(Stage stage, Rectangle bounds, String name) {
		this.stage = stage;
		this.bounds = bounds;
		this.name = name;

		Scene scene = new Scene(base);
//		scene.getStylesheets().add(e) TODO

		stage.setX(bounds.getX());
		stage.setY(bounds.getY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
		stage.setScene(scene);
		stage.setOnCloseRequest(close -> {
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e) {
				System.err.println(e.getMessage());
			}
		});
		stage.setTitle("Grading Support System"); // TODO add proper title

		GlobalScreen.addNativeKeyListener(new GlobalKeyListener(stage));
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			// TODO display in application
			System.err.println("There was a problem registering the native hook, key combinations will not work");
			System.err.println(ex.getMessage());
		}
	}

}
