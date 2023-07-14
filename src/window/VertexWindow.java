package window;

import toolbar.DrawingCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Singleton pattern for vertex window
public class VertexWindow {
    // Attributes
    private static JFrame frame;
    private static JButton selectedColorButton; // Variable to store the selected color button
    private String vertexName;
    private Color vertexColor;
    private int x, y, width, height;
    private static VertexWindow vertexWindow;

    /**
     * private constructor for window frame
     * @param x x position of vertex window on board
     * @param y y position of vertex window on board
     * @param width width of vertex window
     * @param height height of vertex window
     */
    private VertexWindow(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Initialises first vertex window or displays already made window
     * @param x x position of vertex window on board
     * @param y y position of vertex window on board
     * @param width width of vertex window
     * @param height height of vertex window
     * @return window
     */
    public static VertexWindow getVertexWindow(int x, int y, int width, int height){
        if(vertexWindow == null){
            vertexWindow = new VertexWindow(x, y, width, height);
            return vertexWindow;
        }

        return null;
    }



    // Displays window
    public void showAddVerticesDialog() {
        // Create the dialog window

        selectedColorButton = new JButton();
        selectedColorButton.setBackground(null);
        JDialog dialog = new JDialog(frame, "Add Vertex", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocation(x, y);
        dialog.setSize(width, height);
        dialog.setLayout(new GridLayout(3, 2));

        // Create labels and text fields for vertex name and color
        JLabel nameLabel = new JLabel("Vertex Name: ");
        JTextField nameField = new JTextField();

        // Create a panel for the color palette
        JPanel colorPalettePanel = new JPanel(new GridLayout(2, 5));
        String[] colors = {"#E44444", "#78EF45", "#2C6166", "#FF0060", "#F8D210", "#39B5E0", "#FF4C29", "#892CDC", "#cb6ce6", "#930077"};

        for (String colorCode : colors) {
            JButton colorButton = new JButton();
            Color color = Color.decode(colorCode);
            colorButton.setBackground(color);
            colorButton.setPreferredSize(new Dimension(30, 30));
            colorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Deselect the previously selected button
                    if (selectedColorButton != null) {
                        selectedColorButton.setBorder(null);
                    }
                    // Select the current button
                    selectedColorButton = (JButton) e.getSource();
                    selectedColorButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
            });
            colorPalettePanel.add(colorButton);
        }

        JLabel colorLabel = new JLabel("Vertex Color:");

        // Create the "OK" button with custom size
        JButton okButton = new JButton("Add");
        okButton.setPreferredSize(new Dimension(60, 30));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vertexName = nameField.getText().trim();
                vertexColor = selectedColorButton != null ? selectedColorButton.getBackground() : null;

                // Validate input fields
                if (vertexName.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a vertex name.");
                    return;
                }

                if (vertexColor == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select a vertex color.");
                    return;
                }

                if(!DrawingCanvas.UniqueVertexName(vertexName)) {
                    JOptionPane.showMessageDialog(dialog, "The vertex name you entered already exists. Please enter a unique vertex name.");
                    return;
                }

                if(vertexColor != null && !vertexName.isEmpty()){
                    DrawingCanvas.AddVertex(vertexName, vertexColor);
                    vertexColor = null;
                    vertexName = null;

                }

                // TODO: Process the vertex name and color

                dialog.dispose();
            }
        });

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        nameLabel.setFont(font);
        colorLabel.setFont(font);
        okButton.setFont(font);

        font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        nameField.setFont(font);

        // Add the components to the dialog
        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(colorLabel);
        dialog.add(colorPalettePanel);
        dialog.add(new JLabel()); // Placeholder for empty cell
        dialog.add(okButton);

        dialog.setVisible(true);
    }


    // getter for vertex name
    public String getVertexName() {
        return vertexName;
    }

    // getter for vertex color
    public Color getVertexColor() {
        return vertexColor;
    }
}
