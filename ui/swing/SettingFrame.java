// SettingFrame.java
package ui.swing;
import model.*;
import javax.swing.*;
import controller.GameController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingFrame extends JFrame{
	private final GameController controller;
	
    public SettingFrame(GameController controller) {
    	this.controller = controller;
    	
        setTitle("Game Settings");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel centre = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel playerLabel = new JLabel("Number of Players (2-4):");
        JSpinner playerSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
        JLabel pieceLabel = new JLabel("Number of Pieces (2-6):");
        JSpinner pieceSpinner  = new JSpinner(new SpinnerNumberModel(4, 2, 6, 1));

        JLabel boardLabel = new JLabel("Board Type:");
        JComboBox<BoardType> boardCombo = new JComboBox<>(BoardType.values());

        JButton confirmButton = new JButton("Start Game");
//        confirmButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                int players = (int) playerSpinner.getValue();
//                BoardType boardType = (BoardType) boardCombo.getSelectedItem();
//                dispose();
//                new GameFrame(players, boardType);
//            }
//        });
        confirmButton.addActionListener(e -> {
            BoardType type = (BoardType) boardCombo.getSelectedItem();
            int players   = (Integer) playerSpinner.getValue();
            int pieces    = (Integer) pieceSpinner.getValue();

            controller.startGame(type, players, pieces);
            dispose();                        
        });     
        
        
        centre.add(playerLabel);
        centre.add(playerSpinner);
        centre.add(pieceLabel);
        centre.add(pieceSpinner);
        centre.add(boardLabel);
        centre.add(boardCombo);
        add(centre, BorderLayout.CENTER);
        
        add(confirmButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}