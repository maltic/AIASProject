package strategies.firing;

import strategies.RobotData;
import strategies.Strategy;

public class FireIfSlow implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		return (data.scannedVelocity < 2.0) ? new double[] { 1.0 }
				: new double[] { 0.0 };
	}

}
