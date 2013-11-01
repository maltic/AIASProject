package strategies.scanning;

import robocode.util.Utils;
import strategies.RobotData;
import strategies.Strategy;

public class SlowPredictiveScan implements Strategy {

	protected double speed = -1.1;

	@Override
	public double[] getNextMove(RobotData data) {
		double radarTurn = Math.toRadians(data.heading)
				+ Math.toRadians(data.scannedBearing)
				- Math.toRadians(data.scannerHeading);
		return new double[] { speed
				* Math.toDegrees((Utils.normalRelativeAngle(radarTurn))) };

	}
}