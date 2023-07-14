package toolbar;


import window.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ToolbarJPanel extends JPanel {
    // Attributes
    public static JButton addButton;
    public static boolean IsAddPressed = false;
    public static JButton removeButton;
    public static boolean IsRemovePressed = false;
    public static JButton fileButton;
    public static boolean IsFilePressed = false;
    public static JButton routeButton;
    public static boolean IsRoutePressed = false;

    private VertexWindow vertexWindow;
    private AddEdgeWindow addEdgeWindow;
    private RemoveEdgeWindow removeEdgeWindow;
    private ShortestPathWindow shortestPathWindow;
    private OpenWindow openWindow;


    /**
     * Constructor to build toolbar
     */
    public ToolbarJPanel() {

        // Initialise windows
        addEdgeWindow = AddEdgeWindow.getEdgeWindow(400, 170, 400, 340);
        vertexWindow = VertexWindow.getVertexWindow(400, 170, 400, 340);
        removeEdgeWindow = RemoveEdgeWindow.getEdgeWindow(400, 170, 400, 340);
        shortestPathWindow = ShortestPathWindow.getShortestPathWindow(400, 170, 400, 340);
        openWindow = OpenWindow.getOpenWindow(400, 170, 400, 340);



        // Add Button
        addButton = createButton("src/addUnpressed.png", "src/addPressed.png");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IsAddPressed = true;
                removeButton.setIcon(new ImageIcon("src/removeUnpressed.png"));
                fileButton.setIcon(new ImageIcon("src/fileUnpressed.png"));
                routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));

                if(!DrawingCanvas.IsShortestPathEmpty()) DrawingCanvas.UpdateShortestPath();
                JPopupMenu menu = new JPopupMenu();
                JMenuItem addVertexItem = new JMenuItem("Add Vertex");
                addVertexItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Add Vertex action
                        addButton.setIcon(new ImageIcon("src/addUnpressed.png"));
                        IsAddPressed = false;
                        vertexWindow.showAddVerticesDialog();
                    }
                });
                JMenuItem addEdgeItem = new JMenuItem("Add Edge");
                addEdgeItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Add Edge action
                        addButton.setIcon(new ImageIcon("src/addUnpressed.png"));
                        IsAddPressed = false;
                        addEdgeWindow.showAddEdgeDialog();
                    }
                });
                menu.add(addVertexItem);
                menu.add(addEdgeItem);
                menu.show(addButton, addButton.getWidth()+3, 15);
            }
        });
        add(addButton);

        // Remove Button
        removeButton = createButton("src/removeUnpressed.png", "src/removePressed.png");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IsRemovePressed = true;
                addButton.setIcon(new ImageIcon("src/addUnpressed.png"));
                fileButton.setIcon(new ImageIcon("src/fileUnpressed.png"));
                routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));


                if(!DrawingCanvas.IsShortestPathEmpty()) DrawingCanvas.UpdateShortestPath();
                JPopupMenu menu = new JPopupMenu();
                JMenuItem removeVertexItem = new JMenuItem("Remove Vertex");
                removeVertexItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Remove Vertex action
                        removeButton.setIcon(new ImageIcon("src/removeUnpressed.png"));
                        IsRemovePressed = false;
                        DrawingCanvas.RemoveVertex(DrawingCanvas.getCurrentButton());
                    }
                });
                JMenuItem removeEdgeItem = new JMenuItem("Remove Edge");
                removeEdgeItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Remove Edge action
                        removeButton.setIcon(new ImageIcon("src/removeUnpressed.png"));
                        IsRemovePressed = false;
                        removeEdgeWindow.showRemoveEdgeDialog();
                    }
                });
                JMenuItem removeGraph = new JMenuItem("Delete Graph");
                removeGraph.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Remove Edge action
                        removeButton.setIcon(new ImageIcon("src/removeUnpressed.png"));
                        IsRemovePressed = false;
                        DrawingCanvas.ClearGraph();
                    }
                });

                menu.add(removeVertexItem);
                menu.add(removeEdgeItem);
                menu.add(removeGraph);
                menu.show(removeButton, removeButton.getWidth()+3, 15);
            }
        });
        add(removeButton);

        // Route Button
        routeButton = createButton("src/routeUnpressed.png", "src/routePressed.png");
        routeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IsRoutePressed = true;
                addButton.setIcon(new ImageIcon("src/addUnpressed.png"));
                fileButton.setIcon(new ImageIcon("src/fileUnpressed.png"));
                removeButton.setIcon(new ImageIcon("src/removeUnpressed.png"));


                if(!DrawingCanvas.IsShortestPathEmpty()) DrawingCanvas.UpdateShortestPath();
                JPopupMenu menu = new JPopupMenu();
                JMenuItem dijkstra = new JMenuItem("Dijkstra's Algorithm");
                dijkstra.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Dijkstra action
                        routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));
                        IsRoutePressed = false;
                        shortestPathWindow.showShortestPathDialog("dijkstra");
                    }
                });
                JMenuItem bellman = new JMenuItem("Bellman-Ford Algorithm");
                bellman.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Dijkstra action
                        routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));
                        IsRoutePressed = false;
                        shortestPathWindow.showShortestPathDialog("bellman");
                    }
                });
                JMenuItem aSearch = new JMenuItem("A* Search");
                aSearch.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle A* search action
                        routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));
                        IsRoutePressed = false;
                        shortestPathWindow.showShortestPathDialog("aSearch");
                    }
                });
                JMenuItem greedy = new JMenuItem("Greedy Best-first Search");
                greedy.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle greedy action
                        routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));
                        IsRoutePressed = false;
                        shortestPathWindow.showShortestPathDialog("greedy");
                    }
                });
                JMenuItem bidirectional = new JMenuItem("Bidirectional Breath-first Search");
                bidirectional.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle bidirectional action
                        routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));
                        IsRoutePressed = false;
                        shortestPathWindow.showShortestPathDialog("bidirectional");
                    }
                });
                JMenuItem BFS = new JMenuItem("Breath-first Search");
                BFS.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle BFS action
                        routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));
                        IsRoutePressed = false;
                        shortestPathWindow.showShortestPathDialog("BFS");
                    }
                });
                JMenuItem DFS = new JMenuItem("Depth-first Search");
                DFS.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle DFS action
                        routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));
                        IsRoutePressed = false;
                        shortestPathWindow.showShortestPathDialog("DFS");
                    }
                });
                menu.add(dijkstra);
                menu.add(bellman);
                menu.add(aSearch);
                menu.add(greedy);
                menu.add(bidirectional);
                menu.add(BFS);
                menu.add(DFS);
                menu.show(routeButton, routeButton.getWidth()+3, 15);

            }
        });
        add(routeButton);

        // File Button
        fileButton = createButton("src/fileUnpressed.png", "src/filePressed.png");
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IsFilePressed = true;
                addButton.setIcon(new ImageIcon("src/addUnpressed.png"));
                removeButton.setIcon(new ImageIcon("src/removeUnpressed.png"));
                routeButton.setIcon(new ImageIcon("src/routeUnpressed.png"));

                if(!DrawingCanvas.IsShortestPathEmpty()) DrawingCanvas.UpdateShortestPath();
                JPopupMenu menu = new JPopupMenu();
                JMenuItem open = new JMenuItem("Open");
                open.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Open action
                        fileButton.setIcon(new ImageIcon("src/fileUnpressed.png"));
                        IsFilePressed = false;
                        openWindow.showOpenDialog();
                    }
                });
                JMenuItem save = new JMenuItem("Save");
                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle Save action
                        fileButton.setIcon(new ImageIcon("src/fileUnpressed.png"));
                        IsFilePressed = false;
                        DrawingCanvas.SaveFile();
                    }
                });
                menu.add(open);
                menu.add(save);
                menu.show(fileButton, fileButton.getWidth()+3, 15);
            }
        });
        add(fileButton);
    }


    // Create Buttons (used for add,remove,shortest path and save buttons)
    private JButton createButton(String iconUnpressed, String iconPressed) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(40, 40));
        button.setMaximumSize(new Dimension(40, 40));

        ImageIcon defaultIcon = new ImageIcon(iconUnpressed);
        ImageIcon pressedIcon = new ImageIcon(iconPressed);

        button.setIcon(defaultIcon);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        // Add a listener to handle button press and release events
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                IsAddPressed = false;
                IsRemovePressed = false;
                IsFilePressed = false;
                IsRoutePressed = false;
                button.setIcon(pressedIcon);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!IsAddPressed && !IsFilePressed && !IsRemovePressed && !IsRoutePressed)button.setIcon(pressedIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!IsAddPressed && !IsFilePressed && !IsRemovePressed && !IsRoutePressed) button.setIcon(defaultIcon);
            }
        });

        return button;
    }

}




