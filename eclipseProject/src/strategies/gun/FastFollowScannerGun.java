package strategies.gun;

import strategies.RobotData;
import strategies.Strategy;

public class FastFollowScannerGun implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		if (data.gunHeading < data.scannerHeading)
			return new double[] { -10.0 };
		else if (data.gunHeading > data.scannerHeading)
			return new double[] { 10.0 };
		else
			return new double[] { 0.0 };
	}

}