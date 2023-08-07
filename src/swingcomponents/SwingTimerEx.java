package swingcomponents;

import toolbar.ToolbarJPanel;

import java.awt.EventQueue;
import java.util.Objects;
import javax.swing.*;

public class SwingTimerEx extends JFrame {

    // Constructor
    public SwingTimerEx() {
        // Initialisations
        initUI();
    }

    // Initialisations of SwingTimerEx
    private void initUI() {
        // Add board to jframe
        ImageIcon icon = new ImageIcon("src/aktic_v_png_t.png");
        add(new Board());

        setIconImage(icon.getImage());
        setResizable(false);
        pack();

        setTitle("Graph Visualizer");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        // Keep invoking events at a timer
        EventQueue.invokeLater(() -> {
            SwingTimerEx ex = new SwingTimerEx();
            ex.setVisible(true);
        });
    }
}