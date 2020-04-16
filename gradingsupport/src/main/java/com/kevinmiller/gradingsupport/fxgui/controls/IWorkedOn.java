package com.kevinmiller.gradingsupport.fxgui.controls;

import javafx.beans.property.BooleanProperty;

/**
 * the whole point of this interface is to automatically change tabs, when all
 * nodes of the current tabs have been worked on / handled
 * 
 * @author Kevin Miller
 *
 */
public interface IWorkedOn {

	void addListeners();

	boolean validateWorkedOnPropertiesOfChildNodes();

	BooleanProperty getWorkedOnProperty();

}
