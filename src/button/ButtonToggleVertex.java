package button;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * Vertices are represented by buttons on the board
 * This class creates toggle buttons for vertices
 * extended from abstract MyButton
 */
public class ButtonToggleVertex extends MyButton {

    private boolean isPressedFirst = false;
    private String text;
    private Color buttonColor;
    private Color buttonStrokeColor;
    private Color textColor;
    private int diameter;
    private Font font;

    /**
     * Returns the vertex button
     * @param x x position of vertex on the board
     * @param y y position of vertex on the board
     * @param isPressed variable to check if pressed
     * @param text vertex represented by its text
     * @param buttonColor color of vertex button
     */
    public ButtonToggleVertex(int x, int y, boolean isPressed, String text, Color buttonColor) {
        super(x, y, isPressed);
        this.text = text;
        this.buttonColor = buttonColor;

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

    // returns position of the vertex button
    public Point getPoint(){
        return new Point(x, y);
    }

    // sets position of the vertex button
    public void setPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    // returns center position of the vertex button
    public Point getCenter(){
        int x = this.x + diameter/2;
        int y = this.y + diameter/2;
        return new Point(x, y);

    }

    // returns x position
    public int getX(){ return x;}


    //returns y position
    public int getY(){ return y;}


    // returns diameter of the vertex button
    public int getDiameter() {
        return diameter;
    }

    // returns text of vertex button
    public String getText() { return text;}


    // sets the isPressed to the parameter passed
    public void setPressed(boolean pressed){ this.isPressed = pressed;}


    // returns boolean value of vertex button isPressed
    // returns True if the button is pressed
    public boolean getPressed(){ return isPressed;}

    // draws/paints the vertex button
    @Override
    public void paint(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        FontMetrics fontMetrics = g2d.getFontMetrics(font);

        // stores text width and height according to text string
        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        diameter = Math.max(textWidth, textHeight) + 10;

        // different text color and stroke color used if the button is pressed
        if(isPressed) {
            buttonStrokeColor = Color.WHITE;
            textColor = Color.BLACK;
        }
        else {
            buttonStrokeColor = Color.BLACK;
            textColor = Color.WHITE;
        }

        g2d.setColor(buttonStrokeColor);
        g2d.fillOval(x-5, y-5, diameter+10, diameter+10);
        g2d.setColor(buttonColor);
        g2d.fillOval(x, y, diameter, diameter);


        g2d.setFont(font);

        // adjusting position according to text( text width and text height)
        int textX = x + (diameter - textWidth) / 2;
        int textY = y + (diameter - textHeight) / 2 + fontMetrics.getAscent();

        g2d.setColor(textColor);
        g2d.drawString(text, textX, textY);
    }

    // same as paint function above only include extra variable of isShort
    public void paint(Graphics graphics, boolean isShort) {

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        FontMetrics fontMetrics = g2d.getFontMetrics(font);

        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        diameter = Math.max(textWidth, textHeight) + 10;


        if(isPressed) {
            buttonStrokeColor = Color.BLACK;
            textColor = Color.WHITE;
        }
        else {
            buttonStrokeColor = Color.WHITE;
            textColor = Color.BLACK;
        }
        g2d.setColor(buttonStrokeColor);
        g2d.fillOval(x-5, y-5, diameter+10, diameter+10);

        // paints different color if this paint is called
        g2d.setColor(Color.decode("#00bf63"));
        g2d.fillOval(x, y, diameter, diameter);


        g2d.setFont(font);

        int textX = x + (diameter - textWidth) / 2;
        int textY = y + (diameter - textHeight) / 2 + fontMetrics.getAscent();

        g2d.setColor(textColor);
        g2d.drawString(text, textX, textY);
    }

    // calculates diameter of the button
    public static int CalculateDiameter(String text, Graphics graphics){
        FontMetrics fontMetrics = graphics.getFontMetrics(new Font(Font.SANS_SERIF, Font.PLAIN, 10));

        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        //diameter depends upon text( text width and text height )
        int diameter = Math.max(textWidth, textHeight) + 5;

        return diameter;

    }

    /*
    overrides MyButton that implements portion listener
    returns True if mouse cursor is on the vertex button
     */
    @Override
    public boolean onHover(int x, int y){
        if(x > this.x && x < this.x+diameter && y > this.y && y < this.y+diameter && !isPressedFirst) {
            return true;
        }else return false;
    }


    // returns True if vertex button is pressed according to mouse cursor pressed location
    public boolean isPressed(int x, int y){
        if(x > this.x && x < this.x+diameter && y > this.y && y < this.y+diameter) {
            return true;
        }else return false;
    }

    // returns if the vertex button is pressed for the first time
    public boolean isPressedFirst() {
        return isPressedFirst;
    }

    // sets the parameter value if the vertex button is pressed for the first time
    public void setPressedFirst(boolean pressedFirst) {
        isPressedFirst = pressedFirst;
    }

    /*
   overrides MyButton that implements portion listener
   sets isPressed and isPressedFirst true if mouse clicks on vertex button
   sets isPressed and isPressedFirst false if mouse clicks is not on vertex button
    */
    @Override
    public void onClick(int x, int y) {
        if(x > this.x && x < this.x+diameter && y > this.y && y < this.y+diameter && !isPressedFirst) {
            isPressed = true;
            isPressedFirst = true;
        }else if(x > this.x && x < this.x+diameter && y > this.y && y < this.y+diameter && isPressedFirst) {
            isPressed = false;
            isPressedFirst = false;
        }
    }



}
