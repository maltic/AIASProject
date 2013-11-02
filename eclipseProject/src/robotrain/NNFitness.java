package robotrain;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import neuralnet.NeuralNetwork;
import robocode.BattleResults;

/**
 * A generic fitness calculator for neural networks
 * @author Max
 *
 */
public class NNFitness implements GenericFitness<NeuralNetwork> {
	BattleRunner br;
	String enemy, nnFile, bot;
	int rounds;

	public NNFitness(String enemy, String bot, String nnFile, int rounds) {
		br = new BattleRunner();
		this.enemy = enemy;
		this.nnFile = nnFile;
		this.bot = bot;
		this.rounds = rounds;
	}

	@Override
	public double calculateFitness(NeuralNetwork nn) {
		//write the neural net to the right file
		try {
			PrintStream s = new PrintStream(new File(BattleRunner.RobocodePath
					+ "/robots/maxsbots/" + nnFile));
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
		//run the battle
		BattleResults[] res = br.runBattle(true, enemy + "," + bot, rounds);
		//return the percentage score
		return res[1].getScore()
				/ ((double) res[0].getScore() + res[1].getScore());

	}
}
