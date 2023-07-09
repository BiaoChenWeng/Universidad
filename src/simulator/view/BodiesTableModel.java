package simulator.view;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
@SuppressWarnings("serial")
public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private final static String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	private List<Body> _bodies;
	private Controller _controller;

	/**
	 * Constructor of the class BodiesTableModel
	 * 
	 * @param controller The controller of the simulator
	 */
	public BodiesTableModel(Controller controller) {
		this._bodies = new ArrayList<Body>();
		this._controller = controller;
		this._controller.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Body body = this._bodies.get(rowIndex);
		switch (columnIndex) {

		case 0:
			return body.getId();
		case 1:
			return body.getgId();
		case 2:
			return body.getMass();
		case 3:
			return body.getVelocity();
		case 4:
			return body.getPosition();
		case 5:
			return body.getForce();
		default:
			return null;
		}
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		this.fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this._bodies.clear();
		this.fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (Map.Entry<String, BodiesGroup> group : groups.entrySet()) {
			for (Body body : group.getValue()) {
				this._bodies.add(body);
			}
		}
		this.fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		this._bodies.add(b);
		this.fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

	@Override
	public String getColumnName(int index) {
		return this._header[index];
	}
}
