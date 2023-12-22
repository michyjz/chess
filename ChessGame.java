
public class ChessGame {
	
	// the main board used for the game
	private Board gameBoard;
	
	//false = white's turn, true = black's turn
	private boolean turn;

	// last move that was played (used for validating en passant)
	private int[] lastMove;
	
	public ChessGame() {
		gameBoard = new Board();
		turn = false;
		lastMove = new int[4];
	}
	
	public Board getGameBoard() {
		return gameBoard;
	}
	
	public void setGameBoard(Board gameBoard) {
		this.gameBoard = gameBoard;
	}

	public boolean getTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	public void printIntro() {
		System.out.println("Welcome to Chess!");
		System.out.println();
		System.out.println("Chess is a classic two-player board game where the goal is to checkmate your opponent.");
		System.out.println();
		System.out.println("Please familiarize yourself with the rules of chess before playing.");
		System.out.println();
		System.out.println("For each move, enter one string containing both the starting and ending positions of your move.");
		System.out.println("Examples are e2e4, e7e5, g1f3, b8c6, etc.");
		System.out.println();
		System.out.println("For promotions, type the full name of the promoted piece.");
		System.out.println("Options are queen, rook, bishop, and knight.");
		System.out.println();
		System.out.println("Good luck and have fun!");
	}

	public int[] convertNotationToIntgers(String s) {
		// i.e. convert the string "e2e4" to [1, 4, 3, 4]
		// easier to validate and process moves with numbers
		int[] coordinates = new int[4];
		for (int i = 0; i < 4; i++) {
			if (i % 2 != 0) {
				coordinates[i-1] = (s.charAt(i) - '0') - 1;
				continue;
			}
			switch(s.charAt(i)) {
				case 'a': coordinates[i+1] = 0;
					break;
				case 'b': coordinates[i+1] = 1;
					break;
				case 'c': coordinates[i+1] = 2;
					break;
				case 'd': coordinates[i+1] = 3;
					break;
				case 'e': coordinates[i+1] = 4;
					break;
				case 'f': coordinates[i+1] = 5;
					break;
				case 'g': coordinates[i+1] = 6;
					break;
				case 'h': coordinates[i+1] = 7;
					break;
				default: coordinates[i+1] = 8;
					break;
			}
		}
		return coordinates;
	}
		
