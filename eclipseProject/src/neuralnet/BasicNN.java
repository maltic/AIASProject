package neuralnet;

public class BasicNN implements NeuralNetwork {
	protected int[] layers;
	protected double[][][] weights;
	protected double[][] outputs;
	protected int numWeights;

	public BasicNN(int[] layers) {
		this.layers = layers.clone();
		weights = new double[layers.length][][];
		outputs = new double[layers.length][];
		for (int i = 0; i < weights.length - 1; ++i) {
			weights[i] = new double[layers[i]][layers[i + 1]];
			numWeights += layers[i] * layers[i + 1];
		}
		for (int i = 0; i < layers.length; ++i)
			outputs[i] = new double[layers[i]];
	}

	@Override
	public double[] feedForward(double[] input) {
		// clear outputs
		for (int i = 0; i < outputs.length; ++i)
			for (int j = 0; j < outputs[i].length; ++j)
				outputs[i][j] = 0.0;
		if (input.length != outputs[0].length)
			return null;
		outputs[0] = input.clone();
		for (int i = 1; i < weights.length; ++i) {
			for (int j = 0; j < weights[i - 1].length; ++j) {
				for (int k = 0; k < weights[i - 1][j].length; ++k) {
					outputs[i][k] += outputs[i - 1][j] * weights[i - 1][j][k];
				}
			}
			for (int k = 0; k < outputs[i].length; ++k)
				outputs[i][k] = threshold(outputs[i][k]);
		}
		return outputs[outputs.length - 1].clone();
	}

	@Override
	public int getNumWeights() {
		return this.numWeights;
	}

	@Override
	public void setWeights(double[] newWeights) {
		if (newWeights.length != this.numWeights) {
			System.err.println("Wrong number of weights");
			return;
		}
		int ind = 0;
		for (int i = 0; i < weights.length - 1; ++i)
			for (int j = 0; j < weights[i].length; ++j)
				for (int k = 0; k < weights[i][j].length; ++k)
					weights[i][j][k] = newWeights[ind++];
	}

	@Override
	public double threshold(double o) {
		return (o > 0.0) ? 1.0 : 0.0;
		/*
		 * if (o < -15) return 0; if (o > 15) return 1; return 1 / (1 +
		 * Math.exp(-o));
		 */
	}

	@Override
	public double[] getWeights() {
		double[] w = new double[this.numWeights];
		int ind = 0;
		for (int i = 0; i < weights.length - 1; ++i)
			for (int j = 0; j < weights[i].length; ++j)
				for (int k = 0; k < weights[i][j].length; ++k)
					w[ind++] = weights[i][j][k];
		return w;

	}

	@Override
	public int[] getLayers() {
		return this.layers.clone();
	}
}
