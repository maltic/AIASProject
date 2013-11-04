package maxsbots;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import robocode.*;
import robotrain.BattleRunner;
import strategies.RobotData;
import strategies.Strategy;
import strategies.StrategyFactory;

/**
 * A bot controlled by a simple script. The script is a sequence of strategies
 * chosen by ACO or a GA.
 * 
 * @author Max
 * 
 */
public class ScriptBot extends AdvancedRobot {

	/**
	 * Instances of the strategies used
	 */
	private Strategy movement, firing, scanning, gun;
	/**
	 * Knowledge of robot, used to send to strategies
	 */
	protected RobotData data;

	public void run() {
		// load from file
		try {
			FileReader fr = new FileReader(BattleRunner.RobocodePath
					+ "/robots/maxsbots/scriptBotSpec");
			Scanner sc = new Scanner(fr);
			StrategyFactory sf = new StrategyFactory();
			movement = sf.getMovementStrategy(sc.nextInt());
			firing = sf.getFireStrategy(sc.nextInt());
			scanning = sf.getScanStrategy(sc.nextInt());
			gun = sf.getGunStrategy(sc.nextInt());
			sc.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// config and setup
		double[] move;
		data = new RobotData();
		this.setAdjustRadarForGunTurn(true);
		this.setAdjustGunForRobotTurn(true);
		this.setAdjustRadarForRobotTurn(true);
		while (true) {
			data.scannedAge++;
			// update robot data
			data.heading = this.getHeading();
			data.gunHeading = this.getGunHeading();
			data.scannerHeading = this.getRadarHeading();
			data.x = this.getX();
			data.y = this.getY();
			// do movement
			move = movement.getNextMove(data);
			this.setAhead(move[0]);
			this.setTurnLeft(move[1]);
			// do scanning
			move = scanning.getNextMove(data);
			this.setTurnRadarLeft(move[0]);
			// move gun
			move = gun.getNextMove(data);
			this.setTurnGunLeft(move[0]);

			// fire if we might hit
			double rightDir = Math.toDegrees(Math.atan2(data.scannedY - data.y,
					data.scannedX - data.x));
			double diff = Math.abs(rightDir - data.gunHeading);
			double dist = Math.min(diff, 360 - diff);
			if (fireThreshold(dist, data.scannedAge)) {
				this.setFire(firing.getNextMove(data)[0]);
			}
			this.execute();
		}
	}

	/**
	 * @param dist
	 *            Angular distance
	 * @param age
	 *            Age of last scan
	 * @return Fire or not
	 */
	protected boolean fireThreshold(double dist, int age) {
		return Math.abs(dist) < 10 && data.scannedAge < 5;
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		// save all the informaton!
		data.scannedEnergy = e.getEnergy();
		data.scannedDirection = e.getHeading();
		data.scannedVelocity = e.getVelocity();
		data.scannedDistance = e.getDistance();
		data.scannedBearing = e.getBearing();
		data.scannedX = data.x + Math.cos(Math.toRadians(data.scannerHeading))
				* data.scannedDistance;
		data.scannedY = data.y + Math.sin(Math.toRadians(data.scannerHeading))
				* data.scannedDistance;
		data.scannedAge = 0;
	}

}
