import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel implements GameListener{
    private JTextArea logArea;

    public MessagePanel(GameModel model) {
        this.logArea = new JTextArea(6, 80);
        logArea.setEditable(false);
        setLayout(new BorderLayout());
        add(new JScrollPane(logArea), BorderLayout.CENTER);
    }

    public void gameStarted(GameStartedEvent e) {
    	logArea.append("GAME START!");
    }
    public void gameEnded(GameOverEvent e) {
    	logArea.append("WE GOT A WINNER!");
    }
    public void turnChanged(TurnChangedEvent e) {
    	logArea.append("Turn changed : " + e.next());
    }
    
    @Override
    public void pieceMoved(PieceMovedEvent e) {
    	if (e.to() == null) { logArea.append(e.player().id() + "'s" + e.pieces().size() + " piece(s) completed race!\n");}
    	else {logArea.append(e.player().id() + " moved " + e.pieces().size() + " piece(s)\n"); }
    }

    @Override
    public void pieceCaptured(PieceCapturedEvent e) {
        logArea.append(e.attacker().id() + " captured " + e.victims().size() + " piece(s) of" + e.target().id() + "\n");
    }

    @Override
    public void stackFormed(StackFormedEvent e) {
        logArea.append(e.owner().id() + " formed a stack of " + e.stack().size() + "\n");
    }
    
    public void yutThrown(YutThrownEvent e) {
        logArea.append(e.player().id() + "got" + e.result().toString() +"\n");
    }
    
    public void log(String message) {
        logArea.append(message + "\n");
    }


}