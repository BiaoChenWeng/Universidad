package simulator.factories;

import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;
import simulator.model.MovingTowardsTwoFixedPoints;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class MovingTowardsTwoFixedPointBuilder extends Builder<ForceLaws> {
	private static final Vector2D _DEFAULT_c_VALUE = new Vector2D();
	private static final double _DEFAULT_g_VALUE = 9.81;

	/**
	 * An string which identifies the object type
	 */
	static final public String _TYPE_ID = "mt2fp";
	static final private String _DESC = "Moving Toward two fixed point ";
	static final private JSONObject _DATA = new JSONObject(
			"{ \"c1\": \"the first point towards which bodies move (e.g., [100.0,50.0])\",\"g\": \"the length of the first acceleration vector (a number)\"}");

	
	
	/**
	 * Constructor of the MovingTowardsFixedPointBuilder class
	 */
	public MovingTowardsTwoFixedPointBuilder() {
		super(_TYPE_ID, _DESC, _DATA);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {

		validateData(data);
		
		double g = data.optDouble("g1", _DEFAULT_g_VALUE);
		double g2 =data.optDouble("g2", _DEFAULT_g_VALUE);
		Vector2D c = _DEFAULT_c_VALUE;
		if (data.has("c1")) {
			c = new Vector2D(data.getJSONArray("c1").getDouble(0), data.getJSONArray("c1").getDouble(1));
		}
		Vector2D c2 = _DEFAULT_c_VALUE;
		if (data.has("c2")) {
			c = new Vector2D(data.getJSONArray("c2").getDouble(0), data.getJSONArray("c2").getDouble(1));
		}
		

		return new MovingTowardsTwoFixedPoints(c, g,c2,g2);
	}

	@Override
	protected void validateData(JSONObject data) {
		if (data.has("c1")) {
			validateVector2D(data, "c1");
			
		}
		if (data.has("c2")) {
			validateVector2D(data, "c2");
			
		}
	}

	@Override
	public JSONObject getInfo() {
		JSONObject info = super.getInfo();
		info.put("data", _DATA);
		return info;
	}
}
