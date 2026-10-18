package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Gasto.TipoGasto;
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
		
		//creamos 3 jlabel que digan lo gastado, lo restante y el porcentaje(String.format para que solo enseñe dos decimales)
		JLabel gastado = new JLabel("Gastado: " + String.format("%.2f",  gestion.getTotalGastado()));
		JLabel restante = new JLabel("Restante: " + String.format("%.2f",  gestion.getRestante()) );
		JLabel porcentaje = new JLabel("Porcentaje: " + String.format("%.2f",  gestion.getPorcentajeRestante()) );
		
		//queremos añadir los jlabel al panel, pero como cada zona solo guarda un componente, solo se veria el ultimo que añadimos:
		//entonces creamos un panel para añadirlos
		JPanel panelResumen = new JPanel(new GridLayout(3, 1));
		
		//los añadimos a este panel
		panelResumen.add(gastado);
		panelResumen.add(restante);
		panelResumen.add(porcentaje);
		
		//y añadimos el panel2 al panel principal
		this.add(panelResumen, BorderLayout.CENTER);
		
		//CUESTIONARIO DE LA PARTE WEST
		
		//creamos el panel, queremos dos columnas(nombre pregunta y respuesta) y 5 filas(5 datos)
		JPanel panelCuestionario = new JPanel(new GridLayout(6, 2));
		
		//vamos añadiendo las opciones
		JLabel concepto = new JLabel("Concepto");
		panelCuestionario.add(concepto);
		JTextField respuestaConcepto = new JTextField();
		panelCuestionario.add(respuestaConcepto);
		JLabel tipo = new JLabel("Tipo");
		panelCuestionario.add(tipo);
		JComboBox<TipoGasto> respuestaTipo = new JComboBox<>(TipoGasto.values());
		panelCuestionario.add(respuestaTipo);
		JLabel importe = new JLabel("Importe");
		panelCuestionario.add(importe);
		JTextField respuestaImporte = new JTextField();
		panelCuestionario.add(respuestaImporte);
		JLabel fecha = new JLabel("Fecha");
		panelCuestionario.add(fecha);
		JTextField respuestaFecha = new JTextField();
		panelCuestionario.add(respuestaFecha);
		JLabel esFijo = new JLabel("Es fijo");
		panelCuestionario.add(esFijo);
		JCheckBox respuestaEsFijo = new JCheckBox();
		panelCuestionario.add(respuestaEsFijo);
		JLabel vacio = new JLabel(" ");
		panelCuestionario.add(vacio);
		JButton botonRegistrarGasto = new JButton("Registrar gasto");
		panelCuestionario.add(botonRegistrarGasto);
		this.add(panelCuestionario, BorderLayout.WEST);
	}
}
