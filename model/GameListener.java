package model;
public interface GameListener {
    default void pieceMoved(PieceMovedEvent e) {}
    default void pieceCaptured(PieceCapturedEvent e) {}
    default void stackFormed(StackFormedEvent e) {}
    default void gameStarted(GameStartedEvent e) {}
    default void gameEnded(GameOverEvent e) {}
    default void turnChanged(TurnChangedEvent e) {}
    default void yutThrown(YutThrownEvent e) {}
}
