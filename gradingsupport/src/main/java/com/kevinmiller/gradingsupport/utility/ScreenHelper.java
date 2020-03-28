package com.kevinmiller.gradingsupport.utility;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * provides various utility functions for loading content<br>
 * taken and slightly adapted from my previous implementation for SWE2:
 * https://lab.swa.univie.ac.at/submission/g2019w_se2_0110
 * 
 * @author Kevin Miller
 */
public final class ScreenHelper {

	final static Logger logger = Logger.getLogger(ScreenHelper.class.getName());

	public static boolean loadFXML(Node controller, Node root) {
		return loadFXML(controller, root, null);
	}

	/**
	 * @param controller   : call with <b>this</b>
	 * @param root         : call with <b>this</b>
	 * @param separateFXML : if not specified or <b>null</b> loads a .fxml of the
	 *                     SimpleName of the Controller class, can be specified to
	 *                     load other file
	 * @return <b>true</b> if loaded successfully
	 */
	public static boolean loadFXML(Node controller, Node root, String separateFXML) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(controller.getClass().getResource(
					separateFXML != null ? separateFXML : controller.getClass().getSimpleName() + ".fxml"));

			if (root != null) {
				fxmlLoader.setRoot(root);
			}

			fxmlLoader.setController(controller);
			fxmlLoader.load();
			return true;

		} catch (LoadException e) {
			logger.log(Level.SEVERE, "LoadException in ScreenHelper " + e.getMessage(), e);
			e.printStackTrace();
		} catch (NullPointerException e) {
			logger.log(Level.SEVERE, "NullPointerException in ScreenHelper " + e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "IOException in ScreenHelper " + e.getMessage(), e);
		}
		return false;

	}

	public static String loadStylesheet(String filename) {
		try {
			return ScreenHelper.class.getResource("/fxgui/css/" + filename).toExternalForm();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "loadStylesheet failed: " + filename, e);
			return "";
		}
	}

	/*
	 * public static Image loadImage(String filename) { try { return new
	 * Image(ScreenHelper.class.getResourceAsStream("/images/" + filename)); } catch
	 * (Exception | OutOfMemoryError e) { logger.log(Level.SEVERE,
	 * "loadImage failed: " + filename, e); return null; } }
	 */

	/**
	 * load with {@code this,this} to load the .fxml of the same name
	 */
	public static void loadControl(Parent controller, Node root) {
		if (!loadFXML(controller, root)) {
			return;
		}
	}

	public static void setCssClass(Node node, String cssClass, boolean add) {
		if (add) {
			if (!node.getStyleClass().contains(cssClass)) {
				node.getStyleClass().add(cssClass);
			}
		} else {
			node.getStyleClass().remove(cssClass);
		}
	}

	/**
	 * formats the Logs output to a more pleasing yellow/white
	 * 
	 * @param logger
	 */
	public static void configureLogger(java.util.logging.Logger logger) {
		logger.setUseParentHandlers(false);
		ConsoleHandler handler = new ConsoleHandler();
		Formatter formatter = new LogFormatter();
		handler.setFormatter(formatter);
		logger.addHandler(handler);
	}

	private ScreenHelper() {
		// prevent instance
	}
}
