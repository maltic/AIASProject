package strategies.movement;

import strategies.RobotData;
import strategies.Strategy;

public class TightCircleMovement implements Strategy {
	public double[] getNextMove(RobotData data) {
		return new double[] { 4.0, 6.0 };
	}
}
