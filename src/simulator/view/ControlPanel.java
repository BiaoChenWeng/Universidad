package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.*;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements SimulatorObserver {

	private Controller _controller;
	private JToolBar _toolBar;
	private JFileChooser _fileChooser;
	private boolean _stopped = true;

	private JButton _quitButton;
	private JButton _runButton;
	private JButton _loadButton;
	private JButton _physicsButton;
	private JButton _stopButton;
	private JButton _viewerButton;
	private ForceLawsDialog _forceLawsDialog;
	private fuerzaTotalwindow fuerza;
	
	private static final double INITIAL_DELTA_TIME = 2500.0;
	private static final int INITIAL_STEPS = 10000;
	private static final int SPINNER_STEP_SIZE = 100;

	private JSpinner _stepsSpinner;
	private JTextField _deltaTimeField;

	
	
	private JButton _forceButton;
	
	
	
	
	
	
	/**
	 * Constructor of the class ControlPanel
	 * 
	 * @param controller The controller of the simulator
	 */
	public ControlPanel(Controller controller) {
		this._controller = controller;
		initGUI();
		this._controller.addObserver(this);
	}

	/**
	 * Initializes the control panel with it's components
	 */
	private void initGUI() {
		setLayout(new BorderLayout());
		this._toolBar = new JToolBar();
		add(this._toolBar, BorderLayout.PAGE_START);

		// Open Button
		initLoadButton();

		this._toolBar.addSeparator();

		// Physics Button
		initPhysicsButton();

		// Viewer Button
		initViewerButton();

		this._toolBar.addSeparator();

		// Run Button
		initRunButton();

		// Stop Button
		initStopButton();

		// Steps Spinner
		initStepsSpinner();

		// Delta time Text Field
		initDeltaTimeField();

		this._toolBar.add(Box.createGlue());
		this._toolBar.addSeparator();


		
		
		this._forceButton= createButton(Messages.RUN_MESSAGE, Messages.VIEWER_ICON);
		
		// The actions performed when the button is pressed
		this._forceButton.addActionListener((e) -> {
			if(this.fuerza==null)
				this.fuerza= new fuerzaTotalwindow(this._controller); 
			this.fuerza.open();
		});
		this._toolBar.add(this._forceButton);
//		
		// Quit Button
		initQuitButton();

	}

	/**
	 * Initializes the load button
	 */
	private void initLoadButton() {

		this._loadButton = createButton(Messages.LOAD_MESSAGE, Messages.LOAD_ICON);

		// The actions performed when the button is pressed
		this._loadButton.addActionListener((e) -> {
			this._fileChooser = new JFileChooser();
			if (this._fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File file = this._fileChooser.getSelectedFile();
				try {
					InputStream data = new FileInputStream(file);
					this._controller.reset();
					this._controller.loadData(data);
				} catch (FileNotFoundException e1) {
					Utils.showErrorMsg(e1.getMessage());
				}
			}
		});
		this._toolBar.add(this._loadButton);
	}

	/**
	 * Initializes the physics button
	 */
	private void initPhysicsButton() {
		this._physicsButton = createButton(Messages.PHYSICS_MESSAGE, Messages.PHYSICS_ICON);

		// The actions performed when the button is pressed
		this._physicsButton.addActionListener((e) -> {
			if (this._forceLawsDialog == null) {
				this._forceLawsDialog = new ForceLawsDialog(Utils.getWindow(this), _controller);
			}
			this._forceLawsDialog.open();
		});
		this._toolBar.add(this._physicsButton);
	}

	/**
	 * Initializes the viewer button
	 */
	private void initViewerButton() {
		this._viewerButton = createButton(Messages.VIEWER_MESSAGE, Messages.VIEWER_ICON);

		// The actions performed when the button is pressed
		this._viewerButton.addActionListener((e) -> {
			JFrame simulationViewer = new ViewerWindow(Utils.getWindow(this), _controller);
		});
		this._toolBar.add(this._viewerButton);
	}

	/**
	 * Initializes the run button
	 */
	private void initRunButton() {
		this._runButton = createButton(Messages.RUN_MESSAGE, Messages.RUN_ICON);

		// The actions performed when the button is pressed
		this._runButton.addActionListener((e) -> {
			this._stopped = false;
			disableButtons();
			
			try {
				this._controller.setDeltaTime(Double.parseDouble(this._deltaTimeField.getText()));
			} catch (Exception e1) {
				Utils.showErrorMsg(Messages.INVALID_STEP_MESSAGE);	
			}
			run_sim(Integer.parseInt(this._stepsSpinner.getValue().toString()));

		});
		this._toolBar.add(this._runButton);

	}

	/**
	 * Initializes the stop button
	 */
	private void initStopButton() {
		this._stopButton = createButton(Messages.STOP_MESSAGE, Messages.STOP_ICON);

		// The actions performed when the button is pressed
		this._stopButton.addActionListener((e) -> {
			this._stopped = true;
		});
		
		this._toolBar.add(this._stopButton);
	}

	/**
	 * Initializes the steps spinner
	 */
	private void initStepsSpinner() {

		// Steps text
		JLabel stepsText = new JLabel("Steps: ");
		this._toolBar.add(stepsText);

		// The spinner model (Initial value = INITIAL_STEPS, the min value to 0, the max
		// value to Integer.MAX_VALUE and the step size to 100)
		SpinnerModel stepsModel = new SpinnerNumberModel(INITIAL_STEPS, 0, Integer.MAX_VALUE, SPINNER_STEP_SIZE);

		// The spinner
		this._stepsSpinner = new JSpinner(stepsModel);
		this._stepsSpinner.setPreferredSize(new Dimension(85, 45));
		this._stepsSpinner.setMinimumSize(new Dimension(85, 45));
		this._stepsSpinner.setMaximumSize(new Dimension(85, 45));
		this._toolBar.add(this._stepsSpinner);
	}

	/**
	 * Initializes the delta time field
	 */
	private void initDeltaTimeField() {

		// Delta time text
		JLabel deltaTimeText = new JLabel("Delta-Time: ");
		this._toolBar.add(deltaTimeText);

		// The text field
		this._deltaTimeField = new JTextField(Double.toString(INITIAL_DELTA_TIME));
		this._deltaTimeField.setToolTipText("Real time (seconds) corresponding to a step");
		this._deltaTimeField.addActionListener((e) -> { // To ensure only admits a double number. To letters or other
														// symbols are supported/admitted
			try {
				if (Double.parseDouble(this._deltaTimeField.getText()) <= 0) {
					throw new Exception();
				}
			} catch (Exception e1) {
				Utils.showErrorMsg(Messages.INVALID_DELTA_TIME_MESSAGE);
				this._deltaTimeField.setText(Double.toString(INITIAL_DELTA_TIME));
			}
		});
		this._deltaTimeField.setPreferredSize(new Dimension(70, 45));
		this._deltaTimeField.setMinimumSize(new Dimension(70, 45));
		this._deltaTimeField.setMaximumSize(new Dimension(70, 45));
		this._toolBar.add(this._deltaTimeField);
	}

	/**
	 * Initializes the quit button
	 */
	private void initQuitButton() {
		this._quitButton = createButton(Messages.QUIT_MESSAGE, Messages.QUIT_ICON);

		// The actions performed when the button is pressed
		this._quitButton.addActionListener((e) -> {
			Utils.quit(this);
		});
		this._toolBar.add(this._quitButton);
	}

	/**
	 * Disables every button except the stop button
	 */
	private void disableButtons() {
		setAllButtons(false);
	}

	/**
	 * Enables every button except the stop button (which is always enabled)
	 */
	private void enableButtons() {
		setAllButtons(true);
	}

	/**
	 * Enables or disables every button except the stop button depending on the
	 * state
	 * 
	 * @param state A boolean which indicates if the button is set to enabled or
	 *              disabled
	 */
	private void setAllButtons(boolean state) {
		this._quitButton.setEnabled(state);
		this._runButton.setEnabled(state);
		this._loadButton.setEnabled(state);
		this._physicsButton.setEnabled(state);
		this._viewerButton.setEnabled(state);
	}

	/**
	 * Runs the simulation a given amount of steps
	 * 
	 * @param steps The number of times the simulator updates
	 */
	private void run_sim(int steps) {
		if (steps > 0 && !this._stopped) {
			try {
				this._controller.run(1);
			} catch (Exception e) {
				Utils.showErrorMsg(e.getMessage());
				enableButtons();
				this._stopped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(steps - 1));
		} else {
			enableButtons();
			this._stopped = true;
		}
	}

	/**
	 * Creates a button with the given tool tip and the icon stored on a given
	 * directory
	 * 
	 * @param tipText The tool tip of the button
	 * @param iconDir The directory of the icon
	 * @return The created button
	 */
	private JButton createButton(String tipText, String iconDir) {
		JButton boton;

		boton = new JButton();
		boton.setToolTipText(tipText);
		boton.setIcon(new ImageIcon(iconDir));
		return boton;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double deltaTime) {
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		this._deltaTimeField.setText(Double.toString(dt));
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

}
