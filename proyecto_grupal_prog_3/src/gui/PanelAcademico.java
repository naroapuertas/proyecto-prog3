//SOFIA MONGE GONZALEZ
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import domain.GestionAcademica;
import domain.Tarea;

public class PanelAcademico extends JPanel {
 
	private GestionAcademica gestion;
	private ModeloTablaEntregas modelo;
	private JTable tabla;
 
	private JTextField txtAsignatura;
	private JTextField txtNombre;
	private JTextField txtFecha;
	private JComboBox<Integer> cmbEstres;
	private JButton btnAnadir;
	private JTextField txtBuscar;
 
	public PanelAcademico(GestionAcademica gestion) {
		this.gestion = gestion;
		setLayout(new BorderLayout());
 
		//NORTE: formulario para añadir tareas
		JPanel panelFormulario = new JPanel();
		txtAsignatura = new JTextField(10);
		txtNombre = new JTextField(10);
		txtFecha = new JTextField(8);
		cmbEstres = new JComboBox<Integer>(new Integer[] { 1, 2, 3, 4, 5 });
		btnAnadir = new JButton("Añadir");
 
		panelFormulario.add(new JLabel("Asignatura:"));
		panelFormulario.add(txtAsignatura);
		panelFormulario.add(new JLabel("Entrega:"));
		panelFormulario.add(txtNombre);
		panelFormulario.add(new JLabel("Fecha (dd/MM/yyyy):"));
		panelFormulario.add(txtFecha);
		panelFormulario.add(new JLabel("Estrés:"));
		panelFormulario.add(cmbEstres);
		panelFormulario.add(btnAnadir);
		add(panelFormulario, BorderLayout.NORTH);
 
		//CENTRO: tabla
		JPanel panelCentro = new JPanel(new BorderLayout());
 
		JPanel panelBusqueda = new JPanel();
		txtBuscar = new JTextField(15);
		panelBusqueda.add(new JLabel("Buscar:"));
		panelBusqueda.add(txtBuscar);
		panelCentro.add(panelBusqueda, BorderLayout.NORTH);
 
		modelo = new ModeloTablaEntregas(gestion.getTareasOrdenadas());
		tabla = new JTable(modelo);
		tabla.setRowHeight(22);
 
		//renderer: pinta cada fila segun la urgencia de la tarea
		tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				Tarea t = modelo.getTareaEn(row);
 
				if (isSelected) {
					c.setBackground(table.getSelectionBackground());
					c.setForeground(table.getSelectionForeground());
				} else if (t.isCompletada()) {
					c.setBackground(Color.WHITE);
					c.setForeground(Color.GRAY);
				} else {
					c.setForeground(Color.BLACK);
					switch (t.getUrgencia()) {
					case URGENTE:
						c.setBackground(new Color(255, 170, 170)); //rojo
						break;
					case MEDIA:
						c.setBackground(new Color(255, 240, 150)); //amarillo
						break;
					default:
						c.setBackground(new Color(180, 235, 180)); //verde
						break;
					}
				}
				return c;
			}
		});
 
		panelCentro.add(new JScrollPane(tabla), BorderLayout.CENTER);
		add(panelCentro, BorderLayout.CENTER);
 
		
 
		//boton añadir
		btnAnadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
			}
		});
 
		//KeyListener busqueda: filtra la tabla segun se va escribiendo
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				actualizarTabla();
			}
		});
 
		//doble clic en una fila: marca la tarea como completada
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int fila = tabla.rowAtPoint(e.getPoint());
					if (fila >= 0) {
						Tarea t = modelo.getTareaEn(fila);
						gestion.completarTarea(t);
						actualizarTabla();
					}
				}
			}
		});
	}
 
	//vuelve a cargar la tabla con las tareas que coinciden con el texto de busqueda
	private void actualizarTabla() {
		modelo.setTareas(gestion.filtrar(txtBuscar.getText()));
	}

 

}