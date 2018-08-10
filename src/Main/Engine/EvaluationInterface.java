package Main.Engine;

import java.io.Serializable;

/**
 *
 */
public interface EvaluationInterface extends Serializable {

	void resetEvaluation();

	int evaluation(boolean toMove, int lowBound);

	void setMaterialOnly(boolean materialOnly);
}
