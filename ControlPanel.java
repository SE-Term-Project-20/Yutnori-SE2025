import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ControlPanel extends JPanel {
    private final GameManager manager;
    private final GameModel model;
    private final JPanel throwListPanel;

    public ControlPanel(GameManager manager, GameModel model) {
        this.manager = manager;
        this.model = model;

        setLayout(new BorderLayout());

        JButton throwButton = new JButton("Throw Yut");
        throwButton.addActionListener(e -> {
            if (manager.getPhase() != GameManager.Phase.WAITING_FOR_THROW) {
                model.fireLog("You must use all previous Yut results first.");
                return;
            }

            YutResult result = Yut.throwRandom();
            manager.addYutResult(result);
//            model.yutThrown(new YutThrownEvent(model.currentPlayer(), result));
            refreshThrowButtons();
        });

        JPanel actionPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        actionPanel.add(throwButton);

        throwListPanel = new JPanel(new GridLayout(0, 1));
        throwListPanel.setBorder(BorderFactory.createTitledBorder("Available Throws"));

        add(actionPanel, BorderLayout.NORTH);
        add(throwListPanel, BorderLayout.CENTER);
    }

    private void refreshThrowButtons() {
        throwListPanel.removeAll();

        List<YutResult> available = manager.getAvailableThrows();
        for (YutResult result : available) {
            JButton resultButton = new JButton(result.toString());
            resultButton.addActionListener(e -> {
                try {
                    manager.applyThrow(result);
                    refreshThrowButtons();  // update UI after applying
                } catch (Exception ex) {
                    model.fireLog("Error: " + ex.getMessage());
                }
            });
            throwListPanel.add(resultButton);
        }

        throwListPanel.revalidate();
        throwListPanel.repaint();
    }
}
