package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.view.Messages;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class NewtonUniversalGravitation implements ForceLaws {

	private double _gravitationalConstant;

	/**
	 * Constructor of the class NewtonUniversalGravitation
	 * 
	 * @param gravitationalConstant A number involved in calculating gravitational
	 *                              forces
	 * @throws IllegalArgumentException If any of the arguments is invalid
	 */
	public NewtonUniversalGravitation(double gravitationalConstant) {

		validateConstructorData(gravitationalConstant);
		_gravitationalConstant = gravitationalConstant;
	}

	private static void validateConstructorData(double gravitationalConstant) {

		if (gravitationalConstant <= 0) {
			throw new IllegalArgumentException(Messages.INVALID_GRAVITATIONAL_CONSTANT_MESSAGE);
		}
	}

	@Override
	public void apply(List<Body> bodiesToApplyForce) {
		Vector2D acc;
		for (Body i : bodiesToApplyForce) {
			for (Body j : bodiesToApplyForce) {
				acc = forceBetweenTwoBodies(i, j);
				i.addForce(acc);
			}
		}

	}
	
	/**
	 * Calculates the gravitational force between two bodies
	 * 
	 * @param body1 A body that is one of the two involved on the gravitational
	 *              force
	 * @param body2 A body that is one of the two involved on the gravitational
	 *              force
	 * @return The force that is applied by the bodies
	 */
	private Vector2D forceBetweenTwoBodies(Body body1, Body body2) {

		Vector2D force;
		double distance = body1.getPosition().distanceTo(body2.getPosition());

		if (distance == 0) {
			force = new Vector2D(); // If the distance between bodies is 0 force equals (0, 0)
		} else {
			double massProduct = body1.getMass() * body2.getMass();
			Vector2D direccion = body2.getPosition().minus(body1.getPosition()).direction();
			double forceEscalar = _gravitationalConstant * massProduct / (distance * distance);

			force = direccion.scale(forceEscalar);
		}

		return force;
	}

	/**
	 * Gets a description of this force on a String format
	 * 
	 * @return A string describing the object
	 */
	public String toString() {
		return "Newton's Universal Gravitation with G = " + _gravitationalConstant;
	}
}
