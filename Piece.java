
public abstract class Piece {
	
	// false = white, true = black
	private boolean color;
	
	// unicode character of the piece
	private char symbol;
	
	// will be used for king, pawn, and rook
	private boolean hasMoved;
	
	// will be used to determine stalemate
	private int value;

	public Piece(boolean color) {
		this.color = color;
		hasMoved = false;
	}
	
	public boolean getColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	public boolean isHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public abstract boolean canMove(Square s1, Square s2, Board b);
	
	public String toString() {
		return symbol + "";
	}
}
