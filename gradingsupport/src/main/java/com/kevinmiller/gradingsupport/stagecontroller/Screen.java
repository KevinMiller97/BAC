package com.kevinmiller.gradingsupport.stagecontroller;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.kevinmiller.gradingsupport.json.JSONReader;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Screen {

	private final StackPane bodyWrapper = new StackPane();
	private final StackPane footerWrapper = new StackPane();
	private final StackPane notificationWrapper = new StackPane();

	final Stage stage;
	private final Rectangle bounds;
	private final String name;

	Screen(Stage stage, Rectangle bounds, String name) {
		this.stage = stage;
		this.bounds = bounds;
		this.name = name;

		notificationWrapper.setPickOnBounds(false);

		StackPane root = new StackPane();
		root.setPickOnBounds(false);
		root.getChildren().add(new BorderPane(bodyWrapper, null, null, footerWrapper, null));
		root.getChildren().add(notificationWrapper);

		Scene scene = new Scene(root);
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

		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			// TODO display in application
			System.err.println("There was a problem registering the native hook, key combinations will not work");
			System.err.println(ex.getMessage());
		}

		// TODO add proper handling
		JSONReader.loadConfiguration();
	}

}
