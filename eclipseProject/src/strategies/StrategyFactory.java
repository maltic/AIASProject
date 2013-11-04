package strategies;

import strategies.firing.*;
import strategies.gun.*;
import strategies.movement.*;
import strategies.scanning.*;

/**
 * Used to create strategies from IDs
 * @author Max
 *
 */
public class StrategyFactory {
	public static final int MovementStrategies = 6;
	public static final int FireStrategies = 6;
	public static final int ScanStrategies = 4;
	public static final int GunStrategies = 5;

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
			return new FastFollowScannerGun();
		case 2:
			return new SlowSpinGun();
		case 3:
			return new FastSpinGun();
		case 4:
			return new FollowEnemyGun();
		default:
			return null;
		}
	}
}
