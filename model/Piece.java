package model;
public class Piece{
	private Player owner;
	private String pieceID;
	private BoardNode position;
	private boolean isFinished = false;
	private boolean hasPassedStart = false;
	
    public Piece (Player owner) {
        this.owner = owner;
    }
    
	public Player owner() {
		return owner;
	}
	public String getPieceID() {
		return pieceID;
	}
	public BoardNode position() {
		return position;
	}
	boolean isFinished() {
		return isFinished; 
	}
	boolean hasPassedStart() {
		return hasPassedStart; 
	}
	void setPosition(BoardNode dest) {
		this.position = dest;
	}
	
	public boolean isOnBoard() { 
		return position != null; 
	}
	
	public void leaveBoard() {
		position = null; 
	} 
	
	public void enterBoard(BoardNode n) {
		position = n; 
	}
}
	