
public class Pawn extends Piece {
		
	public Pawn(boolean color) {
		super(color);
		if (color) {
			setSymbol('♙');
		} else {
			setSymbol('♟');
		}
		// although pawns are worth 1 point, they have the potential to promote into a better piece 
		setValue(9);
	}

	public boolean canMove(Square s1, Square s2, Board b) {		
		int x1 = s1.getX(), y1 = s1.getY(), x2 = s2.getX(), y2 = s2.getY();
		
		int dir = 1;
		if (getColor()) {
			dir = -1;
		}

		// can move forward two squares if it's the first move
		if (!isHasMoved() && x2 == x1 + 2*dir && y1 == y2) {
			if (b.getSquare(x1 + dir, y1).getPiece() == null && s2.getPiece() == null) {
				return true;
			}
		}
		
		if (x2 == x1 + dir) {
			// moving forward one square
			if (y1 == y2 && s2.getPiece() == null) {
				return true;
			}
			// taking a piece that is one square forward, one square to the left/right
			if (Math.abs(y1 - y2) == 1) {
				if (s2.getPiece() != null && s2.getPiece().getColor() != s1.getPiece().getColor()) {
					return true;
				}
			}
		}
		
		return false;
	}
}

