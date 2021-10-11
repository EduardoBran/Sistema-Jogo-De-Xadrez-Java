package chess;

import boardgame.Position;

public class ChessPosition { 
	
	private char column;
	private int row;
	
	
	public ChessPosition(char column, int row) {
		
		//programação defensiva
		if(column < 'a' || column > 'h' || row < 1 || row > 8) { //se coluna for menor que a e maior que h ou linha menor que 1 ou maior que 8
			
			throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8.");
		}			
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	protected Position toPosition() { //método para converter essa ChessPosition para o Position normal
	
		return new Position(8 - row, column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) {
		
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() {
		return "" + column + row;
	}
	
	
}
