package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.MovingAroundBodyGreaterMass;

public class MovingAroundBodyGreaterMassBuilder extends Builder<ForceLaws> {
	private static final double _G_DEFAULT_VALUE = 6.67e-11;
	private static final double _C_default_value = 0.25;
	private static final JSONObject _DATA = new JSONObject("{ \"G\":\"the gravitational constant (a number)\",\"c\": \"the rotation factor (a number)\"}");
	/**
	 * An string which identifies the object type
	 */
	static final public String _TYPE_ID = "mabgm";
	static final private String _DESC = "Moving around the body with greater mass";

	/**
	 * Constructor of the NewtonUniversalGravitationBuilder class
	 */
	public MovingAroundBodyGreaterMassBuilder () {
		super(_TYPE_ID, _DESC, _DATA);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		double G = data.optDouble("G", _G_DEFAULT_VALUE);
		double c = data.optDouble("C",this._C_default_value);
		return new MovingAroundBodyGreaterMass(G, c);
	}

	@Override
	protected void validateData(JSONObject data) {
		
	}

	@Override
	public JSONObject getInfo() {
		JSONObject info = super.getInfo();
		info.put("data", _DATA);
		return info;
	}
}
