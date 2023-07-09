package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NoForce;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class NoForceBuilder extends Builder<ForceLaws> {

	/**
	 * An string which identifies the object type
	 */
	static final public String _TYPE_ID = "nf";
	static final private String _DESC = "No force";
	static final private JSONObject _DATA = new JSONObject();

	/**
	 * Constructor of the NoForceBuilder class
	 */
	public NoForceBuilder() {
		super(_TYPE_ID, _DESC, _DATA);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		return new NoForce();
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
