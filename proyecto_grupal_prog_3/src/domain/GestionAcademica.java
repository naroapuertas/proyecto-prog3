//SOFIA MONGE GONZALEZ
package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GestionAcademica {
	
	
	private ArrayList<Tarea> listaTareas;
 
	//puntos de rendimiento 
	private static final double PENALIZACION_VENCIDA = 15;

	public GestionAcademica() {
		this.listaTareas = new ArrayList<Tarea>();
		
		//añadimos tareas de prueba (las fechas son relativas a hoy para que se vean los tres colores)
				LocalDate hoy = LocalDate.now();
				listaTareas.add(new Tarea("Programación de aplicaciones", "Prototipo 1 GUI", hoy.plusDays(1), 4));
				listaTareas.add(new Tarea("Bases de Datos", "Actividad diagrama E-R", hoy.plusDays(2), 3));
				listaTareas.add(new Tarea("Estadística", "Actividad CA1", hoy.plusDays(5), 2));
				listaTareas.add(new Tarea("Telemática", "Examen Wireshark", hoy.plusDays(6), 5));
				listaTareas.add(new Tarea("Visualización de Datos", "Dashboard Power BI", hoy.plusDays(15), 3));
				listaTareas.add(new Tarea("Ciencia de Datos", "Informe de análisis", hoy.plusDays(25), 2));
		 
				//una ya completada y otra vencida para poder probar el calculo del rendimiento
				Tarea hecha = new Tarea("Bases de Datos", "Examen tipo test", hoy.minusDays(3), 3);
				hecha.setCompletada(true);
				listaTareas.add(hecha);
				listaTareas.add(new Tarea("Estadística", "Cuestionario 1", hoy.minusDays(1), 1));
		
		
	}
	
	
		public ArrayList<Tarea> getListaTareas() {
			return listaTareas;
		}
	 
		
		public void anadirTarea(Tarea tarea) {
			if (tarea != null) {
				listaTareas.add(tarea);
			}
		}
	 
		
		public void completarTarea(Tarea tarea) {
			if (tarea != null) {
				tarea.setCompletada(true);
			}
		}

		
		//devuelve una copia de la lista ordenada por fecha limite (las mas proximas primero)
		public ArrayList<Tarea> getTareasOrdenadas() {
			ArrayList<Tarea> ordenadas = new ArrayList<Tarea>(listaTareas);
			Collections.sort(ordenadas, new Comparator<Tarea>() {
				public int compare(Tarea t1, Tarea t2) {
					return t1.getFechaLimite().compareTo(t2.getFechaLimite());
				}
			});
			return ordenadas;
		}
	 
		//filtro para la barra de busqueda (KeyListener): devuelve las tareas ordenadas cuya asignatura
		//o nombre contienen el texto, sin importar mayusculas/minusculas
		public ArrayList<Tarea> filtrar(String texto) {
			ArrayList<Tarea> resultado = new ArrayList<Tarea>();
			if (texto == null || texto.trim().isEmpty()) {
				return getTareasOrdenadas();
			}
			String buscado = texto.trim().toLowerCase();
			for (Tarea t : getTareasOrdenadas()) {
				if (t.getAsignatura().toLowerCase().contains(buscado) || t.getNombre().toLowerCase().contains(buscado)) {
					resultado.add(t);
				}
			}
			return resultado;
		}
		
		
		public int getTotalTareas() {
			return listaTareas.size();
		}
	 
		
		public int getTotalCompletadas() {
			int total = 0;
			for (Tarea t : listaTareas) {
				if (t.isCompletada()) {
					total++;
				}
			}
			return total;
		}
	 
		
		public int getTotalPendientes() {
			return getTotalTareas() - getTotalCompletadas();
		}
	 
		
		public int getTotalVencidas() {
			int total = 0;
			for (Tarea t : listaTareas) {
				if (t.isVencida()) {
					total++;
				}
			}
			return total;
		}
	 
		//porcentaje de rendimiento academico para la JProgressBar (de 0 a 100):
		//sube con el % de tareas completadas y baja por cada tarea vencida sin hacer
		public double getPorcentajeRendimiento() {
			if (listaTareas.isEmpty()) {
				return 0;
			}
			double porcentajeCompletadas = (getTotalCompletadas() * 100.0) / getTotalTareas();
			double rendimiento = porcentajeCompletadas - (getTotalVencidas() * PENALIZACION_VENCIDA);
			//usamos Math.max y Math.min para que siempre este entre 0 y 100
			return Math.min(100, Math.max(0, rendimiento));
		}
		
		
		
		
		
		
		
}
