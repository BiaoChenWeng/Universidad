package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class StatusBar extends JPanel implements SimulatorObserver {

	private Controller _controller;

	private JLabel _groupsLabel;
	private JLabel _timeLabel;

	private static final String TIME_TEXT = "Time: %s";
	private static final String GROUPS_TEXT = "Groups: %s";

	public StatusBar(Controller controller) {
		this._controller = controller;
		initGUI();
		this._controller.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));

		// Time counter
		initTimeLabel(this);
		addSeparator();

		// Group's counter
		initGroupCount(this);
		addSeparator();
	}

	private void initTimeLabel(JPanel panel) {

		this._timeLabel = new JLabel();
		this._timeLabel.setPreferredSize(new Dimension(130, 20));
		panel.add(this._timeLabel);
	}

	private void initGroupCount(JPanel panel) {
		this._groupsLabel = new JLabel();
		this._groupsLabel.setPreferredSize(new Dimension(110, 20));
		panel.add(this._groupsLabel);
	}

	private void addSeparator() {
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		separator.setPreferredSize(new Dimension(10, 20));
		this.add(separator);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		updateTimeText(time);
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		updateGroupsText(groups);
		updateTimeText(time);

	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		updateGroupsText(groups);
		updateTimeText(time);
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup group) {
		updateGroupsText(groups);
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

	private void updateGroupsText(Map<String, BodiesGroup> groups) {

		this._groupsLabel.setText(String.format(GROUPS_TEXT, groups.size()));
	}

	private void updateTimeText(double time) {

		this._timeLabel.setText(String.format(TIME_TEXT, time));
	}

}
