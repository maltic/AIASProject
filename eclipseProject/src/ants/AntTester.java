package ants;

import robotrain.GenericFitness;

/**
 * A testing fitness object. Gives fitness for odd numbers.
 * 
 * @author Max
 * 
 */
class OddFit implements GenericFitness<int[]> {

	@Override
	public double calculateFitness(int[] antSolution) {
		double score = 0.0;
		for (int i = 0; i < antSolution.length; ++i) {
			score += antSolution[i];
		}
		return score;
	}

}

/**
 * A little class I used to test if ACO was working
 * 
 * @author Max
 * 
 */
public class AntTester {

	public static void test() {
		OddFit f = new OddFit();
		AntOptimizer ao = new AntOptimizer(32, new int[] { 55, 140, 334, 545,
				2345, 4344, 5434, 3456, 345, 234, 5235 }, f);
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
