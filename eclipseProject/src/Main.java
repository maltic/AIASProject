import ants.*;
import neuralnet.*;
import neuralnet.evo.*;
import robotrain.*;

public class Main {
	public static void main(String[] args) {
		//RobocodeLearning.learnWithNeuralNet("sample.RamFire");
		System.err.println(robotrain.BattleRunner.RobocodePath);
		//String strategy = "GA";
		String strategy = "ACO";
		strategies.StrategySolution result;
		String enemy = "sample.RamFire";
		System.err.println("Enemy (" + enemy + "), Method: " + strategy);
		switch (strategy) {
		case "GA":
			result = RobocodeLearning.learnWithGA(enemy);
			break;
		default:
		case "ACO":			
			result = RobocodeLearning.learnWithACO(enemy);
			break;
		}
		
		RobocodeLearning.learnTargetingGivenStrategyWithNeuralNet(result,
				enemy);
		System.exit(0);

	}

}
