package ui.javafx;

import controller.GameController; // Import GameController
import model.BoardType;         // Import BoardType enum

import javafx.geometry.Insets;
import javafx.scene.Parent; // To return the layout
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane; // Can be used as the root or another pane
import javafx.scene.layout.GridPane;
// Stage is no longer directly managed by this class's show method in the same way

public class SettingView {
    private final GameController controller;
    private BorderPane rootPane; // The main layout pane for this view
    private Spinner<Integer> playerSpinner;
    private Spinner<Integer> pieceSpinner;
    private ComboBox<BoardType> boardTypeComboBox;

    
    public SettingView(GameController controller) {
        this.controller = controller;
        createView();
    }

    private void createView() {
        rootPane = new BorderPane();
        rootPane.setPadding(new Insets(20));

        Label playerLabel = new Label("Number of Players (2-4):");
        playerSpinner = new Spinner<>();
        playerSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4, 2, 1));

        Label pieceLabel = new Label("Number of Pieces (2-6):"); 
        pieceSpinner = new Spinner<>();

        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1,1,1);
        


    	pieceSpinner.setValueFactory(valueFactory);
//        pieceSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 2, 6, 1));
       
        Label boardLabel = new Label("Board Type:");
        boardTypeComboBox = new ComboBox<>();
        boardTypeComboBox.getItems().addAll(BoardType.values()); // Use enum directly
        boardTypeComboBox.setValue(BoardType.SQUARE); // Default selection

        Button confirmButton = new Button("Start Game"); // Changed text for consistency
        confirmButton.setOnAction(e -> {
            int playerCount = playerSpinner.getValue();
            int pieceCount = pieceSpinner.getValue();
            BoardType selectedBoardType = boardTypeComboBox.getValue();

            controller.startGame(selectedBoardType, playerCount, pieceCount);
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10)); // Inner padding for the grid
        gridPane.setVgap(15); // Spacing between rows
        gridPane.setHgap(10); // Spacing between columns

        gridPane.add(playerLabel, 0, 0);
        gridPane.add(playerSpinner, 1, 0);
        gridPane.add(pieceLabel, 0, 1);
        gridPane.add(pieceSpinner, 1, 1);
        gridPane.add(boardLabel, 0, 2);
        gridPane.add(boardTypeComboBox, 1, 2);
        // gridPane.add(confirmButton, 1, 3); // Add to grid or BorderPane bottom

        rootPane.setCenter(gridPane);
        rootPane.setBottom(confirmButton);
        BorderPane.setAlignment(confirmButton, javafx.geometry.Pos.CENTER_RIGHT);
        BorderPane.setMargin(confirmButton, new Insets(20, 0, 0, 0));
    }

    public Parent getView() {
        return rootPane;
    }
}