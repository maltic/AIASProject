package neuralnet.evo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import neuralnet.NeuralNetwork;

public class NeuroEvolver {
	protected Genome[][] populations;
	protected NeuralNetwork nn;
	protected FitnessArbiter f;
	protected Genome.FitCalc fc;
	public float mutationRate = 0.5f;
	public float crossoverRate = 0.4f;
	public float trunkRatio = 0.4f;
	public int keepNum = 1;
	protected static Random r = new Random();

	public NeuroEvolver(NeuralNetwork nn, FitnessArbiter f, int popSize,
			int pops) {
		this.nn = nn;
		this.f = f;
		fc = new Genome.FitCalc(f, nn);
		populations = new Genome[pops][popSize];
		for (int a = 0; a < pops; ++a) {
			for (int i = 0; i < popSize; ++i)
				populations[a][i] = new Genome(fc, nn.getNumWeights());
			select(a);
		}
	}

	protected void select(int a) {
		Arrays.sort(populations[a]);
	}

	protected void breed(int a) {
		int breedNum = (int) (populations[a].length * trunkRatio);
		Genome[] cache = new Genome[breedNum];
		for (int i = 0; i < breedNum; ++i)
			cache[i] = this.populations[a][i];
		for (int i = keepNum; i < populations[a].length; ++i) {
			float n = r.nextFloat();
			if (n < mutationRate)
				populations[a][i] = cache[r.nextInt(breedNum)].mutate(fc);
			else if (n < mutationRate + crossoverRate)
				populations[a][i] = cache[r.nextInt(breedNum)].crossover(fc,
						cache[r.nextInt(breedNum)]);
			else
				populations[a][i] = new Genome(fc, nn.getNumWeights());
		}
	}

	public void evolveStep() {
		for (int a = 0; a < populations.length; ++a) {
			breed(a);
			select(a);
		}
		// perform a trade
		int popa = r.nextInt(populations.length);
		int popb = r.nextInt(populations.length);
		populations[popa][populations[popa].length - 1] = populations[popb][0];
		populations[popb][populations[popb].length - 1] = populations[popb][0];
		select(popa);
		select(popb);
	}

	public int getPopSize() {
		return this.populations.length * populations[0].length;
	}

	public List<Genome> getBest() {
		List<Genome> l = new ArrayList<Genome>();
		for (int a = 0; a < populations.length; ++a)
			l.add(populations[a][0]);
		return l;
	}

	public double getAvgFitness() {
		double tot = 0.0;
		for (int a = 0; a < populations.length; ++a)
			for (int b = 0; b < populations[a].length; ++b)
				tot += populations[a][b].fitness;
		return tot / (populations.length * populations[0].length);
	}
}
