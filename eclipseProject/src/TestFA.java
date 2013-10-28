import neuralnet.NeuralNetwork;
import neuralnet.evo.*;

public class TestFA implements FitnessArbiter {
	static double[][] inputs = { { 1, 0, 0 }, { 1, 1, 1 }, { 0, 0, 0 },
			{ 0, 1, 0 }, { 0, 0, 1 }, { 1, 1, 0 }, { 1, 0, 1 }, { 0, 1, 1 } };
	static double[][] outputs = { { 1 }, { 0 }, { 0 }, { 1 }, { 1 }, { 0 },
			{ 0 }, { 0 } };

	@Override
	public double fitness(NeuralNetwork nn) {
		double err = 0.0;
		for (int i = 0; i < inputs.length; ++i) {
			double[] out = nn.feedForward(inputs[i]);
			for (int j = 0; j < out.length; ++j)
				err += Math.abs(outputs[i][j] - out[j]);
		}
		return -err;
	}

}
