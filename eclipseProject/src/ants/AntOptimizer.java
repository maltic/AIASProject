package ants;

import java.util.*;

public class AntOptimizer {
	protected int numAnts;
	protected int[] domain;
	protected double[][] pheromone;
	protected Random r;
	protected FitnessCalculator fitCalc;
	public double EvaporationRate = 0.05;
	public int pheromoneAnts = 1;
	public double antPheromone = 1.0;
	public double minPheromone = 0.0;
	public double maxPheromone = 2.0;
	public double randomness = 1.0;

	public AntOptimizer(int ants, int[] domain, FitnessCalculator fc) {
		this.numAnts = ants;
		this.domain = domain;
		this.fitCalc = fc;
		r = new Random();
		pheromone = new double[domain.length][];
		for (int i = 0; i < domain.length; ++i)
			pheromone[i] = new double[domain[i]];
	}

	public AntWalk[] simulationStep() {
		AntWalk[] antSolutions = buildAntSolutions();
		Arrays.sort(antSolutions);
		updatePheromone(antSolutions);
		return antSolutions;
	}

	protected AntWalk[] buildAntSolutions() {
		AntWalk[] solutions = new AntWalk[numAnts];
		for (int i = 0; i < numAnts; ++i)
			solutions[i] = buildAntSolution();
		return solutions;
	}

	protected double calcInterest(int step, int choice) {
		return r.nextDouble() * randomness + pheromone[step][choice];

	}

	protected AntWalk buildAntSolution() {
		int[] solution = new int[domain.length];
		for (int i = 0; i < domain.length; ++i) {
			double maxInterest = calcInterest(i, 0);
			int maxIndex = 0;
			for (int j = 1; j < domain[i]; ++j) {
				double interest = calcInterest(i, j);
				if (interest > maxInterest) {
					maxInterest = interest;
					maxIndex = j;
				}
			}
			solution[i] = maxIndex;
		}
		return new AntWalk(solution, fitCalc);
	}

	protected void updatePheromone(AntWalk[] antSolutions) {
		// antSolutions is assumed to be sorted by fitness
		// evaporate old pheromone
		for (int i = 0; i < pheromone.length; ++i)
			for (int j = 0; j < pheromone[i].length; ++j)
				pheromone[i][j] = Math.max(pheromone[i][j] - EvaporationRate,
						minPheromone);

		// find best solution(s)
		for (int i = 0; i < pheromoneAnts; ++i)
			for (int j = 0; j < antSolutions[i].solution.length; ++j) {
				pheromone[j][antSolutions[i].solution[j]] = Math.min(
						pheromone[j][antSolutions[i].solution[j]]
								+ antPheromone / domain.length, maxPheromone);
			}

	}
}
