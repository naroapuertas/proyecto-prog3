package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.GestionFinanciera;

//nuestro panel financiero
public class PanelFinanciero extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GestionFinanciera gestion;

	public PanelFinanciero(GestionFinanciera gestion) {
		super();
		this.gestion = gestion;
		
		//definimos tipo de layout
		this.setLayout(new BorderLayout());
		
		//escribimos el titulo en un jlabel
		JLabel titulo = new JLabel("Módulo financiero");
		
		//añadimos titulo al jpanel, arriba
		this.add(titulo, BorderLayout.NORTH);	
		
		//creamos 3 jlabel que digan lo gastado, lo restante y el porcentaje
		JLabel gastado = new JLabel("Gastado: " + gestion.getTotalGastado());
		JLabel restante = new JLabel("Restante: " + gestion.getRestante());
		JLabel porcentaje = new JLabel("Porcentaje: " + gestion.getPorcentajeRestante());
		
		//queremos añadir los jlabel al panel, pero como cada zona solo guarda un componente, solo se veria el ultimo que añadimos:
		//entonces creamos un panel para añadirlos
		JPanel panel2 = new JPanel(new GridLayout(3, 1));
		
		//los añadimos a este panel
		panel2.add(gastado);
		panel2.add(restante);
		panel2.add(porcentaje);
		
		//y añadimos el panel2 al panel principal
		this.add(panel2);
	}
}
