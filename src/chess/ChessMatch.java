package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Rei;
import chess.pieces.Torre;
//partida de xadrez . Coração do projeto. Onde vão ter as regras do sistema
public class ChessMatch { 
	
	private Board board; //partida de xadrez TEM que ter 1 tabuleiro
	
	
	public ChessMatch() { //quando é criado a partida, o construtor cria o tabuleiro e chama o setup
		 
		board = new Board(8 , 8); //dimensão do tabuleiro de xadrez
		initialSetup();
	}
	
	public ChessPiece[][] getPieces(){ //Retornar uma matriz de peças de xadrez correspondente a essa partida.
		
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		
		for(int i = 0 ; i < board.getRows() ; i++) { //percorrer a matriz de peças do tabuleiro (board) e pra cada peça do meu tabuleiro, faço um downcasting para ChessPiece
			
			for(int j = 0 ; j < board.getColumns() ; j++) {
				
				mat[i][j] = (ChessPiece) board.piece(i,j); //(ChessPiece) downcasting para ChessPiece
			}
		}
		return mat; //retorna a matriz de peça da minha partida de xadrez
	}
	
	//método para retirar a peça da posição de origem e coloca-la na posição de destino
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { //source origem , target destino
		
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		
		validateSourcePosition(source); //método para validar uma posição
		
		Piece capturedPiece = makeMove(source, target); 
		
		return (ChessPiece)capturedPiece; //downcasting pq a peça capturada era do tipo Piece
	}
	
	private Piece makeMove(Position source, Position target) {
		
		Piece p = board.removePiece(source); //retirou a peçada posição de origem
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position.");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the choesen piece");
		}
	}
			
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		
	}
	
	//método responsável por INICIAR a partida de xadrez colocando as peças no tabuleiro
	private void initialSetup() {
		
		placeNewPiece('c', 1, new Torre(board, Color.WHITE));
        placeNewPiece('c', 2, new Torre(board, Color.WHITE));
        placeNewPiece('d', 2, new Torre(board, Color.WHITE));
        placeNewPiece('e', 2, new Torre(board, Color.WHITE));
        placeNewPiece('e', 1, new Torre(board, Color.WHITE));
        placeNewPiece('d', 1, new Rei(board, Color.WHITE));

        placeNewPiece('c', 7, new Torre(board, Color.BLACK));
        placeNewPiece('c', 8, new Torre(board, Color.BLACK));
        placeNewPiece('d', 7, new Torre(board, Color.BLACK));
        placeNewPiece('e', 7, new Torre(board, Color.BLACK));
        placeNewPiece('e', 8, new Torre(board, Color.BLACK));
        placeNewPiece('d', 8, new Rei(board, Color.BLACK));
		
	}
}
