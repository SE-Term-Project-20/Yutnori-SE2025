package ui.swing;
import controller.*;
import model.*;


import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class GameFrame extends JFrame {
    private GameController controller;

    public GameFrame(GameController controller) {
    	this.controller = controller;
    	
        setTitle("Yutnori Game - In Progress");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        int numPlayer = controller.getPlayers().size();
        BoardType boardType = controller.getModel().board().type();
        JLabel info = new JLabel(
            "Players: " + numPlayer +
            ", Board: " + boardType);
        add(info, BorderLayout.NORTH);

        BoardPanel boardPanel = new BoardPanel(controller);
        MessagePanel messagePanel = new MessagePanel(controller.getModel());
        ControlPanel controlPanel = new ControlPanel(controller);  

        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
        add(messagePanel, BorderLayout.SOUTH);
        
        controller.getModel().addGameListener(boardPanel);
        controller.getModel().addGameListener(messagePanel);

        JLabel infoLabel = new JLabel("Players: " + numPlayer + ", Board: " + boardType);
        add(infoLabel, BorderLayout.NORTH);


        setVisible(true);
    }
}
