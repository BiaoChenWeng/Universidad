package simulator.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.*;

import simulator.view.Messages;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class BodiesGroup implements Iterable<Body> {

	private String _gid;
	private ForceLaws _forceLaw;
	private List<Body> _bodies;
	// private List<Body> _bodiesRO;

	/**
	 * Constructor of the class BodiesGroup
	 * 
	 * @param gid      The id that identifies this body group
	 * @param forceLaw The force law that this body group is ruled by
	 * @throws IllegalArgumentException If any of the arguments is not valid
	 */
	public BodiesGroup(String gid, ForceLaws forceLaw) {

		validateConstructorData(gid, forceLaw);

		this._gid = gid;
		this._forceLaw = forceLaw;
		this._bodies = new ArrayList<Body>();
		// this._bodiesRO = Collections.unmodifiableList(this._bodies);
	}

	/**
	 * Validates the data given to the constructor
	 * 
	 * @param gid      The id that identifies a body group
	 * @param forceLaw The force law that a body group is ruled by
	 * @throws IllegalArgumentException If any of the arguments is not valid
	 */
	private static void validateConstructorData(String gid, ForceLaws forceLaw) {
		if (forceLaw == null) {
			throw new IllegalArgumentException(Messages.INVALID_FORCE_LAW_MESSAGE);
		}
		if (gid == null || gid.trim().length() == 0) {
			throw new IllegalArgumentException(String.format(Messages.INVALID_GID_MESSAGE, gid));
		}
	}

	/**
	 * Gets this body group's id
	 * 
	 * @return The id of this body group
	 */
	public String getId() {
		return this._gid;
	}

	/**
	 * Adds the body to this body group
	 * 
	 * @param bodyAdded The body is been added to the to this body group
	 * @throws IllegalArgumentException If there is another body with in this group
	 *                                  with the same id to the one it's been added
	 */
	protected void addBody(Body bodyAdded) {

		if (bodyAdded == null) {
			throw new IllegalArgumentException(Messages.INVALID_BODY_MESSAGE);
		}

		if (bodyAdded.getgId().equals(this._gid)) {

			// Checks if there is another body with the same id on this group
			Iterator<Body> body = this._bodies.iterator();

			while (body.hasNext()) {

				if (body.next().equals(bodyAdded)) {
					throw new IllegalArgumentException(
							String.format(Messages.BODY_ID_REPEATED_MESSAGE, _gid, bodyAdded.getId()));
				}
			}

			// If there was not any body with the same id, it adds it to this group
			this._bodies.add(bodyAdded);
		}
	}

	/**
	 * Sets the force law of this body group to the given one
	 * 
	 * @param forceLaw The new force law of this body group
	 * @throws IllegalArgumentException If the given force law is null (not valid)
	 */
	protected void setForceLaws(ForceLaws forceLaw) {

		if (forceLaw == null) {
			throw new IllegalArgumentException(Messages.INVALID_FORCE_LAW_MESSAGE);
		}

		this._forceLaw = forceLaw;
	}

	/**
	 * Makes the whole group of bodies to advance a given amount of time on the
	 * simulation
	 * 
	 * @param deltaTime The amount of time it simulates that has elapsed
	 * @throws IllegalArgumenException If the deltaTime is not positive
	 */
	protected void advance(double deltaTime) {

		if (deltaTime <= 0) {
			throw new IllegalArgumentException(Messages.INVALID_DELTA_TIME_MESSAGE);
		}

		this.resetForceOfBodies();

		this._forceLaw.apply(this._bodies);

		this.advanceBodies(deltaTime);
	}

	/**
	 * Reset the force of every body on the group
	 */
	private void resetForceOfBodies() {

		for (Body body : this._bodies) {
			body.resetForce();
		}
	}

	/**
	 * Makes every body advance a given amount of time
	 * 
	 * @param deltaTime The amount of time it simulates that has elapsed
	 */
	private void advanceBodies(double deltaTime) {

		for (Body body : this._bodies) {
			body.advance(deltaTime);
		}
	}

	/**
	 * Gets the state of the body group on a JSONObject
	 * 
	 * @return The bodies group state
	 */
	public JSONObject getState() {

		JSONObject state = new JSONObject();

		state.put("id", _gid);
		state.put("bodies", this.getBodiesState());
		return state;
	}

	public String getForceLawsInfo() {
		return this._forceLaw.toString();
	}

	/**
	 * Gets the state of the bodies in this body group
	 * 
	 * @return The state of the bodies in this body group
	 */
	private JSONArray getBodiesState() {

		JSONArray bodiesState = new JSONArray();

		Iterator<Body> body = this._bodies.iterator();
		while (body.hasNext()) {
			bodiesState.put(body.next().getState());
		}

		return bodiesState;
	}

	/**
	 * Gets the state of the body group on a String
	 * 
	 * @return The bodies group state
	 */
	public String toString() {
		return this.getState().toString();
	}

	@Override
	public Iterator<Body> iterator() {
		return new Iterator<Body>() {
			Iterator<Body> iterator = _bodies.iterator();

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Body next() {
				return iterator.next();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Removing a body from groups via the body's iterator");
			}
		};
	}
}
