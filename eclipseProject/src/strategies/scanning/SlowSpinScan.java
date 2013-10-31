package strategies.scanning;

import strategies.RobotData;
import strategies.Strategy;

public class SlowSpinScan implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		return new double[] { 5.0 };
	}

}
