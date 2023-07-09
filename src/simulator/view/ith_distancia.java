package simulator.view;

import java.util.Map;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;

public class ith_distancia extends DefaultTableModel implements SimulatorObserver {

	private final static String[] _header= {"#","Min Dist.","Max Dist."};
	
	private int stepsEjecutado;
	
	public ith_distancia(Controller ctrl) {
		this.setColumnCount(this._header.length);
		ctrl.addObserver(this);
		
	}
	
	
	
	
	

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		Integer[] value= {0,0,0};
			
		for( BodiesGroup x:groups.values()) {
			for(Body i : x) {
				double distance = i.getPosition().distanceTo(new Vector2D());
				
				
			}
		}
		
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body body) {
		// TODO Auto-generated method stub
		
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
