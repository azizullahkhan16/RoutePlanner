package button;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.Serial;
import java.io.Serializable;

/**
 * This is the abstract class for button
 */

public abstract class MyButton implements Serializable {

    // protected so that the child class can access its attributes
    protected int x;
    protected int y;
    protected boolean isPressed;

    /**
     * @param x x position of button on the board
     * @param y y position of button on the board
     * @param isPressed variable to check if pressed
     */
    public MyButton(int x, int y, boolean isPressed) {
        this.x = x;
        this.y = y;
        this.isPressed = isPressed;
    }

    // abstract function that child classes should implement

    // paints the button
    public abstract void paint(Graphics graphics);

    // if mouse clicks the button
    public abstract void onClick(int x, int y);

    // if mouse is on the button
    public abstract boolean onHover(int x, int y);

}
