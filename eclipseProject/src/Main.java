import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import ants.AntOptimizer;
import ants.AntTester;
import ants.AntWalk;
import ants.FitnessCalculator;
import robocode.BattleResults;
import strategies.StrategyFactory;
import MR.DataBot;
import neuralnet.*;
import neuralnet.evo.*;

class RCFitness implements neuralnet.evo.FitnessArbiter {
	BattleRunner br;

	public RCFitness() {
		br = new BattleRunner("sample.MyFirstRobot,MR.NNScriptBot*", 10);
	}

	@Override
	public double fitness(NeuralNetwork nn) {

		try {
			PrintStream s = new PrintStream(new File(
					"C:/robocode/robots/MR/scriptBotNNSpec"));
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
		BattleResults[] res = br.runBattle();
		return res[1].getScore()
				/ ((double) res[0].getScore() + res[1].getScore());

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
			sc.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public double fitness(NeuralNetwork nn) {
		double err = 0.0;
		for (BData bd : hits) {
			double out = nn.feedForward(new double[] { bd.dist, bd.velocity })[0];
			err += Math.abs(1 - out);
		}
		for (BData bd : misses) {
			double out = nn.feedForward(new double[] { bd.dist, bd.velocity })[0];
			err += Math.abs(0 - out);
		}
		return -err;
	}
}

class RCFitCalc implements FitnessCalculator {

	BattleRunner br;

	public RCFitCalc() {
		br = new BattleRunner("sample.MyFirstRobot,MR.ScriptBot*", 40);
	}

	@Override
	public double calculateFitness(int[] antSolution) {
		try {
			PrintStream s = new PrintStream(new File(
					"C:/robocode/robots/MR/scriptBotSpec"));
			for (int i = 0; i < antSolution.length; ++i)
				s.println(antSolution[i]);
			s.flush();
			s.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		BattleResults[] res = br.runBattle();
		return res[1].getScore()
				/ ((double) res[0].getScore() + res[1].getScore());
	}

}

public class Main {
	public static void main(String[] args) {
		// BattleRunner br = new BattleRunner("sample.Walls,MR.ScriptBot*",
		// 100);
		// br.runBattle();
		// try {
		// Thread.sleep(1000000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.exit(0);
		trainNeuralNet();

	}

	public static void trainNeuralNet() {
		NeuralNetwork nn = new BiasNN(new int[] { 2, 3, 1 });
		NeuroEvolver ne = new NeuroEvolver(nn, new RCFitness(), 32, 1);
		ne.trunkRatio = 0.5f;
		ne.mutationRate = 0.6f;
		ne.crossoverRate = 0.35f;
		ne.keepNum = 1;
		List<Genome> best = null;
		for (int i = 0; i < 25; ++i) {
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

	public static void collectData() {
		BattleRunner br = new BattleRunner("sample.RamFire,MR.DataBot*", 100);
		br.runBattle();
	}

	public static void runACO() {
		FitnessCalculator f = new RCFitCalc();
		AntOptimizer ao = new AntOptimizer(32, new int[] {
				StrategyFactory.MovementStrategies,
				StrategyFactory.FireStrategies, StrategyFactory.ScanStrategies,
				StrategyFactory.GunStrategies }, f);
		for (int i = 0; i < 1000; ++i) {
			AntWalk[] sol = ao.simulationStep();
			System.out
					.print("Opt step " + i + " -- " + sol[0].fitness + " :: ");
			for (int j = 0; j < sol[0].solution.length; ++j)
				System.out.print(sol[0].solution[j] + " ");
			System.out.println();
		}
	}

}
