package Main.MultiThreading;

import Main.Engine.BoardInterface;
import Main.Engine.MoveGenerator;
import Main.EngineIO.Logging;
import Main.EngineIO.Transformation;
import Main.EngineIO.UCI;

import java.util.concurrent.Callable;

public class MultiThreadSearch implements Callable<int[]> {

	private BoardInterface   board;
	private BoardInterface   oldBoard;
	private int     depth;
	private boolean moveOrdering;
	private long    timeLimit;
	
	public MultiThreadSearch(BoardInterface board, int depth, int threadNumber, boolean moveOrdering, long timeLimit) {
		this.oldBoard = board;
		this.board = board.cloneBoard();
		this.depth = depth;
		this.moveOrdering = moveOrdering;
		this.timeLimit = timeLimit;
	}
	
	@Override
	public int[] call() {
		long time = System.currentTimeMillis();
		board.getSearch().setNodes(0);
		board.getSearch().setAbortedNodes(0);
		board.getSearch().setQNodes(0);
		int[] move = null;
		board.setRootMoves(board.getMoveGenerators()[0].collectMoves(board.getToMove(), new short[MoveGenerator.MAX_MOVE_COUNT]));
		Logging.printLine("info search started at milli: " + System.currentTimeMillis());
		
		for (int i = 1; i <= depth; i++) {
			if (moveOrdering) {
				move = board.getSearch().rootMax(board.getToMove(), i, time);
			} else {
				move = board.getSearch().negaMax(board.getToMove(), i, i, -30000, 30000);
			}
			
			if (Math.abs(move[move.length - 1]) > 9000) {
				break;
			}
			
			if (System.currentTimeMillis() - time > timeLimit                   // We break if the time is up
			    && board.getSearch().getNodes() > timeLimit * UCI.getLowerKN_Bound() // and we searched enough nodes.
			    || board.getSearch().getNodes() > timeLimit * UCI.getUpperKN_Bound() // Or when we searched more than enough nodes.
                    || board.getBestmove().equals("(none)")) { // or there are no legal moves
				break;
			}
			oldBoard.setBestmove(Transformation.numberToMove(move[0])); // tell the uci thread the current best move
		}
		assert move != null;
		if (board.getBestmove().equals("(none)")) {
		    oldBoard.setBestmove("(none)");
			Logging.printLine("bestmove (none)");
		} else {
			UCI.printEngineOutput("", move, board, board.getToMove(), time);

			Logging.printLine("bestmove " + Transformation.numberToMove(move[0]));
		}
		return move;
	}
}
