package simulator.model;

import simulator.misc.Vector2D;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class MovingBody extends Body {

	/**
	 * Constructor of the class MovingBody
	 * 
	 * @param id       A string that identifies this body
	 * @param gid      A string that identifies the group this body if part of
	 * @param position A vector that indicates this body's position
	 * @param velocity A vector that indicates this body's velocity
	 * @param mass     A number that represents this body's mass
	 * @throws IllegalArgumentException If any of the arguments is not valid
	 */
	public MovingBody(String id, String gid, Vector2D position, Vector2D velocity, double mass) {

		super(id, gid, position, velocity, mass);
	}

	@Override
	protected void advance(double deltaTime) {

		this._position = calculatePositionUARM(deltaTime);
		this._velocity = calculateVelocityUARM(deltaTime);
	}

	/**
	 * Calculates the acceleration of this body given the force that affects it
	 * 
	 * @return The acceleration of this body
	 */
	public Vector2D SecondLawOfNewtonAcceleration() {

		Vector2D acceleration = new Vector2D();

		if (this._mass != 0) {
			acceleration = this._force.scale(1 / this._mass);
		}

		return acceleration;
	}

	/**
	 * Calculates the new position of an object affected by a acceleration UARM
	 * stands for Uniformly Accelerated Rectilinear Motion
	 * 
	 * @param deltaTime Time that it simulates that has elapsed
	 * @return The new position of the body
	 */

	protected Vector2D calculatePositionUARM(double deltaTime) {
		Vector2D positionVariationByVelocity, positionVariationByAcceleration, acceleration;

		acceleration = this.SecondLawOfNewtonAcceleration();
		positionVariationByVelocity = this._velocity.scale(deltaTime);
		positionVariationByAcceleration = acceleration.scale(0.5).scale(deltaTime * deltaTime);

		return this._position.plus(positionVariationByVelocity).plus(positionVariationByAcceleration);
	}

	/**
	 * Calculates the new velocity of an object affected by a acceleration UARM
	 * stands for Uniformly Accelerated Rectilinear Motion
	 * 
	 * @param deltaTime Time that it simulates that has elapsed
	 * @return The new velocity of the body
	 */
	protected Vector2D calculateVelocityUARM(double deltaTime) {
		Vector2D acceleration = this.SecondLawOfNewtonAcceleration();
		return acceleration.scale(deltaTime).plus(this._velocity);
	}

}
