package controller;
import model.*;
import model.GameManager.Phase;
import ui.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;


public class GameController {
	// Always present
	private GameModel  model;
	private GameManager manager;
	private List<Player> players;
	private JFrame activeFrame; 
	
	public void startGame(BoardType type, int playerCnt, int pieceCnt) { 
		this.players = new ArrayList<>();
        for (int i = 1; i <= playerCnt; i++) {
            players.add(new Player("Player " + i, pieceCnt));  
        }  
        model   = new GameModel(type, players);
        manager = new GameManager(model);
        
        model.addGameListener(new GameListener() {
            @Override
            public void gameEnded(GameOverEvent e) {
                showPlacementBoard();
            }

            /* empty stubs for the rest */
            @Override public void gameStarted(GameStartedEvent e) {}
            @Override public void turnChanged(TurnChangedEvent e) {}
            @Override public void pieceMoved(PieceMovedEvent e) {}
            @Override public void pieceCaptured(PieceCapturedEvent e) {}
            @Override public void stackFormed(StackFormedEvent e) {}
            @Override public void yutThrown(YutThrownEvent e) {}
        });
        disposeActiveFrame();
        activeFrame = new GameFrame(this); 
    }
	
	private void disposeActiveFrame() {
	    if (activeFrame != null) activeFrame.dispose();
	}
	
	public void showLanding() { 
		activeFrame = new LandingFrame(this); 
	}
	
	public void showSettings() {
		disposeActiveFrame();                     // close whatever is open
		activeFrame = new SettingFrame(this);     // open settings
	}
	
	private void showPlacementBoard() {
	    disposeActiveFrame();                       // close GameFrame
	    activeFrame = new PlacementFrame(this);   // new window
	}

	public void onThrowYutClicked() {
	    if (manager.getPhase() != GameManager.Phase.WAITING_FOR_THROW) {
	        model.fireLog("Use all pending throws first.");
	        return;
	    }
	    YutResult result = Yut.throwRandom();          // random roll
	    manager.addYutResult(result);                  // store in manager
	    model.yutThrown(new YutThrownEvent(model.currentPlayer(), result));
	}

    public void onYutResultSelected(YutResult r) {
        try {
            manager.applyThrow(r);    // moves selected piece inside
        } catch (Exception ex) {
            model.fireLog(ex.getMessage());
        }
    }
    
    public void onPieceSelected(Piece piece) {
        if (piece == null) return;
        if (piece.owner() != model.currentPlayer()) {
            model.fireLog("Not your piece!");
            return;
        }
        manager.selectPiece(piece);   // store selection
    }
    
    public void resetGame() {
        model.fireLog("Restarting – choose settings again.");
        showSettings();            // simply reopen the SettingFrame
    }

    public void exitGame() { System.exit(0); }
    public List<YutResult> getAvailableThrows(){ return manager.getAvailableThrows(); }
    public List<Player> getPlayers() { return players; }
    public GameModel getModel() { return model; }
    public Piece getSelecetedPiece() { return manager.getSelectedPiece(); }
    public List<Player> getStandings() {
        return players.stream().sorted(Comparator.comparingInt(Player::score).reversed())                   // highest first
                      .collect(Collectors.toUnmodifiableList());
    }
}
