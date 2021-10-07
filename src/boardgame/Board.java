package boardgame;

public class Board { //Classe Tabuleiro
	
	private int rows;
	private int columns;
	private Piece[][] pieces; //matriz de peças
	
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
}
