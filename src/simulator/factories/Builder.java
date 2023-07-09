package simulator.factories;

import org.json.JSONObject;
import simulator.view.Messages;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 * 
 * @param <T> Type of the data the builder can build
 */
public abstract class Builder<T> {
	private String _typeTag;
	private String _desc;
	private JSONObject _data;

	/**
	 * Constructor of the Builder class
	 * 
	 * @param typeTag The string that identifies the builder
	 * @param desc    The string that contains the description of what the builder
	 *                can build
	 * @param data    The json containing the data that contains what the builder
	 *                can build
	 * @throws IllegalArgumentException If any of the data is invalid
	 */
	public Builder(String typeTag, String desc, JSONObject data) {

		validateConstructorData(typeTag, desc, data);
		_typeTag = typeTag;
		_desc = desc;
		_data = data;
	}

	/**
	 * Validates the data given to the constructor
	 * 
	 * @param typeTag     The string that identifies the builder
	 * @param description The string that contains the description of what the
	 *                    builder can build
	 * @param data        The json containing the data that contains what the
	 *                    builder can build
	 * @throws IllegalArgumentException If any of the data is invalid
	 */
	static private void validateConstructorData(String typeTag, String description, JSONObject data) {
		if (description == null || description.length() == 0) {
			throw new IllegalArgumentException(String.format(Messages.INVALID_DESCRIPTION_MESSAGE, description));
		}
		if (typeTag == null || typeTag.trim().length() == 0) {
			throw new IllegalArgumentException(String.format(Messages.INVALID_TYPE_TAG_MESSAGE, typeTag));
		}
		if (data == null) {
			throw new IllegalArgumentException(String.format(Messages.INVALID_DATA_MESSAGE, data));
		}
	}

	/**
	 * Gets the type tag that identifies the object the builder can build
	 * 
	 * @return The type tag that identifies what the builder can build
	 */
	public String getTypeTag() {
		return _typeTag;
	}

	/**
	 * Gets the info of the builder on a JSON format
	 * 
	 * @return The info of the builder
	 */
	public JSONObject getInfo() {
		JSONObject info = new JSONObject();
		info.put("type", _typeTag);
		info.put("desc", _desc);
		info.put("data", _data);
		return info;
	}

	@Override
	public String toString() {
		return _desc;
	}

	/**
	 * Creates a instance of the object with the data given
	 * 
	 * @param data The jsonObject that contains the data of the new object
	 * @return The builded object
	 * @throws IllegalArgumentException If the data given is no valid
	 */
	protected abstract T createInstance(JSONObject data);

	/**
	 * Validates the data for creating an object instance
	 * 
	 * @param data The jsonObject that contains the data of the new object
	 * @throws IllegalArgumentException If the data given is no valid
	 */
	protected abstract void validateData(JSONObject data);

	/**
	 * Validates if the data identified by the given key can be transformed to a 2D
	 * vector
	 * 
	 * @param data The jsonObject that contains the data
	 * @param key  The string that identifies the data
	 * @throws IllegalArgumentException If it can not be transformed to a 2D vector
	 *                                  or the key is not present
	 */
	protected static void validateVector2D(JSONObject data, String key) {

		validateJSONCamp(data, key);
		final int VECTOR_2D_LENGTH = 2;
		if (data.getJSONArray(key).length() != 2) {
			throw new IllegalArgumentException(
					String.format(Messages.INVALID_ARGUMENTS_NUMBER_MESSAGE, key, VECTOR_2D_LENGTH));
		}
	}

	/**
	 * Validates if data contains the key camp
	 * 
	 * @param data The json object it validates if it has the key camp
	 * @param key  The string that identifies the desired camp
	 * @throws IllegalArgumentException If the key is not present on the data given
	 */
	protected static void validateJSONCamp(JSONObject data, String key) {

		if (!data.has(key)) {
			throw new IllegalArgumentException(String.format(Messages.KEY_MISSING_MESSAGE, key));
		}
	}
}
