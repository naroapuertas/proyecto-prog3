package main;

import domain.GestionFinanciera;

public class PruebaGestion {

	public static void main(String[] args) {
		//main de prueba para GestionFinanciera
		//creamos el objeto de la clase GestionFinanciera
		GestionFinanciera gestionF = new GestionFinanciera();
		
		//comprobamos que los metodos funcionen
		 System.out.println(gestionF.getTotalGastado());
		 System.out.println(gestionF.getRestante());
		 System.out.println(gestionF.getPorcentajeRestante());
		
	}

}
