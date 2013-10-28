import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import neuralnet.*;
import neuralnet.evo.*;

class RCFitness implements FitnessArbiter {
	BattleRunner br;

	public RCFitness() {
		br = new BattleRunner();
	}

	@Override
	public double fitness(NeuralNetwork nn) {
		try {
			PrintStream s = new PrintStream(new File(
					"C:/robocode/robots/MR/roboSpec"));
			double[] weights = nn.getWeights();
			int[] layers = nn.getLayers().clone();
			s.println(layers.length);
			for (int i = 0; i < layers.length; ++i)
				s.println(layers[i]);
			for (int i = 0; i < weights.length; ++i)
				s.println(weights[i]);
			s.flush();
			s.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return br.runBattle().getScore();
	}
}

public class Main {
	public static void main(String[] args) {
		NeuralNetwork nn = new BasicNN(new int[] { 3, 3, 1 });
		NeuroEvolver ne = new NeuroEvolver(nn, new TestFA(), 500, 1);
		ne.trunkRatio = 0.4f;
		ne.mutationRate = 0.6f;
		ne.crossoverRate = 0.2f;
		ne.keepNum = 1;
		List<Genome> best = null;
		for (int i = 0; i < 50000; ++i) {
			ne.evolveStep();
			best = ne.getBest();
			Collections.sort(best);
			System.out.println(i + " : " + (best.get(0).getFitness()));
		}
		double[] winner = best.get(0).getWeights();
		for (int i = 0; i < winner.length; ++i)
			System.out.println(winner[i]);

	}

}
