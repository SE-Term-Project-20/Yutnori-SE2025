package controller;

import model.BoardType;
import model.GameListener;
import model.GameManager;
import model.GameModel;
import model.GameOverEvent;
import model.GameStartedEvent;
import model.Piece;
import model.PieceCapturedEvent;
import model.PieceMovedEvent;
import model.Player;
import model.StackFormedEvent;
import model.TurnChangedEvent;
import model.Yut;
import model.YutResult;
import model.YutThrownEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GameController {
    // Always present
    private GameModel model;
    private GameManager manager;
    private List<Player> players;
    private ViewChanger viewChanger; // to request UI transition. 

    public GameController(ViewChanger viewChanger) {
        this.viewChanger = viewChanger;

    }

    public void initializeAndShowSettings() {
        if (viewChanger != null) {
            viewChanger.showLandingScreen();
        }
    }

    public void startGame(BoardType type, int playerCnt, int pieceCnt) {
        this.players = new ArrayList<>();
        for (int i = 1; i <= playerCnt; i++) {
            players.add(new Player("Player " + i, pieceCnt));
        }
        model = new GameModel(type, players, pieceCnt);
        manager = new GameManager(model);

        model.addGameListener(new GameListener() {
            @Override
            public void gameEnded(GameOverEvent e) {
                if (viewChanger != null) {
                    viewChanger.showPlacementScreen(); 
                }
            }

            @Override public void gameStarted(GameStartedEvent e) {}
            @Override public void turnChanged(TurnChangedEvent e) {}
            @Override public void pieceMoved(PieceMovedEvent e) {}
            @Override public void pieceCaptured(PieceCapturedEvent e) {}
            @Override public void stackFormed(StackFormedEvent e) {}
            @Override public void yutThrown(YutThrownEvent e) {}
        });

        if (viewChanger != null) {
            viewChanger.showGameScreen();
        }
    }

    public void requestLandingScreenDisplay() {
        if (viewChanger != null) {
            viewChanger.showLandingScreen();
        }
    }

//    public void requestSettingsScreenDisplay() {
//        if (viewChanger != null) {
//            viewChanger.showSettingsScreen();
//        }
//    }
    public void requestSettingsScreenDisplay() {
        if (viewChanger != null) {
            viewChanger.showSettingsScreen();
        } else {
            System.err.println("GameController: viewChanger is NULL when requesting settings screen!");
        }

    }

    public void requestPlacementScreenDisplay() {
        if (viewChanger != null) {
            viewChanger.showPlacementScreen();
        }
    }

    public void requestGameScreenDisplay() {
        if (viewChanger != null) {
            viewChanger.showGameScreen();
        }
    }


    public void onThrowYutClicked() {
        if (manager == null) {
            model.fireLog("Game not started yet."); 
            return;
        }
        if (manager.getPhase() != GameManager.Phase.WAITING_FOR_THROW) {
            model.fireLog("Use all pending throws first.");
            return;
        }
        YutResult result = Yut.throwRandom(); 
        manager.addYutResult(result); 
        model.yutThrown(new YutThrownEvent(model.currentPlayer(), result));
    }

    public void onYutResultSelected(YutResult r) {
        if (manager == null) return;
        try {
            manager.applyThrow(r);
        } catch (Exception ex) {
            model.fireLog(ex.getMessage());
        }
    }

    public void onPieceSelected(Piece piece) {
        if (manager == null || model == null || piece == null) return;
        if (piece.owner() != model.currentPlayer()) {
            model.fireLog("Not your piece!");
            return;
        }
        manager.selectPiece(piece); 
    }

    public void resetGame() {
        model.fireLog("Restarting – choose settings again.");
        if (viewChanger != null) {
            viewChanger.showSettingsScreen(); // resetting simply calls setting screen to configure a new game. setting-game-placement follows on.
        }
    }

    public void exitGame() {
        System.exit(0); 
    }

    public List<YutResult> getAvailableThrows() {
        return manager != null ? manager.getAvailableThrows() : List.of();
    }

    public List<Player> getPlayers() {
        return players != null ? players : List.of();
    }

    public GameModel getModel() {
        return model;
    }

    public Piece getSelectedPiece() { 
        return manager != null ? manager.getSelectedPiece() : null;
    }

    public List<Player> getStandings() {
        if (players == null) return List.of();
        return players.stream().sorted(Comparator.comparingInt(Player::score).reversed())
                      .collect(Collectors.toUnmodifiableList());
    }

    public GameManager.Phase getCurrentPhase() {
        return manager != null ? manager.getPhase() : null; 
    }

    public void setViewChanger(ViewChanger viewChanger) {
        this.viewChanger = viewChanger;
    }

}