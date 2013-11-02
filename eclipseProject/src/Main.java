import ants.*;
import neuralnet.*;
import neuralnet.evo.*;
import robotrain.*;

public class Main {
	public static void main(String[] args) {
		RobocodeLearning.learnWithNeuralNet("sample.RamFire");
		AntWalk result = RobocodeLearning.learnWithACO("sample.RamFire");
		RobocodeLearning.learnACOTargetingWithNeuralNet(result,
				"sample.RamFire");
		System.exit(0);

	}

}
