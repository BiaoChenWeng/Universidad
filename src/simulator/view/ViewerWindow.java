package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;

public class ViewerWindow extends JFrame implements SimulatorObserver {

	private Controller _ctrl;
	private SimulationViewer _viewer;
	private Frame _parent;
	private SimulatorObserver _this;

	ViewerWindow(Frame parent, Controller ctrl) {
		super("Simulation Viewer");
		_ctrl = ctrl;
		_parent = parent;
		intiGUI();
		_this = this;
		_ctrl.addObserver(this);
	}

	private void intiGUI() {

		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(new JScrollPane(mainPanel));
		_viewer = new Viewer();
		mainPanel.add(_viewer);

		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				_ctrl.removeObserver(_this);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});

		pack();
		if (_parent != null) {
			int x = _parent.getLocation().x + _parent.getWidth() / 2 - getWidth() / 2;
			int y = _parent.getLocation().y + _parent.getHeight() / 2 - getHeight() / 2;
			setLocation(x, y);
		}
		setVisible(true);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		_viewer.update();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		_viewer.reset();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double deltaTime) {
		for (String gId : groups.keySet()) {
			_viewer.addGroup(groups.get(gId));
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup group) {
		_viewer.addGroup(group);
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body body) {
		_viewer.addBody(body);
	}

	@Override
	public void onDeltaTimeChanged(double deltaTime) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup group) {
	}

}
