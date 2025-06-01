package ui.javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import controller.GameController;
import controller.ViewChanger; // Assuming ViewChanger is accessible

public class JavaFXApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. create the JavaFX-specific ViewChanger
        JavaFXViewChanger javafxViewChanger = new JavaFXViewChanger(primaryStage);

        // 2. create the GameController, providing it with the ViewChanger as did with swing
        GameController gameController = new GameController(javafxViewChanger);

        // 3. Give the JavaFXViewChanger a reference to the GameController
        javafxViewChanger.setController(gameController);

        // 4. tell the GameController to show the initial screen for the JavaFX UI
        gameController.requestLandingScreenDisplay(); 
    }   
}