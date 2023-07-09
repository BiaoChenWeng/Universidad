package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
@SuppressWarnings("serial")
class ForceLawsDialog extends JDialog implements SimulatorObserver {

	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;

	private static final String DESCRIPTION_MESSAGE = "Select a force law and provide values for the parameters int the Value Column (default valies are used for parameters with no value).";

	private Controller _controller;
	private JSONObject _forceData;

	private List<JSONObject> _forceLawsInfo;

	private String[] _headers = { "Key", "Value", "Description" };

	private int _selectedLawIndex;
	private int _status;

	private JComboBox<String> _groupsComboBox;
	private JComboBox<String> _lawsComboBox;

	public ForceLawsDialog(Frame parent, Controller controller) {
		super(parent, true);
		this._controller = controller;
		initGUI();
		this._controller.addObserver(this);
	}

	private void initGUI() {
		setTitle("Force Laws Selection");

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		JLabel description = new JLabel(DESCRIPTION_MESSAGE);
		mainPanel.add(description);

		// Force laws table
		initForceLawsTable(mainPanel);

		// Selection panel (groups and force laws combo boxes)
		initSelectionPanel(mainPanel);

		// Confirmation panel (ok and cancel button)
		initConfirmationPanel(mainPanel);

		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}

	/**
	 * Initializes the confirmation panel
	 * 
	 * @param panel The panel where the confirmation panel is added
	 */
	private void initConfirmationPanel(JPanel panel) {
		JPanel confirmationPanel = new JPanel();

		// Cancel Button
		initCancelButton(confirmationPanel);

		// OK Button
		initOkButton(confirmationPanel);

		panel.add(confirmationPanel);
	}

	/**
	 * Initializes the ok button
	 * 
	 * @param panel The panel where the button is added
	 */
	private void initOkButton(JPanel panel) {
		JButton okButton = new JButton("Ok");

		// Action performed when the button is pressed
		okButton.addActionListener((e) -> {
			JSONObject forceData = getJSON();
			JSONObject forceInstance = new JSONObject();
			forceInstance.put("type", this._forceLawsInfo.get(this._selectedLawIndex).getString("type"));
			forceInstance.put("data", forceData);
			try {
				this._controller.setForcesLaws(this._groupsComboBox.getSelectedItem().toString(), forceInstance);
			}catch (Exception a) {
				Utils.showErrorMsg(a.getMessage());
			}
			this._status = 1;
			setVisible(false);
		});

		panel.add(okButton);
	}

	/**
	 * Initializes the selection panel
	 * 
	 * @param panel The panel where the selection panel is added
	 */
	private void initSelectionPanel(JPanel panel) {
		JPanel selectionPanel = new JPanel();
		panel.add(selectionPanel);

		// Force laws combo box
		initLawsComboBox(selectionPanel);

		// Groups combo box
		initGroupsComboBox(selectionPanel);
	}

	/**
	 * Initializes the cancel button
	 * 
	 * @param panel The panel where the button is added
	 */
	private void initCancelButton(JPanel panel) {
		JButton cancelButton = new JButton("Cancel");
		// Action performed when the button is pressed
		cancelButton.addActionListener((e) -> {
			this._status = 0;
			setVisible(false);
		});
		panel.add(cancelButton);
	}

	/**
	 * Initializes the groups combo box
	 * 
	 * @param panel The panel where the combo box is added
	 */
	private void initGroupsComboBox(JPanel panel) {

		// The group's text
		JLabel groupLabel = new JLabel("Group: ");
		panel.add(groupLabel);

		// The combo box
		this._groupsModel = new DefaultComboBoxModel<>();
		this._groupsComboBox = new JComboBox<String>(this._groupsModel);
		panel.add(this._groupsComboBox);
	}

	/**
	 * Initializes the force laws combo box
	 * 
	 * @param panel The panel where the combo box is added
	 */
	private void initLawsComboBox(JPanel panel) {

		// The force law's text
		JLabel forceLawLabel = new JLabel("Force Law: ");
		panel.add(forceLawLabel);

		// The combo box
		this._lawsModel = new DefaultComboBoxModel<>();
		this._lawsComboBox = new JComboBox<String>(this._lawsModel);
		this._lawsComboBox.addActionListener((e) -> {
			this._selectedLawIndex = this._lawsComboBox.getSelectedIndex();
			this._forceData = this._forceLawsInfo.get(this._selectedLawIndex).getJSONObject("data");
			this.updateTable();
		});
		panel.add(this._lawsComboBox);
	}

	/**
	 * Initializes the force law's table
	 * 
	 * @param panel The panel where the table is added
	 */
	private void initForceLawsTable(JPanel panel) {

		// The table's model
		this._dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}

		};
		this._dataTableModel.setColumnIdentifiers(_headers);

		// The table
		JTable dataTable = new JTable(this._dataTableModel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		dataTable.setPreferredSize(new Dimension(700, 400));
		panel.add(dataTable);
	}

	public JSONObject getJSON() {
		StringBuilder s = new StringBuilder();
		s.append('{');
		for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
			String k = _dataTableModel.getValueAt(i, 0).toString();
			String v = _dataTableModel.getValueAt(i, 1).toString();
			if (!v.isEmpty()) {
				s.append('"');
				s.append(k);
				s.append('"');
				s.append(':');
				s.append(v);
				s.append(',');
			}
		}

		if (s.length() > 1)
			s.deleteCharAt(s.length() - 1);
		s.append('}');
	
		return new JSONObject(s.toString());

	}

	private void updateTable() {
		this._dataTableModel.setNumRows(this._forceData.length());
		int i = 0;
		for (String key : this._forceData.keySet()) {
			_dataTableModel.setValueAt(key, i, 0);
			_dataTableModel.setValueAt("", i, 1);
			_dataTableModel.setValueAt(_forceData.getString(key), i, 2);
			i++;
		}
		_dataTableModel.fireTableStructureChanged();
	}


	public int open() {
		if (this._groupsModel.getSize() == 0) {
			Utils.showErrorMsg("There are no groups loaded into the simulator");
			this._status = 0;
			return this._status;
		} else {
			if (getParent() != null) {
				int x = getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2;
				int y = getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2;
				setLocation(x, y);
			}
			pack();
			setVisible(true);
		}
		return this._status;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		for (String gId : groups.keySet()) {
			this._groupsModel.addElement(gId);
		}
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		// Load the info of the force laws
		this._forceLawsInfo = this._controller.getForceLawsInfo();

		for (JSONObject forceLawInfo : this._forceLawsInfo) {
			this._lawsModel.addElement(forceLawInfo.getString("desc"));
		}

		for (String gId : groups.keySet()) {
			this._groupsModel.addElement(gId);
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup group) {
		this._groupsModel.addElement(group.getId());
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body body) {
	}

	@Override
	public void onDeltaTimeChanged(double deltaTime) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup group) {
	}
}
