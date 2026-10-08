
//NAROA PUERTAS ZORRILLA
package domain;

import java.time.LocalDate;

public class Gasto {
	
	private String concepto;
	
	public enum TipoGasto {
		
		COMIDA, OCIO, TRANSPORTE, SALUD, ACADEMICO, VIVIENDA, OTROS 
		
	}
	private TipoGasto tipo;
	private double precio;
	private LocalDate fecha;
	private boolean esFijo;
	
	//Constructor
	public Gasto(String concepto, TipoGasto tipo, double precio, LocalDate fecha, boolean esFijo) {
		super();
		setConcepto(concepto);
		setTipo(tipo);
		setPrecio(precio);
		setFecha(fecha);
		setEsFijo(esFijo);
	}
	
	//Constructor vacio
	public Gasto() {
	    this.concepto = "Sin concepto";
	    this.precio = 0.0;
	    this.tipo = TipoGasto.OTROS;
	    this.fecha = LocalDate.now();
	    this.esFijo = false;
	}

	//getters y setters
	public String getConcepto() {
		return concepto;
	}
	
	//si escribe un concepto vacio, ponemos"sin concepto"
	public void setConcepto(String concepto) {
		if(concepto == null || concepto.isEmpty()) {
			this.concepto = "Sin concepto";
		}else {
			this.concepto = concepto;
		}
	}
	public TipoGasto getTipo() {
		return tipo;
	}
	
	//no aceptamos tipo null, hay que elegir una opcion si no se considera "otros" (seguridad por si no elige una opcion)
	public void setTipo(TipoGasto tipo) {
		if(tipo == null) {
			this.tipo = TipoGasto.OTROS;
		} else {
			this.tipo = tipo;
		}
	}
	public double getPrecio() {
		return precio;
	}
	
	//no queremos precios negativos
	public void setPrecio(double precio) {
		if(precio < 0) {
			this.precio = 0.0;
		} else {
			this.precio = precio;
		}
	}
	public LocalDate getFecha() {
		return fecha;
	}
	
	//no queremos fechas futuras, ni nulas
	public void setFecha(LocalDate fecha) {
		if(fecha==null || fecha.isAfter(LocalDate.now())) {
			this.fecha = LocalDate.now();
		} else {
			this.fecha = fecha;
		}
	}
	public boolean isEsFijo() {
		return esFijo;
	}
	public void setEsFijo(boolean esFijo) {
		this.esFijo = esFijo;
	}
	
	//toString
	@Override
	public String toString() {
		return "Gasto [concepto=" + concepto + ", tipo=" + tipo + ", precio=" + precio + ", fecha=" + fecha
				+ ", esFijo=" + esFijo + "]";
	}
	
}
