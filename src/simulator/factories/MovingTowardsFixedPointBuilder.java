package simulator.factories;

import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	private static final Vector2D _DEFAULT_c_VALUE = new Vector2D();
	private static final double _DEFAULT_g_VALUE = 9.81;

	/**
	 * An string which identifies the object type
	 */
	static final public String _TYPE_ID = "mtfp";
	static final private String _DESC = "Moving Toward fixed point ForceLaw";
	static final private JSONObject _DATA = new JSONObject(
			"{ \"c\": \"the point towards which bodies move (e.g., [100.0,50.0])\",\"g\": \"the length of the acceleration vector (a number)\"}");

	/**
	 * Constructor of the MovingTowardsFixedPointBuilder class
	 */
	public MovingTowardsFixedPointBuilder() {
		super(_TYPE_ID, _DESC, _DATA);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {

		validateData(data);

		double g = data.optDouble("g", _DEFAULT_g_VALUE);
		Vector2D c = _DEFAULT_c_VALUE;
		if (data.has("c")) {
			c = new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1));
		}

		return new MovingTowardsFixedPoint(c, g);
	}

	@Override
	protected void validateData(JSONObject data) {
		if (data.has("c")) {
			validateVector2D(data, "c");
		}
	}

	@Override
	public JSONObject getInfo() {
		JSONObject info = super.getInfo();
		info.put("data", _DATA);
		return info;
	}
}
