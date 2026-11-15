// MIREN BENITEZ RUIZ

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JProgressBar barSaludMental = crearBarra(50);
    private final JProgressBar barAcademico = crearBarra(70);
    private final JProgressBar barFinanciera = crearBarra(80);

    private final transient HiloDesgasteEmocional hiloDesgaste;

    public VentanaPrincipal() {
        setTitle("Sims Universitario - Dashboard Estudiantil");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(820, 620);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelBarras = new JPanel(new GridLayout(3, 2, 10, 8));
        panelBarras.setBorder(BorderFactory.createTitledBorder(
                "Estado del estudiante (pasa el ratón por encima de una barra)"));
        panelBarras.add(new JLabel("Estabilidad emocional / Salud mental:"));
        panelBarras.add(barSaludMental);
        panelBarras.add(new JLabel("Rendimiento académico:"));
        panelBarras.add(barAcademico);
        panelBarras.add(new JLabel("Salud financiera:"));
        panelBarras.add(barFinanciera);
        add(panelBarras, BorderLayout.NORTH);

        ToolTipManager.sharedInstance().setInitialDelay(100); // que aparezca rápido
        añadirTooltipDinamico(barSaludMental, this::textoSaludMental);
        añadirTooltipDinamico(barAcademico, () -> "Rendimiento: " + barAcademico.getValue()
                + "% | Completa entregas para subirlo.");
        añadirTooltipDinamico(barFinanciera, () -> "Presupuesto: " + barFinanciera.getValue()
                + "% | Cuidado con los gastos impulsivos.");

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Bienestar", new PanelBienestar(this));
        pestanas.addTab("Académico", panelProvisional("Aquí irá el panel académico de Sofía"));
        pestanas.addTab("Finanzas", panelProvisional("Aquí irá el panel financiero de Naroa"));
        add(pestanas, BorderLayout.CENTER);

        actualizarColor(barSaludMental);
        actualizarColor(barAcademico);
        actualizarColor(barFinanciera);

        hiloDesgaste = new HiloDesgasteEmocional(this);
        hiloDesgaste.start();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                hiloDesgaste.detener();
            }
        });
    }

    public int getSaludMental() { return barSaludMental.getValue(); }

    /** Sube la salud mental y devuelve el valor nuevo (máximo 100). */
    public int sumarSaludMental(int puntos) {
        return cambiar(barSaludMental, puntos);
    }

    /** Baja la salud mental y devuelve el valor nuevo (mínimo 0). */
    public int restarSaludMental(int puntos) {
        return cambiar(barSaludMental, -puntos);
    }

    public int cambiarAcademico(int delta) { return cambiar(barAcademico, delta); }

    public int cambiarFinanciera(int delta) { return cambiar(barFinanciera, delta); }

    private int cambiar(JProgressBar barra, int delta) {
        int nuevo = Math.max(0, Math.min(100, barra.getValue() + delta));
        barra.setValue(nuevo);
        actualizarColor(barra);
        return nuevo;
    }

    private static JProgressBar crearBarra(int valorInicial) {
        JProgressBar barra = new JProgressBar(0, 100);
        barra.setValue(valorInicial);
        barra.setStringPainted(true);
        barra.setToolTipText("..."); // necesario para que Swing "registre" el tooltip
        return barra;
    }

    private static void actualizarColor(JProgressBar barra) {
        int v = barra.getValue();
        if (v < 30) {
            barra.setForeground(Color.RED);
        } else if (v < 70) {
            barra.setForeground(Color.ORANGE);
        } else {
            barra.setForeground(new Color(0, 170, 0));
        }
    }

    private String textoSaludMental() {
        int v = barSaludMental.getValue();
        String consejo;
        if (v < 30) {
            consejo = "Nivel crítico: haz una pausa, descansa o sal a pasear.";
        } else if (v < 70) {
            consejo = "Nivel estable: buen equilibrio entre estudio y ocio.";
        } else {
            consejo = "¡Excelente! Sigue cuidándote.";
        }
        return "Salud mental: " + v + "% | " + consejo;
    }

    private static void añadirTooltipDinamico(JProgressBar barra, Supplier<String> texto) {
        MouseAdapter oyente = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                barra.setToolTipText(texto.get());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                barra.setToolTipText(texto.get());
            }
        };
        barra.addMouseListener(oyente);        // MouseListener
        barra.addMouseMotionListener(oyente);  // MouseMotionListener
    }

    private static JPanel panelProvisional(String mensaje) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(mensaje));
        return panel;
    }
}