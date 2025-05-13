
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    private final GameModel model;
    private final GameManager manager;

    public GameFrame(int playerCount, BoardType boardType) {
        setTitle("Yutnori Game - In Progress");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("Player " + i));  
        }  

        this.model = new GameModel(boardType, players);
        this.manager = new GameManager(model);

        BoardPanel boardPanel = new BoardPanel(model, manager);
        MessagePanel messagePanel = new MessagePanel(model);
        ControlPanel controlPanel = new ControlPanel(manager, model);  

        model.addGameListener(boardPanel);
        model.addGameListener(messagePanel);
        model.addGameListener(new GameListener() {
            @Override
            public void gameEnded(GameOverEvent e) {
                List<String> ranking = List.of(e.winner().id());  
                SwingUtilities.invokeLater(() -> showPlacementBoard(ranking));
            }
        });


        JLabel infoLabel = new JLabel("Players: " + playerCount + ", Board: " + boardType);
        add(infoLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
        add(messagePanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void showPlacementBoard(List<String> playerRanking) {
        dispose();
        new PlacementFrame(playerRanking);
    }
    
    
}
