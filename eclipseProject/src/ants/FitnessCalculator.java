package ants;

import java.util.Comparator;

public interface FitnessCalculator{

	public abstract double calculateFitness(int[] antSolution);

}
