package window;

import button.ButtonToggleVertex;
import toolbar.DrawingCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Singleton pattern for remove edge window
public class RemoveEdgeWindow {
    // Attributes
    private static JFrame frame;
    private int x, y, width, height;
    private static RemoveEdgeWindow edgeWindow;
    private String start;
    private String end;

    /**
     * Private constructor for remove edge window
     * @param x x position of remove edge window on board
     * @param y y position of remove edge window on board
     * @param width width of remove edge window
     * @param height height of remove edge window
     */
    private RemoveEdgeWindow(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Initialises first remove edge window or displays already made window
     * @param x x position of remove edge window on board
     * @param y y position of remove edge window on board
     * @param width width of remove edge window
     * @param height height of remove edge window
     * @return window
     */
    public static RemoveEdgeWindow getEdgeWindow(int x, int y, int width, int height){
        if(edgeWindow == null){
            edgeWindow = new RemoveEdgeWindow(x, y, width, height);
            return edgeWindow;
        }

        return null;
    }

    // Displays window
    public void showRemoveEdgeDialog() {
        // Create the dialog window
        JDialog dialog = new JDialog(frame, "Remove Edge", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setBounds(x, y, width, height);
        dialog.setLayout(new GridLayout(3, 2));

        // Create labels and text fields for start location, end location, and weight
        JLabel startLabel = new JLabel("Start Vertex:");
        JTextField startField = new JTextField();

        JLabel endLabel = new JLabel("Target Vertex:");
        JTextField endField = new JTextField();

        // Create the "OK" button
        JButton okButton = new JButton("Remove");
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

                if(!DrawingCanvas.SearchEdge(source, destination)){
                    JOptionPane.showMessageDialog(dialog, "The specified edge does not exist.");
                    return;
                }
                if(source != null && destination != null){
                    DrawingCanvas.RemoveEdge(source.getPoint(), destination.getPoint());
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

    // Getter for string name of start vertex
    public String getStart() {
        return start;
    }

    // Getter for string name of end vertex
    public String getEnd() {
        return end;
    }
}
