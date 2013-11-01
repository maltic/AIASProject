package MR;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import robocode.*;
import robocode.util.Utils;
import strategies.RobotData;
import strategies.Strategy;
import strategies.StrategyFactory;

public class ScriptBot extends AdvancedRobot {

	private Strategy movement, firing, scanning, gun;
	protected RobotData data;

	public void run() {
		try {
			FileReader fr = new FileReader(
					"C:/robocode/robots/MR/scriptBotSpec");
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
		double[] move;
		data = new RobotData();
		out.println("fdsf");
		this.setAdjustRadarForGunTurn(true);
		this.setAdjustGunForRobotTurn(true);
		this.setAdjustRadarForRobotTurn(true);
		while (true) {
			data.scannedAge++;
			data.heading = this.getHeading();
			data.gunHeading = this.getGunHeading();
			data.scannerHeading = this.getRadarHeading();
			data.x = this.getX();
			data.y = this.getY();
			move = movement.getNextMove(data);
			this.setAhead(move[0]);
			this.setTurnLeft(move[1]);
			move = scanning.getNextMove(data);
			this.setTurnRadarLeft(move[0]);
			move = gun.getNextMove(data);
			this.setTurnGunLeft(move[0]);

			double rightDir = Math.toDegrees(Math.atan2(data.scannedY - data.y,
					data.scannedX - data.x));
			double diff = Math.abs(rightDir - data.gunHeading);
			double dist = Math.min(diff, 360 - diff);
			if (fireThreshold(dist, data.scannedAge)) {
				this.setFire(firing.getNextMove(data)[0]);
			}
			this.execute();
			// this.scan();
		}
	}

	protected boolean fireThreshold(double dist, int age) {
		return Math.abs(dist) < 10 && data.scannedAge < 5;
	}

	public void onScannedRobot(ScannedRobotEvent e) {
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
		// this.setFire(firing.getNextMove(data)[0]);
	}

}
