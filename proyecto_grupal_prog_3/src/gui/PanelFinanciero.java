package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Gasto.TipoGasto;
import domain.GestionFinanciera;
import domain.Gasto;
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
		botonRegistrarGasto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//leemos el concepto, no puede estar vacio
				String conceptoGasto = respuestaConcepto.getText();
				if (conceptoGasto.isEmpty()) {
					JOptionPane.showMessageDialog(null, "El concepto no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//leemos el importe, tiene que ser un numero (si no es un numero da NumberFormatException)
				double importeGasto;
				try {
					importeGasto = Double.parseDouble(respuestaImporte.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "El importe debe ser un número (con punto para los decimales, por ejemplo 12.5)", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//el importe tiene que ser mayor que 0
				if (importeGasto <= 0) {
					JOptionPane.showMessageDialog(null, "El importe debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//leemos la fecha, tiene que tener el formato aaaa-mm-dd
				LocalDate fechaGasto;
				try {
					fechaGasto = LocalDate.parse(respuestaFecha.getText());
				} catch (DateTimeParseException ex) {
					JOptionPane.showMessageDialog(null, "La fecha debe tener el formato aaaa-mm-dd (por ejemplo 2026-11-15)", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//la fecha no puede ser futura
				if (fechaGasto.isAfter(LocalDate.now())) {
					JOptionPane.showMessageDialog(null, "La fecha no puede ser futura", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//si el gasto es mayor que lo que nos queda, preguntamos si quiere registrarlo igualmente
				if (importeGasto > gestion.getRestante()) {
					int opcion = JOptionPane.showConfirmDialog(null, "Este gasto supera lo que te queda del presupuesto. ¿Quieres registrarlo igualmente?", "Presupuesto superado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (opcion != JOptionPane.YES_OPTION) {
						return;
					}
				}
				
				//creamos el gasto con los datos del formulario y lo registramos en la gestion
				TipoGasto tipoGasto = (TipoGasto) respuestaTipo.getSelectedItem();
				Gasto nuevoGasto = new Gasto(conceptoGasto, tipoGasto, importeGasto, fechaGasto, respuestaEsFijo.isSelected());
				gestion.registrarGasto(nuevoGasto);
				
				//actualizamos los 3 labels del resumen
				gastado.setText("Gastado: " + String.format("%.2f", gestion.getTotalGastado()));
				restante.setText("Restante: " + String.format("%.2f", gestion.getRestante()));
				porcentaje.setText("Porcentaje: " + String.format("%.2f", gestion.getPorcentajeRestante()));
				
				//dejamos el formulario limpio
				respuestaConcepto.setText("");
				respuestaImporte.setText("");
				respuestaTipo.setSelectedIndex(0);
				respuestaFecha.setText(LocalDate.now().toString());
				respuestaEsFijo.setSelected(false);
				
				//avisamos: si queda menos del 50% es un aviso, si no un mensaje normal
				if (gestion.getPorcentajeRestante() < 50) {
					JOptionPane.showMessageDialog(null, "Atención: te queda menos del 50% de tu presupuesto mensual", "Aviso", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Gasto registrado correctamente", "Gasto registrado", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		//FILA PARA FIJAR EL PRESUPUESTO (va abajo porque arriba ya esta el titulo)
		JLabel presupuesto = new JLabel("Presupuesto mensual:");
		JTextField respuestaPresupuesto = new JTextField("" + gestion.getPresupuestoMensual(), 8);
		JButton botonFijarPresupuesto = new JButton("Fijar presupuesto");
		
		//como cada zona solo guarda un componente, los metemos en un panel y añadimos el panel abajo
		JPanel panelSur = new JPanel();
		panelSur.add(presupuesto);
		panelSur.add(respuestaPresupuesto);
		panelSur.add(botonFijarPresupuesto);
		this.add(panelSur, BorderLayout.SOUTH);
		
		//EVENTO DEL BOTON FIJAR PRESUPUESTO
		botonFijarPresupuesto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double nuevoPresupuesto = Double.parseDouble(respuestaPresupuesto.getText());
					
					//si es <= 0 el setter de GestionFinanciera lanza IllegalArgumentException
					gestion.setPresupuestoMensual(nuevoPresupuesto);
					
					//actualizamos los 3 labels del resumen
					gastado.setText("Gastado: " + String.format("%.2f", gestion.getTotalGastado()));
					restante.setText("Restante: " + String.format("%.2f", gestion.getRestante()));
					porcentaje.setText("Porcentaje: " + String.format("%.2f", gestion.getPorcentajeRestante()));
					JOptionPane.showMessageDialog(null, "Presupuesto mensual actualizado", "Presupuesto", JOptionPane.INFORMATION_MESSAGE);
				} catch (NumberFormatException ex) {
					//este catch va primero porque NumberFormatException es un tipo de IllegalArgumentException
					JOptionPane.showMessageDialog(null, "El presupuesto debe ser un número (con punto para los decimales, por ejemplo 300.5)", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//MOUSELISTENER DEL RESUMEN: al pasar el raton se enseña informacion extra
		//añadimos un 4º label al panelResumen (por eso cambiamos su layout a 4 filas)
		JLabel detalle = new JLabel(" ");
		panelResumen.setLayout(new GridLayout(4, 1));
		panelResumen.add(detalle);
		
		//usamos MouseAdapter para no escribir los 5 metodos del MouseListener, solo los que usamos
		panelResumen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				int numGastos = gestion.getListaGastos().size();
				
				//si no hay gastos no dividimos (para no dividir entre 0)
				double media = 0;
				if (numGastos > 0) {
					media = gestion.getTotalGastado() / numGastos;
				}
				detalle.setText("Gastos registrados: " + numGastos + " | Gasto medio: " + String.format("%.2f", media));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				detalle.setText(" ");
			}
		});
 
	}
}
