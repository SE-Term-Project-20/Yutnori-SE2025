package model;
import java.util.List;

public record PieceCapturedEvent(
    Player attacker,
    Player target,
    List<Piece> victims,
    BoardNode at
) {}
