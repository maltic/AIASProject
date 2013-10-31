package strategies;

import strategies.firing.*;
import strategies.gun.SlowFollowScannerGun;
import strategies.movement.*;
import strategies.scanning.MatchSlowRobotScan;
import strategies.scanning.FastSpinScan;
import strategies.scanning.SlowPredictiveScan;
import strategies.scanning.SlowSpinScan;

public class StrategyFactory {
	public static final int MovementStrategies = 6;
	public static final int FireStrategies = 6;
	public static final int ScanStrategies = 4;
	public static final int GunStrategies = 2;

	public Strategy getMovementStrategy(int id) {
		switch (id) {
		case 0:
			return new CircleMovement();
		case 1:
			return new TightCircleMovement();
		case 2:
			return new BigCircleMovement();
		case 3:
			return new SpiralMovement();
		case 4:
			return new RandomAngleMovement();
		case 5:
			return new BackwardCircleMovement();
		default:
			return null;
		}
	}

	public Strategy getFireStrategy(int id) {
		switch (id) {
		case 0:
			return new WeakFire();
		case 1:
			return new StrongFire();
		case 2:
			return new CloseFire();
		case 3:
			return new FireIfSlow();
		case 4:
			return new MediumFire();
		case 5:
			return new MaxStrengthFire();
		default:
			return null;
		}
	}

	public Strategy getScanStrategy(int id) {
		switch (id) {
		case 0:
			return new MatchSlowRobotScan();
		case 1:
			return new FastSpinScan();
		case 2:
			return new SlowPredictiveScan();
		case 3:
			return new SlowSpinScan();
		default:
			return null;
		}
	}

	public Strategy getGunStrategy(int id) {
		switch (id) {
		case 0:
			return new SlowFollowScannerGun();
		case 1:
			return new SlowFollowScannerGun();
		default:
			return null;
		}
	}
}
