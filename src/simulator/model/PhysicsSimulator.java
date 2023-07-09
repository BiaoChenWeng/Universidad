package simulator.model;

import simulator.view.Messages;
import simulator.view.Observable;

import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import simulator.view.SimulatorObserver;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class PhysicsSimulator implements Observable<SimulatorObserver> {
	private double _deltaTime;
	private double _timeElapsed;
	private ForceLaws _defaultForceLaw;
	private List<String> _groupIds;
	private Map<String, BodiesGroup> _groups;
	private Map<String, BodiesGroup> _groupsRO;
	private List<SimulatorObserver> _observers;

	/**
	 * Constructor of the class PhysicsSimulator
	 *
	 * @param defaultForceLaw The default Force Law applied to new groups.
	 * @param deltaTime       A double which indicates how much time is each
	 *                        simulation step.
	 */
	public PhysicsSimulator(ForceLaws defaultForceLaw, double deltaTime) {

		validateConstructorData(defaultForceLaw, deltaTime);

		this._defaultForceLaw = defaultForceLaw;
		this._deltaTime = deltaTime;
		this._groups = new HashMap<String, BodiesGroup>();
		this._groupsRO = Collections.unmodifiableMap(this._groups);
		this._groupIds = new ArrayList<String>();
		this._observers = new ArrayList<SimulatorObserver>();
	}

	private static void validateConstructorData(ForceLaws defaultForceLaw, double deltaTime) {
		if (deltaTime <= 0) {
			throw new IllegalArgumentException(Messages.INVALID_DELTA_TIME_MESSAGE);
		}
		if (defaultForceLaw == null) {
			throw new IllegalArgumentException(Messages.INVALID_FORCE_LAW_MESSAGE);
		}
	}

	/**
	 * Makes every group to simulate that has elapsed _deltaTime's time
	 */
	public void advance() {
		for (Map.Entry<String, BodiesGroup> group : this._groups.entrySet()) {
			group.getValue().advance(_deltaTime);
		}
		this._timeElapsed += this._deltaTime;

		for (SimulatorObserver observer : this._observers) {
			observer.onAdvance(this._groupsRO, this._timeElapsed);
		}
	}

	/**
	 * Sets a new delta time
	 * 
	 * @param deltaTime A double which indicates how much time is each simulation
	 *                  step.
	 * @throws IllegalArgumentException If the value is not positive (strictly
	 *                                  greater than 0)
	 */
	public void setDeltaTime(double deltaTime) {

		if (deltaTime <= 0) {
			throw new IllegalArgumentException(Messages.INVALID_DELTA_TIME_MESSAGE);
		}

		this._deltaTime = deltaTime;

		for (SimulatorObserver observer : this._observers) {
			observer.onDeltaTimeChanged(deltaTime);
		}
	}

	/**
	 * Set for an specific group the given force law as it's own force law
	 * 
	 * @param groupId  A string that identifies the group who's force law it's being
	 *                 changed
	 * @param forceLaw The force law that is applied to the group
	 * @throws IllegalArgumentException If the given group does not exits
	 */
	public void setForceLaws(String groupId, ForceLaws forceLaw) {

		checkContainsGroup(groupId);
		this._groups.get(groupId).setForceLaws(forceLaw);

		for (SimulatorObserver observer : this._observers) {
			observer.onForceLawsChanged(this._groups.get(groupId));
		}
	}

	/**
	 * Add a body group to the simulator
	 * 
	 * @param groupId A string that identifies the group who's force law it's being
	 *                changed
	 * @throws IllegalArgumentException If groupId is null or is not on the
	 *                                  simulator
	 */
	public void addGroup(String groupId) {

		checkRepeatedGroup(groupId);

		this._groupIds.add(groupId);
		this._groups.put(groupId, new BodiesGroup(groupId, this._defaultForceLaw));

		for (SimulatorObserver observer : this._observers) {
			observer.onGroupAdded(this._groups, this._groups.get(groupId));
		}
	}

	/**
	 * Checks if there is a group with the given id
	 * 
	 * @param groupId The string that identifies the group
	 * @throws IllegalArgumentException If the group id is null or it is not present
	 *                                  on the simulator
	 */
	private void checkContainsGroup(String groupId) {
		if (groupId == null) {
			throw new IllegalArgumentException(Messages.INVALID_GID_MESSAGE);
		}
		if (!this._groups.containsKey(groupId)) {
			throw new IllegalArgumentException(String.format(Messages.GROUP_ID_NOT_PRESENT_MESSAGE, groupId));
		}
	}

	/**
	 * Checks if there is not a group with the given id
	 * 
	 * @param groupId The string that identifies the group
	 * @throws IllegalArgumentException If the group id is null or it is present on
	 *                                  the simulator
	 */
	private void checkRepeatedGroup(String groupId) {
		if (groupId == null) {
			throw new IllegalArgumentException(Messages.INVALID_GID_MESSAGE);
		}
		if (this._groups.containsKey(groupId)) {
			throw new IllegalArgumentException(String.format(Messages.GROUP_ID_REPEATED_MESSAGE, groupId));
		}
	}

	/**
	 * Add a body to a group of the simulator
	 * 
	 * @param body A body that is going to be added
	 * @throws IllegalArgumentException If body is null or it's group id is not in
	 *                                  the simulator
	 */
	public void addBody(Body body) {
		this.checkContainsGroup(body.getgId());
		this._groups.get(body.getgId()).addBody(body);

		for (SimulatorObserver observer : this._observers) {
			observer.onBodyAdded(_groups, body);
		}
	}

	/**
	 * Get the current state of the simulator
	 * 
	 * @return A JSON containing the simulator's current state
	 */
	public JSONObject getState() {

		JSONObject physicSimulatorState = new JSONObject();

		physicSimulatorState.put("time", this._timeElapsed);
		physicSimulatorState.put("groups", getGroupsState());

		return physicSimulatorState;
	}

	/**
	 * Gets the state of the bodies in this body group
	 * 
	 * @return The state of the bodies in this body group
	 */
	private JSONArray getGroupsState() {

		JSONArray groupsState = new JSONArray();

		Iterator<String> groupIdIterator = this._groupIds.iterator();

		while (groupIdIterator.hasNext()) {
			groupsState.put(this._groups.get(groupIdIterator.next()).getState());
		}

		return groupsState;
	}

	/**
	 * Converts the Simulator to String containing it's current state
	 * 
	 * @return A String containing the Simulator's information
	 */
	public String toString() {

		return this.getState().toString();
	}

	/**
	 * Resets the state of the physics simulator
	 */
	public void reset() {
		this._groups.clear();
		this._groupIds.clear();
		this._timeElapsed = 0;
		// Notify on reset
		for (SimulatorObserver observer : this._observers) {
			observer.onReset(_groupsRO, _timeElapsed, _deltaTime);
		}
	}

	@Override
	public void addObserver(SimulatorObserver observer) {
		if (this._observers.contains(observer)) {
			throw new IllegalArgumentException(Messages.OBSERVER_ALREADY_OBSERVING);
		}
		this._observers.add(observer);
		observer.onRegister(_groupsRO, _timeElapsed, _deltaTime);
	}

	@Override
	public void removeObserver(SimulatorObserver observer) {
		if (!this._observers.contains(observer)) {
			throw new IllegalArgumentException(Messages.OBSERVER_NOT_PRESENT);
		}
		this._observers.remove(observer);
	}

}
