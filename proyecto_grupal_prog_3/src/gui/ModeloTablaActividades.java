// MIREN BENITEZ RUIZ

package gui;
 
import domain.Actividad;
 
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.table.AbstractTableModel;

public class ModeloTablaActividades extends AbstractTableModel {
 
    private static final long serialVersionUID = 1L;
    private static final String[] COLUMNAS = {"Actividad", "Categoría", "Puntos", "Fecha"};
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
 
    private final transient List<Actividad> actividades = new ArrayList<>();
 
    @Override
    public int getRowCount() { return actividades.size(); }
 
    @Override
    public int getColumnCount() { return COLUMNAS.length; }
 
    @Override
    public String getColumnName(int columna) { return COLUMNAS[columna]; }
 
    @Override
    public Class<?> getColumnClass(int columna) {
        return columna == 2 ? Integer.class : String.class;
    }
 
    @Override
    public Object getValueAt(int fila, int columna) {
        Actividad a = actividades.get(fila);
        switch (columna) {
            case 0: return a.getNombre();
            case 1: return a.getCategoria();
            case 2: return a.getImpactoBienestar();
            case 3: return a.getFecha().format(FORMATO);
            default: return "";
        }
    }
 
    /** Añade una actividad y avisa a la tabla de que se redibuje. */
    public void anadir(Actividad actividad) {
        actividades.add(actividad);
        fireTableRowsInserted(actividades.size() - 1, actividades.size() - 1);
    }
 
    /** Quita la actividad de esa fila y la devuelve. */
    public Actividad eliminar(int fila) {
        Actividad quitada = actividades.remove(fila);
        fireTableRowsDeleted(fila, fila);
        return quitada;
    }
 
    public Actividad get(int fila) { return actividades.get(fila); }
 
    /** Copia de la lista (para calcular estadísticas sin tocar la original). */
    public List<Actividad> getTodas() { return new ArrayList<>(actividades); }
}
 