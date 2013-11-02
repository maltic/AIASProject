package ga;



public class Candidate implements Comparable<Candidate>, strategies.StrategySolution {

	protected int[] solution;
	protected double fitness;
	
	
	public static int[] random(int[] domain, java.util.Random rand) {
		int[] s = new int[domain.length];
		for (int i = 0; i < domain.length; ++i) {
			s[i] = rand.nextInt(domain[i]);
		}
		return s;
	}
	
	public Candidate (int[] domain, java.util.Random rand, robotrain.GenericFitness<int[]> fc) {
		this.solution = Candidate.random(domain, rand);
		this.fitness = fc.calculateFitness(this.solution);
		if (this.solution.length > 4) {
			System.err.println("Wtf you doin? rand candidate");
		}
	}
	
	public Candidate (int[] solution, robotrain.GenericFitness<int[]> fc) {
		this.fitness = fc.calculateFitness(solution);
		this.solution = solution.clone();
		if (this.solution.length > 4) {
			System.err.println("Wtf you doin? new int[]");
		}
	}
	
	private Candidate (int[] solution, double fitness) {
		this.solution = solution.clone();
		this.fitness = fitness;		
	}
	
	@Override
	public double getFitness() {
		// Gets the fitness of a candidate
		return fitness;
	}

	@Override
	public int[] getSolution() {
		// Gets the solution in the candidate
		return solution.clone();
	}
	

	@Override
	public int compareTo(Candidate other) {
		// Compares two candidates
		return Double.compare(this.fitness, other.fitness);
	}
	
	public Candidate clone() {
		return new Candidate(this.solution, this.fitness);
	}

	int[] breed(Candidate b, java.util.Random r) {
		Candidate a = this;
		int[] s = new int[this.solution.length];
		final int pos = r.nextInt(this.solution.length);		
		for (int i = 0; i < pos; ++i) {
			s[i] = a.solution[i];
		}
		for (int i = pos; i < b.solution.length; ++i) {
			if (i == 4) {
				System.err.println("b.solution.length: " + b.solution.length);
				System.err.println("pos: " + pos);
				System.err.println("pos < b.solution.length: " + (pos < b.solution.length));
			}
			s[i] = b.solution[i];
		}
		return s;
	}
	
	int[] mutate(int[] domain, java.util.Random r, double lambda) {
		double L = Math.exp(lambda);
		int k = 0;
		double p = 1.0;
		do {
			k = k + 1;
			p *= r.nextDouble();
		} while ( p > L );
		int[] ret = this.solution.clone();
		for (int i = 1; i < k; ++i) { //Poisson value is actually k - 1
			int j = r.nextInt(this.solution.length);
			ret[j] = r.nextInt(domain[j]);
		}
		return ret;
	}
}
