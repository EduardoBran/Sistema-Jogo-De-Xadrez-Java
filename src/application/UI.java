package application;

import chess.ChessPiece;

public class UI { //UserInterface
	
	public static void printBoard(ChessPiece[][] pieces) {
		
		for(int i = 0 ; i < pieces.length ; i++) {
			
			System.out.print((8 - i) + " ");
			
			for (int j = 0 ; j < pieces.length ; j++) {
				
				printPiece(pieces[i][j]);				
			}
			System.out.println();
		}		
		System.out.println("  a b c d e f g h");
	}
	
	//metodo auxiliar para imprimir uma peça
	private static void printPiece(ChessPiece piece) { //imprimi 1 peça
		
		if (piece == null) { //se a peça for igual a nulo, nao tem peça no tabuleiro
			System.out.print("-");
		}
		else {
			System.out.print(piece);
		}
		System.out.print(" ");
	}
}
