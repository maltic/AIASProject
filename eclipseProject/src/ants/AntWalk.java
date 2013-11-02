package ants;

import robotrain.GenericFitness;

public class AntWalk implements Comparable<AntWalk> {
	public int[] solution;
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
}
