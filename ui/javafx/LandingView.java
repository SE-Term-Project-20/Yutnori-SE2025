package ui.javafx;

import controller.GameController; 
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;      
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class LandingView {
    private final GameController controller;
    private BorderPane rootPane; 

    public LandingView(GameController controller) {
        this.controller = controller;
        createView();
    }

    private void createView() {
        rootPane = new BorderPane();
        rootPane.setPadding(new Insets(20)); 

        Label welcomeLabel = new Label("Welcome to Yutnori!"); 
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20)); 
        rootPane.setCenter(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.CENTER);

        Button startGameButton = new Button("Start Game Setup");
        startGameButton.setOnAction(e -> {
            controller.requestSettingsScreenDisplay();
        });

        rootPane.setBottom(startGameButton);
        BorderPane.setAlignment(startGameButton, Pos.CENTER);
        BorderPane.setMargin(startGameButton, new Insets(20, 0, 0, 0));

    }

    public Parent getView() {
        return rootPane;
    }
}