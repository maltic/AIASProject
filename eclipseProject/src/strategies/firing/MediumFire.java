package strategies.firing;

import strategies.RobotData;
import strategies.Strategy;

public class MediumFire implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		return new double[] { 1.5 };
	}

}