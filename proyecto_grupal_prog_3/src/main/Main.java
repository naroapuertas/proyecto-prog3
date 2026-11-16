package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import domain.GestionAcademica;
import domain.GestionFinanciera;
import gui.PanelAcademico;
import gui.PanelFinanciero;
import gui.VentanaPrincipal;

public class Main {

	public static void main(String[] args) {
		
		//MAIN DE SOFIA
			JFrame ventana = new JFrame("Prueba panel académico");
			ventana.add(new PanelAcademico(new GestionAcademica()));
			ventana.setSize(850, 400);
			ventana.setLocationRelativeTo(null);
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ventana.setVisible(true);
			
			//MAIN NAROA
			//creamos el objeto de la clase GestionFinanciera
			GestionFinanciera gestionF = new GestionFinanciera();
			
			//comprobamos que los metodos funcionen
			 System.out.println(gestionF.getTotalGastado());
			 System.out.println(gestionF.getRestante());
			 System.out.println(gestionF.getPorcentajeRestante());
			 
			 //creamos una ventana JFrame temporal para probar nuestro panel financiero
			 JFrame ventanaPrueba = new JFrame();
			 
			 //cremos un panel (recibe la getsion financiera)
			 PanelFinanciero panel = new PanelFinanciero(gestionF);
			 
			 //la añadimos a la ventana y modificamos la ventana
			 ventanaPrueba.add(panel);
			 ventanaPrueba.setSize(400, 400);
			 ventanaPrueba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 ventanaPrueba.setVisible(true);
			 
			 //MAIN MIREN
			 SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
		

	}

}
