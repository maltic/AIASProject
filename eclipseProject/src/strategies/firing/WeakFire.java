package strategies.firing;

import strategies.RobotData;
import strategies.Strategy;

public class WeakFire implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		return new double[] { 0.5 };
	}

}
