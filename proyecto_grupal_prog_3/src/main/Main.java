package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gui.VentanaPrincipal;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			//la unica ventana (JFrame) de toda la aplicacion, el resto de pantallas son JPanel dentro de ella
			JFrame ventana = new JFrame("Sims Universitario - Dashboard Estudiantil");
			VentanaPrincipal principal = new VentanaPrincipal();
			ventana.add(principal);
			ventana.setSize(900, 680);
			ventana.setLocationRelativeTo(null);
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//al cerrar la ventana paramos el hilo de desgaste emocional
			ventana.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					principal.detenerHilo();
				}
			});
			ventana.setVisible(true);
		});
	}

}
