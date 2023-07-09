package simulator.view;

import java.util.Map;

import simulator.model.BodiesGroup;
import simulator.model.Body;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public interface SimulatorObserver {
	/**
	 * It performs an action when the simulator advances
	 * 
	 * @param groups The collection of groups containing it's information
	 * @param time   A number that indicates the amount of time elapsed
	 */
	public void onAdvance(Map<String, BodiesGroup> groups, double time);

	/**
	 * It performs an action when the simulator resets
	 * 
	 * @param groups    The collection of groups containing it's information
	 * @param time      A number that indicates the amount of time elapsed
	 * @param deltaTime A number that indicates the amount of time that it's added
	 *                  each time the simulator advance
	 */
	public void onReset(Map<String, BodiesGroup> groups, double time, double deltaTime);

	/**
	 * It performs an action when the observer is added to the simulator
	 * 
	 * @param groups    The collection of groups containing it's information
	 * @param time      A number that indicates the amount of time elapsed
	 * @param deltaTime A number that indicates the amount of time that it's added
	 *                  each time the simulator advance
	 */
	public void onRegister(Map<String, BodiesGroup> groups, double time, double deltaTime);

	/**
	 * It performs an action when a group is added to the simulator
	 * 
	 * @param groups The collection of groups containing it's information
	 * @param group  The body group that is being added to the simulator
	 */
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup group);

	/**
	 * It performs an action when a body is added to the simulator
	 * 
	 * @param groups The collection of groups containing it's information
	 * @param body   The body that is being added to the simulator
	 */
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body body);

	/**
	 * It performs an action when the simulator's delta time is changed
	 * 
	 * @param deltaTime A number that indicates the amount of time that it's added
	 *                  each time the simulator advance
	 */
	public void onDeltaTimeChanged(double deltaTime);

	/**
	 * It performs an action when the force law of a body group is changed
	 * 
	 * @param group The body group whose force law is being changed
	 */
	public void onForceLawsChanged(BodiesGroup group);
}
