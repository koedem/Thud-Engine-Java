package Main.Engine;

import java.io.Serializable;

/**
 *
 */
public interface MoveGeneratorInterface extends Serializable {

	void resetMoveGenerator();

	int[] activityEval(boolean toMove, int[] storage, int[] whiteSize);

	short[] collectCaptures(boolean toMove, short[] allCaptures);

	short[] collectMoves(boolean toMove, short[] allMoves);

	int[] collectAllPNMoves(int[] storage, Main.Engine.BoardInterface board, boolean toMove);

	int[] collectCheckMoves(int[] storage, int[] checks, Main.Engine.BoardInterface board, boolean toMove);

	int[] collectPNSearchMoves(int[] storage, int[] checks, Main.Engine.BoardInterface board, boolean toMove);
}
