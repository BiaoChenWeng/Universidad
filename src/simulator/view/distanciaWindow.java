package simulator.view;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import simulator.control.Controller;

public class distanciaWindow extends JPanel{
	
	
	
	private distanciaTotal tabla;

	public distanciaWindow(Controller c) {
		tabla = new distanciaTotal(c);
		initGui();
	}

	private void initGui() {
		this.setLayout(new BorderLayout());
		distanciaTotal x = this.tabla;
		JPanel distanceTable= new InfoTable("distance",x );
		JButton b = new JButton("Reset Distance");
		b.addActionListener((e)->{
			
			x.resetDistance();
		});
		this.add(b,BorderLayout.PAGE_START);
		this.add(distanceTable,BorderLayout.CENTER);
		
		
	}
	
	
	
}
