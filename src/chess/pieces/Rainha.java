package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rainha extends ChessPiece {

	public Rainha(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possibleMoves() {

		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// verificando acima da minha peça
		p.setValues(position.getRow() - 1, position.getColumn());

		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posica p exister e não
																				// tiver uma peça la (!getBoard)

			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {

			mat[p.getRow()][p.getColumn()] = true;
		}

		// verificando a esquerda da minha peça
		p.setValues(position.getRow(), position.getColumn() - 1);

		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posica p exister e não
																				// tiver uma peça la (!getBoard)

			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {

			mat[p.getRow()][p.getColumn()] = true;
		}

		// verificando a direita da minha peça
		p.setValues(position.getRow(), position.getColumn() + 1);

		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posica p exister e não
																				// tiver uma peça la (!getBoard)

			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {

			mat[p.getRow()][p.getColumn()] = true;
		}

		// verificando abaixo da minha peça
		p.setValues(position.getRow() + 1, position.getColumn());

		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posica p exister e não
																				// tiver uma peça la (!getBoard)

			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {

			mat[p.getRow()][p.getColumn()] = true;
		}

		// noroeste
		p.setValues(position.getRow() - 1, position.getColumn() - 1);

		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posica p exister e não
																				// tiver uma peça la (!getBoard)

			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {

			mat[p.getRow()][p.getColumn()] = true;
		}

		// nordeste
		p.setValues(position.getRow() - 1, position.getColumn() + 1);

		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posica p exister e não
																				// tiver uma peça la (!getBoard)

			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {

			mat[p.getRow()][p.getColumn()] = true;
		}

		// Sudeste
		p.setValues(position.getRow() + 1, position.getColumn() + 1);

		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posica p exister e não
																				// tiver uma peça la (!getBoard)

			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {

			mat[p.getRow()][p.getColumn()] = true;
		}

		// Sudoeste
		p.setValues(position.getRow() + 1, position.getColumn() - 1);

		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posica p exister e não
																				// tiver uma peça la (!getBoard)

			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {

			mat[p.getRow()][p.getColumn()] = true;
		}

		return mat;
	}
}