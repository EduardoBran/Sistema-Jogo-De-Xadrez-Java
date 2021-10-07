package chess;

import boardgame.Board;
//partida de xadrez . Coração do projeto. Onde vão ter as regras do sistema
public class ChessMatch { 
	
	private Board board; //partida de xadrez TEM que ter 1 tabuleiro
	
	
	public ChessMatch() {
		 
		board = new Board(8 , 8); //dimensão do tabuleiro de xadrez
	}
	
	public ChessPiece[][] getPieces(){ //Retornar um matriz de peças de xadrez correspondente a essa partida.
		
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		
		for(int i = 0 ; i < board.getRows() ; i++) { //percorrer a matriz de peças do tabuleiro (board) e pra cada peça do meu tabuleiro, faço um downcasting para ChessPiece
			
			for(int j = 0 ; j < board.getColumns() ; j++) {
				
				mat[i][j] = (ChessPiece) board.piece(i,j); //(ChessPiece) downcasting para ChessPiece
			}
		}
		return mat; //retorna a matriz de peça da minha partida de xadrez
	}	
}
