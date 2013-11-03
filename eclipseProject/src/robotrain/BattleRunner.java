package robotrain;

import java.io.File;

import robocode.BattleResults;
import robocode.control.*;
import robocode.control.events.*;

/**
 * Used to run robocode battles maintains an instance of the engine so a new JVM
 * doesn't need to be started for every battle
 * 
 * @author Max
 * 
 */
public class BattleRunner extends BattleAdaptor {

	//public static final String RobocodePath = "C:/robocode";
	public static final String RobocodePath = "/home/mitch/robocode";
	/**
	 * @author Max A callback class that listens to the robocode engine
	 * 
	 */
	private class BattleObserver extends BattleAdaptor {

		public BattleResults[] results;

		// Called when the battle is completed successfully with battle results
		public void onBattleCompleted(BattleCompletedEvent e) {
			// System.out.println("-- Battle has completed --");
			results = e.getIndexedResults();

		}

		// Called when the game sends out an information message during the
		// battle
		public void onBattleMessage(BattleMessageEvent e) {
			// Ignore all messages
		}

		// Called when the game sends out an error message during the battle
		public void onBattleError(BattleErrorEvent e) {
			System.err.println("Err> " + e.getError());
		}
	}

	private RobocodeEngine eninge;
	private BattleObserver observer;

	public BattleRunner() {
		eninge = new RobocodeEngine(new File(RobocodePath));
		observer = new BattleObserver();
		eninge.setVisible(true);
		eninge.addBattleListener(observer);
	}

	/**
	 * Run a robocode battle
	 * 
	 * @param visible
	 *            Toggle for robocode visibility
	 * @param robotNames
	 *            The robocode robots in the battle (eg.
	 *            "sample.MyFirstRobot,sample.RamFire")
	 * 
	 * @param numRounds
	 *            Number of rounds in a battle
	 */
	public BattleResults[] runBattle(boolean visible, String robotNames,
			int numRounds) {
		//make the specification of the battle based on input fields
		BattlefieldSpecification bfs = new BattlefieldSpecification();
		RobotSpecification[] robots = eninge.getLocalRepository(robotNames);
		BattleSpecification spec = new BattleSpecification(numRounds, bfs,
				robots);
		//run the battle and return results
		eninge.runBattle(spec, true);
		return observer.results;
	}
}
