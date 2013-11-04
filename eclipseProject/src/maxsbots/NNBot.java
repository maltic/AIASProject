package maxsbots;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import neuralnet.BiasNN;
import neuralnet.NeuralNetwork;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robotrain.BattleRunner;

/**
 * Neural network controlled robot. Uses a NN to control all actions of the bot,
 * and which takes as input all the bots knowledge.
 * 
 * @author Max
 * 
 */
public class NNBot extends AdvancedRobot {

	/**
	 * The controller
	 */
	private NeuralNetwork nn;

	/**
	 * Data collected when the enemy was last scanned
	 */
	private double scannedHeading, scannedVelocity, scannedEnergy, scannedX,
			scannedY;
	/**
	 * Age (in turns) of the last scan. Start off as 'infinitely' large
	 */
	private int scanAge = 9999;

	public void run() {
		// load the neural network from file
		try {
			FileReader fr = new FileReader(BattleRunner.RobocodePath
					+ "/robots/maxsbots/roboSpec");
			Scanner sc = new Scanner(fr);
			int nLayers = sc.nextInt();
			int[] layers = new int[nLayers];
			for (int i = 0; i < nLayers; ++i)
				layers[i] = sc.nextInt();
			nn = new BiasNN(layers);
			double[] weights = new double[nn.getNumWeights()];
			for (int i = 0; i < weights.length; ++i)
				weights[i] = sc.nextDouble();
			nn.setWeights(weights);
			sc.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// config
		double width = getBattleFieldWidth(), height = getBattleFieldHeight();
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		while (true) {
			// create a normalized input vector
			double[] input = new double[] { scannedHeading / 360,
					(scannedVelocity + 8) / 16, scannedEnergy / 100,
					scannedX / width, scannedY / height,
					Math.max(scanAge / 15.0, 1.0), getX() / width,
					getY() / height, getEnergy() / 100, getHeading() / 360,
					getRadarHeading() / 360, getGunHeading() / 360,
					(getVelocity() + 8) / 16 };
			// feed into neural net
			double[] out = nn.feedForward(input);
			// execute neural net actions
			// these all need to be scaled to the robocode max/min
			setAhead(out[0] * 4 - 2);
			setTurnLeft(out[1] * 20 - 10);
			setTurnGunLeft(out[2] * 40 - 20);
			setTurnRadarLeft(out[3] * 90 - 45);
			out[4] *= 3;
			if (out[4] > 0.1)
				setFire(out[4]);
			execute();
			scanAge++;
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		// save the scanned information
		scannedHeading = e.getHeading();
		double scannedDistance = e.getDistance();
		scannedVelocity = e.getVelocity();
		scannedEnergy = e.getEnergy();
		scannedX = getX() + Math.cos(getRadarHeadingRadians())
				* scannedDistance;
		scannedY = getY() + Math.sin(getRadarHeadingRadians())
				* scannedDistance;
		scanAge = 0;
	}
}
