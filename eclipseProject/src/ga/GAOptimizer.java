package ga;

import java.util.Arrays;




public class GAOptimizer implements strategies.StrategyOptimizer {

	
		
	private int populationSize;
	private int[] domain;
	private robotrain.GenericFitness<int[]> fitCalc;
	private java.util.Random r;
	private GAOptimizerSettings settings;
	
	private Candidate[] pool;
	
	private double average;
	private double best;
	private Candidate bestCandidate;
	
	public GAOptimizer (GAOptimizerSettings settings, int[] domain, robotrain.GenericFitness<int[]> fc) {
		this.populationSize = settings.size;
		this.domain = domain;
		System.err.print("this.domain.length: " + domain.length + "[");
		for (int i = 0; i < domain.length; ++i) {
			System.err.print(domain[i] + ", ");
		}
		System.err.println();
		this.fitCalc = fc;
		this.best = 0;
		this.average = 0;
		r = new java.util.Random();
		this.pool = null;
		this.settings = settings;
	}
	
	private Candidate getBestInPool() {
		return this.pool[0].clone();
	}
	
	@Override
	public Candidate train(int steps) {
		// Repeat the GA process for steps.

		System.out.println("---------Starting GA-----------");
		System.out.println("Output format is as follows...");
		System.out.println("Generation# : MaximumFitness , AverageFitness");
		System.out.println(0 + " : " + this.best + " , " + this.average);
		for (int i = 1; i <= steps; ++i) {
			simulationStep();
			Candidate bestC = this.getBestInPool();
			System.out.println(i + " : " + bestC.fitness + " , " + this.average);
			System.out.print("The best ant solution was: <");
			for (int j = 0; j < bestC.solution.length; ++j)
				System.out.print(bestC.solution[j]
						+ ((j == bestC.solution.length - 1) ? "" : ", "));
			System.out.println(">");
		}
		System.out.println("---------Ending GA-----------");		
		return this.bestCandidate.clone();
	}

	@Override
	public Candidate[] simulationStep() {
		// Perform a single step of the GA
		this.evolve();
		return this.pool.clone();
	}

	private double getAverage() {
		double avg = 0.0;
		for (int j = 0; j < this.pool.length; ++j)
			avg += this.pool[j].fitness;
		avg /= this.pool.length;			
		return avg;
	}
	private void evolve() {
		if (this.pool == null) { //Create new pool from scratch
			this.pool = new Candidate[this.populationSize];
			for (int i = 0; i < this.populationSize; ++i) {
				this.pool[i] = new Candidate(this.domain, r, fitCalc);
			}
		} else { //Evolve current pool
			Candidate[] newpool = new Candidate[settings.size];
			java.util.HashSet<int[]> members = new java.util.HashSet<>();
			int i = (int)java.lang.Math.floor(settings.elitism);	
			int trials = 0;
			for (int j = 0; j < i; ++j) { //elitism
				newpool[j] = this.pool[j];
			}
			while (i < settings.size && trials < settings.maxevolvetrials) {
				++trials;
				if (r.nextDouble() < settings.breed) {
					Candidate a = this.select();
					Candidate b = this.select();
					int[] c = a.breed(b, r);
					if (!members.contains(c)) {
						members.add(c);
						newpool[i++] = new Candidate(c, fitCalc);
					}
					if (i != settings.size) {
						c = b.breed(a, r);
						if (!members.contains(c)) {
							members.add(c);
							newpool[i++] = new Candidate(c, fitCalc);
						}
					}
				} else if (r.nextDouble() < settings.mutation) {
					int[] c = this.select().mutate(this.domain, r, settings.mutationLambda);
					if (!members.contains(c)) {
						members.add(c);
						newpool[i++] = new Candidate(c, fitCalc);
					}
				} else {
					Candidate c = this.select();
					if (!members.contains(c)) {
						members.add(c.solution); //avoid clone
						newpool[i++] = c;
					}
				}
			}
			if (trials == settings.maxevolvetrials) while (i < newpool.length) {
				 newpool[i++] = new Candidate(this.select().mutate(this.domain, r, settings.mutationLambda), fitCalc);
			}
			this.pool = newpool;
		}
		
		Arrays.sort(this.pool);
		if (this.getBestInPool().getFitness() > this.best) {
			this.best = this.getBestInPool().getFitness();
			this.bestCandidate = this.getBestInPool();
		}
		this.average = getAverage();		
	}
	
	private Candidate select() {
		final int t = settings.tourney;
		Candidate winner = null;
		for (int i = 0; i < t; ++i) {
			Candidate c = this.pool[r.nextInt(this.pool.length)]; 
			if (winner == null) {
				winner = c; 
			} else {
				if (winner.compareTo(c) < 0) {
					winner = c;
				}
			}
		}
		return winner;
	}

}
