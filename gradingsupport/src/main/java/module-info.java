module com.kevinmiller.gradingsupport {
    requires javafx.controls;
    requires javafx.fxml;
	requires jnativehook;
	requires javafx.graphics;
	requires java.logging;

    opens com.kevinmiller.gradingsupport to javafx.fxml;
    exports com.kevinmiller.gradingsupport;
}