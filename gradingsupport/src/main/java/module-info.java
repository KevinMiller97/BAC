module com.kevinmiller.gradingsupport {
    requires javafx.controls;
    requires javafx.fxml;
	requires jnativehook;
	requires javafx.graphics;
	requires java.logging;
	requires org.json;
	requires java.base;
	requires org.apache.commons.io;

    opens com.kevinmiller.gradingsupport to javafx.fxml;
    exports com.kevinmiller.gradingsupport;
}