package domain;

import java.time.LocalDate;
import java.util.ArrayList;

import domain.Gasto.TipoGasto;

public class GestionFinanciera {
	
	//en esta clase guardaremos y gestionaremos los gastos
	private ArrayList<Gasto> listaGastos;
	private double presupuestoMensual;
	
	
	public GestionFinanciera(ArrayList<Gasto> listaGastos, double presupuestoMensual) {
		super();
		this.listaGastos = listaGastos;
		this.presupuestoMensual = presupuestoMensual;
	}
	
	public GestionFinanciera() {
		this.listaGastos = new ArrayList<Gasto>();
		this.presupuestoMensual = 300;
		
		//añadimos gastos de prueba
		listaGastos.add(new Gasto("Bocata",TipoGasto.COMIDA, 2.60, LocalDate.now(), false ));
		listaGastos.add(new Gasto("Gym",TipoGasto.SALUD, 39.90, LocalDate.now(), true ));
		listaGastos.add(new Gasto("Fotocopias",TipoGasto.ACADEMICO, 4.50, LocalDate.now(), false ));
		listaGastos.add(new Gasto("Cine",TipoGasto.OCIO, 6.90, LocalDate.now(), false ));
	}
	

}
