
public class Queen extends Piece {
	
	public Queen(boolean color) {
		super(color);
		if (color) {
			setSymbol('♕');
		} else {
			setSymbol('♛');
		}
		setValue(9);
	}
	
	public boolean canMove(Square s1, Square s2, Board b) {
		// moves vertically, horizontally, or diagonally without any pieces in the way
		// essentially can move either like a rook or like a bishop
		return new Rook(getColor()).canMove(s1, s2, b) || new Bishop(getColor()).canMove(s1, s2, b);
	}
}
