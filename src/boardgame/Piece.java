package boardgame;

public class Piece { //Classe peça
	
	protected Position position; //protected pq esse tipo de posição nao é posição do xadrez. Ela é uma posição simples de matiz. Eu não quero que essa posição seja visivel na camada de xadrez.
	private Board board;
	
	public Piece(Board board) {
		
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}

	
	
}
