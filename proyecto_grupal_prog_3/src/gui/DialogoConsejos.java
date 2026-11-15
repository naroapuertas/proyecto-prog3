// MIREN BENITEZ RUIZ

package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DialogoConsejos extends JDialog {

    private static final long serialVersionUID = 1L;

    public DialogoConsejos(JFrame padre, int saludMental) {
        super(padre, "Consejos de bienestar", true); // true = bloquea la ventana de detrás
        setLayout(new BorderLayout(10, 10));

        JTextArea texto = new JTextArea(construirTexto(saludMental));
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        texto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(texto), BorderLayout.CENTER);

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());
        JPanel abajo = new JPanel();
        abajo.add(cerrar);
        add(abajo, BorderLayout.SOUTH);

        setSize(420, 320);
        setLocationRelativeTo(padre);
    }

    private static String construirTexto(int valor) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tu estabilidad emocional está en el ").append(valor).append("%.\n\n");
        if (valor < 30) {
            sb.append("NIVEL CRÍTICO\n")
              .append("- Haz una pausa y duerme lo que necesites.\n")
              .append("- Sal a dar un paseo de 20 minutos.\n")
              .append("- Habla con alguien de confianza.\n")
              .append("- Si te sientes así durante muchos días, busca apoyo en el servicio de la universidad.");
        } else if (valor < 70) {
            sb.append("NIVEL ESTABLE\n")
              .append("- Mantén un buen equilibrio entre estudio y ocio.\n")
              .append("- Intenta hacer algo de deporte esta semana.\n")
              .append("- Reserva un rato libre cada día sin pantallas.");
        } else {
            sb.append("NIVEL EXCELENTE\n")
              .append("- ¡Sigue así!\n")
              .append("- Aprovecha para adelantar entregas sin agobio.\n")
              .append("- Cuida tus rutinas de sueño y ocio para mantenerte aquí.");
        }
        return sb.toString();
    }
}