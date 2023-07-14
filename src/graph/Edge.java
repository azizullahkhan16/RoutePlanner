package graph;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.module.FindException;

public class Edge implements Serializable {
    // default attributes
    Point start;
    Point end;
    Vertex sourceVertex;
    Vertex destinationVertex;
    int weight;
    boolean isShortestEdge;

    private Font font;

    /**
     *
     * @param start arrow point of the edge
     * @param end tail point of the edge
     * @param weight weight of edge
     * @param sourceVertex edge outgoing from vertex
     * @param destinationVertex edge incoming to vertex
     */
    public Edge(Point start, Point end, int weight, Vertex sourceVertex, Vertex destinationVertex) {
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.sourceVertex = sourceVertex;
        this.destinationVertex = destinationVertex;
        isShortestEdge = false;

        try {
            // Load the font file
            File fontFile = new File("src/fonts/Roboto-Medium.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            // Register the font with the graphics environment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            // Use the font in your application
            String fontName = customFont.getFontName();
            font = new Font(fontName, Font.PLAIN, 18);
            // ...
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    // sets isShort Edge according to the parameter value passed
    public void setShortestEdge(boolean shortestEdge) {
        isShortestEdge = shortestEdge;
    }

    // Draws the edge ( represented by an arrow )
    public void paint(Graphics graphics){

        Graphics2D g2d = (Graphics2D) graphics;

        // Enable antialiasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(isShortestEdge){
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.decode("#00bf63"));
        }else {
            g2d.setColor(Color.WHITE);
        }

        int midX = (start.x + end.x) / 2;
        int midY = (start.y + end.y) / 2;

        // Calculate the angle between the start and end points
        double angle = Math.atan2(end.y - start.y, end.x - start.x);

        // Determine the length of the arrowhead lines
        int arrowheadLength = 10;

        // Calculate the coordinates of the arrowhead points
        int arrowHeadX1 = (int) (midX - arrowheadLength * Math.cos(angle + Math.PI / 4));
        int arrowHeadY1 = (int) (midY - arrowheadLength * Math.sin(angle + Math.PI / 4));
        int arrowHeadX2 = (int) (midX - arrowheadLength * Math.cos(angle - Math.PI / 4));
        int arrowHeadY2 = (int) (midY - arrowheadLength * Math.sin(angle - Math.PI / 4));

        // Calculate the weight position based on the angle of the arrow
        int weightOffset = 20; // Distance between the arrowhead and weight

        int weightX, weightY;

        if (Math.abs(start.x - end.x) < Math.abs(start.y - end.y)) {
            // Vertical arrow
            weightX = midX + weightOffset;
            weightY = midY;
        } else {
            // Horizontal or diagonal arrow
            weightX = midX;
            weightY = midY - weightOffset;
        }

        // Draw the line
        g2d.drawLine(start.x, start.y, end.x, end.y);

        // Create the arrowhead polygon
        Polygon arrowhead = new Polygon();
        arrowhead.addPoint(midX+2, midY);
        arrowhead.addPoint(arrowHeadX1+2, arrowHeadY1);
        arrowhead.addPoint(arrowHeadX2+2, arrowHeadY2);

        // Draw the arrowhead
        g2d.fillPolygon(arrowhead);

        // Draw the weight
        g2d.setFont(font);
        g2d.drawString(String.valueOf(weight), weightX, weightY);
    }

}
