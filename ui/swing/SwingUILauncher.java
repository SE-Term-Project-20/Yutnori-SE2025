package ui.swing;

import controller.GameController;

public class SwingUILauncher {

    public static void launch(GameController controller) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            controller.requestLandingScreenDisplay();
        });
    }
}