package Main.Engine;

import java.io.Serializable;

/**
 *
 */
public interface BitBoardInterface extends Serializable {

	long getBitBoard(int toMove, int piece, int index);

	int getSquare(int square);

	void add(int piece, int colour, int square);

	void remove(int square);

	boolean move(int startSquare, int endSquare);

	void printBitBoard();
}
