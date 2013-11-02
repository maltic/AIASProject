package strategies;


public interface StrategyOptimizer {
	public StrategySolution train(int steps);
	public StrategySolution[] simulationStep();
	
}
