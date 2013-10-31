package strategies.scanning;

import strategies.RobotData;
import strategies.Strategy;

public class MatchSlowRobotScan implements Strategy {

	@Override
	public double[] getNextMove(RobotData data) {
		if (data.scannerHeading < data.heading)
			return new double[] { -3.0 };
		else if (data.scannerHeading > data.heading)
			return new double[] { 3.0 };
		else
			return new double[] { 0.0 };
	}

}
