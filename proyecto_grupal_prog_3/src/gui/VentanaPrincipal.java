// MIREN BENITEZ RUIZ

package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.ToolTipManager;

import domain.GestionAcademica;
import domain.GestionFinanciera;

public class VentanaPrincipal extends JPanel {

    private static final long serialVersionUID = 1L;

    // nombres de las "cartas" (pantallas) entre las que se cambia
    private static final String MENU = "menu";
    private static final String BIENESTAR = "bienestar";
    private static final String ACADEMICO = "academico";
    private static final String FINANZAS = "finanzas";

    private final JProgressBar barSaludMental = crearBarra(50);
    private final JProgressBar barAcademico = crearBarra(70);
    private final JProgressBar barFinanciera = crearBarra(80);

    // datos compartidos: una sola gestión de cada tipo para toda la aplicación
    private final transient GestionAcademica gestionAcademica = new GestionAcademica();
    private final transient GestionFinanciera gestionFinanciera = new GestionFinanciera();

    // CardLayout: en el centro solo se ve una pantalla a la vez (menú, bienestar, académico o finanzas)
    private final CardLayout cartas = new CardLayout();
    private final JPanel panelCartas = new JPanel(cartas);

    private final transient HiloDesgasteEmocional hiloDesgaste;

    public VentanaPrincipal() {
        setLayout(new BorderLayout(10, 10));

        // NORTH: título
        JLabel titulo = new JLabel("Sims Universitario", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 28f));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        add(titulo, BorderLayout.NORTH);

        // CENTER: el menú con los tres botones y las pantallas de cada área (los paneles que ya estaban hechos)
        panelCartas.add(crearMenu(), MENU);
        panelCartas.add(crearPantalla(new PanelBienestar(this)), BIENESTAR);
        panelCartas.add(crearPantalla(new PanelAcademico(gestionAcademica)), ACADEMICO);
        panelCartas.add(crearPantalla(new PanelFinanciero(gestionFinanciera)), FINANZAS);
        add(panelCartas, BorderLayout.CENTER);

        // SOUTH: las tres barras de estado, centradas (se ven siempre, en cualquier pantalla)
        JPanel panelBarras = new JPanel(new GridLayout(3, 2, 10, 8));
        panelBarras.setBorder(BorderFactory.createTitledBorder(
                "Estado del estudiante (pasa el ratón por encima de una barra)"));
        panelBarras.setPreferredSize(new Dimension(600, 130));
        panelBarras.add(new JLabel("Estabilidad emocional / Salud mental:"));
        panelBarras.add(barSaludMental);
        panelBarras.add(new JLabel("Rendimiento académico:"));
        panelBarras.add(barAcademico);
        panelBarras.add(new JLabel("Salud financiera:"));
        panelBarras.add(barFinanciera);

        // con FlowLayout el panel de barras queda centrado y no ocupa todo el ancho
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0)); // margen abajo: sube las barras
        panelSur.add(panelBarras);
        add(panelSur, BorderLayout.SOUTH);

        ToolTipManager.sharedInstance().setInitialDelay(100); // que aparezca rápido
        añadirTooltipDinamico(barSaludMental, this::textoSaludMental);
        añadirTooltipDinamico(barAcademico, () -> "Rendimiento: " + barAcademico.getValue()
                + "% | Completa entregas para subirlo.");
        añadirTooltipDinamico(barFinanciera, () -> "Presupuesto: " + barFinanciera.getValue()
                + "% | Cuidado con los gastos impulsivos.");

        actualizarColor(barSaludMental);

        // las barras académica y financiera se calculan con los datos de cada gestión:
        // las ponemos al arrancar y las refrescamos cada medio segundo
        actualizarBarras();
        new Timer(500, e -> actualizarBarras()).start();

        hiloDesgaste = new HiloDesgasteEmocional(this);
        hiloDesgaste.start();
    }

    /** Para el hilo de desgaste emocional (se llama al cerrar la ventana). */
    public void detenerHilo() {
        hiloDesgaste.detener();
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

    /** Pone las barras académica y financiera con los datos actuales de sus gestiones. */
    private void actualizarBarras() {
        barAcademico.setValue((int) Math.round(gestionAcademica.getPorcentajeRendimiento()));
        actualizarColor(barAcademico);
        barFinanciera.setValue((int) Math.round(gestionFinanciera.getPorcentajeRestante()));
        actualizarColor(barFinanciera);
    }

    /** Menú con los tres botones grandes, uno por cada área (del color de su barra). */
    private JPanel crearMenu() {
        JButton btnBienestar = crearBotonArea("Bienestar", new Color(180, 235, 180));
        JButton btnAcademico = crearBotonArea("Académico", new Color(170, 205, 245));
        JButton btnFinanzas = crearBotonArea("Finanzas", new Color(255, 240, 150));

        btnBienestar.addActionListener(e -> cartas.show(panelCartas, BIENESTAR));
        btnAcademico.addActionListener(e -> cartas.show(panelCartas, ACADEMICO));
        btnFinanzas.addActionListener(e -> cartas.show(panelCartas, FINANZAS));

        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 25, 0));
        panelBotones.setPreferredSize(new Dimension(0, 160));
        panelBotones.add(btnBienestar);
        panelBotones.add(btnAcademico);
        panelBotones.add(btnFinanzas);

        // GridBagLayout centra los botones en el medio del espacio libre (así quedan más abajo)
        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.fill = GridBagConstraints.HORIZONTAL; // ocupan todo el ancho
        restricciones.weightx = 1;
        restricciones.insets = new Insets(0, 40, 0, 40);    // márgenes a izquierda y derecha

        JPanel menu = new JPanel(new GridBagLayout());
        menu.add(panelBotones, restricciones);
        return menu;
    }

    /** Pantalla de un área: su panel con un botón arriba para volver al menú. */
    private JPanel crearPantalla(JPanel panel) {
        JButton btnVolver = new JButton("Volver al menú");
        btnVolver.addActionListener(e -> cartas.show(panelCartas, MENU));

        JPanel barraSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barraSuperior.add(btnVolver);

        JPanel pantalla = new JPanel(new BorderLayout());
        pantalla.add(barraSuperior, BorderLayout.NORTH);
        pantalla.add(panel, BorderLayout.CENTER);
        return pantalla;
    }

    private static JButton crearBotonArea(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(boton.getFont().deriveFont(Font.BOLD, 22f));
        boton.setBackground(color);
        boton.setFocusPainted(false);
        return boton;
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
}
