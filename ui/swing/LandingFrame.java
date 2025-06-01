// LandingFrame.java
package ui.swing;

import controller.GameController;
import javax.swing.*;
import java.awt.*;


public class LandingFrame extends JFrame {
    private final GameController controller;

    public LandingFrame(GameController controller) {
        this.controller = controller;
        setTitle("Yutnori Game - Landinwg");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Yutnori!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> controller.requestSettingsScreenDisplay());

        add(welcomeLabel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}