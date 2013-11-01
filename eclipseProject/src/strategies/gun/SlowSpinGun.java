package strategies.gun;

import strategies.RobotData;
import strategies.Strategy;

public class SlowSpinGun implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		return new double[] { 3.0 };
	}
}
