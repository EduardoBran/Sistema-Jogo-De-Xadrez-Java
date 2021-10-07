package boardgame;

public class Board { //Classe Tabuleiro
	
	private int rows;
	private int columns;
	private Piece[][] pieces; //matriz de peças (todo tabuleiro tem 1 ou mais peças)
	
	public Board(int rows, int columns) {
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; //minha matriz de peças é instanciada com Pieces na quantidade de linhas e colunas informadas
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	//método para retornar uma peça dada a uma linha / coluna.
	public Piece piece(int row, int column) {
		
		return pieces[row][column]; //retorna a matriz pieces na linha [row] e na coluna [column]
	}
	
	//método para retornar a peça pela posição
	public Piece piece(Position position) {
		
		return pieces[position.getRow()][position.getColumn()];
	}
	
	//método que recebe uma peça e uma posição e é responsável por colocar uma peça nessa posição do tabuleiro.
	public void placePiece(Piece piece, Position position) {
		
		pieces[position.getRow()][position.getColumn()] = piece; //vai na matriz de peças do tabuleiro na linha (position.getRow) e na coluna (position.getColumn) e vai atribuir a essa posição da minha matriz de peças a peça que veio como argumento. Pegamos a matriz na posição dada e atribuimos a ela a peça que eu informei.
		piece.position = position; //dizendo que a peça nao esta mais nula e sim na posição (position)
	}
}
