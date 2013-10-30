import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import MR.DataBot;
import neuralnet.*;
import neuralnet.evo.*;

class RCFitness implements FitnessArbiter {
	BattleRunner br;

	public RCFitness() {
		br = new BattleRunner();
	}

	@Override
	public double fitness(NeuralNetwork nn) {
		/*
		 * try { PrintStream s = new PrintStream(new File(
		 * "C:/robocode/robots/MR/roboSpec")); double[] weights =
		 * nn.getWeights(); int[] layers = nn.getLayers().clone();
		 * s.println(layers.length); for (int i = 0; i < layers.length; ++i)
		 * s.println(layers[i]); for (int i = 0; i < weights.length; ++i)
		 * s.println(weights[i]); s.flush(); s.close();
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 */
		return br.runBattle().getBulletDamage();

	}
}

class FireFitness implements FitnessArbiter {
	private class BData {
		double x, y, heading, dist, velocity, eheading;

		public BData(Scanner sc) {
			this.x = sc.nextDouble() / 800.0;
			this.y = sc.nextDouble() / 600.0;
			this.heading = sc.nextDouble() / 360.0;
			double power = sc.nextDouble() / 3.0;
			this.dist = sc.nextDouble() / 1000.0;
			double ee = sc.nextDouble() / 100.0;
			double em = sc.nextDouble() / 100.0;
			this.velocity = (sc.nextDouble() + 8.0) / 16.0;
			this.eheading = sc.nextDouble() / 360.0;
			// System.out.println(x + " " + y + " " + heading + " " + power +
			// " "
			// + dist + " " + ee + " " + em + " " + velocity + " "
			// + eheading);
		}
	}

	private ArrayList<BData> hits;
	private ArrayList<BData> misses;

	public FireFitness() {
		hits = new ArrayList<BData>();
		misses = new ArrayList<BData>();
		try {
			FileReader fr = new FileReader(
					"C:\\robocode\\robots\\MR\\DataBot.data\\data");
			Scanner sc = new Scanner(fr);
			while (sc.hasNextInt()) {
				int numHits = sc.nextInt();
				for (int i = 0; i < numHits; ++i) {
					hits.add(new BData(sc));
				}
				int numMisses = sc.nextInt();
				for (int i = 0; i < numMisses; ++i) {
					misses.add(new BData(sc));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public double fitness(NeuralNetwork nn) {
		double err = 0.0;
		for (BData bd : hits) {
			double[] out = nn.feedForward(new double[] { bd.dist, bd.heading,
					bd.velocity, bd.x, bd.y, bd.eheading });
			err += Math.abs(1 - out[0]);
		}
		for (BData bd : misses) {
			double[] out = nn.feedForward(new double[] { bd.dist, bd.heading,
					bd.velocity, bd.x, bd.y, bd.eheading });
			err += Math.abs(0 - out[0]);
		}
		return -err;
	}
}

public class Main {
	public static void main(String[] args) {
		NeuralNetwork nn = new BasicNN(new int[] { 6, 7, 3, 1 });
		NeuroEvolver ne = new NeuroEvolver(nn, new FireFitness(), 50, 500);
		ne.trunkRatio = 0.4f;
		ne.mutationRate = 0.6f;
		ne.crossoverRate = 0.3f;
		ne.keepNum = 1;
		List<Genome> best = null;
		for (int i = 0; i < 1000; ++i) {
			ne.evolveStep();
			best = ne.getBest();
			Collections.sort(best);
			System.out.println(i + " : " + (best.get(0).getFitness()) + " , "
					+ ne.getAvgFitness());
		}
		double[] winner = best.get(0).getWeights();
		for (int i = 0; i < winner.length; ++i)
			System.out.println(winner[i]);

	}

}
