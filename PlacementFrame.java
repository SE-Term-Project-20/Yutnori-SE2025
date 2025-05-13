// PlacementFrame.java
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlacementFrame extends JFrame {
    public PlacementFrame(List<String> playerRanking) {
        setTitle("Game Results");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Game Over - Final Standings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        int rank = 1;
        for (String player : playerRanking) {
            listModel.addElement(rank + ". " + player);
            rank++;
        }
        JList<String> rankingList = new JList<>(listModel);
        add(new JScrollPane(rankingList), BorderLayout.CENTER);

        JButton closeButton = new JButton("Exit");
        closeButton.addActionListener(e -> System.exit(0));
        add(closeButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}