
public class Square {

	// the square's x and y positions
	private int x;
	private int y;
	
	// the piece that occupies this square (null means it is unoccupied)
	private Piece piece;
	
	public Square(int x, int y, Piece piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public String toString() {
		if (piece == null) {
			return " ";
		}
		return piece.toString();
	}
}
