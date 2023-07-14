package graph;

import button.ButtonToggleVertex;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/*
    Implementation of Graph data structure
 */
public class Graph implements Serializable{
    // Array list that contains all vertices made
    private ArrayList<Vertex> vertexList;

    //Array list that contains all edgeList made
    private ArrayList<Edge> edgeList;

    /**
     * Matrix of edgeList weight
     * Time complexity of O(1)
     * used list inside list for better flexibility and scalability
     */
    private List<List<Integer>> adjMatrix;

    // Total number of vertices
    private int numOfVertices;

    // Total number of edgeList
    private int numOfEdges;

    // List of vertices that come in shortest path
    private ArrayList<ButtonToggleVertex> verticesInShortestpath;

    // List of edgeList that come in shortest path
    private ArrayList<Edge> edgesInShortestpath;

    /**
     * Constructor
     * initialises attributes
     */
    public Graph(){
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();

        adjMatrix = new ArrayList<>();

        numOfVertices = 0;
        numOfEdges = 0;
        verticesInShortestpath = new ArrayList<>();
        edgesInShortestpath = new ArrayList<>();
    }

    // Return arraylist that contains all vertices
    public ArrayList<Vertex> getVertexList() {
        return vertexList;
    }

    // Return arraylist that contains all edgeList
    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    // Return matrix that contains all weights of edgeList
    public List<List<Integer>> getAdjMatrix() {
        return adjMatrix;
    }

    // Returns total number vertices in graph
    public int getNumOfVertices() {
        return numOfVertices;
    }

    // Returns total edges in graph
    public int getNumOfEdges() {
        return numOfEdges;
    }

