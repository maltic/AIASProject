package strategies.firing;

import strategies.RobotData;
import strategies.Strategy;

public class MaxStrengthFire implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		return new double[] { 3.0 };
	}

}
