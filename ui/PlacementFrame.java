// PlacementFrame.java
package ui;

import javax.swing.*;

import controller.GameController;
import model.Player;

import java.awt.*;
import java.util.List;

public class PlacementFrame extends JFrame {
	private GameController controller;
	
    public PlacementFrame(GameController controller) {
    	this.controller = controller;
    	
        setTitle("Game Results");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Game Over - Final Standings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

//        // current version terminates the game as soon as a winner is born, below logic doesn't hold as only one player will be shown.
//        int rank = 1;
//        for (String player : playerRanking) {
//            listModel.addElement(rank + ". " + player);
//            rank++;
//        }
//        JList<String> rankingList = new JList<>(listModel);
//        add(new JScrollPane(rankingList), BorderLayout.CENTER);
        List<Player> ranking = controller.getStandings();
        JPanel list = new JPanel(new GridLayout(ranking.size(), 1));
        for (int i = 0; i < ranking.size(); i++) {
            list.add(new JLabel((i + 1) + ". " + ranking.get(i).id()));
        }
        add(list, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> controller.exitGame());

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> controller.resetGame());

        JPanel btns = new JPanel();
        btns.add(restartButton);
        btns.add(exitButton);
        add(btns, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
