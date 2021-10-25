package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece { //peças de xadrez
	
	private Color color;
	private int moveCount;
	

	public ChessPiece(Board board, Color color) {
		super(board); //repassa a chamada para o construtor da super classe que é o construtor da classe Piece
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	//método para incrementar movimentos
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	public ChessPosition getChessPosition() {
		
		return ChessPosition.fromPosition(position);
	}
	
	//implementação para verificar se existe uma peça adversária
	protected boolean isThereOpponentPiece(Position position) {
		
		ChessPiece p = (ChessPiece) getBoard().piece(position); //pegando a peça nesta posição
		return p != null && p.getColor() != color; //para concluir que a peça p é uma peça adversário, tem que testar se é diferente de nulo p.getCOlor é diferente da cor da minha peça		
	}
}
