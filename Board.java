
public class Board {
	
	private Square[][] board = new Square[8][8];

	// initiate/setup the board with pieces
	public Board() {
		// empty squares
		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new Square(i, j, null);
			}
		}
		
		// pawns
		for (int j = 0; j < 8; j++) {
			board[1][j] = new Square(1, j, new Pawn(false));
			board[6][j] = new Square(6, j, new Pawn(true));
		}
		
		// rooks
		board[0][0] = new Square(0, 0, new Rook(false));
		board[0][7] = new Square(0, 7, new Rook(false));
		board[7][0] = new Square(7, 0, new Rook(true));
		board[7][7] = new Square(7, 7, new Rook(true));
		
		// knights
		board[0][1] = new Square(0, 1, new Knight(false));
		board[0][6] = new Square(0, 6, new Knight(false));
		board[7][1] = new Square(7, 1, new Knight(true));
		board[7][6] = new Square(7, 6, new Knight(true));
		
		// bishops
		board[0][2] = new Square(0, 2, new Bishop(false));
		board[0][5] = new Square(0, 5, new Bishop(false));
		board[7][2] = new Square(7, 2, new Bishop(true));
		board[7][5] = new Square(7, 5, new Bishop(true));

		// queens
		board[0][3] = new Square(0, 3, new Queen(false));
		board[7][3] = new Square(7, 3, new Queen(true));
		
		// kings
		board[0][4] = new Square(0, 4, new King(false));
		board[7][4] = new Square(7, 4, new King(true));
	}
	
	public Square getSquare(int x, int y) {
		return board[x][y];
	}
	
	public void printBoard() {
		for (int i = 7; i >= 0; i--) {
			// row borders
			System.out.print("  ");
			for (int j = 0; j < 8; j++) {
				System.out.print("+---");
			}
			System.out.println("+");
			
			// row numbers
			System.out.print(i+1 + " ");
			
			// column borders + pieces
			for (int j = 0; j < 8; j++) {
				System.out.print("| " + board[i][j] + " ");
			}
			System.out.println("|");
		}
		// last row border
		System.out.print("  ");
		for (int j = 0; j < 8; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
		
		// column letters
		System.out.println("    a   b   c   d   e   f   g   h ");
	}
}
