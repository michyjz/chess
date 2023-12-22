
public class ChessRunner {

	public static void main(String[] args) {
		// initialization and introduction of game		
		ChessGame g = new ChessGame();
		g.printIntro();
		
		//keep running the game until there is a checkmate or a stalemate
		while (!g.isCheckmate(false) && !g.isCheckmate(true) && !g.isStalemate(false) && !g.isStalemate(true)) {
			System.out.println();
			// move is the user input string, coords is the actual coordinate locations
			String move;
			int[] coords;
			int moveType = 0;
			do {
				// output whose turn it is and print the board
				if (g.getTurn()) {
					System.out.println("*********** Black's turn ***********");
				} else {
					System.out.println("*********** White's turn ***********");
				}
				g.getGameBoard().printBoard();
				
				// tell user if they are in check
				if (g.inCheck(g.getTurn(), g.getGameBoard())) {
					System.out.println("Check!");
				}
				
				// ask for user input for their move until it is valid
				do {
					System.out.print("Enter your move: ");
					move = TextIO.getlnString();
				} while (move.length() != 4);
				
				// convert and validate move
				coords = g.convertNotationToIntgers(move);
				moveType = g.validMove(coords[0], coords[1], coords[2], coords[3], g.getTurn(), g.getGameBoard(), true);
			} while (moveType == 0);

			Square s1 = g.getGameBoard().getSquare(coords[0], coords[1]);
			Square s2 = g.getGameBoard().getSquare(coords[2], coords[3]);
			
			// the value of moveType categorizes different types of moves as follows:
			// 1 - normal move
			// 2 - queen side castle
			// 3 - king side castle
			// 4 - en passant
			if (moveType == 1) {
				g.processTurn(s1, s2, g.getTurn(), g.getGameBoard(), false);
			} else if (moveType == 2) {
				g.queenSideCastle(g.getTurn(), g.getGameBoard());
			} else if (moveType == 3) {
				g.kingSideCastle(g.getTurn(), g.getGameBoard());
			} else if (moveType == 4) {
				g.enPassant(s1, s2, g.getTurn(), g.getGameBoard());
			}
			g.setTurn(!g.getTurn());
		}
		System.out.println();
		
		// end of game conditions
		System.out.println("************ Game Over! ************");
		g.getGameBoard().printBoard();
		if (g.isCheckmate(true)) {
			// black is in checkmate, white wins
			System.out.println("White wins!");
		} else if (g.isCheckmate(false)) {
			// white is in checkmate, black wins
			System.out.println("Black wins!");
		} else if (g.isStalemate(true) || g.isStalemate(false)) {
			// not in check and no legal moves, or insufficient material
			System.out.println("It's a draw.");
		}
	}
}
