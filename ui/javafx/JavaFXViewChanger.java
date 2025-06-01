package ui.javafx;

import controller.GameController;
import controller.ViewChanger; // Assuming ViewChanger is in controller package
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.util.List; 
import java.util.stream.Collectors; 

public class JavaFXViewChanger implements ViewChanger { 

    private Stage primaryStage;
    private GameController controller;

    public JavaFXViewChanger(Stage primaryStage) {
        if (primaryStage == null) {
            throw new IllegalArgumentException("PrimaryStage cannot be null.");
        }
        this.primaryStage = primaryStage;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    private void showScene(Parent rootNode, String title) {
        if (controller == null) {
            System.err.println("JavaFXViewChanger Error: GameController not set. Cannot show scene: " + title);
            Label errorLabel = new Label("Error: Controller not initialized for JavaFXViewChanger.");
            VBox errorLayout = new VBox(errorLabel);
            errorLayout.setAlignment(Pos.CENTER);
            Scene errorScene = new Scene(errorLayout, 300, 100);
            primaryStage.setScene(errorScene);
            primaryStage.setTitle("Error");
            if (!primaryStage.isShowing()) {
                primaryStage.show();
            }
            return;
        }
        if (rootNode == null) {
            System.err.println("JavaFXViewChanger Error: Root node is null for scene: " + title + ". Displaying error.");
            Label errorLabel = new Label("Error: UI content (root node) for '" + title + "' is missing.");
            VBox errorLayout = new VBox(errorLabel);
            errorLayout.setAlignment(Pos.CENTER);
            Scene errorScene = new Scene(errorLayout, 400, 200);
            primaryStage.setScene(errorScene);
            primaryStage.setTitle("View Error");
            if (!primaryStage.isShowing()) {
                primaryStage.show();
            }
            return;
        }

        Scene scene = new Scene(rootNode, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Yutnori - " + title);
        if (!primaryStage.isShowing()) {
            primaryStage.show();
        }
    }

    @Override
    public void showLandingScreen() {
        System.out.println("JavaFXViewChanger: Requesting Landing Screen.");
        if (controller == null) {
            System.err.println("Controller not set in JavaFXViewChanger for showLandingScreen.");
            showScene(new VBox(new Label("Error: Controller not set for Landing View.")), "Error");
            return;
        }
        LandingView landingView = new LandingView(controller);
        Parent landingRootNode = landingView.getView(); 
        showScene(landingRootNode, "Welcome"); 
    }

    @Override
    public void showSettingsScreen() {
        System.out.println("JavaFXViewChanger: Requesting Settings Screen.");
        if (controller == null) {
            System.err.println("Controller not set in JavaFXViewChanger for showSettingsScreen.");
            showScene(new VBox(new Label("Error: Controller not set for Settings View.")), "Error");
            return;
        }
        SettingView settingsView = new SettingView(controller);
        Parent settingsRootNode = settingsView.getView();
        showScene(settingsRootNode, "Game Settings");
    }

    @Override
    public void showGameScreen() {
        System.out.println("JavaFXViewChanger: Requesting Game Screen.");
        if (controller == null) {
            System.err.println("Controller not set in JavaFXViewChanger for showGameScreen.");
             showScene(new VBox(new Label("Error: Controller not set.")), "Error");
            return;
        }

        GameView gameView = new GameView(controller);
        gameView.show(this.primaryStage); 
    }

    @Override
    public void showPlacementScreen() {
        System.out.println("JavaFXViewChanger: Requesting Placement Screen.");
        if (controller == null) {
            System.err.println("Controller not set for showPlacementScreen.");
            showScene(new VBox(new Label("Error: Controller not set.")), "Error");
            return;
        }
        PlacementView placementView = new PlacementView(controller);
        showScene(placementView.getView(), "Game Over");
    }
}