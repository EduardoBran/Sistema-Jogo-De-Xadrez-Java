package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bispo;
import chess.pieces.Cavalo;
import chess.pieces.Peao;
import chess.pieces.Rainha;
import chess.pieces.Rei;
import chess.pieces.Torre;
//partida de xadrez . Cora��o do projeto. Onde v�o ter as regras do sistema
public class ChessMatch { 
	
	private int turn;
	private Color currentPlayer;
	private Board board; //partida de xadrez TEM que ter 1 tabuleiro
	private boolean check; //come�a como false
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	
	public ChessMatch() { //quando � criado a partida, o construtor cria o tabuleiro e chama o setup
		 
		board = new Board(8 , 8); //dimens�o do tabuleiro de xadrez
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
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	public ChessPiece[][] getPieces(){ //Retornar uma matriz de pe�as de xadrez correspondente a essa partida.
		
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		
		for(int i = 0 ; i < board.getRows() ; i++) { //percorrer a matriz de pe�as do tabuleiro (board) e pra cada pe�a do meu tabuleiro, fa�o um downcasting para ChessPiece
			
			for(int j = 0 ; j < board.getColumns() ; j++) {
				
				mat[i][j] = (ChessPiece) board.piece(i,j); //(ChessPiece) downcasting para ChessPiece
			}
		}
		return mat; //retorna a matriz de pe�a da minha partida de xadrez
	}
	
	//imprimir as posi��es poss�veis atrav�s de uma posi��o de origem
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}	
		
