package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;
import simulator.view.Messages;

public class MovingAroundBodyGreaterMass implements ForceLaws{

	private double _gravitationalConstant;
	private double c;

	/**
	 * Constructor of the class NewtonUniversalGravitation
	 * 
	 * @param gravitationalConstant A number involved in calculating gravitational
	 *                              forces
	 * @throws IllegalArgumentException If any of the arguments is invalid
	 */
	public MovingAroundBodyGreaterMass(double gravitationalConstant,double c) {

		validateConstructorData(gravitationalConstant);
		_gravitationalConstant = gravitationalConstant;
		this.c= c;
	}

	private static void validateConstructorData(double gravitationalConstant) {

		if (gravitationalConstant <= 0) {
			throw new IllegalArgumentException(Messages.INVALID_GRAVITATIONAL_CONSTANT_MESSAGE);
		}
	}

	@Override
	public void apply(List<Body> bodiesToApplyForce) {
		Body max_mass=null;
		if(!bodiesToApplyForce.isEmpty()) {
			max_mass=bodiesToApplyForce.get(0);
			for(Body x : bodiesToApplyForce) {
				if(x.getMass() >max_mass.getMass()) {
					max_mass= x;
				}
			}
		}
		
		Vector2D force ;
		
		
		
		for (Body i : bodiesToApplyForce) {
			force = this.forceBetweenTwoBodies(i, max_mass);
			i.addForce(force);
			
		}

	}
	
	
	private Vector2D forceBetweenTwoBodies(Body body1, Body body2) {

		Vector2D force;
		double distance = body1.getPosition().distanceTo(body2.getPosition());

		if (distance == 0) {
			force = new Vector2D(); // If the distance between bodies is 0 force equals (0, 0)
		} else {
			double massProduct = body1.getMass() * body2.getMass() *(1-this.c);
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
		return "Peso con  G = " + _gravitationalConstant + " y c = "+this.c;
	}

}
