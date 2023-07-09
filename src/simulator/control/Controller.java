package simulator.control;

import simulator.model.*;
import simulator.view.SimulatorObserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.*;

import simulator.factories.*;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class Controller {

	private PhysicsSimulator _simulator;
	private Factory<ForceLaws> _forceFactory;
	private Factory<Body> _bodiesFactory;

	/**
	 * @param simulator     PhsicsSimulator to run the movement of the bodies
	 * @param ForceFactory  Factory for all the forceLaws
	 * @param BodiesFactory Factory for all the Bodies
	 */
	public Controller(PhysicsSimulator simulator, Factory<ForceLaws> ForceFactory, Factory<Body> BodiesFactory) {
		this._simulator = simulator;
		this._bodiesFactory = BodiesFactory;
		this._forceFactory = ForceFactory;
	}

	/**
	 * Makes the simulator advance a given number of times and outputs the state of
	 * the simulator each times it makes it advance
	 * 
	 * @param steps The number of times simulator is made to advance
	 * @param out   The output stream where the state of the simulator is outputted
	 */
	public void run(int steps, OutputStream out) {
		PrintStream p = new PrintStream(out);

		p.println("{");
		p.println("\"states\": [");

		p.println(this._simulator);

		for (int i = 0; i < steps; i++) {
			this._simulator.advance();
			p.print(",");
			p.println(this._simulator);

		}
		p.println("]");
		p.println("}");
	}

	/**
	 * Makes the simulator advance a given number of times
	 * 
	 * @param steps The number of times simulator is made to advance
	 */
	public void run(int steps) {
		for (int i = 0; i < steps; i++) {
			this._simulator.advance();
		}
	}

	/**
	 * Loads the data from the given input stream into the simulator
	 * 
	 * @param in The input stream from which it loads the data
	 */
	public void loadData(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray groups = jsonInput.getJSONArray("groups");
		for (int i = 0; i < groups.length(); i++) {
			this._simulator.addGroup(groups.getString(i));
		}

		if (jsonInput.has("laws")) {
			JSONArray laws = jsonInput.getJSONArray("laws");
			for (int i = 0; i < laws.length(); i++) {
				JSONObject law = laws.getJSONObject(i);
				this._simulator.setForceLaws(law.getString("id"),
						this._forceFactory.createInstance(law.getJSONObject("laws")));
			}
		}

		JSONArray bodies = jsonInput.getJSONArray("bodies");
		for (int i = 0; i < bodies.length(); i++) {
			this._simulator.addBody(this._bodiesFactory.createInstance(bodies.getJSONObject(i)));
		}
	}

	/**
	 * Resets the simulator
	 */
	public void reset() {
		this._simulator.reset();
	}

	/**
	 * Sets the simulator's delta time to the given one
	 * 
	 * @param deltaTime A double which indicates how much time is each simulation
	 *                  step.
	 */
	public void setDeltaTime(double deltaTime) {
		this._simulator.setDeltaTime(deltaTime);
	}

	/**
	 * Adds the given observer from the simulator
	 * 
	 * @param observer The observer that is being added from the simulator
	 */
	public void addObserver(SimulatorObserver observer) {
		this._simulator.addObserver(observer);
	}

	/**
	 * Removes the given observer from the simulator
	 * 
	 * @param observer The observer that is being removed from the simulator
	 */
	public void removeObserver(SimulatorObserver observer) {
		this._simulator.removeObserver(observer);
	}

	/**
	 * Gets the information of every available force law
	 * 
	 * @return A list of every force law's information
	 */
	public List<JSONObject> getForceLawsInfo() {
		return this._forceFactory.getInfo();
	}

	/**
	 * Sets the force law of the body group identified by the given group id to the
	 * force law made of the given info
	 * 
	 * @param groupId      A string that identifies the group who's force law it's
	 *                     being changed
	 * @param infoForceLaw The json object that contains the info of the new force
	 *                     law
	 */
	public void setForcesLaws(String groupId, JSONObject infoForceLaw) {
		ForceLaws forceLaw = this._forceFactory.createInstance(infoForceLaw);
		this._simulator.setForceLaws(groupId, forceLaw);
	}
}
