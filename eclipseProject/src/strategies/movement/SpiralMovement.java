package strategies.movement;

import strategies.RobotData;
import strategies.Strategy;

public class SpiralMovement implements Strategy {

	private double currTurn = 20.0;

	public double[] getNextMove(RobotData data) {
		currTurn *= 0.99;
		if (currTurn < 0.3)
			currTurn = 20.0;
		return new double[] { 2.5, currTurn };
	}

}
