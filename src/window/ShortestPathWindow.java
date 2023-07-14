package window;

import button.ButtonToggleVertex;
import toolbar.DrawingCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Singleton pattern for shortest path window
public class ShortestPathWindow {
    // Attributes
    private static JFrame frame;
    private int x, y, width, height;
    private static ShortestPathWindow shortestPathWindow;
    private String start;
    private String end;


    /**
     * Private constructor for shortest path window
     * @param x x position of shortest path window on board
     * @param y y position of shortest path window on board
     * @param width width of shortest path window
     * @param height height of shortest path window
     */
    private ShortestPathWindow(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Initialises first shortest path window or displays already made window
     * @param x x position of shortest path window on board
     * @param y y position of shortest path window on board
     * @param width width of shortest path window
     * @param height height of shortest path window
     * @return window
     */
    public static ShortestPathWindow getShortestPathWindow(int x, int y, int width, int height){
        if(shortestPathWindow == null){
            shortestPathWindow = new ShortestPathWindow(x, y, width, height);
            return shortestPathWindow;
        }

        return null;
    }

    // Displays window
    public void showShortestPathDialog(String type) {
        // Create the dialog window
        JDialog dialog = new JDialog(frame, "Path Finding", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setBounds(x, y, width, height);
        dialog.setLayout(new GridLayout(3, 2));

        // Create labels and text fields for start location, end location, and weight
        JLabel startLabel = new JLabel("Start Vertex:");
        JTextField startField = new JTextField();

        JLabel endLabel = new JLabel("Target Vertex:");
        JTextField endField = new JTextField();

        // Create the "OK" button
        JButton okButton = new JButton("Find");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start = startField.getText().trim();
                end = endField.getText().trim();

                // Validate input fields
                if (start.isEmpty() || end.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.");
                    return;
                }

                ButtonToggleVertex source= DrawingCanvas.SearchVertex(start);
                ButtonToggleVertex destination = DrawingCanvas.SearchVertex(end);

                if(source == null || destination == null){
                    JOptionPane.showMessageDialog(dialog, "The specified vertices are not found in the graph.");
                    return;
                }

                if(source.equals(destination)){
                    JOptionPane.showMessageDialog(dialog, "The specified vertices are not distinct.");
                    return;
                }

                if(source != null && destination != null){
                    switch (type) {
                        case "dijkstra":
                            if (!DrawingCanvas.FindShortestPathDijkstra(source, destination)) {
                                JOptionPane.showMessageDialog(dialog, "The shortest path using Dijkstra's Algorithm cannot be found.");
                                return;
                            }
                            break;

                        case "bellman":
                            if (!DrawingCanvas.FindShortestPathBellman(source, destination)) {
                                JOptionPane.showMessageDialog(dialog, "The shortest path using Bellman-Ford Algorithm cannot be found.");
                                return;
                            }
                            break;
                        case "aSearch":
                            if (!DrawingCanvas.FindShortestPathAStarSearch(source, destination)) {
                                JOptionPane.showMessageDialog(dialog, "The shortest path using A* Search cannot be found.");
                                return;
                            }
                            break;
                        case "greedy":
                            if (!DrawingCanvas.FindShortestPathGreedy(source, destination)) {
                                JOptionPane.showMessageDialog(dialog, "The shortest path using Greedy Best-first Search cannot be found.");
                                return;
                            }
                            break;
                        case "bidirectional":
                            if (!DrawingCanvas.FindShortestPathBidirectional(source, destination)) {
                                JOptionPane.showMessageDialog(dialog, "The shortest path using Bidirectional Breath-first Search cannot be found.");
                                return;
                            }
                            break;
                        case "BFS":
                            if (!DrawingCanvas.FindShortestPathBFS(source, destination)) {
                                JOptionPane.showMessageDialog(dialog, "The shortest path using Breath-first Search cannot be found.");
                                return;
                            }
                            break;
                        case "DFS":
                            if (!DrawingCanvas.FindShortestPathDFS(source, destination)) {
                                JOptionPane.showMessageDialog(dialog, "The shortest path using Depth-first Search cannot be found.");
                                return;
                            }
                            break;
                    }
                }


                // TODO: Add code to process the start, end, and weight values

                dialog.dispose();
            }
        });

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        startLabel.setFont(font);
        endLabel.setFont(font);
        okButton.setFont(font);

        font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        startField.setFont(font);
        endField.setFont(font);

        // Add the components to the dialog
        dialog.add(startLabel);
        dialog.add(startField);
        dialog.add(endLabel);
        dialog.add(endField);
        dialog.add(new JLabel()); // Placeholder for empty cell
        dialog.add(okButton);

        dialog.setVisible(true);
    }
}
