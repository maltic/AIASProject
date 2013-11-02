package neuralnet;

public class BiasNN extends BasicNN {

	protected int[] oldLayers;

	public BiasNN(int[] l) {
		oldLayers = l.clone();
		layers = l.clone();
		layers[0]++;
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
		double[] newInput = new double[input.length + 1];
		System.arraycopy(input, 0, newInput, 0, input.length);
		newInput[input.length] = 1.0;
		return super.feedForward(newInput);
	}

	@Override
	public double threshold(double o) {
		//sigmoid function
		if (o < -15)
			return 0;
		if (o > 15)
			return 1;
		return 1 / (1 + Math.exp(-o));

	}

	@Override
	public int[] getLayers() {
		return this.oldLayers.clone();
	}

}
