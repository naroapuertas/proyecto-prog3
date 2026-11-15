// MIREN BENITEZ RUIZ

package domain;
 
import java.time.LocalDateTime;
 
public class Actividad {
 
    private String nombre;
    private String categoria;       // Deporte, Ocio, Viajes, Descanso...
    private int impactoBienestar;   // Puntos que sube la barra de salud mental
    private LocalDateTime fecha;    // Cuándo se registró
 
    public Actividad(String nombre, String categoria, int impactoBienestar) {
        this(nombre, categoria, impactoBienestar, LocalDateTime.now());
    }
 
    public Actividad(String nombre, String categoria, int impactoBienestar, LocalDateTime fecha) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.impactoBienestar = impactoBienestar;
        this.fecha = fecha;
    }
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
 
    public int getImpactoBienestar() { return impactoBienestar; }
    public void setImpactoBienestar(int impactoBienestar) { this.impactoBienestar = impactoBienestar; }
 
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
 
    @Override
    public String toString() {
        return nombre + " (" + categoria + ") +" + impactoBienestar + " pts";
    }
}
 