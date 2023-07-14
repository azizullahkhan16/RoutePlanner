package swingcomponents;

import toolbar.DrawingCanvas;
import toolbar.ToolbarJPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class Board extends JPanel
        implements ActionListener , MouseInputListener{

    // Attributes
    public static final int B_WIDTH = 1280;
    public static final int B_HEIGHT = 680;

    private final int DELAY = 10;
    private Timer timer;
    private DrawingCanvas drawingCanvas;
    private ToolbarJPanel toolbarJPanel;


    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }
    }

    // Constructor
    public Board() {

        // Initialises board
        initBoard();
    }
    private void InitializeAssets() {
    }

    // Adds properties needed in board
    private void initBoard() {

        // Adding IO to board
    	addMouseListener( this );
    	addMouseMotionListener( this );
    	addKeyListener(new TAdapter());

        // Setting board screen
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setFocusable(true);
        setBackground(Color.decode("#373737"));

        InitializeAssets();

        // Initialise parts of board
        drawingCanvas = new DrawingCanvas(80, 0, B_WIDTH, B_HEIGHT);
        toolbarJPanel=new ToolbarJPanel();
        setLayout(null); // Set the layout manager to null layout
        toolbarJPanel.setBounds(0, 0, 60, 200); // Set the bounds of toolbarJPanel
        toolbarJPanel.setBackground(Color.decode("#373737"));

        add(toolbarJPanel); // Add toolbarJPanel to the parent container

        timer = new Timer(DELAY, this);
        timer.start();
    }

    // Paints the board
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
       drawingCanvas.paint(g);
    }

    // MOUSE LISTENERS
    // Checks mouse clicked on board
    @Override
	public void mouseClicked(MouseEvent e) {
        drawingCanvas.onClick(e.getX(), e.getY());
    }

    //  Checks mouse pressed on board
	@Override
	public void mousePressed(MouseEvent e) {
        drawingCanvas.onPress(e.getX(), e.getY());
	}

    // Checks mouse released on board
	@Override
	public void mouseReleased(MouseEvent e) {
        drawingCanvas.onRelease(e.getX(), e.getY());
	}

    // Checks mouse entered on board
	@Override
	public void mouseEntered(MouseEvent e) {
	}

    // Checks mouse excited on board
	@Override
	public void mouseExited(MouseEvent e) {

	}

    // Checks mouse dragged on board
	@Override
	public void mouseDragged(MouseEvent e) {
        drawingCanvas.onDrag(e.getX(), e.getY());
	}

    // Checks mouse moved on board
	@Override
	public void mouseMoved(MouseEvent e) {
        drawingCanvas.onMove(e.getX(), e.getY());
    }

	// refreshing
    @Override
    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().sync();
        repaint();
    }
}