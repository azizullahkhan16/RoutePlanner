package toolbar;

import button.ButtonToggleVertex;
import graph.Graph;
import swingcomponents.PortionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static swingcomponents.Board.B_HEIGHT;
import static swingcomponents.Board.B_WIDTH;

public class DrawingCanvas implements PortionListener {
    // Attributes
    private static int x, y, width, height;
    private static Graph graph;
    private static ArrayList<Point> circlePositions;
    private static ArrayList<ButtonToggleVertex> verticesButtons;
    private static ButtonToggleVertex currentButton;
    private static ButtonToggleVertex source;
    private static ButtonToggleVertex destination;
    private static boolean IsAStarSearch = false;
    private static boolean IsGreedySearch = false;
    private static boolean ShowSaveLabel = false;

    /**
     * Constructor for drawing panel(portion) on board
     * @param x x position of drawing panel
     * @param y y position of drawing panel
     * @param width width of drawing panel
     * @param height height of drawing panel
     */
    public DrawingCanvas(int x, int y, int width, int height) {
        DrawingCanvas.x = x;
        DrawingCanvas.y = y;
        DrawingCanvas.width = width;
        DrawingCanvas.height = height;

        // Initialise graph and arraylists
        graph = new Graph();
        circlePositions = new ArrayList<>();
        verticesButtons = new ArrayList<>();
    }

    // Adding vertex to drawing panel
    public static void AddVertex(String name, Color color){
        Point centre = getNewVertexPosition();
        ButtonToggleVertex newButton = new ButtonToggleVertex(centre.x, centre.y, false, name, color);
        graph.addVertex(newButton); // Add vertex to graph
        verticesButtons.add(newButton); // Add to arraylist of Vertex buttons
        circlePositions.add(centre); // Add the vertex center to circle position arraylist
    }

    // Remove vertex from drawing board
    public static void RemoveVertex(ButtonToggleVertex label){
        graph.removeVertex(graph.searchVertex(label)); // Remove the vertex
        graph.removeAllEdges(label); // Remove all of its edges incident on it
    }

    // Returns true if unique name passed
    public static boolean UniqueVertexName(String name){
        return graph.uniqueVertexName(name);
    }

    // Removes edge from drawing board
    public static void RemoveEdge(Point source, Point destination){
        graph.deleteEdge(source, destination); // Delete edge from graph
    }

    /**
     * Add edge to drawing board
     * @param start Enter vertex to start on
     * @param end Enter vertex to end on
     * @param weight Enter weight
     */
    public static void AddEdge(Point start, Point end, int weight){
        graph.addEdge(start, end, weight); // Add edge to graph
    }

    // Update edge
    public static void UpdateEdge(ButtonToggleVertex buttonToggleVertex){
        graph.updateEdge(buttonToggleVertex); // Update edge on graph
    }

    /**
     * Checks if the edge from vertex start and end exits
     * @param start Enter start vertex of edge
     * @param end Enter end vertex of edge
     * @return True if edge exists in graph
     */
    public static boolean IsEdgeExist(ButtonToggleVertex start, ButtonToggleVertex end){
        return graph.isEdgeExist(start, end); //checks in graph
    }

    // Searches vertex name in graph
    public static ButtonToggleVertex SearchVertex(String name){
        return graph.searchVertex(name);
    }

    /**
     * Searches edge through source and destination vertex
     * @param source Enter start vertex
     * @param destination Enter end vertex
     * @return edge if found
     */
    public static boolean SearchEdge(ButtonToggleVertex source, ButtonToggleVertex destination){
        return graph.searchEdge(source, destination);
    }

    // Calculates center point on where to add vertex
    private static Point getNewVertexPosition() {
        int vertexDiameter = 50;
        int frameWidth = width;
        int frameHeight = height;
        int x = DrawingCanvas.x+vertexDiameter;
        int y = DrawingCanvas.y+vertexDiameter;

        // Check if the new vertex overlaps with any existing circles
        while (x + vertexDiameter < frameWidth && y + vertexDiameter < frameHeight) {
            boolean overlaps = false;
            for (Point position : circlePositions) {
                int dx = position.x - x;
                int dy = position.y - y;
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < vertexDiameter) {
                    overlaps = true;
                    break;
                }
            }

            if (!overlaps) {
                return new Point(x, y);
            }

            // Move to the next position
            x += vertexDiameter;
            if (x + vertexDiameter >= frameWidth) {
                x = vertexDiameter;
                y += vertexDiameter;
            }
        }

