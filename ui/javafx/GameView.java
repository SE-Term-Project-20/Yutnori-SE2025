package ui.javafx;

import controller.GameController;
import model.BoardType;
import model.GameListener;
import model.GameModel; // For type hints and direct use if needed (e.g. by MessagePane)
import model.GameOverEvent;
import model.GameStartedEvent;
import model.TurnChangedEvent;
import model.PieceMovedEvent;
import model.PieceCapturedEvent;
import model.StackFormedEvent;
import model.YutThrownEvent;
// import model.Player; // If directly using Player type from model, often controller.getPlayers() is List<Player>

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.application.Platform;
import java.util.List; // For playerRanking type hint

public class GameView {
    private Stage stage; 
    private final GameController controller;

    private final BoardPane boardPane;
    private final MessagePane messagePane;
    private final ControlPane controlPane;

    public GameView(GameController controller) { 
        this.controller = controller;

        this.boardPane = new BoardPane(this.controller);
        this.messagePane = new MessagePane(this.controller.getModel()); 
        this.controlPane = new ControlPane(this.controller);


        GameModel currentModel = this.controller.getModel();
        if (currentModel != null) {  // just ensuring gamemodel was correctly constructed within controller's startGame()
            currentModel.addGameListener(boardPane);
            currentModel.addGameListener(messagePane);
            currentModel.addGameListener(new GameListener() {
                @Override
                public void gameEnded(GameOverEvent e) {
                    Platform.runLater(() -> showPlacementBoardViaController()); 
                }

                // Empty stubs for other GameListener methods
                @Override public void gameStarted(GameStartedEvent e) {}
                @Override public void turnChanged(TurnChangedEvent e) {}
                @Override public void pieceMoved(PieceMovedEvent e) {}
                @Override public void pieceCaptured(PieceCapturedEvent e) {}
                @Override public void stackFormed(StackFormedEvent e) {}
                @Override public void yutThrown(YutThrownEvent e) {}
            });
        } else {
            System.err.println("GameView Error: GameModel is null. Listeners not added.");
        }
    }

    public void show(Stage stage) {
        this.stage = stage;
        GameModel currentModel = controller.getModel();

        if (currentModel == null) {
            System.err.println("GameView Error: Cannot show GameView, GameModel is null.");
            // Optionally display an error message in the UI
            Label errorLabel = new Label("Error: Game not initialized properly.");
            BorderPane errorRoot = new BorderPane(errorLabel);
            Scene errorScene = new Scene(errorRoot, 300, 100);
            stage.setScene(errorScene);
            stage.setTitle("Error");
            stage.show();
            return;
        }

        // Get info from the controller's model and players list
        int playerCount = controller.getPlayers() != null ? controller.getPlayers().size() : 0;
        BoardType boardType = currentModel.board().type();
        int pieceCount = currentModel.getPieceCnt(); // Assuming GameModel has this method

        Label infoLabel = new Label("Players: " + playerCount + ", Pieces: " + pieceCount + ", Board: " + boardType);

        BorderPane root = new BorderPane();
        root.setTop(infoLabel);
        root.setCenter(boardPane); // boardPane created with controller
        root.setRight(controlPane);  // controlPane created with controller
        root.setBottom(messagePane); // messagePane created with controller.getModel()

        Scene scene = new Scene(root, 900, 700); 


        stage.setScene(scene);
        stage.setTitle("Yutnori Game - In Progress (JavaFX)");
        stage.show();
    }

    private void showPlacementBoardViaController() {
        System.out.println("Game ended. Requesting placement screen via controller.");
        controller.requestPlacementScreenDisplay();
    }
}