package simulator.model;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Daniel Lopez
 * @author Biao Chen0
 */
public class NoForce implements ForceLaws {

	/**
	 * Constructor of the class NoForce
	 */
	public NoForce() {
	}

	@Override
	public void apply(List<Body> bodies) {
	}

	/**
	 * Gets the state of this force on a String format
	 * 
	 * @return A string containing the state
	 */
	public String toString() {

		return "No force";
	}
}
