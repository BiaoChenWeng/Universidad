package simulator.model;

import java.util.List;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public interface ForceLaws {
	/**
	 * Applies the bodies the forces they cause each other
	 * 
	 * @param bodiesToApplyForce
	 * 				A list of bodies which contains the bodies the forces is been applied
	 */
	public void apply(List<Body> bodiesToApplyForce);
}