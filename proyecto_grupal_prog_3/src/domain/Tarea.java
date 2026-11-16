//SOFIA MONGE GONZALEZ
package domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Tarea {
	
		private String asignatura;
		private String nombre;
		private LocalDate fechaLimite;
		private int nivelEstres; //de 1 a 5
		private boolean completada;
	 
		//niveles de urgencia: los usara el renderer de la JTable para pintar de rojo, amarillo o verde
		public enum Urgencia {
			URGENTE, MEDIA, LEJANA
		}
	 
		
		public Tarea(String asignatura, String nombre, LocalDate fechaLimite, int nivelEstres) {
			super();
			setAsignatura(asignatura);
			setNombre(nombre);
			setFechaLimite(fechaLimite);
			setNivelEstres(nivelEstres);
			this.completada = false;
		}
	 
		
		public Tarea() {
			this.asignatura = "Sin asignatura";
			this.nombre = "Sin nombre";
			this.fechaLimite = LocalDate.now();
			this.nivelEstres = 1;
			this.completada = false;
		}
	 
		//getters y setters
		public String getAsignatura() {
			return asignatura;
		}
	 
		//si escribe una asignatura vacia, ponemos "Sin asignatura"
		public void setAsignatura(String asignatura) {
			if (asignatura == null || asignatura.trim().isEmpty()) {
				this.asignatura = "Sin asignatura";
			} else {
				this.asignatura = asignatura.trim();
			}
		}
	 
		public String getNombre() {
			return nombre;
		}
	 
		//si escribe un nombre vacio, ponemos "Sin nombre"
		public void setNombre(String nombre) {
			if (nombre == null || nombre.trim().isEmpty()) {
				this.nombre = "Sin nombre";
			} else {
				this.nombre = nombre.trim();
			}
		}
	 
		public LocalDate getFechaLimite() {
			return fechaLimite;
		}
	 
		//no fecha null; las fechas pasadas SI se aceptan 
		public void setFechaLimite(LocalDate fechaLimite) {
			if (fechaLimite == null) {
				this.fechaLimite = LocalDate.now();
			} else {
				this.fechaLimite = fechaLimite;
			}
		}
	 
		public int getNivelEstres() {
			return nivelEstres;
		}
	 
		//el nivel de estres tiene que estar entre 1 y 5; sino el + cercano
		public void setNivelEstres(int nivelEstres) {
			if (nivelEstres < 1) {
				this.nivelEstres = 1;
			} else if (nivelEstres > 5) {
				this.nivelEstres = 5;
			} else {
				this.nivelEstres = nivelEstres;
			}
		}
	 
		public boolean isCompletada() {
			return completada;
		}
	 
		public void setCompletada(boolean completada) {
			this.completada = completada;
		}
	 
		//dias hasta la fecha limite
		public long getDiasRestantes() {
			return ChronoUnit.DAYS.between(LocalDate.now(), fechaLimite);
		}
	 
		//tarea vencida si no esta completada y ya paso su fecha limite
		public boolean isVencida() {
			return !completada && getDiasRestantes() < 0;
		}
	 
		//urgente: vence en los proximos 2 dias (o ya vencio); media: de 3 a 7 dias; lejana: mas de 7 dias
		public Urgencia getUrgencia() {
			long dias = getDiasRestantes();
			if (dias <= 2) {
				return Urgencia.URGENTE;
			} else if (dias <= 7) {
				return Urgencia.MEDIA;
			} else {
				return Urgencia.LEJANA;
			}
		}
	 
		//toString
		@Override
		public String toString() {
			return "Tarea [asignatura=" + asignatura + ", nombre=" + nombre + ", fechaLimite=" + fechaLimite
					+ ", nivelEstres=" + nivelEstres + ", completada=" + completada + "]";
		}
	 
	}
	 

	
	
