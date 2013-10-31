package strategies.movement;

import strategies.RobotData;
import strategies.Strategy;

public class BigCircleMovement implements Strategy {

	public double[] getNextMove(RobotData data) {
		return new double[] { 10.0, 1.5 };
	}
}
