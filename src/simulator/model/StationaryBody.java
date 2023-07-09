package simulator.model;

import simulator.misc.Vector2D;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class StationaryBody extends Body {

	/**
	 * Constructor of the class StationaryBody
	 * 
	 * @param id       A string that identifies this body
	 * @param gid      A string that identifies the group this body if part of
	 * @param position A vector that indicates the body's position
	 * @param mass     A number that represents the body's mass
	 */
	public StationaryBody(String id, String gid, Vector2D position, double mass) {
		// The velocity vector on the super constructor is (0, 0) as stationary bodies
		// does not moves
		super(id, gid, position, new Vector2D(), mass);
	}

	@Override
	protected void advance(double time) {
	}
}
