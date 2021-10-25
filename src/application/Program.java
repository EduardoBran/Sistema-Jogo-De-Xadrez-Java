package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;


public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		//funcao para imprimir peças da partida
		while (!chessMatch.getCheckMate()) {
			
			try {
				UI.clearScreen(); //limpar tela
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				System.out.println();
				
				if(capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if (chessMatch.getPromoted() != null) {
					System.out.print("Enter piece for promotion (B / C / T / Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
				}				
			}
			catch (ChessException e) {
				System.out.println(e.getLocalizedMessage());
				sc.nextLine(); //programa aguardar eu digitar algo
			}
			catch (InputMismatchException e) {
				System.out.println(e.getLocalizedMessage());
				sc.nextLine(); //programa aguardar eu digitar algo
			}	
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}
}


