package boardgame;

public class Board { //Classe Tabuleiro
	
	private int rows;
	private int columns;
	private Piece[][] pieces; //matriz de peças (todo tabuleiro tem 1 ou mais peças)
	
	public Board(int rows, int columns) {
		
		if (rows < 1 || columns < 1) { //verificação para ver se o tabuleiro tem pelo menos 1 linha ou 1 coluna
			
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column.");
		}
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; //minha matriz de peças é instanciada com Pieces na quantidade de linhas e colunas informadas
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	
	//método para retornar uma peça dada a uma linha / coluna.
	public Piece piece(int row, int column) {
		
		if(!positionExists(row, column)) {
			
			throw new BoardException("Position not on the board.");
		}
		return pieces[row][column]; //retorna a matriz pieces na linha [row] e na coluna [column]
	}
	
	//método para retornar a peça pela posição
	public Piece piece(Position position) {
		
		if(!positionExists(position)) {
			
			throw new BoardException("Position not on the board.");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	//método que recebe uma peça e uma posição e é responsável por colocar uma peça nessa posição do tabuleiro.
	public void placePiece(Piece piece, Position position) {
		
		if(thereIsAPiece(position)) { //teste para ver se ja existe 1 peça nesta posição
			
			throw new BoardException("There is already a piece on position " + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece; //vai na matriz de peças do tabuleiro na linha (position.getRow) e na coluna (position.getColumn) e vai atribuir a essa posição da minha matriz de peças a peça que veio como argumento. Pegamos a matriz na posição dada e atribuimos a ela a peça que eu informei.
		piece.position = position; //dizendo que a peça nao esta mais nula e sim na posição (position)
	}
	
	private boolean positionExists(int row, int column) {
		
		return row >= 0 && row < rows && column >= 0 && column< columns; //condição completa para ver se uma posição existe		
	}	
	
	public boolean positionExists(Position position) { //teste para ver se uma posição existe
		
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) { //teste para ver se tem uma peça nesta posição
		
			if(!positionExists(position)) {
			
			throw new BoardException("Position not on the board.");
		}
		return piece(position) != null;
	}
}
