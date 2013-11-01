package strategies.gun;

import robocode.util.Utils;
import strategies.RobotData;
import strategies.Strategy;

public class FollowEnemyGun implements Strategy {
	protected double speed = -1.1;

	@Override
	public double[] getNextMove(RobotData data) {
		if (data.scannedAge < 3) {
			double radarTurn = Math.toRadians(data.heading)
					+ Math.toRadians(data.scannedBearing)
					- Math.toRadians(data.gunHeading);
			return new double[] { speed
					* Math.toDegrees((Utils.normalRelativeAngle(radarTurn))) };
		} else
			return new double[] { 0.0 };
	}

}
