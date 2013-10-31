package MR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import robocode.*;

public class DataBot extends AdvancedRobot {

	private class BulletData {
		Bullet b;
		public double x, y, dist, energyMe, energyEnemy, velocity, heading;

		public BulletData(Bullet b, double x, double y, double dist, double em,
				double ee, double v, double h) {
			this.b = b;
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
	private ArrayList<BulletData> bulletData;
	private HashMap<Bullet, BulletData> bulletMap;

	public void run() {
		out.println(this.getEnergy());
		hits = new ArrayList<Bullet>();
		misses = new ArrayList<Bullet>();
		bulletData = new ArrayList<BulletData>();
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
		try {
			RobocodeFileWriter fw = new RobocodeFileWriter(
					"C:\\robocode\\robots\\MR\\DataBot.data\\data", true);
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
