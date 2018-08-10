package Main.Engine;

import Main.EngineIO.Logging;
import Main.EngineIO.Transformation;

/**
 *
 */
public class Perft {

	private BoardInterface board;
	private MoveGenerator[] moveGenerator;
	private short[][] moves = new short[100][MoveGenerator.MAX_MOVE_COUNT];

	public Perft(BoardInterface board) {
		this.board = board;
		MoveGeneratorInterface[] interfaces = board.getMoveGenerators();
		moveGenerator = new MoveGenerator[interfaces.length];
		for (int i = 0; i < interfaces.length; i++) {
			moveGenerator[i] = (MoveGenerator) interfaces[i];
		}
	}

	public void rootPerft(int depth) {
		long startTime = System.currentTimeMillis();
		for (int i = 1; i <= depth; i++) {
			long result = incrementalPerft(i, 0);
			long timeDiff = System.currentTimeMillis() - startTime + 1;
			Logging.printLine("Perft depth " + Integer.toString(i) + ": " + Long.toString(result) + " (" + Transformation.nodeCountOutput(result) + ") in "
			                  + Transformation.timeUsedOutput(timeDiff) + " with " + Transformation.nodeCountOutput(result * 1000 / timeDiff) + "/s.");
		}
	}

	private long perft(int depthLeft, int depthSoFar) {
		moves[depthSoFar] = moveGenerator[0].collectMoves(board.getToMove(), moves[depthSoFar]);
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

	private long incrementalPerft(int depthLeft, int depthSoFar) {
		MoveGenerator localMoveGenerator = this.moveGenerator[depthSoFar];
		long perftCount = 0;
		if (depthLeft > 1) {
			if (board.getToMove()) {
				if (!localMoveGenerator.setupDwarfMoveIterator()) { // i.e. there are no pieces on the board
					return 0;
				}
				int move;
				while ((move = localMoveGenerator.iterativeDwarfMoveGenerator()) > 0) {
					int token = board.makeMove(move);
					perftCount += incrementalPerft(depthLeft - 1, depthSoFar + 1);
					board.unmakeMove(move, token);
				}
			} else {
				if (!localMoveGenerator.setupTrollMoveIterator()) { // i.e. there are no pieces on the board
					return 0;
				}
				int move;
				while ((move = localMoveGenerator.iterativeTrollMoveGenerator()) > 0) {
					int token = board.makeMove(move);
					perftCount += incrementalPerft(depthLeft - 1, depthSoFar + 1);
					board.unmakeMove(move, token);
				}
			}
		} else {
			perftCount = moveGenerator[0].collectMoves(board.getToMove(), moves[depthSoFar])[0];
		}
		return perftCount;
	}
}
