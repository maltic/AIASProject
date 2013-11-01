package strategies.gun;

import strategies.RobotData;
import strategies.Strategy;
import robocode.util.*;

public class SlowFollowScannerGun implements Strategy {
	protected double speed = -1.1;

	@Override
	public double[] getNextMove(RobotData data) {
		double radarTurn = Math.toRadians(data.scannerHeading)
				- Math.toRadians(data.gunHeading);
		return new double[] { speed
				* Math.toDegrees((Utils.normalRelativeAngle(radarTurn))) };
	}

}
