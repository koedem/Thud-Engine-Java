package Main.Engine;

import Main.EngineIO.Logging;
import Main.EngineIO.Transformation;

/**
 *
 */
public class Perft {

	BoardInterface board;
	short[][] moves = new short[100][MoveGenerator.MAX_MOVE_COUNT];

	public Perft(BoardInterface board) {
		this.board = board;
	}

	public void rootPerft(int depth) {
		long startTime = System.currentTimeMillis();
		for (int i = 1; i <= depth; i++) {
			long result = perft(i, 0);
			long timeDiff = System.currentTimeMillis() - startTime + 1;
			Logging.printLine("Perft depth " + Integer.toString(i) + ": " + Long.toString(result) + " (" + Transformation.nodeCountOutput(result) + ") in "
			                  + Transformation.timeUsedOutput(timeDiff) + " with " + Transformation.nodeCountOutput(result * 1000 / timeDiff) + "/s.");
		}
	}

	public long perft(int depthLeft, int depthSoFar) {
		moves[depthSoFar] = board.getMoveGenerator().collectMoves(board.getToMove(), moves[depthSoFar]);
		if (depthLeft > 1) {
			long perftCount = 0;
			for (int index = 1; index <= moves[depthSoFar][0]; index++) {
				int token = board.makeMove(Short.toUnsignedInt(moves[depthSoFar][index]));
				perftCount += perft(depthLeft - 1, depthSoFar + 1);
				board.unmakeMove(Short.toUnsignedInt(moves[depthSoFar][index]), token);
			}
			return perftCount;
		} else {
			return moves[depthSoFar][0];
		}
	}
}