    // Sets list of vertices to passed value
    public void setVertexList(ArrayList<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    // Sets list of edges to passed value
    public void setEdgeList(ArrayList<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    // Sets adjacent matrix to vertices to passed value
    public void setAdjMatrix(List<List<Integer>> adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

    // Sets total number of vertices to passed value
    public void setNumOfVertices(int numOfVertices) {
        this.numOfVertices = numOfVertices;
    }

    // Sets total number of edges to passed value
    public void setNumOfEdges(int numOfEdges) {
        this.numOfEdges = numOfEdges;
    }

    // Adds a vertex to the graph
    public void addVertex(ButtonToggleVertex label) {
        vertexList.add(new Vertex(label)); // Add to arraylist of all vertices in graph
        numOfVertices++; // Increment number of vertices in graph
        adjustAdjacencyMatrix(); // Update matrix
        printGraph();
    }

    // Creates Indexes for new vertex added to the matrix
    private void adjustAdjacencyMatrix() {
        for (List<Integer> row : adjMatrix) {
            row.add(0); // Add an initial edge weight of 0 for the new vertex
        }
        List<Integer> newRow = new ArrayList<>();
        for (int i = 0; i < numOfVertices; i++) {
            newRow.add(0);
        }
        adjMatrix.add(newRow);
    }

    // Removes vertex from the graph
    public void removeVertex(int vertexIndex) {
        if (vertexIndex >= 0 && vertexIndex < numOfVertices) {
            vertexList.remove(vertexIndex); // Remove from arraylist of all vertices of the graph
            adjMatrix.remove(vertexIndex); // Remove the matrix column from adjacent Matrix

            for (List<Integer> row : adjMatrix) {
                row.remove(vertexIndex); // Remove the row for all other vertices for the removed vertex index
            }

            numOfVertices--; // Decrement total number of vertices
            printGraph();

        } else {
            System.out.println("Invalid vertex index!");
        }
    }

    /**
     * creates edge with parameters of:
     * @param start origin vertex of edge
     * @param end destination vertex of edge
     * @param weight weight of edge
     */

    public void addEdge(Point start, Point end, int weight){
        int source = 0;
        int destination = 0;
        Vertex sourceVertex = null;
        Vertex destinationVertex = null;

        for (int i = 0; i < vertexList.size(); i++) { // Finding start and end vertex in arraylist of all vertices
            if(vertexList.get(i).getPoint().equals(start)){
                // If found start vertex : set source to index of matches vertex and retrieve its vertex button
                source = i;
                sourceVertex = vertexList.get(i);
            } else if(vertexList.get(i).getPoint().equals(end)) {
                // If found end vertex :set destination to index of matched vertex and retrieve its vertex button
                destination = i;
                destinationVertex = vertexList.get(i);
            }
        }

        // Create new edge and add to list of edges
        edgeList.add(new Edge(sourceVertex.label.getCenter(), destinationVertex.label.getCenter(), weight, sourceVertex, destinationVertex));
        numOfEdges++; // Increment total number of edges in graph

        // Updating adjacency matrix
        addEdge(source, destination, weight);

    }

    /**
     * Setting weight to the adjacency matrix accordingly
     * @param source index of origin vertex
     * @param destination index of destination vertex
     * @param weight weight of edge
     */
    public void addEdge(int source, int destination, int weight){
        if (source >= 0 && source < numOfVertices && destination >= 0 && destination < numOfVertices) {
            adjMatrix.get(source).set(destination, weight);
            printGraph();
        } else {
            System.out.println("Invalid vertex index!");
        }
    }

    // Updating edge
    // Deep copying the edge
    public void updateEdge(ButtonToggleVertex label){
        for (Edge e : edgeList){
            if(e.sourceVertex.label.equals(label)) e.start = label.getCenter();
            else if(e.destinationVertex.label.equals(label)) e.end = label.getCenter();
        }
        for (Edge e : edgesInShortestpath){
            if(e.sourceVertex.label.equals(label)) e.start = label.getCenter();
            else if(e.destinationVertex.label.equals(label)) e.end = label.getCenter();
        }
    }

    // Deleting all edges of a vertex from graph
    public void removeAllEdges(ButtonToggleVertex label){
        ArrayList<Edge> waste = new ArrayList<>();
        for (Edge e : edgeList){
            if (e.sourceVertex.label.equals(label) || e.destinationVertex.label.equals(label)){
                //adding all edges adjacent to vertex in waste arraylist
                waste.add(e);
            }
        }

        // Removing all edges in waste arraylist from graph
        for (Edge e : waste){
            edgeList.remove(e);
        }
    }

    // Deleting edge from graph
    public void deleteEdge(Point start, Point end){
        int source = 0;
        int destination = 0;
        Vertex sourceVertex = null;
        Vertex destinationVertex = null;

        // Finding origin and destination index and vertex of the edge to delete
        for (int i = 0; i < vertexList.size(); i++) {
            if(vertexList.get(i).getPoint().equals(start)){
                source = i;
                sourceVertex = vertexList.get(i);
            } else if(vertexList.get(i).getPoint().equals(end)) {
                destination = i;
                destinationVertex = vertexList.get(i);
            }
        }

        // Deleting edge
        deleteEdgeInMatrix(source, destination);
        deleteEdge(sourceVertex.label, destinationVertex.label);

    }

    // Removing edge from graph
    public void deleteEdge(ButtonToggleVertex source, ButtonToggleVertex end){
        Edge waste = null;
        // Removes edge from array list of all edges
        for (Edge e : edgeList){
            if (e.sourceVertex.label.equals(source) && e.destinationVertex.label.equals(end)){
                waste = e;
                break;
            }
        }
        edgeList.remove(waste);
    }

    // Updates the adjacency matrix after deleting edge
    public void deleteEdgeInMatrix(int source, int destination) {
        if (source >= 0 && source < numOfVertices && destination >= 0 && destination < numOfVertices) {
            // Sets the edge weight to 0
            // Represents null/ no edge
            adjMatrix.get(source).set(destination, 0);
            printGraph();
        } else {
            System.out.println("Invalid vertex index!");
        }
    }

    // Searches vertex name in arraylist of all vertices in graph
    public ButtonToggleVertex searchVertex(String name){

        for(Vertex v : vertexList){
            if(v.getName().equals(name)) return v.label;
        }
        // Returns null if does not find vertex
        return null;
    }

    // Searches vertex label in arraylist of all vertices in graph
    public int searchVertex(ButtonToggleVertex label){

        for (int i = 0; i < vertexList.size(); i++) {
            if(vertexList.get(i).label.equals(label)) return i;
        }
        // Returns -1 if does not find vertex
        return -1;
    }

    // Searches edge in arraylist of all edges in graph
    public boolean searchEdge(ButtonToggleVertex source, ButtonToggleVertex destination){

        for (Edge e : edgeList){
            if(e.sourceVertex.label.equals(source) && e.destinationVertex.label.equals(destination)) return true;
        }
        // Returns false if does not find edge
        return false;
    }

    // Displays/draws vertex of parameters value/index
    public void displayVertex(int v){
        System.out.println(vertexList.get(v).label);
    }

    // Checks if vertex name is unique
    public boolean uniqueVertexName(String name){
        // Compares from name of all vertices in graph
        for (Vertex v : vertexList){
            if(v.label.getText().equals(name)) return false;
        }
        // Returns true if it does not match with any vertex name
        return true;
    }

    // Prints adjacency matrix in terminal
    public void printGraph() {
        for (int i = 0; i < adjMatrix.size(); i++) {
            for (int j = 0; j < adjMatrix.get(i).size(); j++) {
                System.out.print(adjMatrix.get(i).get(j) + " ");
            }
            System.out.println();
        }

        System.out.println("__________________________");
    }

    // Clears the shortest path arraylist of edges and vertices
    public void updateShortestPath(){
        verticesInShortestpath.clear();
        edgesInShortestpath.clear();

    }

    // Returns true if arraylist of the shortest path vertices is empty
    public boolean isShortestPathEmpty(){
        if (verticesInShortestpath.isEmpty()) return true;
        return false;
    }

    // Returns true if edge is in arraylist of all edges in graph
    public boolean isEdgeExist(ButtonToggleVertex start, ButtonToggleVertex end){

        for (Edge e : edgeList){
            if((e.sourceVertex.label.equals(start) && e.destinationVertex.label.equals(end)) || (e.sourceVertex.label.equals(end) && e.destinationVertex.label.equals(start))){
                return true;
            }
        }
        // Returns false if cannot find edge between start and end vertex
        return false;
    }

    // Paints graph
    public void paint(Graphics graphics) {

        // Paints all vertices in graph
        for (Vertex v : vertexList){
            v.paint(graphics);
        }

        // Paints all edges in graph
        for (Edge e : edgeList){
            e.paint(graphics);
        }

        // Paints vertices again on top of edges
        for (Vertex v : vertexList){
            v.paint(graphics);
        }


    }

    // Paints the shortest path
    public void paintShortestPath(Graphics graphics) {

        // Paints vertices of the shortest path
        for (ButtonToggleVertex b : verticesInShortestpath){
            b.paint(graphics, true);
        }

        // Paints edges of the shortest path
        for (Edge e : edgesInShortestpath){
            e.paint(graphics);
        }

        // Paints vertices of the shortest path
        for (ButtonToggleVertex b : verticesInShortestpath){
            b.paint(graphics, true);
        }


    }

    // Searches edges for shortest path
    public void findShortestPathEdges(){
        edgesInShortestpath.clear();

        for (int i = 0; i < verticesInShortestpath.size()-1; i++) {
            for (Edge e : edgeList) {
                //checks edges of vertices in shortest path
                if (verticesInShortestpath.get(i).equals(e.sourceVertex.label) && verticesInShortestpath.get(i+1).equals(e.destinationVertex.label)){
                    Edge newEdge = new Edge(e.start, e.end, e.weight, e.sourceVertex, e.destinationVertex);
                    newEdge.isShortestEdge = true;
                    edgesInShortestpath.add(newEdge);
                }
            }
        }

    }

    // Breath first search implementation
    public boolean BFSShortestPath(ButtonToggleVertex start, ButtonToggleVertex end) {
        Queue<ButtonToggleVertex> queue = new LinkedList<>();
        Set<ButtonToggleVertex> visited = new HashSet<>();
        Map<ButtonToggleVertex, ButtonToggleVertex> parent = new HashMap<>();

        // Adding start vertex to queue
        queue.add(start);
        visited.add(start);
        parent.put(start, null);

        while (!queue.isEmpty()) {
            ButtonToggleVertex current = queue.poll();

            if (current == end) {
                break; // Target vertex reached, exit the loop
            }

            int currentIndex = searchVertex(current);

            // Get adjacent vertices
            for (int i = 0; i < adjMatrix.get(currentIndex).size(); i++) {
                int weight = adjMatrix.get(currentIndex).get(i);
                ButtonToggleVertex neighbor = vertexList.get(i).label;

                if (weight != 0 && !visited.contains(neighbor)) {
                    // Add neighbour to queue
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                }
            }
        }

        if (!visited.contains(end)) {
            return false; // No path exists between start and target vertices
        }

        // Reconstruct the shortest path
        verticesInShortestpath.clear();
        ButtonToggleVertex vertex = end;

        while (vertex != null) {
            verticesInShortestpath.add(vertex);
            vertex = parent.get(vertex);
        }

        // Reversing arraylist of vertices in shortest path
        Collections.reverse(verticesInShortestpath);
        // Finding edges of vertices in shortest path
        findShortestPathEdges();

        return true;
    }

    // Depth first search implementation
    public boolean DFSShortestPath(ButtonToggleVertex start, ButtonToggleVertex end) {
        Deque<ButtonToggleVertex> stack = new LinkedList<>();
        boolean[] visited = new boolean[vertexList.size()];
        ButtonToggleVertex[] parent = new ButtonToggleVertex[vertexList.size()];

        // Adding start vertex to stack
        stack.addFirst(start);
        visited[searchVertex(start)] = true;
        parent[searchVertex(start)] = null;

        while (!stack.isEmpty()) {
            ButtonToggleVertex current = stack.removeFirst();

            if (current == end) {
                break; // Target vertex reached, exit the loop
            }

            int currentIndex = searchVertex(current);

            // Finding adjacent vertices
            for (int i = adjMatrix.get(currentIndex).size() - 1; i >= 0; i--) {
                int weight = adjMatrix.get(currentIndex).get(i);
                ButtonToggleVertex neighbor = vertexList.get(i).label;

                if (weight != 0 && !visited[searchVertex(neighbor)]) {
                    stack.addFirst(neighbor);
                    visited[searchVertex(neighbor)] = true;
                    parent[searchVertex(neighbor)] = current;
                }
            }
        }

        if (!visited[searchVertex(end)]) {
            return false; // No path exists between start and target vertices
        }

        // Reconstruct the shortest path
        verticesInShortestpath.clear();
        ButtonToggleVertex vertex = end;

        while (vertex != null) {
            verticesInShortestpath.add(vertex);
            vertex = parent[searchVertex(vertex)];
        }

        // Reversing arraylist of vertices in shortest path
        Collections.reverse(verticesInShortestpath);
        // Finding edges of vertices in shortest path
        findShortestPathEdges();

        return true;
    }

    // Dijkstra path implementation
    public boolean dijkstraShortestPath(ButtonToggleVertex start, ButtonToggleVertex end) {
        // Initialize data structures
        Map<ButtonToggleVertex, Integer> distances = new HashMap<>();
        Map<ButtonToggleVertex, ButtonToggleVertex> previousVertices = new HashMap<>();
        Set<ButtonToggleVertex> visited = new HashSet<>();

        // Set initial distances to infinity except for the start vertex
        for (Vertex vertex : vertexList) {
            distances.put(vertex.label, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        // Process vertices in order of increasing distance
        while (!visited.contains(end)) {
            ButtonToggleVertex current = null;
            int minDistance = Integer.MAX_VALUE;

            // Find the vertex with the minimum distance
            for (ButtonToggleVertex vertex : distances.keySet()) {
                int distance = distances.get(vertex);
                if (!visited.contains(vertex) && distance < minDistance) {
                    minDistance = distance;
                    current = vertex;
                }
            }

            // If no reachable vertices remain, exit the loop
            if (current == null) {
                break;
            }

            visited.add(current);

            // Update distances to neighbors of the current vertex
            int currentIndex = searchVertex(current);
            List<Integer> neighbors = adjMatrix.get(currentIndex);

            for (int i = 0; i < neighbors.size(); i++) {
                int weight = neighbors.get(i);
                ButtonToggleVertex neighbor = vertexList.get(i).label;

                if (weight != 0 && !visited.contains(neighbor)) {
                    int newDistance = distances.get(current) + weight;

                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previousVertices.put(neighbor, current);
                    }
                }
            }
        }

        if (!visited.contains(end)) {
            return false; // No path exists between start and target vertices
        }

        // Reconstruct the shortest path
        verticesInShortestpath.clear();
        ButtonToggleVertex vertex = end;

        while (vertex != null) {
            verticesInShortestpath.add(vertex);
            vertex = previousVertices.get(vertex);
        }

        // Reversing arraylist of vertices in shortest path
        Collections.reverse(verticesInShortestpath);
        // Finding edges of vertices in shortest path
        findShortestPathEdges();

        return true;
    }

    // A* implementation
    public boolean aStarShortestPath(ButtonToggleVertex start, ButtonToggleVertex goal) {
        // Initialize data structures
        Map<ButtonToggleVertex, Integer> distances = new HashMap<>();
        Map<ButtonToggleVertex, ButtonToggleVertex> previousVertices = new HashMap<>();
        Set<ButtonToggleVertex> visited = new HashSet<>();

        // Set initial distances to infinity except for the start vertex
        for (Vertex vertex : vertexList) {
            distances.put(vertex.label, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        // Priority queue to store vertices based on their estimated total cost
        PriorityQueue<ButtonToggleVertex> queue = new PriorityQueue<>(Comparator.comparingInt(v -> distances.get(v) + calculateHeuristic(v, goal)));
        queue.offer(start);

        while (!queue.isEmpty()) {
            ButtonToggleVertex current = queue.poll();

            visited.add(current);

            if (current.equals(goal)) {
                break; // Reached the goal vertex
            }

            // Update distances to neighbors of the current vertex
            int currentIndex = searchVertex(current);
            List<Integer> neighbors = adjMatrix.get(currentIndex);

            for (int i = 0; i < neighbors.size(); i++) {
                int weight = neighbors.get(i);
                ButtonToggleVertex neighbor = vertexList.get(i).label;

                if (weight != 0 && !visited.contains(neighbor)) {
                    int newDistance = distances.get(current) + weight;

                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previousVertices.put(neighbor, current);
                        queue.offer(neighbor); // Add neighbor to the priority queue
                    }
                }
            }
        }

        if (!visited.contains(goal)) {
            return false; // No path exists between start and goal vertices
        }

        // Reconstruct the shortest path
        verticesInShortestpath.clear();
        ButtonToggleVertex vertex = goal;

        while (vertex != null) {
            verticesInShortestpath.add(vertex);
            vertex = previousVertices.get(vertex);
        }

        // Reversing arraylist of vertices in shortest path
        Collections.reverse(verticesInShortestpath);
        // Finding edges of vertices in shortest path
        findShortestPathEdges();

        return true;
    }

    // Used in A* algorithm calculations
    private int calculateHeuristic(ButtonToggleVertex current, ButtonToggleVertex goal) {
        int x1 = current.getX(); // x-coordinate of current vertex
        int y1 = current.getY(); // y-coordinate of current vertex
        int x2 = goal.getX(); // x-coordinate of goal vertex
        int y2 = goal.getY(); // y-coordinate of goal vertex

        // Calculate the Euclidean distance
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        return (int) distance; // Return the distance as an integer
    }

    //Greedy best first search algorithm implementation
    public boolean greedyBestFirstSearch(ButtonToggleVertex start, ButtonToggleVertex goal) {
        // Initialize data structures
        PriorityQueue<ButtonToggleVertex> queue = new PriorityQueue<>(Comparator.comparingInt(v -> calculateHeuristic(v, goal)));
        Map<ButtonToggleVertex, ButtonToggleVertex> previousVertices = new HashMap<>();
        Set<ButtonToggleVertex> visited = new HashSet<>();

        // Enqueue the start vertex
        queue.add(start);

        // Process vertices in the priority queue until the goal is reached or the queue is empty
        while (!queue.isEmpty()) {
            ButtonToggleVertex current = queue.poll();

            visited.add(current);

            if (current.equals(goal)) {
                // Goal vertex found, reconstruct the shortest path
                verticesInShortestpath.clear();
                ButtonToggleVertex vertex = current;

                while (vertex != null) {
                    verticesInShortestpath.add(vertex);
                    vertex = previousVertices.get(vertex);
                }

                Collections.reverse(verticesInShortestpath);
                findShortestPathEdges();

                return true; // Shortest path found
            }

            // Explore neighbors of the current vertex
            int currentIndex = searchVertex(current);
            List<Integer> neighbors = adjMatrix.get(currentIndex);

            for (int i = 0; i < neighbors.size(); i++) {
                int weight = neighbors.get(i);
                ButtonToggleVertex neighbor = vertexList.get(i).label;

                if (weight != 0 && !visited.contains(neighbor) && !queue.contains(neighbor)) {
                    previousVertices.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return false; // No path exists from start to goal
    }

    // Bi-direction search algorithm implementation
    public boolean bidirectionalSearchShortestPath(ButtonToggleVertex start, ButtonToggleVertex end) {
        // Initialize data structures
        Set<ButtonToggleVertex> startVisited = new HashSet<>();
        Set<ButtonToggleVertex> endVisited = new HashSet<>();
        Map<ButtonToggleVertex, ButtonToggleVertex> startParent = new HashMap<>();
        Map<ButtonToggleVertex, ButtonToggleVertex> endParent = new HashMap<>();

        // Two queue for bi directional search
        Queue<ButtonToggleVertex> startQueue = new LinkedList<>();
        Queue<ButtonToggleVertex> endQueue = new LinkedList<>();

        // Add start and end vertex to the queues
        startVisited.add(start);
        endVisited.add(end);
        startParent.put(start, null);
        endParent.put(end, null);

        ButtonToggleVertex intersection = null;

        startQueue.offer(start);
        endQueue.offer(end);

        while (!startQueue.isEmpty() && !endQueue.isEmpty()) {
            ButtonToggleVertex startVertex = startQueue.poll();
            ButtonToggleVertex endVertex = endQueue.poll();

            // Expand the start side
            int startVertexIndex = searchVertex(startVertex);

            for (int i = 0; i < adjMatrix.get(startVertexIndex).size(); i++) {
                int weight = adjMatrix.get(startVertexIndex).get(i);
                ButtonToggleVertex neighbor = vertexList.get(i).label;

                if (weight != 0 && !startVisited.contains(neighbor)) {
                    startQueue.offer(neighbor);
                    startVisited.add(neighbor);
                    startParent.put(neighbor, startVertex);

                    if (endVisited.contains(neighbor)) {
                        intersection = neighbor;
                        break;
                    }
                }
            }

            // Check if an intersection is found
            if (intersection != null) {
                break;
            }

            // Expand the end side
            int endVertexIndex = searchVertex(endVertex);

            for (int i = 0; i < adjMatrix.get(endVertexIndex).size(); i++) {
                int weight = adjMatrix.get(i).get(endVertexIndex);
                ButtonToggleVertex neighbor = vertexList.get(i).label;

                if (weight != 0 && !endVisited.contains(neighbor)) {
                    endQueue.offer(neighbor);
                    endVisited.add(neighbor);
                    endParent.put(neighbor, endVertex);

                    if (startVisited.contains(neighbor)) {
                        intersection = neighbor;
                        break;
                    }
                }
            }

            // Check if an intersection is found
            if (intersection != null) {
                break;
            }
        }

        // Check if a path exists between start and end vertices
        if (intersection == null) {
            return false; // No path exists
        }

        // Reconstruct the shortest path
        verticesInShortestpath.clear();

        // Reconstruct path from start to intersection
        ButtonToggleVertex vertex = intersection;
        while (vertex != null) {
            verticesInShortestpath.add(vertex);
            vertex = startParent.get(vertex);
        }

        // Reverse the path from end to intersection (excluding intersection)
        vertex = endParent.get(intersection);
        while (vertex != null) {
            verticesInShortestpath.add(0, vertex);
            vertex = endParent.get(vertex);
        }

        Collections.reverse(verticesInShortestpath);
        findShortestPathEdges();

        return true; // Path exists
    }

    // BellmanFord Shortest path algorithm
    public boolean bellmanFordShortestPath(ButtonToggleVertex start, ButtonToggleVertex end) {
        Map<ButtonToggleVertex, Integer> distances = new HashMap<>();
        Map<ButtonToggleVertex, ButtonToggleVertex> parent = new HashMap<>();

        // Step 1: Initialize distances and parent pointers
        for (Vertex vertex : vertexList) {
            distances.put(vertex.label, Integer.MAX_VALUE);
            parent.put(vertex.label, null);
        }

        distances.put(start, 0);

        // Step 2: Relax edgeList repeatedly
        for (int i = 0; i < vertexList.size() - 1; i++) {
            for (Vertex vertex : vertexList) {
                int vertexIndex = searchVertex(vertex.label);

                for (int j = 0; j < adjMatrix.get(vertexIndex).size(); j++) {
                    int weight = adjMatrix.get(vertexIndex).get(j);
                    ButtonToggleVertex neighbor = vertexList.get(j).label;

                    if (weight != 0 && distances.get(vertex.label) + weight < distances.get(neighbor)) {
                        distances.put(neighbor, distances.get(vertex.label) + weight);
                        parent.put(neighbor, vertex.label);
                    }
                }
            }
        }

        // Step 3: Check for negative cycles
        for (Vertex vertex : vertexList) {
            int vertexIndex = searchVertex(vertex.label);

            for (int i = 0; i < adjMatrix.get(vertexIndex).size(); i++) {
                int weight = adjMatrix.get(vertexIndex).get(i);
                ButtonToggleVertex neighbor = vertexList.get(i).label;

                if (weight != 0 && distances.get(vertex.label) + weight < distances.get(neighbor)) {
                    return false; // Negative cycle detected, no shortest path exists
                }
            }
        }

        if(distances.get(end) == Integer.MAX_VALUE) return false;

        // Step 4: Reconstruct the shortest path
        verticesInShortestpath.clear();
        ButtonToggleVertex vertex = end;

        while (vertex != null) {
            verticesInShortestpath.add(vertex);
            vertex = parent.get(vertex);
        }

        Collections.reverse(verticesInShortestpath);
        findShortestPathEdges();

        return true; // Shortest path found
    }

}
