package simulator.model;

import java.util.List;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.view.Messages;
/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class MovingTowardsFixedPoint implements ForceLaws {
	

	protected Vector2D _fixedPoint;
	protected double _accelerationTowardsPoint;
	
	/**
	 * Constructor of the class MovingTowardsFixedPoint
	 * 
	 * @param fixedPoint
	 * 			Position towards the acceleration is applied
	 * @param acceleration
	 * 			Acceleration applied to a body affected by this force law
	 * @throws IllegalArgumentException
	 * 			If any of the arguments is not valid
	 */
	public MovingTowardsFixedPoint(Vector2D fixedPoint, double acceleration) {
		
		validateConstructorData(fixedPoint, acceleration);
		this._accelerationTowardsPoint=acceleration;
		this._fixedPoint= fixedPoint;
	}
	
	/**
	 * Constructor of the class MovingTowardsFixedPoint
	 * 
	 * @param fixedPoint
	 * 			Position towards the acceleration is applied
	 * @param acceleration
	 * 			Acceleration applied to a body affected by this force law
	 * @throws IllegalArgumentException
	 * 			If any of the arguments is not valid
	 */
	protected static void validateConstructorData(Vector2D fixedPoint, double acceleration) {
		
		if (fixedPoint == null) {
			throw new IllegalArgumentException(Messages.INVALID_POINT_MESSAGE);
		}
		if(acceleration <= 0) {
			throw new IllegalArgumentException(Messages.INVALID_ACCELERATION_MESSAGE);
		}
	}
	
	@Override
	public void apply(List<Body> bodiesToApplyForce) {
	
		for(Body body : bodiesToApplyForce) {
			
			Vector2D directionOfForce = this._fixedPoint.minus( body.getPosition() ).direction();
			body.addForce( directionOfForce.scale( this._accelerationTowardsPoint * body.getMass() ) );
		}
	}
	
	/**
	 * Gets the state of this force on a String format
	 * 
	 * @return A string containing the state
	 */
	public String toString() {
		return "Moving towards "+this._fixedPoint.toString()+" with constant acceleration "+this._accelerationTowardsPoint;
	}
}
