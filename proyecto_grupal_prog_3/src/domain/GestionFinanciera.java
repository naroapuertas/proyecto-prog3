//NAROA PUERTAS ZORRILLA
package domain;

import java.time.LocalDate;
import java.util.ArrayList;

import domain.Gasto.TipoGasto;

public class GestionFinanciera {
	
	//en esta clase guardaremos y gestionaremos los gastos
	private ArrayList<Gasto> listaGastos;
	private double presupuestoMensual;

	//constructor (vacio)
	public GestionFinanciera() {
		this.listaGastos = new ArrayList<Gasto>();
		this.presupuestoMensual = 300;
		
		//añadimos gastos de prueba
		listaGastos.add(new Gasto("Bocata",TipoGasto.COMIDA, 2.60, LocalDate.now(), false ));
		listaGastos.add(new Gasto("Gym",TipoGasto.SALUD, 39.90, LocalDate.now(), true ));
		listaGastos.add(new Gasto("Fotocopias",TipoGasto.ACADEMICO, 4.50, LocalDate.now(), false ));
		listaGastos.add(new Gasto("Cine",TipoGasto.OCIO, 6.90, LocalDate.now(), false ));
	}
	
	//generamos getters/setters de los atributos
	public double getPresupuestoMensual() {
		return presupuestoMensual;
	}

	//no queremos que se añada un presupuesto <= 0, avisamos a usuario
	public void setPresupuestoMensual(double presupuestoMensual) {
		if(presupuestoMensual <= 0) {
			throw new IllegalArgumentException("El presupuesto debe ser mayor que 0");
		} else {
			this.presupuestoMensual = presupuestoMensual;
		}
	}

	public ArrayList<Gasto> getListaGastos() {
		return listaGastos;
	}
	
	//metodo que meta a nuestra lista de gastos cada gasto que se registre
	public void registrarGasto(Gasto gasto) {
		listaGastos.add(gasto);
	}
	
	//queremos saber cual es el total gastado hasta ahora
	public double getTotalGastado() {
		double total = 0;
		for(Gasto g: listaGastos) {
			total += g.getPrecio();
		}
		return total;
	}
	
	//metodo para saber cuanto $$ nos queda
	public double getRestante() {
		return presupuestoMensual - getTotalGastado();
	}
	
	//metodo para saber el porcentaje de lo que nos queda
	//para el aviso de cuanto nos queda y la JProgressBar
	public double getPorcentajeRestante() {
		if (presupuestoMensual <= 0) {
			return 0;
		} else {
			//usamos math.max para que nunca de un resultado negativo
			return Math.max(0,(getRestante()*100)/presupuestoMensual);
		}
	}
}

