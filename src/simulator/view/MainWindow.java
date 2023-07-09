package simulator.view;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import simulator.control.Controller;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private static final int PREFERED_WIDTH = 600;
	private static final int PREFERED_HEIGHT = 600;

	private Controller _controller;

	/**
	 * Constructor of the window
	 * 
	 * @param controller The controller of the simulator
	 */
	public MainWindow(Controller controller) {
		super("Physics Simulator");
		_controller = controller;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(PREFERED_WIDTH, PREFERED_HEIGHT));
		setContentPane(mainPanel);

		JPanel controlPanel = new ControlPanel(_controller);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);

		JPanel StatusBar = new StatusBar(_controller);
		mainPanel.add(StatusBar, BorderLayout.PAGE_END);

		// Definition of the tabled panel (it uses a vertical BoxLayout)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// Groups table
		JPanel groupsTable = new InfoTable("Groups", new GroupsTableModel(_controller));
		groupsTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(groupsTable);

		// Bodies table
		JPanel bodiesTable = new InfoTable("Bodies", new BodiesTableModel(_controller));
		bodiesTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(bodiesTable);
//		
//		JPanel ith= new InfoTable("distancia", new ith_distancia(this._controller));
//		ith.setPreferredSize(new Dimension(500, 250));
//		contentPanel.add(ith);
//		
		distanciaWindow distanceTable = new distanciaWindow(_controller);	
		distanceTable.setPreferredSize(new Dimension(500, 250));		
		contentPanel.add(distanceTable);
		
		
//		JPanel VTable = new InfoTable("vel", new VelocityTable(_controller));
//		VTable.setPreferredSize(new Dimension(500, 250));
//		contentPanel.add(VTable);

		
//		JPanel VTable = new InfoTable("force", new fuerzaTotal(_controller));
//		VTable.setPreferredSize(new Dimension(500, 250));
//		contentPanel.add(VTable);
		
		// Center the window to the center of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();

		this.setLocation((width - this.PREFERED_WIDTH) / 2, (height - this.PREFERED_HEIGHT) / 2);

		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				Utils.quit(MainWindow.this);
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

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}

}