	//m�todo para retirar a pe�a da posi��o de origem e coloca-la na posi��o de destino
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { //source origem , target destino
		
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		
		validateSourcePosition(source); //m�todo para validar uma posi��o
		validadeTargetPosition(source, target);
		
		Piece capturedPiece = makeMove(source, target); 
		
		if (testCheck(currentPlayer)) {
			
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check.");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
				
		//Movimento Especial - Promo��o
		promoted = null;
		if (movedPiece instanceof Peao) {
			if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if (testCheckMate(opponent(currentPlayer))) {
			
			checkMate = true;
		}
		else {			
			nextTurn(); //trocar o turno
		}
		
		// Movimento Especial - en passant
		if (movedPiece instanceof Peao && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece)capturedPiece; //downcasting pq a pe�a capturada era do tipo Piece
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		
		if(promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q") ) {
			throw new InvalidParameterException("Invalid type for promotion");
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
		
	}
	
	private ChessPiece newPiece(String type, Color color) {
		
		if(type.equals("B")) return new Bispo(board, color); 
		if(type.equals("C")) return new Cavalo(board, color);
		if(type.equals("Q")) return new Rainha(board, color);
		return new Torre(board, color);
	}
	
	private Piece makeMove(Position source, Position target) {
		
		ChessPiece p = (ChessPiece)board.removePiece(source); //retirou a pe�ada posi��o de origem
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// Movimento Especial Kingside rook (rook pequeno)
		if (p instanceof Rei && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece torre = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(torre, targetT);
			torre.increaseMoveCount();
		}
		
		// Movimento Especial Queenside rook (rook grande)
		if (p instanceof Rei && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece torre = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(torre, targetT);
			torre.increaseMoveCount();
		}
		
		// Movimento Especial - en passant
		if (p instanceof Peao) {
			
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				
				Position peaoPosition;
				
				if (p.getColor() == Color.WHITE) {
					
					peaoPosition = new Position(target.getRow() + 1, target.getColumn());
				}
				else {
					
					peaoPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(peaoPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}		
		return capturedPiece;
	}
	
	//m�todo para desfazer o movimento (evitar que o jogador se coloque em xeque)
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		
		ChessPiece p =  (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// Movimento Especial Kingside rook (rook pequeno)
		if (p instanceof Rei && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece torre = (ChessPiece)board.removePiece(targetT);
			board.placePiece(torre, sourceT);
			torre.decreaseMoveCount();
		}
		
		// Movimento Especial Queenside rook (rook grande)
		if (p instanceof Rei && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece torre = (ChessPiece)board.removePiece(targetT);
			board.placePiece(torre, sourceT);
			torre.decreaseMoveCount();
		}
		// Movimento Especial - en passant
		if (p instanceof Peao) {
			
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				
				ChessPiece peao = (ChessPiece)board.removePiece(target);
				
				Position peaoPosition;
				
				if (p.getColor() == Color.WHITE) {
					
					peaoPosition = new Position(3, target.getColumn());
				}
				else {
					
					peaoPosition = new Position(4, target.getColumn());
				}
				board.placePiece(peao, peaoPosition);
			}
		}
		
	}
	 
	private void validateSourcePosition(Position position) {
		
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position.");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { //exce��o para caso o jogardo esteja tentando mover uma pe�a advers�ria
			throw new ChessException("The chosen piece is not yours.");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the choesen piece");
		}
				
	}
	
	private void validadeTargetPosition(Position source, Position target) {
		
		if(!board.piece(source).possibleMove(target)) { //se para pe�a de origem a posi��o de destino nao � um movimento possivel, entao nao posso mexer para la
			
			throw new ChessException("The chosen piece can't move to target position.");
		}	
	}
	
	//m�todo para alternar oos jogadores
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; //tern�rio, se o jogador atual for igual a Color.WHITE ent�o agora ele vai ser o Color.BLACK , caso contr�rio ele vai ser o Color.WHITE 
	}
	
	private Color opponent (Color color) {
		
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	//m�todo para varrer as pe�as do jogo localizando o rei daquela cor
	private ChessPiece king(Color color) {
		
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		
		for (Piece p : list) {
			
			if (p instanceof Rei) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board.");
	}
	
	//m�todo para varrer todas as pe�as advers�rias e testar se alguma delas cai na posi��o do Rei
	private boolean testCheck(Color color) {
		
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				
				return true;
			}
		}
		return false;
	}
	
	
	//m�todo para o checkMate
	private boolean testCheckMate(Color color) {
		
		if(!testCheck(color)) {
			
			return false;
		}
		
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); 
		
		for (Piece p : list) {
			
			boolean[][] mat = p.possibleMoves();
				
			for(int i = 0 ; i < board.getRows() ; i++) {
				for(int j = 0; j < board.getColumns() ; j++) {
					
					if (mat[i][j]) {
						
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i , j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						
						if (!testCheck) {
							return false;
						}
					}
				}
			}			
		}		
		return true;
	}
	
			
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece); 
		
	}
	
	//m�todo respons�vel por INICIAR a partida de xadrez colocando as pe�as no tabuleiro
	private void initialSetup() {
		
		placeNewPiece('a', 1, new Torre(board, Color.WHITE));
		placeNewPiece('b', 1, new Cavalo(board, Color.WHITE));
		placeNewPiece('c', 1, new Bispo(board, Color.WHITE));
		placeNewPiece('d', 1, new Rainha(board, Color.WHITE));
        placeNewPiece('e', 1, new Rei(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bispo(board, Color.WHITE));
        placeNewPiece('g', 1, new Cavalo(board, Color.WHITE));
        placeNewPiece('h', 1, new Torre(board, Color.WHITE));
        placeNewPiece('a', 2, new Peao(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Peao(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Peao(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Peao(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Peao(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Peao(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Peao(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Peao(board, Color.WHITE, this));
        

		placeNewPiece('a', 8, new Torre(board, Color.BLACK));
		placeNewPiece('b', 8, new Cavalo(board, Color.BLACK));
		placeNewPiece('c', 8, new Bispo(board, Color.BLACK));
		placeNewPiece('d', 8, new Rainha(board, Color.BLACK));
        placeNewPiece('e', 8, new Rei(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bispo(board, Color.BLACK));
        placeNewPiece('g', 8, new Cavalo(board, Color.BLACK));
        placeNewPiece('h', 8, new Torre(board, Color.BLACK));
        placeNewPiece('a', 7, new Peao(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Peao(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Peao(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Peao(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Peao(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Peao(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Peao(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Peao(board, Color.BLACK, this));
		
	}
}