        return null; // No valid position found
    }

    /**
     * Finds shortest path through dijkstra algorithm
     * @param source start vertex
     * @param destination end vertex
     * @return True if path exists
     */
    public static boolean FindShortestPathDijkstra(ButtonToggleVertex source, ButtonToggleVertex destination){
        return graph.dijkstraShortestPath(source, destination);
    }

    /**
     * Finds shortest path through bellman algorithm
     * @param source start vertex
     * @param destination end vertex
     * @return True if path exists
     */
    public static boolean FindShortestPathBellman(ButtonToggleVertex source, ButtonToggleVertex destination){
        return graph.bellmanFordShortestPath(source, destination);
    }

    /**
     * Finds shortest path through A* algorithm
     * @param source start vertex
     * @param destination end vertex
     * @return True if path exists
     */
    public static boolean FindShortestPathAStarSearch(ButtonToggleVertex source, ButtonToggleVertex destination){
        DrawingCanvas.source = source;
        DrawingCanvas.destination = destination;
        IsAStarSearch = true;
        return graph.aStarShortestPath(source, destination);
    }

    /**
     * Finds shortest path through greedy search algorithm
     * @param source start vertex
     * @param destination end vertex
     * @return True if path exists
     */
    public static boolean FindShortestPathGreedy(ButtonToggleVertex source, ButtonToggleVertex destination){
        DrawingCanvas.source = source;
        DrawingCanvas.destination = destination;
        IsGreedySearch = true;
        return graph.greedyBestFirstSearch(source, destination);
    }

    /**
     * Finds shortest path through Bi-directional search algorithm
     * @param source start vertex
     * @param destination end vertex
     * @return True if path exists
     */
    public static boolean FindShortestPathBidirectional(ButtonToggleVertex source, ButtonToggleVertex destination){
        return graph.bidirectionalSearchShortestPath(source, destination);
    }

    /**
     * Finds shortest path through Breath first search algorithm
     * @param source start vertex
     * @param destination end vertex
     * @return True if path exists
     */
    public static boolean FindShortestPathBFS(ButtonToggleVertex source, ButtonToggleVertex destination){
        return graph.BFSShortestPath(source, destination);
    }

    /**
     * Finds shortest path through Depth first search algorithm
     * @param source start vertex
     * @param destination end vertex
     * @return True if path exists
     */
    public static boolean FindShortestPathDFS(ButtonToggleVertex source, ButtonToggleVertex destination){
        return graph.DFSShortestPath(source, destination);
    }

    // Clears the drawing canvas
    public static void ClearGraph(){
        graph = new Graph();
        circlePositions.clear();
        verticesButtons.clear();
        currentButton = null;
    }

    // Updates shortest path depending on algorithm used
    public static void UpdateShortestPath(){
        DrawingCanvas.source = null;
        DrawingCanvas.destination = null;
        IsAStarSearch = false;
        IsGreedySearch = false;
        graph.updateShortestPath();
    }

    // Checks if the shortest path is empty
    public static boolean IsShortestPathEmpty(){
        return graph.isShortestPathEmpty();
    }

    // Gets current button
    public static ButtonToggleVertex getCurrentButton() {
        return currentButton;
    }

    // To save the graphs
    public static void SaveFile(){
        try {
            // Get the current date and time
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String fileName = dateFormat.format(currentDate);

            FileOutputStream fileOut = new FileOutputStream("src/savefiles/"+fileName + ".ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(graph);
            objectOut.writeObject(verticesButtons);
            objectOut.writeObject(circlePositions);
            objectOut.writeObject(currentButton);
            objectOut.close();
            fileOut.close();
            System.out.println("Successfully saved!");
            ShowSaveLabel = true;

            // Take a snapshot of the drawing canvas
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.decode("#373737")); // Set transparent background
            g2d.fillRect(0, 0, width, height);
            graph.paint(g2d);
            g2d.dispose();

            // Save as JPEG image
            String jpegFileName = fileName + ".jpeg";
            File jpegFile = new File("src/savefiles/" + jpegFileName);
            ImageIO.write(image, "jpeg", jpegFile);
            System.out.println("Successfully saved as JPEG image: " + jpegFileName);

            // Create a Timer object
            Timer timer = new Timer();

            // Schedule a TimerTask to change the flag value after 5 seconds
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    ShowSaveLabel = false;  // Change the flag value after 5 seconds
                }
            }, 3000);  // 5000 milliseconds = 5 seconds
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // To load the saved files back
    public static void LoadFile(String file){
        try {
            FileInputStream fileIn = new FileInputStream("src/savefiles/"+ file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            graph = (Graph) objectIn.readObject();
            verticesButtons = (ArrayList<ButtonToggleVertex>) objectIn.readObject();
            circlePositions = (ArrayList<Point>) objectIn.readObject();
            currentButton = (ButtonToggleVertex)  objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Paint the drawing canvas
    public void paint(Graphics graphics){

        // Paints graph
        graph.paint(graphics);

        // If shortest path is not empty , paint shortest path
        if(!IsShortestPathEmpty()) graph.paintShortestPath(graphics);

        if(ShowSaveLabel){
            Graphics2D graphics2D = (Graphics2D) graphics;
            Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(font);
            graphics2D.drawString("File Saved", 1200, 20);
        }

    }

    // Check to set boundary of drawing canvas
    public static boolean InBounds(int x ,int y){
        if((x > DrawingCanvas.x && x < DrawingCanvas.width-38 && y > DrawingCanvas.y && y < 210) ||
                (x > 0 && x < B_WIDTH-35 && y > 210 && y < B_HEIGHT-36)) {
            return true;
        }
        return false;
    }

    // On click implementation
    @Override
    public void onClick(int x, int y) {

        for (ButtonToggleVertex b : verticesButtons){
            if(b.onHover(x, y)){
                for (ButtonToggleVertex b1 : verticesButtons){
                    b1.setPressed(false);
                    b1.setPressedFirst(false);
                }
                b.onClick(x, y); // check the clicked button
                currentButton = b; // set current button if clicked
            }
        }

        // Buttons for user functionality

        ToolbarJPanel.addButton.setIcon(new ImageIcon("src/addUnpressed.png"));
        ToolbarJPanel.IsAddPressed = false;

        ToolbarJPanel.removeButton.setIcon(new ImageIcon("src/removeUnpressed.png"));
        ToolbarJPanel.IsRemovePressed = false;

        ToolbarJPanel.fileButton.setIcon(new ImageIcon("src/fileUnpressed.png"));
        ToolbarJPanel.IsFilePressed = false;

        ToolbarJPanel.routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));
        ToolbarJPanel.IsRoutePressed = false;

    }

    // On press implementation
    @Override
    public void onPress(int x, int y) {
    }

    // On drag implementation
    @Override
    public void onDrag(int x, int y) {
        if(currentButton != null) {
            if (InBounds(x, y)) {
                if (currentButton.isPressedFirst()) {
                    currentButton.setPoint(x, y); // Change the button coordinate if it is dragged
                    UpdateEdge(currentButton); // Update the button accordingly
                    if(!IsShortestPathEmpty() && source != null && destination != null && IsAStarSearch) FindShortestPathAStarSearch(source, destination);
                    if(!IsShortestPathEmpty() && source != null && destination != null && IsGreedySearch) FindShortestPathGreedy(source, destination);
                }
            }
        }
    }

    // On release implementation
    @Override
    public void onRelease(int x, int y) {

    }

    // On move implementation
    @Override
    public void onMove(int x, int y) {

        for (ButtonToggleVertex b : verticesButtons){
            if(b.onHover(x, y)){
                b.setPressed(true);
            }else if(!b.isPressedFirst()) b.setPressed(false);
        }
    }
}
