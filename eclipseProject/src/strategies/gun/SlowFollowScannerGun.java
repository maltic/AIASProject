package strategies.gun;

import strategies.RobotData;
import strategies.Strategy;

public class SlowFollowScannerGun implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		if (data.gunHeading < data.scannerHeading)
			return new double[] { -3.0 };
		else if (data.gunHeading > data.scannerHeading)
			return new double[] { 3.0 };
		else
			return new double[] { 0.0 };
	}

}
