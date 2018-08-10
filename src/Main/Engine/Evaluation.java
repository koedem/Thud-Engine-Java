package Main.Engine;

/**
 *
 */
public class Evaluation implements EvaluationInterface {

	private boolean materialOnly = false;
	private BoardInterface board;

	public Evaluation(BoardInterface board) {
		this.board = board;
	}

	@Override
	public void resetEvaluation() {
		// TODO implement
	}

	@Override
	public int evaluation(boolean toMove, int lowBound) {
		board.getSearch().incrementNodes();
		return 0;// TODO implement
	}

	@Override
	public void setMaterialOnly(boolean materialOnly) {
		this.materialOnly = materialOnly;
	}
}
