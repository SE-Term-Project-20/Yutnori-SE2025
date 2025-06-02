package ui.javafx;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import model.GameListener;
import model.GameModel;
import model.GameStartedEvent;
import model.GameOverEvent;
import model.TurnChangedEvent;
import model.PieceMovedEvent;
import model.PieceCapturedEvent;
import model.StackFormedEvent;
import model.YutThrownEvent;

public class MessagePane extends VBox implements GameListener {
    private TextArea logArea;
    private GameModel model;

    public MessagePane(GameModel model) {
    	this.model = model;
        this.logArea = new TextArea();
        logArea.setPrefRowCount(6);
        logArea.setPrefColumnCount(80); 
        logArea.setEditable(false);
        logArea.setWrapText(true); 

        ScrollPane scrollPane = new ScrollPane(logArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);
    }

    @Override
    public void gameStarted(GameStartedEvent e) {
        logArea.appendText("GAME START!\n"); 
    }

    @Override
    public void gameEnded(GameOverEvent e) {
        logArea.appendText("WE GOT A WINNER: " + e.winner().id() + "!\n"); 
    }

    @Override
    public void turnChanged(TurnChangedEvent e) {
        logArea.appendText("Turn passed to : " + this.model.currentPlayer().id() + "\n");
    }

    @Override
    public void pieceMoved(PieceMovedEvent e) {
        if (e.to() == null) {
            logArea.appendText(e.player().id() + "'s " + e.pieces().size() + " piece(s) completed race!\n");
        } else {
            logArea.appendText(e.player().id() + " moved " + e.pieces().size() + " piece(s).\n"); 
        }
    }

    @Override
    public void pieceCaptured(PieceCapturedEvent e) {
        // Aligned with the more detailed message from Swing version
        logArea.appendText(e.attacker().id() + " captured " + e.victims().size() +
                           " piece(s) of " + e.target().id() + ".\n" +
                           e.attacker().id() + " gets one more throw!\n");
    }

    @Override
    public void stackFormed(StackFormedEvent e) {
        logArea.appendText(e.owner().id() + " formed a stack of " + e.stack().size() + ".\n"); 
    }

    public void log(String message) {
        logArea.appendText(message + "\n");
    }
}