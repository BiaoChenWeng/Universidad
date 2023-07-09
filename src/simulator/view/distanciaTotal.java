package simulator.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;

public class distanciaTotal extends AbstractTableModel implements SimulatorObserver {
	private final static String[] _header = { "Body", "AccumulatedDistance" };

	private class pair {
		public Vector2D p;
		public double distancia;

		public pair() {
			p = new Vector2D();
			distancia = 0.0;
		}

		public pair(Vector2D p) {
			this.p = p;
			distancia = 0.0;
		}

	}

	Map<String, pair> bodies_position;
	List<Body> lista;
	private Controller ctrl;

	public distanciaTotal(Controller ctrl) {
		this.bodies_position = new HashMap<>();
		this.lista = new ArrayList<>();
		this.ctrl = ctrl;
		this.ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.lista.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this._header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body b = lista.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return b.getId();
		case 1:
			return this.bodies_position.get(b.getgId() + ":" + b.getId()).distancia;
		}
		return null;

	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for (BodiesGroup x : groups.values()) {
			for (Body i : x) {
				pair a = this.bodies_position.get(i.getgId() + ":" + i.getId());
				a.distancia=a.distancia + (a.p.distanceTo(i.getPosition()));
				a.p= i.getPosition();
				this.bodies_position.put(i.getgId() + ":" + i.getId(), a);
			}
		}
		fireTableDataChanged();
	}

	public void resetDistance() {
		for (pair x : this.bodies_position.values()) {
			x.distancia = 0;
		}
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		this.bodies_position = new HashMap<>();
		this.lista = new ArrayList<>();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double deltaTime) {

		for (BodiesGroup x : groups.values()) {
			for (Body i : x) {
				this.lista.add(i);
				this.bodies_position.put(i.getgId() + ":" + i.getId(), new pair(i.getPosition()));
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup group) {

	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body body) {
		lista.add(body);
		this.bodies_position.put(body.getgId() + ":" + body.getId(), new pair(body.getPosition()));
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
