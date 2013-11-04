package neuralnet.evo;

import java.util.Random;

import robotrain.GenericFitness;
import neuralnet.NeuralNetwork;

/**
 * A potential neural network weight configuration for neuroevolution
 * 
 * @author Max
 * 
 */
public class Genome implements Comparable<Genome> {
	protected final double[] weights;
	protected final double fitness;
	protected Random r = new Random();

	public double getFitness() {
		return fitness;
	}

	public double[] getWeights() {
		return this.weights.clone();
	}

	public Genome(GenericFitness<NeuralNetwork> f, NeuralNetwork nn, double[] w) {
		weights = w.clone();
		nn.setWeights(this.weights);
		fitness = f.calculateFitness(nn);

	}

	public Genome(GenericFitness<NeuralNetwork> f, NeuralNetwork nn, int size) {
		weights = new double[size];
		for (int i = 0; i < size; ++i)
			weights[i] += (r.nextBoolean()) ? r.nextDouble() * 15 : -r
					.nextDouble() * 15;
		nn.setWeights(this.weights);
		fitness = f.calculateFitness(nn);
	}

	public Genome mutate(GenericFitness<NeuralNetwork> f, NeuralNetwork nn) {
		int mutations = r.nextInt(weights.length / 2);
		double[] w = this.weights.clone();
		for (int i = 0; i < mutations; ++i) {
			w[r.nextInt(weights.length)] += (r.nextBoolean()) ? r.nextDouble() * 3
					: -r.nextDouble() * 3;
		}
		return new Genome(f, nn, w);
	}

	public Genome crossover(GenericFitness<NeuralNetwork> f, NeuralNetwork nn,
			Genome other) {
		int split = r.nextInt(weights.length - 1);
		double[] w = new double[weights.length];
		for (int i = 0; i < split; ++i)
			w[i] = weights[i];
		for (int i = split; i < weights.length; ++i)
			w[i] = other.weights[i];
		return new Genome(f, nn, w);
	}

	@Override
	public int compareTo(Genome o) {
		if (fitness < o.fitness)
			return 1;
		else if (fitness > o.fitness)
			return -1;
		else
			return 0;
	}

}