	public boolean inCheck(boolean kingColor, Board b) {
		// locate the king's x and y
		int kingX = 0, kingY = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (b.getSquare(i, j).getPiece() != null) {
					if ((kingColor && b.getSquare(i,j).getPiece().getSymbol() == '♔') ||
						(!kingColor && b.getSquare(i,j).getPiece().getSymbol() == '♚')) {
						kingX = i;
						kingY = j;
						break;
					} 
				}
			}
		}
		// return based on if the king is under attacked
		return isAttacked(kingX, kingY, kingColor, b);
	}
	
	public boolean isAttacked(int x, int y, boolean color, Board b) {
		// this function is mainly used for determining whether the king is in check or not
		// also used for castling abilities
		
		// the square that is being evaluated
		Square start = b.getSquare(x, y);
		
		// 8 directions (up, down, left, right, 4 diagonals) to expand outwards
		int dirX[] = {1, -1, 0, 0, 1, 1, -1, -1};
		int dirY[] = {0, 0, 1, -1, 1, -1, 1, -1};
		int xIndex, yIndex;
		for (int i = 0; i < 8; i++) {
			// start from the start square and move one step in the current direction
			xIndex = x + dirX[i];
			yIndex = y + dirY[i];
			
			// expand while the indices are in bounds
			while (xIndex >= 0 && xIndex < 8 && yIndex >= 0 && yIndex < 8) {
				Square s = b.getSquare(xIndex, yIndex);
				if (s.getPiece() == null) {
					// nothing is in the way, continue searching in the same direction
					xIndex += dirX[i];
					yIndex += dirY[i];
					continue;
				}
				if (s.getPiece().getColor() != color) {
					if (b.getSquare(xIndex, yIndex).getPiece().canMove(s, start, b)) {
						// return true if this piece is able to attack the start square
						return true;
					}
					// if this piece can't attack the start square, it blocks all attacks from this direction
					break;
				}
				// similarly, any piece that is the same color as the start square can block attacks
				break;
			}
		}
		
		// also 8 potential squares where knights may attack from
		int jumpX[] = {1, 1, 2, 2, -1, -1, -2, -2};
		int jumpY[] = {2, -2, 1, -1, 2, -2, 1, -1};
		for (int i = 0; i < 8; i++) {
			xIndex = x + jumpX[i];
			yIndex = y + jumpY[i];
			// if square is out of bounds, don't search
			if (xIndex < 0 || xIndex > 7 || yIndex < 0 || yIndex > 7) {
				continue;
			}
			Square s = b.getSquare(xIndex, yIndex);
			if (s.getPiece() != null && s.getPiece().getColor() != color) {
				if (b.getSquare(xIndex, yIndex).getPiece().canMove(s, start, b)) {
					// return true if the knight is able to attack the start square
					return true;
				}
			}
		}
		
		// if no attacks are detected, return false
		return false;
	}
	
	public void promotePawn(Square s, boolean color, boolean testMove) {
		if (testMove) {
			// if this is a test, default promotion to queen
			s.setPiece(new Queen(color));
		} else {
			String choice = "";
			// keep asking for input until given a valid piece
			do {
				System.out.print("What piece would you like to promote to: ");
				choice = TextIO.getWord();
			} while (!choice.equalsIgnoreCase("queen") && !choice.equalsIgnoreCase("rook") && 
					!choice.equalsIgnoreCase("bishop") && !choice.equalsIgnoreCase("knight"));
			// replace the pawn with the desired piece
			if (choice.equalsIgnoreCase("queen")) {
				s.setPiece(new Queen(color));
			} else if (choice.equalsIgnoreCase("rook")) {
				s.setPiece(new Rook(color));
			} else if (choice.equalsIgnoreCase("bishop")) {
				s.setPiece(new Bishop(color));
			} else {
				s.setPiece(new Knight(color));
			}
		}
	}
	
	public Board cloneBoard() {
		Board clone = new Board();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// copy each square from the original board
				if (gameBoard.getSquare(i, j).getPiece() == null) {
					clone.getSquare(i, j).setPiece(null);
				} else {
					clone.getSquare(i, j).setPiece(gameBoard.getSquare(i, j).getPiece());
				}
			}
		}
		return clone;
	}
	
	public int validCastle(Square s1, Square s2, boolean color, Board b, boolean printErrors) {
		// can only castle when not in check, has to be moving the king, must be king's first move
		if (!inCheck(color, b) && !s1.getPiece().isHasMoved() && s1.getPiece() != null && 
			(s1.getPiece().getSymbol() == '♔' || s1.getPiece().getSymbol() == '♚')) {
			// specify the row number and rook symbol based on color
			int row = 0;
			char rook = '♜';
			if (color) {
				row = 7;
				rook = '♖';
			}
			
			// queen's side castle
			if (s2.getY() == 2) {
				// rook must be in the left corner, has not moved yet
				if (b.getSquare(row, 0).getPiece() != null && b.getSquare(row, 0).getPiece().getSymbol() == rook && 
					!b.getSquare(row, 0).getPiece().isHasMoved()) {
					// squares in between must be empty and not under attack by an enemy piece
					if (b.getSquare(row, 2).getPiece() == null && !isAttacked(row, 2, color, b) && 
						b.getSquare(row, 3).getPiece() == null && !isAttacked(row, 3, color, b)) {
						return 2;
					}
					if (printErrors) {
						System.out.println("Invalid move: cannot castle on queen's side");
					}
				}
			// king's side castle
			} else if (s2.getY() == 6) {
				// rook must be in the right corner, has not moved yet
				if (b.getSquare(row, 7).getPiece() != null && b.getSquare(row, 7).getPiece().getSymbol() == rook && 
					!b.getSquare(row, 7).getPiece().isHasMoved()) {
					// squares in between must be empty and not under attack by an enemy piece
					if (b.getSquare(row, 5).getPiece() == null && !isAttacked(row, 5, color, b) && 
						b.getSquare(row, 6).getPiece() == null && !isAttacked(row, 6, color, b)) {
						return 3;
					}
					if (printErrors) {
						System.out.println("Invalid move: cannot castle on king's side");
					}
				}
			}
		}
		return 0;
	}
	
	public int validEnPassant(Square s1, Square s2, boolean color, Board b, boolean printErrors) {
		int x1 = s1.getX(), y1 = s1.getY(), x2 = s2.getX(), y2 = s2.getY();
		int dir = 1, row = 4;
		if (color) {
			dir = -1;
			row = 3;
		}
		// correct positioning
		if (x1 == row && Math.abs(y2 - y1) == 1 && (x2 - x1) == dir) {
			// the moving pawn has to land on an empty square
			if (s2.getPiece() == null) {
				Square passedPawn = b.getSquare(x1, y2);
				// two pieces must be different colors
				if (passedPawn.getPiece() != null && passedPawn.getPiece().getColor() != color) {
					// both pieces must be pawns
					if ((s1.getPiece().getSymbol() == '♙' && passedPawn.getPiece().getSymbol() == '♟') || 
						(s1.getPiece().getSymbol() == '♟' && passedPawn.getPiece().getSymbol() == '♙')) {
						// can only happen immediately after the passed pawn's move
						if (lastMove[0] == x2 + dir && lastMove[1] == y2 &&
							lastMove[2] == x2 - dir && lastMove[3] == y2) {
							return 4;
						}
					}
					if (printErrors) {
						System.out.println("Invalid move: cannot do en passant");
					}
				}
			}
		}
		return 0;
	}

	public int validMove(int x1, int y1, int x2, int y2, boolean color, Board b, boolean printErrors) {		
		// moving out of bounds
		if (x1 < 0 || x1 > 7 || y1 < 0 || y1 > 7 || x2 < 0 || x2 > 7 || y2 < 0 || y2 > 7) {
			if (printErrors) {
				System.out.println("Invalid move: out of bounds");
			}
			return 0;
		}
		
		Square s1 = b.getSquare(x1, y1);
		Square s2 = b.getSquare(x2, y2);

		// selecting a square with no piece
		if (s1.getPiece() == null) {
			if (printErrors) {
				System.out.println("Invalid move: square does not contain a piece");
			}
			return 0;
		}
		
		// selecting a piece of the wrong color
		if (s1.getPiece().getColor() != color) {
			if (printErrors) {
				System.out.println("Invalid move: wrong color");
			}
			return 0;
		}
		
		// moving to a square with a piece of the same color
		if (s2.getPiece() != null && s1.getPiece().getColor() == s2.getPiece().getColor()) {
			if (printErrors) {
				System.out.println("Invalid move: taking your own piece");
			}
			return 0;
		}
		
		// create a copy of the board and simulate the move
		Board boardCopy = cloneBoard();
		Square s3 = boardCopy.getSquare(x1, y1);
		Square s4 = boardCopy.getSquare(x2, y2);
		processTurn(s3, s4, color, boardCopy, true);
		
		// make sure the move doesn't lead to a check
		if (inCheck(color, boardCopy)) {
			if (printErrors) {
				System.out.println("Invalid move: still in check");
			}
			return 0;
		}
		
		// special case: castling
		int castleType = validCastle(s1, s2, color, b, printErrors);
		if (castleType != 0) {
			return castleType;
		} 
		
		// special case: en passant
		int enPassant = validEnPassant(s1, s2, color, b, printErrors);
		if (enPassant != 0) {
			return enPassant;
		} 
		
		// check if the specific piece type can move from s1 to s2
		if (!s1.getPiece().canMove(s1, s2, gameBoard)) {
			if (printErrors) {
				System.out.println("Invalid move: piece cannot move");
			}
			return 0;
		}
		
		// if it has not already returned, this is a standard legal move
		return 1;
	}
	
	public boolean availableMoves(boolean color) {
		// checks every possible move for the given color
		for (int x1 = 0; x1 < 8; x1++) {
			for (int y1 = 0; y1 < 8; y1++) {
				for (int x2 = 0; x2 < 8; x2++) {
					for (int y2 = 0; y2 < 8; y2++) {
						// make sure that both locations are distinct
						if (x1 == x2 && y1 == y2) {
							continue;
						}
						// if possible to move between these two locations, 
						if (validMove(x1, y1, x2, y2, color, gameBoard, false) > 0) {
							return true;
						}
					}
				}
			}
		}
		
		// no valid moves were detected in the entire board
		return false;
	}
	
	public boolean isCheckmate(boolean color) {
		// checkmate happens when there is a check and no available moves
		return inCheck(color, gameBoard) && !availableMoves(color);
	}
	
	public boolean isStalemate(boolean color) {
		int whiteTotalValue = 0, blackTotalValue = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard.getSquare(i, j).getPiece() != null) { 
					if (gameBoard.getSquare(i, j).getPiece().getColor()) {
						blackTotalValue += gameBoard.getSquare(i, j).getPiece().getValue();
					} else {
						whiteTotalValue += gameBoard.getSquare(i, j).getPiece().getValue();
					}
				}
			}
		}	
		// stalemate can happen when both white and black have insufficient material to deliver checkmate
		if (whiteTotalValue <= 3 && blackTotalValue <= 3) {
			return true;
		}
		// stalemate also happens when there is no check and no available moves
		return !inCheck(color, gameBoard) && !availableMoves(color);
	}
	
	public void processTurn(Square s1, Square s2, boolean color, Board b, boolean testMove) {
		// if the move is not a test, update the last move
		if (!testMove) {
			s1.getPiece().setHasMoved(true);
			lastMove[0] = s1.getX();
			lastMove[1] = s1.getY();
			lastMove[2] = s2.getX();
			lastMove[3] = s2.getY();
		}
		// move the piece and empty its original square
		s2.setPiece(s1.getPiece());
		s1.setPiece(null);
		
		// check for promotions
		if (color && s2.getX() == 0 && s2.getPiece().getSymbol() == '♙') {
			promotePawn(s2, color, testMove);
		} else if (!color && s2.getX() == 7 && s2.getPiece().getSymbol() == '♟') {
			promotePawn(s2, color, testMove);
		}
	}
	
	public void queenSideCastle(boolean color, Board b) {
		int row = 0;
		if (color) {
			row = 7;
		}
		// move the king and rook
		b.getSquare(row, 3).setPiece(b.getSquare(row, 0).getPiece());
		b.getSquare(row, 2).setPiece(b.getSquare(row, 4).getPiece());
		// empty their original positions
		b.getSquare(row, 0).setPiece(null);
		b.getSquare(row, 4).setPiece(null);
	}
	
	public void kingSideCastle(boolean color, Board b) {
		int row = 0;
		if (color) {
			row = 7;
		}
		// move the king and rook
		b.getSquare(row, 5).setPiece(b.getSquare(row, 7).getPiece());
		b.getSquare(row, 6).setPiece(b.getSquare(row, 4).getPiece());
		// empty their original positions
		b.getSquare(row, 7).setPiece(null);
		b.getSquare(row, 4).setPiece(null);
	}
	
	public void enPassant(Square s1, Square s2, boolean color, Board b) {
		// move the pawn and empty its original position
		s2.setPiece(s1.getPiece());
		s1.setPiece(null);
		// passed pawn is taken
		b.getSquare(s1.getX(), s2.getY()).setPiece(null);
	}
}
