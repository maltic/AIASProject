package strategies.scanning;

import strategies.RobotData;
import strategies.Strategy;

public class FastSpinScan implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		return new double[] { 30.0 };
	}

}
