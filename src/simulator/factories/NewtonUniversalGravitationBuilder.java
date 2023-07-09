package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	private static final double _G_DEFAULT_VALUE = 6.67e-11;
	private static final JSONObject _DATA = new JSONObject("{ \"G\":\"the gravitational constant (a number)\"}");
	/**
	 * An string which identifies the object type
	 */
	static final public String _TYPE_ID = "nlug";
	static final private String _DESC = "Newton Universal Gravitation ForceLaws";

	/**
	 * Constructor of the NewtonUniversalGravitationBuilder class
	 */
	public NewtonUniversalGravitationBuilder() {
		super(_TYPE_ID, _DESC, _DATA);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {

		double G = data.optDouble("G", _G_DEFAULT_VALUE);
		return new NewtonUniversalGravitation(G);
	}

	@Override
	// No data needs to be validated
	protected void validateData(JSONObject data) {
	}

	@Override
	public JSONObject getInfo() {
		JSONObject info = super.getInfo();
		info.put("data", _DATA);
		return info;
	}
}
