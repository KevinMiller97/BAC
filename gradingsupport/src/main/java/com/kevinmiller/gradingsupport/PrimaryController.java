package com.kevinmiller.gradingsupport;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        ApplicationLauncher.setRoot("secondary");
    }
}
