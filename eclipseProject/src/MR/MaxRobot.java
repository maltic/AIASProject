package MR;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import neuralnet.BasicNN;
import neuralnet.NeuralNetwork;
import robocode.*;

public class MaxRobot extends AdvancedRobot {
	private NeuralNetwork nn;

	public void run() {
		try {
			FileReader fr = new FileReader(
					"C:/robocode/robots/MR/roboSpec");
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
			this.setAhead(-100);
			this.setTurnLeft(1000);
			this.execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		double[] input = new double[3];
		input[0] = e.getDistance();
		input[1] = e.getVelocity();
		input[2] = e.getBearing();
		double[] out = nn.feedForward(input);
		if(out[0] > 0.00001)
			this.setFire(1.0);
	}

}
