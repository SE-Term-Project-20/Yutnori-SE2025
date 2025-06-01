package model;

import java.util.ArrayList;
import java.util.List;

public class GameManager{
	public enum Phase {
	    WAITING_FOR_THROW,
	    WAITING_FOR_PIECE_SELECTION
	}

    private final GameModel model;
    private List<YutResult> availableThrows = new ArrayList<>();
    private Piece selected;
    private Phase currentPhase = Phase.WAITING_FOR_THROW;


    public GameManager(GameModel model) {
        this.model = model;
    }

    public void selectPiece(Piece p) {
        if (currentPhase != Phase.WAITING_FOR_PIECE_SELECTION)
            throw new IllegalStateException("Must throw yut first.");
        if (p.owner() != model.currentPlayer()) throw new IllegalStateException("Not your turn!");
        selected = p;
    }
    
    public void addYutResult(YutResult result) {
        availableThrows.add(result);
        model.fireLog("Yut result added for " + model.currentPlayer().id()  + " : " + result);
        if(result == YutResult.BACKDO && model.currentPlayer().active().size() == 0 && availableThrows.size() == 1) {
        	model.fireLog("Can't use back-do");
        	availableThrows.clear();
        	model.nextTurn();
        	return;
        }
        // stay in THROWING phase if YUT or MO
        if (result.extraTurn()) {
            model.fireLog("Extra throw granted! Throw again.");
            currentPhase = Phase.WAITING_FOR_THROW;
        } else {
            currentPhase = Phase.WAITING_FOR_PIECE_SELECTION;
        }
    }

    public void useYutResult(YutResult result) {
        if (!availableThrows.contains(result)) throw new IllegalStateException("Invalid Yut selection");
        applyThrow(result);
        availableThrows.remove(result);
    }

    public List<YutResult> getAvailableThrows() {
        return List.copyOf(availableThrows);
    }

    public void applyThrow(YutResult result) {
    	if (result == YutResult.BACKDO && !selected.isOnBoard()) {
            throw new IllegalStateException("Back-Do can only be applied to a piece already on the board.");
        }
        if (selected == null) throw new IllegalStateException("No piece selected.");
        if (!availableThrows.contains(result)) throw new IllegalStateException("Invalid YutResult.");

        movePiece(selected, result.steps());
        availableThrows.remove(result);
        selected = null;

        model.fireLog("Used: " + result + "  |  Remaining: " + availableThrows.size());

        // currentPhase being waiting for throw implies extra throw granted from capturing opponent's piece
        if (availableThrows.isEmpty() && currentPhase != Phase.WAITING_FOR_THROW) {
            currentPhase = Phase.WAITING_FOR_THROW;
            model.nextTurn();
            model.fireLog("Turn passed to " + model.currentPlayer().id());
        } 
    }


    private void movePiece(Piece clicked, int steps) {
        if (!clicked.isOnBoard()) {
            BoardNode entry = model.board().entryNode();
            clicked.owner().reserve().remove(clicked);
            clicked.owner().active().add(clicked);
            landPiece(clicked, entry);
        }

        BoardNode from = clicked.position();
        List<Piece> movers = pickMovers(clicked);
        movers.forEach(p -> from.getPiecesOnNode().remove(p));
        BoardNode dest = traverse(from, steps);
        model.firePieceMoved(new PieceMovedEvent(clicked.owner(), movers, from, dest));
        movers.forEach(p -> landPiece(p, dest));

    }

    private List<Piece> pickMovers(Piece clicked) {
        BoardNode node = clicked.position();
        List<Piece> at = node.getPiecesOnNode();
        boolean stack = at.size() > 1 && at.stream().allMatch(pc -> pc.owner() == clicked.owner());
        return stack ? new ArrayList<>(at) : List.of(clicked);
    }

    private BoardNode traverse(BoardNode from, int steps) {
        int remaining = Math.abs(steps);
        BoardNode n = from;
        boolean isFirstStep;
        BoardNode oneBeforeCur = null;
        BoardNode temp = null;
        for (int i = 0; i < remaining; i++) {
            if (n == model.board().startNode() && steps > 0) {
            	System.out.println("race finish filtered here");
                return null;
            }
            List<BoardNode> paths = (steps >= 0) ? n.nextNodes() : n.prevNodes();  // gives next node to move onto
            isFirstStep = (i == 0);
            oneBeforeCur = temp;
            temp = n;
            // index 0 : general case | index 1 : take shortcut
            if (isFirstStep && n.isFork() && paths.size() > 1) {
                // �б������� ������ ��� �� �б� ����(index 1)
                n = paths.get(1);
            }
            else if (isFirstStep && n.isCenter() && paths.size() > 1) {
                // �߽������� ������ ��� �� ������ ����(index 0)
                n = paths.get(1);
            }
            else if (!isFirstStep && n.isCenter() && paths.size() > 1) {
                n = (oneBeforeCur.takeShortcut()) ? paths.get(1) : paths.get(0);
            }
            else if (!isFirstStep && n.isFork() && paths.size() > 1) {
                // �б����� ������ ��� �� �Ϲ� ���(index 0)
                n = paths.get(0);
            }
            else {
                // ���� ��γ� ���� ���� ���
                n = paths.get(0);
            }

            oneBeforeCur = n;
        }
        return n;
    }

    private void landPiece(Piece p, BoardNode node) {
        if (node == null) {p.isFinished(); p.leaveBoard(); model.currentPlayer().active().remove(p); p.owner().incScore(); 
            p.owner().incrementFinishedPieces();
            if (p.owner().hasFinishedAllPieces()) {
            	model.fireGameOver(new GameOverEvent(p.owner()));
            }
            return;
        }

        List<Piece> there = node.getPiecesOnNode();
        boolean mixed = !there.isEmpty() && there.stream().anyMatch(pc -> pc.owner() != p.owner());

        if (mixed) {
            List<Piece> victims = new ArrayList<>(there);
            Player target = victims.get(0).owner();
            victims.removeIf(pc -> pc.owner() == p.owner());
            for (Piece v : victims) {
                there.remove(v);
                v.leaveBoard();
                v.owner().active().remove(v);
                v.owner().reserve().add(v);
            }
            model.firePieceCaptured(new PieceCapturedEvent(p.owner(), target, victims, node));
            
            currentPhase = Phase.WAITING_FOR_THROW;
        }

        there.add(p);
        p.setPosition(node);

        if (there.size() > 1 && there.stream().allMatch(pc -> pc.owner() == p.owner())) {
            model.fireStackFormed(new StackFormedEvent(p.owner(), new ArrayList<>(there), node));
        }
    }
    
    public Phase getPhase() {
        return currentPhase;
    }
    
    public Piece getSelectedPiece() {
    	return selected;
    }
}