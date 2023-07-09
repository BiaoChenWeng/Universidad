package simulator.factories;

import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class MovingBodyBuilder extends Builder<Body> {
	/**
	 * An string which identifies the object type
	 */
	static final private String _TYPE_ID = "mv_body";
	static final private String _DESC = "Moving Body";
	static final private JSONObject _DATA = new JSONObject(
			"{ \"id\": \"the idenfier of the body (a string)\",\"gid\": \"the idenfier of the group the body belongs (a string)\",\"position\": \"where the body is located (a vector2D)\",\"velocity\": \"the velociry the body has (a vector2D)\",\"mass\": \"how much the body weights (a number)\"}");

	/**
	 * Constructor of the MovingBodyBuilder class
	 */
	public MovingBodyBuilder() {
		super(_TYPE_ID, _DESC, _DATA);
	}

	@Override
	protected Body createInstance(JSONObject data) {

		validateData(data);

		String id = data.getString("id");
		String gid = data.getString("gid");
		Vector2D p = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
		Vector2D v = new Vector2D(data.getJSONArray("v").getDouble(0), data.getJSONArray("v").getDouble(1));
		double m = data.getDouble("m");

		return new MovingBody(id, gid, p, v, m);
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