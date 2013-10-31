package strategies.movement;

import strategies.RobotData;
import strategies.Strategy;

public class BackwardCircleMovement implements Strategy {
	public double[] getNextMove(RobotData data) {
		return new double[] { -7.0, 2.0 };
	}
}

