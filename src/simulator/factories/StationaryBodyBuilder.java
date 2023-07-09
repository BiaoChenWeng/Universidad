package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class StationaryBodyBuilder extends Builder<Body> {

	/**
	 * An string which identifies the object type
	 */
	static final private String _TYPE_ID = "st_body";
	static final private String _DESC = "StationaryBody the body does not move";
	static final private JSONObject _DATA = new JSONObject(
			"{ \"id\": \"the idenfier of the body (a string)\",\"gid\": \"the idenfier of the group the body belongs (a string)\",\"position\": \"where the body is located (a vector2D)\",\"mass\": \"how much the body weights (a number)\"}");

	/**
	 * Constructor of the StationaryBodyBuilder class
	 */
	public StationaryBodyBuilder() {
		super(_TYPE_ID, _DESC, _DATA);
	}

	@Override
	protected Body createInstance(JSONObject data) {

		validateData(data);

		String id = data.getString("id");
		String gid = data.getString("gid");
		Vector2D p = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
		double m = data.getDouble("m");

		return new StationaryBody(id, gid, p, m);
	}

	@Override
	protected void validateData(JSONObject data) {
		validateJSONCamp(data, "id");
		validateJSONCamp(data, "gid");
		validateVector2D(data, "p");
		validateJSONCamp(data, "m");
	}

	@Override
	public JSONObject getInfo() {
		return super.getInfo();
	}

}
