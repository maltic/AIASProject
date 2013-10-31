package strategies.firing;

import strategies.RobotData;
import strategies.Strategy;

public class CloseFire implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		return (data.scannedDistance < 100.0) ? new double[] { 1.0 }
				: new double[] { 0.0 };
	}

}
