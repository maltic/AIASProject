import java.io.File;

import robocode.BattleResults;
import robocode.control.*;
import robocode.control.events.*;
import neuralnet.NeuralNetwork;

public class BattleRunner extends BattleAdaptor {
	private class BattleObserver extends BattleAdaptor {

		public BattleResults[] br;

		// Called when the battle is completed successfully with battle results
		public void onBattleCompleted(BattleCompletedEvent e) {
			//System.out.println("-- Battle has completed --");
			br = e.getIndexedResults();

		}

		// Called when the game sends out an information message during the
		// battle
		public void onBattleMessage(BattleMessageEvent e) {
			//System.out.println("Msg> " + e.getMessage());
		}

		// Called when the game sends out an error message during the battle
		public void onBattleError(BattleErrorEvent e) {
			//System.out.println("Err> " + e.getError());
		}
	}

	RobocodeEngine re;
	BattleObserver bo;
	BattleSpecification bs;

	public BattleRunner() {
		re = new RobocodeEngine(new File("C:/robocode/"));
		bo = new BattleObserver();
		re.setVisible(true);
		re.addBattleListener(bo);
		BattlefieldSpecification bfs = new BattlefieldSpecification();
		RobotSpecification[] robots = re
				.getLocalRepository("sample.MyFirstRobot,MR.MaxRobot*");
		bs = new BattleSpecification(10, bfs, robots);
	}

	public BattleResults runBattle() {
		re.runBattle(bs, true);
		//System.out.println("Done");
		return bo.br[1];
	}
}
