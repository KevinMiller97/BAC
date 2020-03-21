package com.kevinmiller.gradingsupport.stagecontroller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

// https://github.com/kwhat/jnativehook/releases
// https://stackoverflow.com/questions/26216864/registering-multi-key-presses-with-jnativehook

/**
 * Handles system wide key presses to allow shortcut based interaction with the
 * application, regardless of whether it is focussed or not
 * 
 * @author Alex Barker, adapted by Kevin Miller
 */
public class GlobalKeyListener implements NativeKeyListener {

	private boolean ctrl = false;

	public GlobalKeyListener() {
		// Get the logger for "org.jnativehook" and set the level to warning.
		// Set level to warning or else it spams the console
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		logger.setUseParentHandlers(false);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
			ctrl = true;
		} else if (e.getKeyCode() == NativeKeyEvent.VC_B) {
			if (ctrl) {
				System.out.println("CTRL+B combination pressed");
				// TODO: key combination activated, bring screen to front
			}
		}
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