package MR;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import neuralnet.BiasNN;
import neuralnet.NeuralNetwork;

public class NNScriptBot extends ScriptBot {
	NeuralNetwork nn;

	@Override
	public void run() {
		try {
			FileReader fr = new FileReader(
					"C:/robocode/robots/MR/scriptBotNNSpec");
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
		// i don't scale distance or age here, because age cannot be normalized
		double[] in = new double[] { dist, (double) age };
		double[] out = nn.feedForward(in);
		return out[0] > 0.5;
	}
}
