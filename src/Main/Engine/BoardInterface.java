package Main.Engine;

import Main.Engine.EvaluationInterface;
import Main.Engine.MoveGeneratorInterface;
import Main.Engine.SearchInterface;

import java.io.Serializable;
import java.util.Hashtable;

/**
 *
 */
public interface BoardInterface extends Serializable {

	String getBestmove();

	void setBestmove(String bestmove);

	void printBoard();

	/**
	 * Execute the move and remember possibly lost information. (e.g. in chess captured piece, castling right, e.p.)
	 * These will have to be restored when a corresponding unmakeMove is called.
	 * @param move The move to be made. Usually the format will be startSquare endSquare however details are left
	 *             to the implementation.
	 * @return An integer token to be returned when unmaking the move.
	 */
	int makeMove(int move);

	/**
	 * @param move
	 */
	void unmakeMove(int move, int token);

	short[] getRootMoves();

	void setRootMoves(short[] rootMoves);

	byte getSquare(int square);

	boolean getToMove();

	int getMoveNumber();

	BoardInterface cloneBoard();

	Hashtable<Long, Node> getHashTable();

	long getZobristHash();

	void changeToMove();

	int getPieceAdvancement(int piece);

	short getMaterialCount();

	int getPiecesLeft();

	void putHashTableElement(Node node);

	void makeMove(String move);

	SearchInterface getSearch();

	MoveGeneratorInterface[] getMoveGenerators();

	MoveGeneratorInterface getMoveGenerator();

	EvaluationInterface getEvaluation();

	BitBoardInterface getBitboard();

	void setBitboard(BitBoardInterface bitboard);

	int[] getDwarfLocations();

	int[] getTrollLocations();
}
