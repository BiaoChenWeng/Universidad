package simulator.model;

import simulator.misc.Vector2D;
import simulator.view.Messages;

import java.util.Objects;

import org.json.*;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public abstract class Body {

	protected String _id;
	protected String _gid;
	protected Vector2D _position;
	protected Vector2D _velocity;
	protected double _mass;
	protected Vector2D _force;

	/**
	 * Constructor of the class Body
	 * 
	 * @param id       A string that identifies this body
	 * @param gid      A string that identifies the group this body if part of
	 * @param position A vector that indicates this body's position
	 * @param velocity A vector that indicates this body's velocity
	 * @param mass     A number that represents this body's mass
	 * @throws IllegalArgumentException If any of the arguments is not valid
	 */
	protected Body(String id, String gid, Vector2D position, Vector2D velocity, double mass) {

		validateConstructorData(id, gid, position, velocity, mass);

		this._id = id;
		this._gid = gid;
		this._position = position;
		this._velocity = velocity;
		this._mass = mass;

		// The initial force is (0, 0)
		this.resetForce();
	}

	/**
	 * Validates the data given to the constructor
	 * 
	 * @param id       A string that identifies a body
	 * @param gid      A string that identifies the group a body if part of
	 * @param position A vector that indicates a body's position
	 * @param velocity A vector that indicates a body's velocity
	 * @param mass     A number that represents a body's mass
	 * @throws IllegalArgumentException If any of the arguments is not valid
	 */
	private static void validateConstructorData(String id, String gid, Vector2D position, Vector2D velocity,
			double mass) {
		if (id == null || id.trim().length() == 0) {
			throw new IllegalArgumentException(String.format(Messages.INVALID_ID_MESSAGE, id));
		}
		if (gid == null || gid.trim().length() == 0) {
			throw new IllegalArgumentException(String.format(Messages.INVALID_GID_MESSAGE, gid));
		}
		if (position == null) {
			throw new IllegalArgumentException(Messages.INVALID_POSITION_MESSAGE);
		}
		if (velocity == null) {
			throw new IllegalArgumentException(Messages.INVALID_VELOCITY_MESSAGE);
		}
		if (mass <= 0) {
			throw new IllegalArgumentException(Messages.INVALID_MASS_MESSAGE);
		}
	}

	/**
	 * Adds to this body the given force
	 * 
	 * @param force A vector that is the force added to this body
	 */
	protected void addForce(Vector2D force) {

		this._force = this._force.plus(force);
	}

	/**
	 * Sets the force of this body to (0, 0)
	 */
	protected void resetForce() {
		this._force = new Vector2D();
	}

	/**
	 * Gets the id of this body
	 * 
	 * @return This body's id
	 */
	public String getId() {

		return _id;
	}

	/**
	 * Gets the group's id of the group this body belongs
	 * 
	 * @return This body's group id
	 */
	public String getgId() {

		return _gid;
	}

	/**
	 * Gets the mass of this body
	 * 
	 * @return This body's mass
	 */
	public double getMass() {

		return _mass;
	}

	/**
	 * Gets the position of this body
	 * 
	 * @return This body's position
	 */
	public Vector2D getPosition() {

		return _position;
	}

	/**
	 * Gets the velocity of this body
	 * 
	 * @return This body's velocity
	 */
	public Vector2D getVelocity() {

		return _velocity;
	}

	/**
	 * Gets the force of this body
	 * 
	 * @return This body's force
	 */
	public Vector2D getForce() {

		return _force;
	}

	/**
	 * Gets the state of this body on a JSONObject
	 * 
	 * @return This body's state
	 */
	public JSONObject getState() {

		JSONObject state = new JSONObject();

		state.put("id", _id);
		state.put("p", _position.asJSONArray());
		state.put("v", _velocity.asJSONArray());
		state.put("f", _force.asJSONArray());
		state.put("m", _mass);

		return state;
	}

	/**
	 * Gets the state of this body on a string
	 * 
	 * @return This body's state
	 */
	public String toString() {
		return getState().toString();
	}

	/**
	 * Makes the body to simulate that has elapsed a given amount of time
	 * 
	 * @param deltaTime Time that it simulates that has elapsed
	 */
	protected abstract void advance(double deltaTime);

	@Override
	public int hashCode() {
		return Objects.hash(_gid, _id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Body other = (Body) obj;
		return Objects.equals(_gid, other._gid) && Objects.equals(_id, other._id);
	}

}
