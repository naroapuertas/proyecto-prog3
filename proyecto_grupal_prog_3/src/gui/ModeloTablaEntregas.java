//SOFIA MONGE GONZALEZ 
package gui;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import domain.Tarea;

public class ModeloTablaEntregas extends DefaultTableModel {
	
	
	//titulos de las columnas
	private String[] titulos = { "ASIGNATURA", "ENTREGA", "FECHA LÍMITE", "ESTRÉS", "ESTADO" };
 
	private ArrayList<Tarea> listaTareas;

	public ModeloTablaEntregas(ArrayList<Tarea> lista) {
		this.listaTareas = lista;
		
	}
	//cambia la lista que se muestra y avisa a la tabla para que se repinte
		public void setTareas(ArrayList<Tarea> lista) {
			this.listaTareas = lista;
			fireTableDataChanged();
		}
	 
		//devuelve la tarea de una fila 
		public Tarea getTareaEn(int fila) {
			return listaTareas.get(fila);
		}
	 
		@Override
		public int getRowCount() {
			if (listaTareas == null) {
				return 0;
			}
			return listaTareas.size();
		}
	 
		@Override
		public int getColumnCount() {
			//return 5;
			return titulos.length;
		}
	 
		@Override
		public String getColumnName(int column) {
			return titulos[column];
		}
	 
		//la tabla no se edita directamente
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	 
		@Override
		public Object getValueAt(int row, int column) {
			Tarea t = listaTareas.get(row);
			switch (column) {
			case 0:
				return t.getAsignatura();
			case 1:
				return t.getNombre();
			case 2:
				return t.getFechaLimite().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			case 3:
				return t.getNivelEstres();
			case 4:
				if (t.isCompletada()) {
					return "Completada";
				} else if (t.isVencida()) {
					return "Vencida";
				} else {
					return "Pendiente";
				}
			default:
				return null;
			}
		}
	
	
	
	

}
