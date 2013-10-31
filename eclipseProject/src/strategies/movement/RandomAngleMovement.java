package strategies.movement;

import java.util.Random;

import strategies.RobotData;
import strategies.Strategy;

public class RandomAngleMovement implements Strategy {
	private static double[] move = new double[] { 3.5, 0.0 };
	int counter = 0;
	int turnCount = 0;
	Random r = new Random();

	public double[] getNextMove(RobotData data) {
		if (counter == 0) {
			counter = r.nextInt(10) + 75;
			turnCount = 1 + r.nextInt(25);
		} else
			counter--;
		if (turnCount > 0) {
			move[1] = 5.0;
			turnCount--;
		}
		else
			move[1] = 0.0;
		return move.clone();
	}
}