package com.kevinmiller.gradingsupport.stagecontroller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javafx.application.Platform;
import javafx.stage.Stage;

// https://github.com/kwhat/jnativehook/releases
// https://stackoverflow.com/questions/26216864/registering-multi-key-presses-with-jnativehook

/**
 * Handles system wide key presses to allow shortcut based interaction with the
 * application, regardless of whether it is focussed or not
 * 
 * @author Alex Barker, adapted by Kevin Miller
 */
public class GlobalKeyListener implements NativeKeyListener {

	final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
	private boolean ctrl = false;
	private final Stage stage;

	public GlobalKeyListener(Stage stage) {
		// Get the logger for "org.jnativehook" and set the level to warning.
		// Set level to warning or else it spams the console
		logger.setLevel(Level.WARNING);
		logger.setUseParentHandlers(false);
		this.stage = stage;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
			ctrl = true;
		} else if (e.getKeyCode() == NativeKeyEvent.VC_B) {
			if (ctrl) {
				Platform.runLater(() -> {
					bringToFront();
				});
				System.out.println("CTRL+B combination pressed");
			}
		}
	}

	private void bringToFront() {
		stage.setAlwaysOnTop(true);
		UserScreen.focus();
		stage.setAlwaysOnTop(false);
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
			ctrl = false;
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
	}
}