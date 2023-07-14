package graph;

import button.ButtonToggleVertex;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.Serializable;

public class Vertex implements Serializable {
    // Attributes
    ButtonToggleVertex label;

    /**
     *
     * @param label Vertex Button
     */
    public Vertex(ButtonToggleVertex label) {
        this.label = label;
    }

    // Returns point of button
    public Point getPoint(){
        return label.getPoint();
    }

    // Returns text of button
    public String getName(){
        return label.getText();
    }

    // Paints/Draws the button
    public void paint(Graphics graphics){
        label.paint(graphics);
    }
}
