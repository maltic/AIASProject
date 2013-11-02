import ants.*;
import neuralnet.*;
import neuralnet.evo.*;
import robotrain.*;

public class Main {
	public static void main(String[] args) {
		System.err.println(robotrain.BattleRunner.RobocodePath);
		String strategy = "GA";
		//String strategy = "ACO";
		strategies.StrategySolution result;
		switch (strategy) {
		case "GA":
			result = RobocodeLearning.learnWithGA("sample.Tracker");
			break;
		default:
		case "ACO":			
			result = RobocodeLearning.learnWithACO("sample.Tracker");
			break;
		}
		
		RobocodeLearning.learnTargetingGivenStrategyWithNeuralNet(result,
				"sample.Tracker");
		System.exit(0);

	}

}
