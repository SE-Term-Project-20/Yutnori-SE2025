package app;

import controller.GameController;
import ui.swing.SwingUILauncher;
import ui.swing.SwingViewChanger; 
import ui.javafx.JavaFXApp;


public class Main {

    public static void main(String[] args) {
        String uiChoice = "swing"; // Default to Swing

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("javafx")) {
                uiChoice = "javafx";
            } else if (args[0].equalsIgnoreCase("swing")) {
                uiChoice = "swing";
            } else {
                System.out.println("Warning: Invalid UI choice '" + args[0] + "'. Defaulting to Swing.");
                System.out.println("Usage: java app.Main [swing|javafx]");
            }
        } else {
            System.out.println("No UI specified. Defaulting to Swing.");
            System.out.println("Tip: Run with 'javafx' or 'swing' argument to choose UI (e.g., java app.Main javafx).");
        }
        

        System.out.println("Launching with " + uiChoice.toUpperCase() + " UI...");

        if (uiChoice.equals("javafx")) {
            javafx.application.Application.launch(JavaFXApp.class, args);
        } else {
        	// default is swing.
            // 1. create the Swing-specific ViewChanger
            SwingViewChanger swingViewChanger = new SwingViewChanger();

            // 2. create the GameController, providing it with the ViewChanger
            GameController gameController = new GameController(swingViewChanger);

            // 3. give the SwingViewChanger a reference to the GameController
            swingViewChanger.setController(gameController);

            SwingUILauncher.launch(gameController);
        }
    }
}