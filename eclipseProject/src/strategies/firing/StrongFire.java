package strategies.firing;

import strategies.RobotData;
import strategies.Strategy;

public class StrongFire implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		return new double[] { 2.5 };
	}

}
