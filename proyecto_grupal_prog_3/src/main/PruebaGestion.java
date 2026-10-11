package main;

import javax.swing.JFrame;

import domain.GestionFinanciera;
import gui.PanelFinanciero;

public class PruebaGestion {

	public static void main(String[] args) {
		//main de prueba para GestionFinanciera
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
		
	}

}
