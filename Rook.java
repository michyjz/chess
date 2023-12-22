
public class Rook extends Piece {
	
	public Rook(boolean color) {
		super(color);
		if (color) {
			setSymbol('♖');
		} else {
			setSymbol('♜');
		}
		setValue(5);
	}
	
	public boolean canMove(Square s1, Square s2, Board b) {	
		int x1 = s1.getX(), y1 = s1.getY(), x2 = s2.getX(), y2 = s2.getY();
		
		// two positions are not on the same row or same column
		if (x1 != x2 && y1 != y2) {
			return false;
		}
		
		// move vertically
		if (x1 != x2) {
			// swap if x1 is greater than x2
			if (x1 > x2) {
				int temp = x1;
				x1 = x2;
				x2 = temp;
			}
			int xIndex = x1 + 1;
			while (xIndex < x2) {
				if (b.getSquare(xIndex, y1).getPiece() != null) {
					return false;
				}
				xIndex++;
			}
		// move horizontally
		} else if (y1 != y2) {
			// swap if y1 is greater than y2
			if (y1 > y2) {
				int temp = y1;
				y1 = y2;
				y2 = temp;
			}
			int yIndex = y1 + 1;
			while (yIndex < y2) {
				if (b.getSquare(x1, yIndex).getPiece() != null) {
					return false;
				}
				yIndex++;
			}			
		}
		return true;
	}
}
