package ants;

import robotrain.GenericFitness;

/**
 * Represents a single walk by an ant through the solution space
 * 
 * @author Max
 * 
 */
public class AntWalk implements Comparable<AntWalk>,
		strategies.StrategySolution {
	/**
	 * The solution found
	 */
	public int[] solution;
	/**
	 * The solutions fitness
	 */
	public double fitness;

	public AntWalk(int[] solution, GenericFitness<int[]> fc) {
		this.fitness = fc.calculateFitness(solution);
		this.solution = solution;
	}

	@Override
	public int compareTo(AntWalk o) {
		if (o.fitness > fitness)
			return 1;
		else if (o.fitness == fitness)
			return 0;
		else
			return -1;
	}

	public int[] getSolution() {
		return this.solution.clone();
	}

	public double getFitness() {
		return this.fitness;
	}
}
