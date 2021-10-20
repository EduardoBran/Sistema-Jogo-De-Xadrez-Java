package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Rei;
import chess.pieces.Torre;
//partida de xadrez . Coração do projeto. Onde vão ter as regras do sistema
public class ChessMatch { 
	
	private int turn;
	private Color currentPlayer;
	private Board board; //partida de xadrez TEM que ter 1 tabuleiro
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	
	public ChessMatch() { //quando é criado a partida, o construtor cria o tabuleiro e chama o setup
		 
		board = new Board(8 , 8); //dimensão do tabuleiro de xadrez
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
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
	
	//imprimir as posições possíveis através de uma posição de origem
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}	
		
	//método para retirar a peça da posição de origem e coloca-la na posição de destino
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { //source origem , target destino
		
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		
		validateSourcePosition(source); //método para validar uma posição
		validadeTargetPosition(source, target);
		
		Piece capturedPiece = makeMove(source, target); 
		
		nextTurn(); //trocar o turno
		
		return (ChessPiece)capturedPiece; //downcasting pq a peça capturada era do tipo Piece
	}
	
	private Piece makeMove(Position source, Position target) {
		
		Piece p = board.removePiece(source); //retirou a peçada posição de origem
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	 
	private void validateSourcePosition(Position position) {
		
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position.");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { //exceção para caso o jogardo esteja tentando mover uma peça adversária
			throw new ChessException("The chosen piece is not yours.");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the choesen piece");
		}
				
	}
	
	private void validadeTargetPosition(Position source, Position target) {
		
		if(!board.piece(source).possibleMove(target)) { //se para peça de origem a posição de destino nao é um movimento possivel, entao nao posso mexer para la
			
			throw new ChessException("The chosen piece can't move to target position.");
		}	
	}
	
	//método para alternar oos jogadores
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; //ternário, se o jogador atual for igual a Color.WHITE então agora ele vai ser o Color.BLACK , caso contrário ele vai ser o Color.WHITE 
	}
			
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece); 
		
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
