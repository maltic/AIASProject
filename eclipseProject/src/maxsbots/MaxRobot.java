package maxsbots;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import neuralnet.BasicNN;
import neuralnet.NeuralNetwork;
import robocode.*;
import robotrain.BattleRunner;

public class MaxRobot extends AdvancedRobot {
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
			nn = new BasicNN(layers);
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
			this.setAhead(-10);
			this.setTurnLeft(10);
			this.execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		double[] inputs = new double[3];
		inputs[0] = e.getDistance();
		inputs[1] = this.getHeading();
		inputs[2] = this.getEnergy();
		double[] out = nn.feedForward(inputs);
		if (out[0] > 0.04)
			this.setFire(out[0] * 3);
		this.execute();
	}

}
