package maxsbots;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import robotrain.BattleRunner;
import neuralnet.BiasNN;
import neuralnet.NeuralNetwork;

public class NNScriptBot extends ScriptBot {
	NeuralNetwork nn;

	@Override
	public void run() {
		try {
			FileReader fr = new FileReader(BattleRunner.RobocodePath
					+ "/robots/maxsbots/scriptBotNNSpec");
			Scanner sc = new Scanner(fr);
			int[] layers = new int[sc.nextInt()];
			out.println(layers.length);
			for (int i = 0; i < layers.length; ++i)
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
		super.run();
	}

	@Override
	protected boolean fireThreshold(double dist, int age) {
		// input vector is normalized first
		double[] in = new double[] { (dist + 360) / 720,
				Math.max(age / 15.0, 1.0) };
		double[] out = nn.feedForward(in);
		return out[0] > 0.5;
	}
}
