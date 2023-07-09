package simulator.view;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;

public class fuerzaTotalwindow extends JDialog {
	private Controller controller;
	private InfoTable tabla;

	public fuerzaTotalwindow(Controller ctrl) {
		this.controller = ctrl;
		initGui();
	}

	private void initGui() {
		setTitle("fuerzaTotal");

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		this.tabla = new InfoTable("fuerza", new fuerzaTotal(controller));
		mainPanel.add(tabla);
		
		
		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	public void open() {
		setVisible(true);
	}
}
