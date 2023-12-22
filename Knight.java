
public class Knight extends Piece {
	
	public Knight(boolean color) {
		super(color);
		if (color) {
			setSymbol('♘');
		} else {
			setSymbol('♞');
		}
		setValue(3);
	}
	
	public boolean canMove(Square s1, Square s2, Board b) {
		int xMove = Math.abs(s1.getX() - s2.getX());
		int yMove = Math.abs(s1.getY() - s2.getY());
		
		// move 1 step in one direction and 2 steps in another direction
		// can "jump" over pieces, not necessary to check the squares in between
		return (xMove * yMove == 2);
	}
}
