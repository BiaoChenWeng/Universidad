package simulator.view;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 * 
 * @param <T> The type of observer that can observe this interface
 */
public interface Observable<T> {
	/**
	 * Adds the observer to this object
	 * 
	 * @param o The observer that is been added
	 */
	void addObserver(T o);

	/**
	 * Removes the observer to this object
	 * 
	 * @param o The observer that is been removed
	 */
	void removeObserver(T o);
}
