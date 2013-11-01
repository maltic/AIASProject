package MR;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import robocode.*;
import strategies.RobotData;
import strategies.Strategy;
import strategies.StrategyFactory;

public class ScriptBot extends AdvancedRobot {

	private Strategy movement, firing, scanning, gun;
	private RobotData data;

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
			double dist = Math.atan2(
					Math.sin(data.scannedDirection - data.gunHeading),
					Math.cos(data.scannedDirection - data.gunHeading));
			if (dist < 5.0 && data.scannedAge < 3)
				this.setFire(firing.getNextMove(data)[0]);
			this.execute();
			//this.scan();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		data.scannedEnergy = e.getEnergy();
		data.scannedDirection = e.getHeading();
		data.scannedVelocity = e.getVelocity();
		data.scannedDistance = e.getDistance();
		data.scannedBearing = e.getBearing();
		data.scannedAge = 0;
		// this.setFire(firing.getNextMove(data)[0]);
	}

}
