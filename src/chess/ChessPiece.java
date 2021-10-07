package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece { //peças de xadrez
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board); //repassa a chamada para o construtor da super classe que é o construtor da classe Piece
		this.color = color;
	}

	public Color getColor() {
		return color;
	}	
}
