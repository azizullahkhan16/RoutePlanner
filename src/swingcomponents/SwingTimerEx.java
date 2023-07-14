package swingcomponents;

import toolbar.ToolbarJPanel;

import java.awt.EventQueue;
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
        add(new Board());

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