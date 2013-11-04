package maxsbots;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import robocode.*;
import robotrain.BattleRunner;

/**
 * A simple bot that goes in circles and shoots an enemy when it sees it. We
 * used this bot to collect information about firing for statistical analysis.
 * Unfortunaltey we didn't get time to follow through with learning from the
 * data collected.
 * 
 * @author Max
 * 
 */
public class DataBot extends AdvancedRobot {

	/**
	 * Meta-Information about a bullet collected at the time it was fired
	 * 
	 * @author Max
	 * 
	 */
	private class BulletData {
		public double x, y, dist, energyMe, energyEnemy, velocity, heading;

		public BulletData(Bullet b, double x, double y, double dist, double em,
				double ee, double v, double h) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.energyMe = em;
			this.energyEnemy = ee;
			this.velocity = v;
			this.heading = h;
		}
	}

	private ArrayList<Bullet> hits, misses;
	private HashMap<Bullet, BulletData> bulletMap;

	public void run() {
		out.println(this.getEnergy());
		hits = new ArrayList<Bullet>();
		misses = new ArrayList<Bullet>();
		new ArrayList<BulletData>();
		bulletMap = new HashMap<Bullet, BulletData>();
		while (true) {
			setAhead(10);
			setTurnLeft(10);
			this.execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		Bullet b = setFireBullet(1.0);
		this.bulletMap.put(b,
				new BulletData(b, getX(), getY(), e.getDistance(), getEnergy(),
						e.getEnergy(), e.getVelocity(), e.getHeading()));
	}

	public void onBulletHit(BulletHitEvent event) {
		this.hits.add(event.getBullet());
	}

	public void onBulletMissed(BulletMissedEvent event) {
		this.misses.add(event.getBullet());
	}

	public void onRoundEnded(RoundEndedEvent event) {
		// append all the hit and miss information to file
		try {
			RobocodeFileWriter fw = new RobocodeFileWriter(
					BattleRunner.RobocodePath
							+ "/robots/maxsbots/DataBot.data/data", true);
			fw.append("" + hits.size() + System.lineSeparator());
			for (Bullet b : hits) {
				BulletData bd = bulletMap.get(b);
				fw.append("" + bd.x + System.lineSeparator());
				fw.append("" + bd.y + System.lineSeparator());
				fw.append("" + b.getHeading() + System.lineSeparator());
				fw.append("" + b.getPower() + System.lineSeparator());
				fw.append("" + bd.dist + System.lineSeparator());
				fw.append("" + bd.energyEnemy + System.lineSeparator());
				fw.append("" + bd.energyMe + System.lineSeparator());
				fw.append("" + bd.velocity + System.lineSeparator());
				fw.append("" + bd.heading + System.lineSeparator());
			}
			fw.append("" + misses.size() + System.lineSeparator());
			for (Bullet b : misses) {
				BulletData bd = bulletMap.get(b);
				fw.append("" + bd.x + System.lineSeparator());
				fw.append("" + bd.y + System.lineSeparator());
				fw.append("" + b.getHeading() + System.lineSeparator());
				fw.append("" + b.getPower() + System.lineSeparator());
				fw.append("" + bd.dist + System.lineSeparator());
				fw.append("" + bd.energyEnemy + System.lineSeparator());
				fw.append("" + bd.energyMe + System.lineSeparator());
				fw.append("" + bd.velocity + System.lineSeparator());
				fw.append("" + bd.heading + System.lineSeparator());
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
