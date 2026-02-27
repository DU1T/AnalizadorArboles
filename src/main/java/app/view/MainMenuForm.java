package app.view;

import javax.swing.*;
import java.awt.*;

public class MainMenuForm
{
    public JPanel PanelPrincipal;
    private JLabel lblTitulo;
    private JButton btnIniciar;
    private JButton btnExit;

    public MainMenuForm()
    {
        PanelPrincipal.setPreferredSize(new Dimension(400, 300));
        btnIniciar.addActionListener(e -> abrirAnalizador());
        btnExit.addActionListener(e -> System.exit(0));
    }
    private void abrirAnalizador()
    {
        AnalisisArbolForm analizador = new AnalisisArbolForm();

        mostrarVentana(analizador.PanelPrincipal,"Analizador de Arboles");
    }
    private void mostrarVentana(JPanel panel, String titulo) {

        JFrame frame = new JFrame(titulo);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Analizador de Arboles");
            frame.setContentPane(new MainMenuForm().PanelPrincipal);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });
    }


}
