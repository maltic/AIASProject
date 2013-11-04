package strategies;

/**
 * Anything that can train strategy based robots
 * 
 * @author Max
 * 
 */
public interface StrategyOptimizer {
	public StrategySolution train(int steps);

	public StrategySolution[] simulationStep();

}
