package simulator.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;

public class VelocityTable extends AbstractTableModel implements SimulatorObserver {

	private final static String[] _header = { "id", "gid", "max_velocity" };
	private Map<Body, Double> velocity;
	private List<Body> orden;

	public VelocityTable(Controller controller) {
		this.velocity = new HashMap<>();
		this.orden = new ArrayList<>();
		controller.addObserver(this);
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return velocity.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this._header.length;
	}

	@Override
	public String getColumnName(int column) {
		return this._header[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body x = this.orden.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return x.getId();
		case 1:
			return x.getgId();
		case 2:
			return this.velocity.get(x);
		default:
			break;
		}
		return null;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for (BodiesGroup x : groups.values()) {
			for (Body b : x) {
				double curr = b.getVelocity().magnitude();
				double max = this.velocity.get(b);
				if (max < curr) {
					this.velocity.put(b, curr);
				}
			}
		}
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		this.velocity.clear();
		this.orden.clear();

	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		for (BodiesGroup x : groups.values()) {
			for (Body b : x) {
				double curr = b.getVelocity().magnitude();

				this.velocity.put(b, curr);
				this.orden.add(b);
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup group) {

	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body body) {
		this.velocity.put(body, body.getVelocity().magnitude());
		this.orden.add(body);
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(BodiesGroup group) {
		// TODO Auto-generated method stub

	}

}
