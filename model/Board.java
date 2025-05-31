package model;
import java.util.List;


public class Board {
    private final List<BoardNode> nodes;
    private final BoardNode       startNode;
    private final BoardNode       entryNode;
    private final BoardType       type;
	private final BoardLayout     layout;

    public Board(BoardType type) {
        this.layout        = type.create();  
        this.nodes         = layout.buildNodes();
        this.startNode     = layout.startNode();
        this.entryNode     = layout.entryNode();  // hidden node to handle first move
        this.type          = type;
    }

    public List<BoardNode> nodes()     { return nodes; }
    public BoardNode       startNode() { return startNode; }
    public BoardNode       entryNode() { return entryNode; }
    public BoardType       type()      { return type; }
    public BoardLayout     layout()    { return layout; }
}