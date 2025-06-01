package ui.swing;

import controller.GameController;
import controller.ViewChanger; // Make sure this import path is correct for your ViewChanger interface
                             // If ViewChanger is an inner interface of GameController, it would be:
                             // import controller.GameController.ViewChanger;

import javax.swing.JFrame;

// Import your specific Swing frame classes from the ui.swing package.
// Ensure these class names match your actual frame class names.
import ui.swing.LandingFrame;
import ui.swing.SettingFrame;
import ui.swing.GameFrame;
import ui.swing.PlacementFrame;

public class SwingViewChanger implements ViewChanger { 
    private JFrame activeFrame;
    private GameController controller; 

    
    public void setController(GameController controller) {
        this.controller = controller;
    }

    private void disposeActiveFrame() {
        if (activeFrame != null) {
            activeFrame.setVisible(false);
            activeFrame.dispose();     
            activeFrame = null;        
        }
    }

    @Override
    public void showLandingScreen() {
        if (controller == null) {
            System.err.println("SwingViewChanger Error: GameController not set. Cannot show LandingFrame.");
            return;
        }
        disposeActiveFrame();
        activeFrame = new LandingFrame(this.controller);
        System.out.println("Swing: LandingFrame should be displayed.");
    }

    @Override
    public void showSettingsScreen() {
        if (controller == null) {
            System.err.println("SwingViewChanger Error: GameController not set. Cannot show SettingFrame.");
            return;
        }
        disposeActiveFrame();
        activeFrame = new SettingFrame(this.controller);
        System.out.println("Swing: SettingFrame should be displayed.");
    }

    @Override
    public void showGameScreen() {
        if (controller == null) {
            System.err.println("SwingViewChanger Error: GameController not set. Cannot show GameFrame.");
            return;
        }
        disposeActiveFrame();
        activeFrame = new GameFrame(this.controller);
 
        System.out.println("Swing: GameFrame should be displayed.");
    }

    @Override
    public void showPlacementScreen() {
        if (controller == null) {
            System.err.println("SwingViewChanger Error: GameController not set. Cannot show PlacementFrame.");
            return;
        }
        disposeActiveFrame();
        activeFrame = new PlacementFrame(this.controller);

        System.out.println("Swing: PlacementFrame should be displayed.");
    }

}