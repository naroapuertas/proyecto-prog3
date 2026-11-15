// MIREN BENITEZ RUIZ

package gui;

import domain.Actividad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;


public class PanelBienestar extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int MAX_LONGITUD_NOMBRE = 40;

    private final VentanaPrincipal ventana;
    private final ModeloTablaActividades modelo = new ModeloTablaActividades();

    private final JTextField txtNombre = new JTextField();
    private final JComboBox<String> comboCategoria = new JComboBox<>(
            new String[] {"Deporte", "Ocio", "Viajes", "Descanso", "Comida reconfortante"});
    private final JSpinner spinnerImpacto = new JSpinner(new SpinnerNumberModel(10, 5, 50, 5));
    private final JTable tabla = new JTable(modelo);

    private final JButton btnRegistrar = new JButton("Registrar actividad");
    private final JButton btnEliminar = new JButton("Eliminar seleccionada");
    private final JButton btnResumen = new JButton("Generar resumen");
    private final JButton btnConsejos = new JButton("Ver consejos");

    public PanelBienestar(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);
        add(crearBotones(), BorderLayout.SOUTH);
        conectarEventos();
    }

    private JPanel crearFormulario() {
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Nueva actividad de bienestar"));
        form.add(new JLabel("Nombre (Enter para registrar):"));
        form.add(txtNombre);
        form.add(new JLabel("Categoría:"));
        form.add(comboCategoria);
        form.add(new JLabel("Impacto positivo (puntos):"));
        form.add(spinnerImpacto);
        return form;
    }

    private JScrollPane crearTabla() {
        tabla.setRowHeight(24);
        tabla.setAutoCreateRowSorter(true); // clic en la cabecera = ordenar

        DefaultTableCellRenderer renderPuntos = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable t, Object valor,
                    boolean seleccionada, boolean foco, int fila, int columna) {
                JLabel etiqueta = (JLabel) super.getTableCellRendererComponent(
                        t, "+" + valor, seleccionada, foco, fila, columna);
                etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
                etiqueta.setFont(etiqueta.getFont().deriveFont(Font.BOLD));
                if (!seleccionada) {
                    etiqueta.setForeground(new Color(0, 130, 0));
                }
                return etiqueta;
            }
        };
        tabla.getColumnModel().getColumn(2).setCellRenderer(renderPuntos);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial de actividades"));
        return scroll;
    }

    private JPanel crearBotones() {
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        botones.add(btnRegistrar);
        botones.add(btnEliminar);
        botones.add(btnResumen);
        botones.add(btnConsejos);
        return botones;
    }

    private void conectarEventos() {
        btnRegistrar.addActionListener(e -> registrarActividad());
        btnEliminar.addActionListener(e -> eliminarActividad());
        btnResumen.addActionListener(e -> generarResumen());
        btnConsejos.addActionListener(e -> new DialogoConsejos(
                (JFrame) SwingUtilities.getWindowAncestor(this), ventana.getSaludMental()).setVisible(true));

        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    registrarActividad();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    txtNombre.setText("");
                }
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tabla.getSelectedRow() != -1) {
                    int filaModelo = tabla.convertRowIndexToModel(tabla.getSelectedRow());
                    Actividad a = modelo.get(filaModelo);
                    JOptionPane.showMessageDialog(PanelBienestar.this,
                            "Actividad: " + a.getNombre() + "\nCategoría: " + a.getCategoria()
                            + "\nPuntos: +" + a.getImpactoBienestar(),
                            "Detalle", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void registrarActividad() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un nombre para la actividad.",
                    "Falta el nombre", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocusInWindow();
            return;
        }
        if (nombre.length() > MAX_LONGITUD_NOMBRE) {
            JOptionPane.showMessageDialog(this,
                    "El nombre es demasiado largo (máximo " + MAX_LONGITUD_NOMBRE + " caracteres).",
                    "Nombre demasiado largo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String categoria = (String) comboCategoria.getSelectedItem();
        int puntos = (Integer) spinnerImpacto.getValue();

        modelo.anadir(new Actividad(nombre, categoria, puntos));
        int nuevoValor = ventana.sumarSaludMental(puntos);
        txtNombre.setText("");
        txtNombre.requestFocusInWindow();

        JOptionPane.showMessageDialog(this,
                "¡Actividad registrada! Tu salud mental ahora está al " + nuevoValor + "%.",
                "Bienestar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarActividad() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona primero una actividad de la tabla.",
                    "Nada seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int filaModelo = tabla.convertRowIndexToModel(fila);
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Eliminar \"" + modelo.get(filaModelo).getNombre() + "\"? También se restarán sus puntos.",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            Actividad quitada = modelo.eliminar(filaModelo);
            ventana.restarSaludMental(quitada.getImpactoBienestar());
        }
    }

    private void generarResumen() {
        List<Actividad> copia = modelo.getTodas();
        if (copia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todavía no hay actividades para resumir.",
                    "Sin datos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        btnResumen.setEnabled(false);
        btnResumen.setText("Calculando...");

        Thread hilo = new Thread(() -> {
            String resultado;
            try {
                Thread.sleep(2500); // tarea costosa
                resultado = calcularResumen(copia);
            } catch (InterruptedException e) {
                resultado = "El cálculo fue interrumpido.";
                Thread.currentThread().interrupt();
            }
            final String texto = resultado;
            SwingUtilities.invokeLater(() -> {
                btnResumen.setText("Generar resumen");
                btnResumen.setEnabled(true);
                JOptionPane.showMessageDialog(this, texto, "Resumen de bienestar",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        }, "Hilo-Resumen");
        hilo.setDaemon(true);
        hilo.start();
    }

    private static String calcularResumen(List<Actividad> actividades) {
        int total = 0;
        Map<String, Integer> porCategoria = new HashMap<>();
        for (Actividad a : actividades) {
            total += a.getImpactoBienestar();
            porCategoria.merge(a.getCategoria(), a.getImpactoBienestar(), Integer::sum);
        }
        String mejor = "";
        int max = -1;
        for (Map.Entry<String, Integer> entrada : porCategoria.entrySet()) {
            if (entrada.getValue() > max) {
                max = entrada.getValue();
                mejor = entrada.getKey();
            }
        }
        return "Actividades registradas: " + actividades.size()
                + "\nPuntos totales: " + total
                + "\nMedia por actividad: " + (total / actividades.size())
                + "\nCategoría que más te ayuda: " + mejor + " (" + max + " pts)";
    }
}