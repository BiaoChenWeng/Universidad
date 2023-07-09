package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;

/**
 * @author Dani
 * @author Biao Chen
 */
@SuppressWarnings("serial")
public class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {

	private String[] _header = { "Id", "Force Laws", "Bodies" };
	private List<BodiesGroup> _groups;
	private Controller _controller;

	/**
	 * The constructor of the GroupsTableModel class
	 * 
	 * @param controller The controller of the simulator
	 */
	public GroupsTableModel(Controller controller) {
		this._groups = new ArrayList<BodiesGroup>();
		this._controller = controller;
		this._controller.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _groups.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		BodiesGroup bodiesGroup = this._groups.get(rowIndex);
		switch (columnIndex) {

		case 0:
			return bodiesGroup.getId();
		case 1:
			return bodiesGroup.getForceLawsInfo();
		case 2:
			StringBuffer bodiesIds = new StringBuffer();
			for (Body body : bodiesGroup) {
				bodiesIds.append(body.getId() + " ");
			}
			return bodiesIds;
		}
		return null;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this._groups = new ArrayList<BodiesGroup>();
		this.fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (Map.Entry<String, BodiesGroup> group : groups.entrySet()) {
			this._groups.add(group.getValue());
		}
		this.fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		this._groups.add(g);
		this.fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		this.fireTableDataChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		this.fireTableDataChanged();
	}

	@Override
	public String getColumnName(int index) {
		return this._header[index];
	}
}