package boardgame;

public abstract class Piece { //Classe peça
	
	protected Position position; //protected pq esse tipo de posição nao é posição do xadrez. Ela é uma posição simples de matiz. Eu não quero que essa posição seja visivel na camada de xadrez.
	private Board board; //toda peça tem 1 tabuleiro
	
	
	public Piece(Board board) {
		
		this.board = board;
		position = null;   //posicao de uma peça recém criada vai ser inicialmente nula pois não foi colocada ainda.
	}

	protected Board getBoard() { //somente classes dentro do mesmo pacote e sublclasses podem acessar o tabuleiro de uma peça.
		return board;		
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) {
		
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	//verificando se existe pelo meno 1 movimento possível para essa peça
	public boolean isThereAnyPossibleMove() {
		
		boolean[][] mat = possibleMoves();
		
		for(int i = 0 ; i < mat.length ; i++) {
			for(int j = 0 ; j < mat.length ; j++) {
				
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
}
