package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsTwoFixedPoints extends MovingTowardsFixedPoint {
	
	private Vector2D c2 ;
	private double g2;
	public MovingTowardsTwoFixedPoints(Vector2D fixedPoint, double acceleration, Vector2D c2, double g2) {
		super(fixedPoint, acceleration);
		validateConstructorData(c2, g2);
		this.c2= c2;
		this.g2= g2;
	}

	@Override
	public void apply(List<Body> bodiesToApplyForce) {
		for (Body body : bodiesToApplyForce) {

			Vector2D directionOfForce1 = this._fixedPoint.minus(body.getPosition()).direction();
			Vector2D directionOfForce2 = this.c2.minus(body.getPosition()).direction();
			Vector2D uno = directionOfForce1.scale(this._accelerationTowardsPoint);
			Vector2D dos =directionOfForce2.scale(this.g2);
			
			Vector2D result = uno.plus(dos);
			
			body.addForce(result.scale(body.getMass()));
		}

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
