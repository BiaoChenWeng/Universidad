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

public class fuerzaTotal extends AbstractTableModel implements SimulatorObserver {

	private static final String[] header = {"Group","Body","TotalForces"};
	private List<Body> bodies ;
	private Map<String,Vector2D> fuerza;
	private Controller _controller;
	
	
	public fuerzaTotal(Controller controller) {
		this.bodies = new ArrayList<Body>();
		this.fuerza= new HashMap<>();
		this._controller = controller;
		this._controller.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.bodies.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body b = this.bodies.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return b.getgId();
		case 1:
			return b.getId();

		case 2 :
			return fuerza.get(b.getgId()+":"+b.getId());
		}
		
		return null;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for(BodiesGroup x : groups.values()) {
			for(Body y : x) {
				Vector2D current_force = y.getForce();
				Vector2D result = current_force.plus(this.fuerza.get(x.getId()+":"+y.getId()));
				this.fuerza.put(y.getgId()+":"+y.getId(), result);
			}
		}
		fireTableStructureChanged();

	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		this.bodies.clear();
		this.fuerza.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		for(BodiesGroup x : groups.values()) {
			for(Body y : x) {
				this.bodies.add(y);
				this.fuerza.put(y.getgId()+":"+y.getId(), new Vector2D());
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup group) {
		

	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body body) {
		this.bodies.add(body);
		this.fuerza.put(body.getgId()+":"+body.getId(), new Vector2D());
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
	@Override
	public String getColumnName(int index) {
		return this.header[index];
	}
}