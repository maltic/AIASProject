package neuralnet.evo;

import java.util.Random;

import neuralnet.NeuralNetwork;

public class Genome implements Comparable<Genome> {
	protected final double[] weights;
	protected final double fitness;

	protected static class FitCalc {
		private FitnessArbiter f;
		private NeuralNetwork nn;

		public FitCalc(FitnessArbiter f, NeuralNetwork nn) {
			this.f = f;
			this.nn = nn;
		}

		public double fitness(Genome g) {
			nn.setWeights(g.weights);
			return f.fitness(nn);
		}

	}

	public double getFitness() {
		return fitness;
	}

	public double[] getWeights() {
		return this.weights.clone();
	}

	protected static Random r = new Random();

	public Genome(FitCalc f, double[] w) {
		weights = w.clone();
		fitness = f.fitness(this);

	}

	public Genome(FitCalc f, int size) {
		weights = new double[size];
		for (int i = 0; i < size; ++i)
			weights[i] += (r.nextBoolean()) ? r.nextDouble() * 16 : -r
					.nextDouble() * 16;
		fitness = f.fitness(this);
	}

	public Genome mutate(FitCalc f) {
		int mutations = r.nextInt(weights.length / 2);
		double[] w = this.weights.clone();
		for (int i = 0; i < mutations; ++i) {
			w[r.nextInt(weights.length)] += (r.nextBoolean()) ? r.nextDouble() * 3
					: -r.nextDouble() * 3;
		}
		return new Genome(f, w);
	}

	public Genome crossover(FitCalc f, Genome other) {
		int split = r.nextInt(weights.length - 1);
		double[] w = new double[weights.length];
		for (int i = 0; i < split; ++i)
			w[i] = weights[i];
		for (int i = split; i < weights.length; ++i)
			w[i] = other.weights[i];
		return new Genome(f, w);
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
