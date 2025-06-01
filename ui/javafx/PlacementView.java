package ui.javafx;

import java.util.List;
import controller.GameController;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment; // For multi-line label alignment
import model.Player;


public class PlacementView {
    private final GameController controller;
    private VBox rootPane;

    public PlacementView(GameController controller) {
    	this.controller = controller;
        createView();
    }

    private void createView() {
        rootPane = new VBox(20); // Spacing between elements
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setPadding(new Insets(25));

        Label titleLabel = new Label("Game Over!");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        List<Player> standings = controller.getStandings();
        String rankingText = "Placements:\n";
        for (int i = 0; i < standings.size(); i++ ) {
        	rankingText += i + standings.get(i).id();
        }
        Label rankingLabel = new Label(rankingText);
        rankingLabel.setStyle("-fx-font-size: 16px;");
        rankingLabel.setTextAlignment(TextAlignment.CENTER);


        Button playAgainButton = new Button("Play Again (Go to Settings)");
        playAgainButton.setOnAction(e -> controller.requestSettingsScreenDisplay());

        Button exitButton = new Button("Exit Game");
        exitButton.setOnAction(e -> controller.exitGame());

        rootPane.getChildren().addAll(titleLabel, rankingLabel, playAgainButton, exitButton);
    }

    public Parent getView() {
        return rootPane;
    }
}
