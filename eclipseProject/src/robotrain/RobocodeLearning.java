package robotrain;

import java.io.File;
import java.util.List;

import strategies.StrategyFactory;
import ants.*;
import neuralnet.*;
import neuralnet.evo.*;

/**
 * All the learning methods implemented for this project
 * 
 * @author Max
 * 
 */
public class RobocodeLearning {
	public static AntWalk learnWithACO(String enemy) {
		AntOptimizer ao = new AntOptimizer(32, new int[] {
				StrategyFactory.MovementStrategies,
				StrategyFactory.FireStrategies, StrategyFactory.ScanStrategies,
				StrategyFactory.GunStrategies }, new ACOFitness(enemy,
				"maxsbots.ScriptBot*", "scriptBotSpec", 40));
		return ao.train(25);
	}

	public static List<Genome> learnWithNeuralNet(String enemy) {
		NeuralNetwork nn = new BiasNN(new int[] { 12, 13, 7, 5 });
		NeuroEvolver ne = new NeuroEvolver(nn, new NNFitness(enemy,
				"maxsbots.NNBot*", "roboSpec", 40), 32, 3);
		return ne.train(100);
	}

	public static List<Genome> learnACOTargetingWithNeuralNet(AntWalk antWalk,
			String enemy) {
		// because we are calculating the fitness, the scriptBotSpec will have
		// the right aco information in it ready for neuralevolution
		ACOFitness af = new ACOFitness(enemy, "maxsbots.ScriptBot*",
				"scriptBotSpec", 40);
		System.out.println("Initial fitness of ACO bot: "
				+ af.calculateFitness(antWalk.solution));
		// now refine targeting using neuroevolution
		NeuralNetwork nn = new BiasNN(new int[] { 2, 3, 1 });
		NeuroEvolver ne = new NeuroEvolver(nn, new NNFitness(enemy,
				"maxsbots.NNScriptBot*", "scriptBotNNSpec", 40), 16, 2);
		return ne.train(25);
	}

	public static List<Genome> learnTargetingWithNeuralnet(String enemy) {
		// clear old data
		File file = new File(BattleRunner.RobocodePath
				+ "/robots/maxsbots/DataBot.data/data");
		file.delete();
		// collect the data
		BattleRunner br = new BattleRunner();
		br.runBattle(true, enemy + ",maxsbots.DataBot*", 100);
		// train the neural network using the data
		NeuralNetwork nn = new BiasNN(new int[] { 6, 7, 3, 1 });
		NeuroEvolver ne = new NeuroEvolver(nn, new DataNNFitness(), 32, 64);
		return ne.train(1000);
	}
}
