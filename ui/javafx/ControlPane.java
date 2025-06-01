package ui.javafx;

import controller.GameController; 
import model.GameModel;         
import model.YutResult;         
import model.Yut;               

import javafx.scene.layout.VBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import java.util.List;

public class ControlPane extends VBox {
    private final GameController controller; 
    private final GridPane throwListPane;
    private final TitledPane throwTitledPane;
    private final TitledPane actionTitledPane;
   
    public ControlPane(GameController controller) { 
        this.controller = controller;
        this.throwListPane = new GridPane();
        this.throwListPane.setPadding(new Insets(10)); 
        this.throwListPane.setHgap(5);
        this.throwListPane.setVgap(5);

     
        Button throwButton = new Button("Throw Yut");
        throwButton.setOnAction(e -> {
            controller.onThrowYutClicked(); 
            refreshThrowButtons();          
        });

        GridPane actionPane = new GridPane();
        actionPane.setHgap(10);
        actionPane.setVgap(10);
        actionPane.setPadding(new Insets(10)); // Consistent padding
        actionPane.add(throwButton, 0, 0);
        actionTitledPane = new TitledPane("Actions", actionPane);
        actionTitledPane.setCollapsible(false);

        throwTitledPane = new TitledPane("Available Throws", throwListPane);
        throwTitledPane.setCollapsible(false);

        this.setSpacing(10); 
        this.setPadding(new Insets(10)); 
        this.getChildren().addAll(actionTitledPane, throwTitledPane);

        refreshThrowButtons(); 
    }

    private void refreshThrowButtons() {
        throwListPane.getChildren().clear();
        int row = 0;
        List<YutResult> available = controller.getAvailableThrows();
        if (available == null) return; 
        for (YutResult result : available) {
            Button resultButton = new Button(result.toString());
            resultButton.setMinWidth(100); 
            resultButton.setOnAction(e -> {
                controller.onYutResultSelected(result);
                refreshThrowButtons();  
            });
            throwListPane.add(resultButton, 0, row++);
        }
    }
}