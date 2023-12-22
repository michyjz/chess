
public class Bishop extends Piece {
	
	public Bishop(boolean color) {
		super(color);
		if (color) {
			setSymbol('♗');
		} else {
			setSymbol('♝');
		}
		setValue(3);
	}
	
	public boolean canMove(Square s1, Square s2, Board b) {
		int x1 = s1.getX(), y1 = s1.getY(), x2 = s2.getX(), y2 = s2.getY();
		
		// two positions are not on the same diagonal
		if ((x1 + y1 != x2 + y2) && (x1 - y1 != x2 - y2)) {
			return false;
		}
		
		int xIndex, yIndex;
		// swap if x1 is greater than x2
		if (x1 > x2) {
			int temp = x1;
			x1 = x2;
			x2 = temp;
			temp = y1;
			y1 = y2;
			y2 = temp;
		}
		// bottom left to top right diagonal (/)
		if (x1 - y1 == x2 - y2) {
			xIndex = x1 + 1;
			yIndex = y1 + 1;
			while (xIndex < x2 && yIndex < y2) {
				if (b.getSquare(xIndex, yIndex).getPiece() != null) {
					return false;
				}
				xIndex++;
				yIndex++;
			}
		// bottom right to top left diagonal (\)
		} else if (x1 + y1 == x2 + y2) {
			xIndex = x1 + 1;
			yIndex = y1 - 1;
			while (xIndex < x2 && yIndex > y2) {
				if (b.getSquare(xIndex, yIndex).getPiece() != null) {
					return false;
				}
				xIndex++;
				yIndex--;
			}			
		} 
		
		return true;
	}
}
