package robotrain;

/**
 * A generic fitness calculator
 * 
 * @author Max
 * 
 * @param <E>
 *            What to calculate fitness for
 */
public interface GenericFitness<E> {

	public abstract double calculateFitness(E candidate);

}
