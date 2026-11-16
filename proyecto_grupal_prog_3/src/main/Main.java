package main;

import javax.swing.JFrame;

import domain.GestionAcademica;
import gui.PanelAcademico;

public class Main {

	public static void main(String[] args) {
		
		//MAIN DE SOFIA
			JFrame ventana = new JFrame("Prueba panel académico");
			ventana.add(new PanelAcademico(new GestionAcademica()));
			ventana.setSize(850, 400);
			ventana.setLocationRelativeTo(null);
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ventana.setVisible(true);
		

	}

}
