package model;
import java.util.List;

public interface BoardLayout {
	List<BoardNode> buildNodes();   // create every node and wire them
	BoardNode       startNode();    // point to the start node
	BoardNode  	entryNode();    // point to the virtual node the piece lands on as entering the board from reserve(not depicted, plays a holder role)
	BoardType       type();         // enum
}
