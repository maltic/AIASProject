package ants;

import java.util.*;

import robotrain.GenericFitness;

/**
 * An implementation of Ant Colony Optimization
 * 
 * @author Max
 * 
 */
public class AntOptimizer implements strategies.StrategyOptimizer {
	protected int numAnts;
	/**
	 * Specification of problem domain. This needs a little explaining. The
	 * problem domain is assumed to always be finite. Furthermore it is assumed
	 * to be represented using layers. A problem domain consists of a vector of
	 * layer sizes. An ant can choose one node form each layer in its walk
	 * through the problem graph. These layers are sequential and exclusive.
	 * What the number chosen mean is up to the programmer. This array contains
	 * the number of nodes in each layer. An ant will choose a node id between 0
	 * and domain layer size - 1.
	 * 
	 * An example walk might be <3, 4, 11, 2> for a problem domain of <5, 9, 22,
	 * 3>. As you can see the ant will simply choose numbers lower than the
	 * specified maximum.
	 */
	protected int[] domain;
	// amount of pheromone on each node in the domain
	protected double[][] pheromone;
	protected Random r;
	protected GenericFitness<int[]> fitCalc;
	public double EvaporationRate = 0.05;
	/**
	 * number of ants allowed to lay down pheromone
	 */
	public int pheromoneAnts = 1;
	/**
	 * amount of pheromone an ant carries
	 */
	public double antPheromone = 1.0;
	public double minPheromone = 0.0;
	public double maxPheromone = 2.0;
	/**
	 * maximum random amount added to an ants interest in a node
	 */
	public double randomness = 1.0;

	/**
	 * @param ants
	 *            Number of ants
	 * @param domain
	 *            Domain specification
	 * @param fc
	 */
	public AntOptimizer(int ants, int[] domain, GenericFitness<int[]> fc) {
		this.numAnts = ants;
		this.domain = domain;
		this.fitCalc = fc;
		r = new Random();
		pheromone = new double[domain.length][];
		for (int i = 0; i < domain.length; ++i)
			pheromone[i] = new double[domain[i]];
	}

	/**
	 * Does a batch training loop
	 * 
	 * @param steps
	 *            Number of training steps
	 * @return The best AntWalk
	 */
	public AntWalk train(int steps) {
		System.out.println("---------Starting ACO-----------");
		System.out.println("Output format is as follows...");
		System.out.println("Generation# : MaximumFitness , AverageFitness");
		AntWalk[] sol = null;
		for (int i = 0; i < steps; ++i) {
			sol = simulationStep();
			double avg = 0.0;
			for (int j = 0; j < sol.length; ++j)
				avg += sol[j].fitness;
			avg /= sol.length;
			System.out.println(i + " : " + sol[0].fitness + " , " + avg);
			System.out.print("The best ant solution was: <");
			for (int j = 0; j < sol[0].solution.length; ++j)
				System.out.print(sol[0].solution[j]
						+ ((j == sol[0].solution.length - 1) ? "" : ", "));
			System.out.println(">");
		}
		System.out.println("---------Ending ACO-----------");
		return sol[0];
	}

	/**
	 * Runs a single ACO step
	 * 
	 * @return All the AntWalks generated in this step
	 */
	public AntWalk[] simulationStep() {
		AntWalk[] antSolutions = buildAntSolutions();
		Arrays.sort(antSolutions);
		updatePheromone(antSolutions);
		return antSolutions;
	}
	
	/**
	 * @return All the ant solutions
	 */
	protected AntWalk[] buildAntSolutions() {
		AntWalk[] solutions = new AntWalk[numAnts];
		for (int i = 0; i < numAnts; ++i)
			solutions[i] = buildAntSolution();
		return solutions;
	}
	
	/**
	 * @param step The layer in the domain
	 * @param choice The chosen id
	 * @return The interested the ant has in this node
	 */
	protected double calcInterest(int step, int choice) {
		return r.nextDouble() * randomness + pheromone[step][choice];

	}
	
	/**
	 * @return A single simulated ant walk
	 */
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
	
	/**
	 * Updates the amount of pheromone in the problem domain
	 * @param antSolutions All the ant's solutions
	 */
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
