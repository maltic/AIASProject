package maxsbots;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import neuralnet.BiasNN;
import neuralnet.NeuralNetwork;
import robocode.*;
import robotrain.BattleRunner;

public class NNFireBot extends AdvancedRobot {

	private NeuralNetwork nn;

	public void run() {
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
		while (true) {
			ahead(10);
			turnLeft(10);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {

		double x = getX() / 800.0;
		double y = getY() / 600.0;
		double heading = getHeading() / 360.0;
		double dist = e.getDistance() / 1000.0;
		double velocity = (e.getVelocity() + 8.0) / 16.0;
		double eheading = e.getHeading() / 360.0;
		double out = nn.feedForward(new double[] { x, y, heading,
				velocity, eheading, dist })[0];
		if (out > 0.5)
			fire(1.0);
	}
}
