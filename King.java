
public class King extends Piece {
		
	public King(boolean color) {
		super(color);
		if (color) {
			setSymbol('♔');
		} else {
			setSymbol('♚');
		}
		// the king does not have a specific value as it cannot be taken
		setValue(0);
	}
	
	public boolean canMove(Square s1, Square s2, Board b) {
		int xMove = Math.abs(s1.getX() - s2.getX());
		int yMove = Math.abs(s1.getY() - s2.getY());
		
		// moves 1 step in any direction
		return (xMove + yMove == 1 || xMove * yMove == 1);
	}
}
