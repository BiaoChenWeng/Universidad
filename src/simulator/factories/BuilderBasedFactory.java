package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import simulator.view.Messages;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 *
 * @param <T> The type of the objects the factory can build
 */
public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;

	/**
	 * Constructor of the BuilderBasedFactory class
	 */
	public BuilderBasedFactory() {
		this._builders = new HashMap<>();
		this._buildersInfo = new LinkedList<>();
	}

	/**
	 * Constructor of the BuilderBasedFactory class
	 * 
	 * @param builders The list of builders that are added to this factory
	 */
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		for (Builder<T> b : builders) {
			this.addBuilder(b);
		}
	}

	/**
	 * Adds the given builder to this factory
	 * 
	 * @param builder The builder that its been added
	 */
	public void addBuilder(Builder<T> builder) {
		this._builders.put(builder.getTypeTag(), builder);
		this._buildersInfo.add(builder.getInfo());
	}

	@Override
	public T createInstance(JSONObject info) {
		if (info == null) {
			throw new IllegalArgumentException(Messages.INVALID_INSTANCE_INFO_NULL);
		}

		if (!this._builders.containsKey(info.getString("type"))) {
			throw new IllegalArgumentException(Messages.INVALID_INSTANCE_VALUE + info.toString());
		}

		JSONObject data = info.has("data") ? info.getJSONObject("data") : new JSONObject();
		T createdInstance = this._builders.get(info.getString("type")).createInstance(data);

		if (createdInstance == null) {
			throw new IllegalArgumentException(Messages.INVALID_INSTANCE_VALUE + info.toString());
		}

		return createdInstance;
	}

	/**
	 * @return A collection with the information of the factory
	 */

	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}

}
