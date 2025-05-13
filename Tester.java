import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        // Option 1: Launch full flow from Landing Frame
        SwingUtilities.invokeLater(() -> new LandingFrame());

        // Option 2: Directly show Placement Board for testing
        // List<String> mockRanking = Arrays.asList("Player 3", "Player 1", "Player 2", "Player 4");
        // SwingUtilities.invokeLater(() -> new PlacementFrame(mockRanking));
    }
}
