package simulator.factories;


import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;
import simulator.model.TempSensBody;

public class TempSensBodyBuilder extends Builder<Body>{
	/**
	 * An string which identifies the object type
	 */
	static final private String _TYPE_ID = "tempsens";
	static final private String _DESC = "Temperatura";
	static final private JSONObject _DATA = new JSONObject(
			"{ \"id\": \"the idenfier of the body (a string)\",\"gid\": \"the idenfier of the group the body belongs (a string)\",\"position\": \"where the body is located (a vector2D)\",\"mass\": \"how much the body weights (a number)\"}");

	
	private static final double _default_minT= 1e5;
	private static final double _default_maxT= 10e5;
	private static final double _default_redF= 0.3;
	private static final double _default_incG= 10e-5;
	/**
	 * Constructor of the StationaryBodyBuilder class
	 */
	public TempSensBodyBuilder() {
		super(_TYPE_ID, _DESC, _DATA);
	}

	@Override
	protected Body createInstance(JSONObject data) {

		validateData(data);

		String id = data.getString("id");
		String gid = data.getString("gid");
		Vector2D p = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
		double m = data.getDouble("m");
		Vector2D v = new Vector2D(data.getJSONArray("v").getDouble(0), data.getJSONArray("v").getDouble(1));
		double minT = data.optDouble("minT",this._default_minT);
		double maxT = data.optDouble("maxT",this._default_maxT);
		double redF = data.optDouble("redF",this._default_redF);
		double incF = data.optDouble("incF",this._default_incG);
		return new TempSensBody(id, gid, p, v,m, minT, maxT, redF, incF);
	}

	@Override
	protected void validateData(JSONObject data) {
		validateJSONCamp(data, "id");
		validateJSONCamp(data, "gid");
		validateVector2D(data, "p");
		validateVector2D(data, "v");
		validateJSONCamp(data, "m");
	}

	@Override
	public JSONObject getInfo() {
		return super.getInfo();
	}
}
