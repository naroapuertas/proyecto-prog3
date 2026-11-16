package main;

import javax.swing.SwingUtilities;

import gui.VentanaPrincipal;

public class MainBienestar {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}