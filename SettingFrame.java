// SettingFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingFrame extends JFrame{
    public SettingFrame() {
        setTitle("Game Settings");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel playerLabel = new JLabel("Number of Players (2-4):");
        SpinnerNumberModel playerModel = new SpinnerNumberModel(2, 2, 4, 1);
        JSpinner playerSpinner = new JSpinner(playerModel);

        JLabel boardLabel = new JLabel("Board Type:");
        JComboBox<BoardType> boardCombo = new JComboBox<>(BoardType.values());

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int players = (int) playerSpinner.getValue();
                BoardType boardType = (BoardType) boardCombo.getSelectedItem();
                dispose();
                new GameFrame(players, boardType);
            }
        });

        add(playerLabel);
        add(playerSpinner);
        add(boardLabel);
        add(boardCombo);
        add(new JLabel());
        add(confirmButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}