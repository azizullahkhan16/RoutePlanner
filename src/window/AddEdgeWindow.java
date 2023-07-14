package window;

import button.ButtonToggleVertex;
import toolbar.DrawingCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
// Singleton pattern for add edge window
public class AddEdgeWindow {
    // Attributes
    private static JFrame frame;
    private int x, y, width, height;
    private static AddEdgeWindow edgeWindow;
    private String start;
    private String end;
    private int weight;

    /**
     * Private constructor for add edge window
     * @param x x position of add edge window on board
     * @param y y position of add edge window on board
     * @param width width of add edge window
     * @param height height of add edge window
     */
    private AddEdgeWindow(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Initialises first add edge window or displays already made window
     * @param x x position of add edge window on board
     * @param y y position of add edge window on board
     * @param width width of add edge window
     * @param height height of add edge window
     * @return window
     */
    public static AddEdgeWindow getEdgeWindow(int x, int y, int width, int height){
        if(edgeWindow == null){
            edgeWindow = new AddEdgeWindow(x, y, width, height);
            return edgeWindow;
        }

        return null;
    }

    // Displays window
    public void showAddEdgeDialog() {
        // Create the dialog window
        JDialog dialog = new JDialog(frame, "Add Edge", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setBounds(x, y, width, height);
        dialog.setLayout(new GridLayout(4, 2));

        // Create labels and text fields for start location, end location, and weight
        JLabel startLabel = new JLabel("Start Vertex:");
        JTextField startField = new JTextField();

        JLabel endLabel = new JLabel("Target Vertex: ");
        JTextField endField = new JTextField();

        JLabel weightLabel = new JLabel("Weight:");
        JTextField weightField = new JTextField();

        // Create the "OK" button
        JButton okButton = new JButton("Add");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start = startField.getText().trim();
                end = endField.getText().trim();
                String weightStr = weightField.getText().trim().replaceAll("\\s+", "");

                // Validate input fields
                if (start.isEmpty() || end.isEmpty() || weightStr.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.");
                    return;
                }

                boolean correctWeight = false;

                try {
                    weight = Integer.parseInt(weightStr);
                    correctWeight = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "The weight value must be a valid integer.");
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

                if(source != null && destination != null && correctWeight){
                    if(DrawingCanvas.IsEdgeExist(source, destination)){
                        JOptionPane.showMessageDialog(dialog, "The edge already exists.");
                        return;
                    }else DrawingCanvas.AddEdge(source.getPoint(), destination.getPoint(), weight);
                }


                // TODO: Add code to process the start, end, and weight values

                dialog.dispose();
            }
        });

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        startLabel.setFont(font);
        endLabel.setFont(font);
        weightLabel.setFont(font);
        okButton.setFont(font);

        font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        startField.setFont(font);
        endField.setFont(font);
        weightField.setFont(font);

        // Add the components to the dialog
        dialog.add(startLabel);
        dialog.add(startField);
        dialog.add(endLabel);
        dialog.add(endField);
        dialog.add(weightLabel);
        dialog.add(weightField);
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

    // Getter for weight of edge
    public int getWeight() {
        return weight;
    }
}
