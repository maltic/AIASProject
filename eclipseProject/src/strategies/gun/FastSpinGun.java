package strategies.gun;

import strategies.RobotData;
import strategies.Strategy;

public class FastSpinGun implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		return new double[] { 13.0 };
	}
}
