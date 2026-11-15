// MIREN BENITEZ RUIZ

package gui;

import javax.swing.SwingUtilities;

public class HiloDesgasteEmocional extends Thread {

    private static final long INTERVALO_MS = 10_000; // cada 10 segundos
    private static final int PUNTOS_DESGASTE = 1;

    private final VentanaPrincipal ventana;
    private volatile boolean activo = true;

    public HiloDesgasteEmocional(VentanaPrincipal ventana) {
        super("Hilo-Desgaste-Emocional");
        this.ventana = ventana;
        setDaemon(true); // si se cierra el programa, este hilo muere con él
    }

    @Override
    public void run() {
        while (activo) {
            try {
                Thread.sleep(INTERVALO_MS);
            } catch (InterruptedException e) {
                return; // parar
            }
            if (activo) {
                SwingUtilities.invokeLater(() -> ventana.restarSaludMental(PUNTOS_DESGASTE));
            }
        }
    }

    public void detener() {
        activo = false;
        interrupt();
    }
}