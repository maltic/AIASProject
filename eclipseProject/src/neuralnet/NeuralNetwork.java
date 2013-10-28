package neuralnet;

public interface NeuralNetwork {

	public double[] feedForward(double[] input);

	public double threshold(double o);

	public int getNumWeights();

	public double[] getWeights();

	public int[] getLayers();

	public void setWeights(double[] newWeights);
}
