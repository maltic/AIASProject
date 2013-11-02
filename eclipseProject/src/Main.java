import ants.*;
import neuralnet.*;
import neuralnet.evo.*;
import robotrain.*;

public class Main {
	public static void main(String[] args) {
		AntWalk result = RobocodeLearning.learnWithACO("sample.Tracker");
		RobocodeLearning.learnACOTargetingWithNeuralNet(result,
				"sample.Tracker");
		System.exit(0);

	}

}
