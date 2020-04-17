module com.kevinmiller.gradingsupport {
	requires javafx.controls;
	requires javafx.fxml;
	requires jnativehook;
	requires javafx.graphics;
	requires java.logging;
	requires org.json;
	requires java.base;
	requires org.apache.commons.io;
	requires javafx.base;
	requires javaluator;
	requires java.desktop;

	opens com.kevinmiller.gradingsupport to javafx.fxml;
	opens com.kevinmiller.gradingsupport.fxgui to javafx.fxml;
	opens com.kevinmiller.gradingsupport.fxgui.controls to javafx.fxml;
	opens com.kevinmiller.gradingsupport.fxgui.controls.footer to javafx.fxml;
	opens com.kevinmiller.gradingsupport.fxgui.controls.section to javafx.fxml;
	opens com.kevinmiller.gradingsupport.fxgui.controls.segment to javafx.fxml;
	opens com.kevinmiller.gradingsupport.fxgui.controls.subpoint to javafx.fxml;
	opens com.kevinmiller.gradingsupport.fxgui.controls.segment.finaloverview to javafx.fxml;

	exports com.kevinmiller.gradingsupport;
}