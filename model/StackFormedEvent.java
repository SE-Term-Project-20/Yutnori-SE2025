package model;
import java.util.List;

public record StackFormedEvent(
    Player owner,
    List<Piece> stack,
    BoardNode at
) {}
