package model;
import java.util.ArrayList;

public class BoardNode {
    private ArrayList<Piece> piecesOnNode = new ArrayList<>();
    private ArrayList<BoardNode> nextNodes = new ArrayList<>();
    private ArrayList<BoardNode> prevNodes = new ArrayList<>();
    private boolean isForked = false;
    private boolean isCenter = false;
    private boolean takeShortcut = false;
    public BoardNode() {
    	
    }
    
    public ArrayList<Piece> getPiecesOnNode(){
    	return this.piecesOnNode;
    }
    
    public ArrayList<BoardNode> nextNodes(){
    	return this.nextNodes;
    }
    public ArrayList<BoardNode> prevNodes(){
    	return this.prevNodes;
    }
    
    public void addNext(BoardNode next) {
    	if (next == null) throw new IllegalArgumentException("next was null");
    	this.nextNodes.add(next);
    }
    
    public void addPrev(BoardNode prev) {
    	this.prevNodes.add(prev);
    }
    
    public boolean isFork() {
        return isForked;
    }
    public boolean isCenter() {
        return isCenter;
    }
    public void setFork(boolean fork) {
        this.isForked = fork;
    }
    public void setCenter(boolean center) {
        this.isCenter = center;
    }
    
    public void setShortcut(boolean sc) {
    	this.takeShortcut = sc;
    }
    
    public boolean takeShortcut() {
    	return this.takeShortcut;
    }
}
