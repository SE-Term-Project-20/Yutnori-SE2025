package ui.swing;
import javax.swing.*;

import controller.GameController;

import java.awt.*;
import java.util.List;
import model.*;

public class ControlPanel extends JPanel {
    private final GameController controller;
    private final JPanel throwListPanel;

    public ControlPanel(GameController controller) {
        this.controller = controller; 

        setLayout(new BorderLayout());

        JButton throwButton = new JButton("Throw Yut");
//        throwButton.addActionListener(e -> {
//            if (manager.getPhase() != GameManager.Phase.WAITING_FOR_THROW) {
//                model.fireLog("You must use all previous Yut results first.");
//                return;
//            }
//
//            YutResult result = Yut.throwRandom();
//            manager.addYutResult(result);
//            refreshThrowButtons();
//        });
        throwButton.addActionListener(e -> {
        	controller.onThrowYutClicked();
        	refreshThrowButtons();
        	}
        );
        
        JPanel actionPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        actionPanel.add(throwButton);

        throwListPanel = new JPanel(new GridLayout(0, 1));
        throwListPanel.setBorder(BorderFactory.createTitledBorder("Available Throws"));

        add(actionPanel, BorderLayout.NORTH);
        add(throwListPanel, BorderLayout.CENTER);
        refreshThrowButtons(); 
    }

    private void refreshThrowButtons() {
        throwListPanel.removeAll();

//        List<YutResult> available = manager.getAvailableThrows();
//        for (YutResult result : available) {
//            JButton resultButton = new JButton(result.toString());
//            resultButton.addActionListener(e -> {
//                try {
//                    manager.applyThrow(result);
//                    refreshThrowButtons();  // update UI after applying
//                } catch (Exception ex) {
//                    model.fireLog("Error: " + ex.getMessage());
//                }
//            });
//            throwListPanel.add(resultButton);
//        }
        controller.getAvailableThrows().forEach(result -> {
            JButton btn = new JButton(result.toString());
            btn.addActionListener(e -> {
                controller.onYutResultSelected(result);
                refreshThrowButtons();  
            });
            throwListPanel.add(btn);
        });

        throwListPanel.revalidate();
        throwListPanel.repaint();
    }
}
