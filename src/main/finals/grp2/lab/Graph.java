package main.finals.grp2.lab;

import main.finals.grp2.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

public class Graph {

    // ============================================ INNER CLASSES ============================================

    public static class Vertex implements Comparable<Vertex> {
        public String ID;
        public PairList<Vertex, Integer> edges = new PairList<>();


//        private boolean visited;
//        private Vertex prevVertex;
        private double minDistance = Double.POSITIVE_INFINITY;
        private Vertex parent;

        public Vertex(String ID) {
            this.ID = ID;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("ID = ").append(ID).append("");
            // UNCOMMENT THIS IF YOU WANT TO PRINT THE EDGES
//            for (int i = 0; i < edges.size(); i++) {
//                PairList.Node<Vertex, Integer> curr = edges.getAt(i);
//                builder.append("\t EDGE = ").append(curr.key.ID).append(" WEIGHT = ").append(curr.val).append("\n");
//            }
            return builder.toString();
        }

        @Override
        public boolean equals(Object o) {
            return ((Vertex) o).ID.equalsIgnoreCase(this.ID);
        }

        public int compareTo(Vertex otherVertex) {
            return Double.compare(this.minDistance, otherVertex.minDistance);
        }
    }

    // ============================================ DATA MEMBERS ============================================

    private final List<Vertex> vertices;
    private TYPE type;

    public enum TYPE {
        DIRECTED,
        UNDIRECTED
    }

    public Graph(TYPE type) {
        this.type = type;
        vertices = new ArrayList<>();
    }

    public Graph() {
        this(TYPE.UNDIRECTED);
    }

    public Graph(File file) {
        vertices = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line = in.readLine();
            if (line.equalsIgnoreCase("DIRECTED"))
                type = TYPE.DIRECTED;
            if (line.equalsIgnoreCase("UNDIRECTED"))
                type = TYPE.UNDIRECTED;

            while (true) {
                line = in.readLine();
                if (line == null)
                    break;
                String[] data = line.split(",");
                addEdge(Integer.parseInt(data[0]), data[1], data[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEdge(int weight, String from, String to) {
        if (type == TYPE.UNDIRECTED)
            insertUndirected(weight, from, to);
        else if (type == TYPE.DIRECTED)
            insertDirected(weight, from, to);
    }

    private void insertDirected(int weight, String from, String to) {
        Vertex v = getVertex(from);
        if (v != null) { // if node exists just insert the destination
            v.edges.put(new Vertex(to), weight);
        } else { // if not then create a node then insert to graph
            Vertex nV = new Vertex(from); // new vertex
            nV.edges.put(new Vertex(to), weight);
            vertices.insert(nV);
        }
    }

    private void insertUndirected(int weight, String from, String to) {
        Vertex start = getVertex(from);
        Vertex end = getVertex(to);
        Vertex nS = new Vertex(from);
        Vertex nE = new Vertex(to);

        if (start != null) { // if starting exists insert the edge to it
            start.edges.put(new Vertex(to), weight);
            if (end != null) { // if end exists insert the edge to it
                end.edges.put(new Vertex(from), weight);
            } else { // if it does not, create a node then insert to graph
                nE.edges.put(new Vertex(from), weight);
                vertices.insert(nE);
            }
        } else if (end != null) { // if end != null and start == null then create end then insert starting node to graph
            end.edges.put(new Vertex(from), weight);
            nS.edges.put(new Vertex(to), weight);
            vertices.insert(nS);
        } else {
            if (from.equalsIgnoreCase(to)) { // checks if it points to self
                Vertex self = new Vertex(from);
                self.edges.put(new Vertex(to), weight);
                vertices.insert(self);
            } else { // from node doesn't exist & to node doesn't exist so create both nodes and insert to graph
                Vertex f = new Vertex(from);
                f.edges.put(new Vertex(to), weight);
                Vertex t = new Vertex(to);
                t.edges.put(new Vertex(from), weight);
                vertices.insert(f);
                vertices.insert(t);
            }
        }
    }

    private Vertex getVertex(String ID) {
        for (int i = 0; i < vertices.getSize(); i++) {
            final Vertex toReturn = vertices.getElement(i);
            if (ID.equals(toReturn.ID))
                return toReturn;
        }
        return null;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public boolean isDirected() {
        return type == TYPE.DIRECTED;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < vertices.getSize(); i++) {
            builder.append(vertices.getElement(i));
        }
        return builder.toString();
    }

    /**
     * procedure DFS_iterative(G, v) is
     * let S be a stack
     * S.push(v)
     * while S is not empty do
     * v = S.pop()
     * if v is not labeled as discovered then
     * label v as discovered
     * if v is the goal then
     * return v
     * for all edges from v to w in G.adjacentEdges(v) do
     * S.push(w)
     *
     * @param start starting pos
     * @return pathway to ending pos
     */
    public Queue<Dictionary.Node<Graph.Vertex, Graph.Vertex>> depthFirstSearch(String start) {
        Queue<Dictionary.Node<Graph.Vertex, Graph.Vertex>> path = new DoublyLinkedList<>();
        Stack<Vertex> stack = new DoublyLinkedList<>();
        Dictionary<Vertex, Boolean> visitedNodes = initVisitedNodes();

        stack.push(getVertex(start));
        while (!stack.isEmpty()) {
            Vertex v = stack.pop();
            if (!visitedNodes.getNode(v).val) {
                Dictionary.Node<Vertex, Boolean> currNode = visitedNodes.getNode(v);
                if (!path.isEmpty())
                    path.enqueue(new Dictionary.Node<>(getVertex(path.peek().key.ID), getVertex(v.ID)));
                else
                    path.enqueue(new Dictionary.Node<>(getVertex(v.ID),getVertex(v.ID)));
                currNode.val = true;
                for (int i = 0; i < v.edges.size(); i++) {
                    path.enqueue(new Dictionary.Node<>(getVertex(v.ID),getVertex(v.edges.getAt(i).key.ID)));
                    stack.push(getVertex(v.edges.getAt(i).key.ID));

                }
            }
        }
//        throw new NoSuchElementException("path not found");
        return path;
    }

    /**
     * procedure BFS(G, root) is
     * let Q be a queue
     * label root as discovered
     * Q.enqueue(root)
     * while Q is not empty do
     * v := Q.dequeue()
     * if v is the goal then
     * return v
     * for all edges from v to w in G.adjacentEdges(v) do
     * if w is not labeled as discovered then
     * label w as discovered
     * Q.enqueue(w)
     *
     * @param start starting pos
     * @return pathway to ending pos
     */
    public Queue<Dictionary.Node<Vertex,Vertex>> breadthFirstSearch(String start) {
        Queue<Dictionary.Node<Vertex,Vertex>> path = new DoublyLinkedList<>();
        Queue<Vertex> stack = new DoublyLinkedList<>();
        Dictionary<Vertex, Boolean> visitedNodes = initVisitedNodes();

        stack.enqueue(getVertex(start));
        while (!stack.isEmpty()) {
            Vertex v = stack.dequeue();
            if (!visitedNodes.getNode(v).val) {
                Dictionary.Node<Vertex, Boolean> currNode = visitedNodes.getNode(v);
                if (!path.isEmpty())
                    path.enqueue(new Dictionary.Node<>(getVertex(path.peek().key.ID), getVertex(v.ID)));
                else
                    path.enqueue(new Dictionary.Node<>(getVertex(v.ID),getVertex(v.ID)));
                currNode.val = true;
                for (int i = 0; i < v.edges.size(); i++) {
                    path.enqueue(new Dictionary.Node<>(getVertex(v.ID),getVertex(v.edges.getAt(i).key.ID)));
                    stack.enqueue(getVertex(v.edges.getAt(i).key.ID));
                }
            }
        }
        return path;
    }

    private Dictionary<Vertex, Boolean> initVisitedNodes() {
        Dictionary<Vertex, Boolean> vN = new Dictionary<>();
        for (int i = 0; i < vertices.getSize(); i++) {
            vN.put(vertices.getElement(i), false);
        }
        return vN;
    }

    public PairList<String[], Queue<PairList.Node<Vertex, Vertex>>> dijkstra(String start){
        Queue<Dictionary.Node<Vertex,Vertex>> path = new DoublyLinkedList<>();

        Vertex startVertex = getVertex(start);
        startVertex.minDistance = 0f;
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();

        priorityQueue.enqueue(startVertex);
        while (!priorityQueue.isEmpty()){

            Vertex parentVertex = priorityQueue.dequeue();
            for (int i=0;i<parentVertex.edges.size();i++){

                parentVertex.edges.set(i, new PairList.Node<Vertex, Integer>
                        (getVertex(parentVertex.edges.getAt(i).key.ID), parentVertex.edges.getAt(i).val));
                Vertex childVertex = parentVertex.edges.getAt(i).key;
                double weight = parentVertex.edges.getAt(i).val;
                double distanceFromParent = parentVertex.minDistance+weight;
                path.enqueue(new Dictionary.Node<>(getVertex(parentVertex.ID),getVertex(childVertex.ID)));
                if (childVertex.minDistance>distanceFromParent){
                    priorityQueue.remove(parentVertex);
                    relax(parentVertex, childVertex, distanceFromParent);
                    priorityQueue.enqueue(childVertex);
                }
            }
        }

        return getEndWeightPath(this);
    }



    public void relax(Vertex u, Vertex v, Double weight){
        v.minDistance = weight;
        v.parent = u;
    }

    public static List<Vertex> getShortestPathFrom(Vertex source){
        List<Vertex> path = new ArrayList<Vertex>();
        for(Vertex node = source; node!=null; node = node.parent){
            path.insert(node);
        }
        path.reverse();
        return path;
    }


    /*
    Returns: PairList<String[], Queue<PairList.Node<Vertex, Vertex>>>
    Where:
    - Each index of PairList corresponds to 1 path
    - String[0]: end vertex, String[1]: cost of path, String[2]: path in string format
    - Queue<PairList.Node<Vertex, Vertex>>:
        edges visited by path where first Vertex = from and second Vertex = to
     */
    public PairList<String[], Queue<PairList.Node<Vertex, Vertex>>> getEndWeightPath(Graph g){
        PairList<String[], Queue<PairList.Node<Vertex, Vertex>>> toReturn = new PairList<>();
        int vertexCount = g.vertices.getSize();

        for (int i = 0; i < vertexCount; i++) { // for every vertex
            List<Vertex> path = getShortestPathFrom(g.vertices.getElement(i));
            String[] tableRow = new String[3];
            String pathString = "";
            tableRow[0] = vertices.getElement(i).ID;
            tableRow[1] = String.valueOf(vertices.getElement(i).minDistance);
            for (int j=0;j<path.getSize();j++) pathString += path.getElement(j)+"->";
            tableRow[2] = pathString.substring(0,pathString.length()-2);
            toReturn.put(tableRow, pathListToQueue(path));
        }

        return toReturn;
   }

   public Queue<PairList.Node<Vertex, Vertex>> pathListToQueue(List<Vertex> vertex) {
       Queue<PairList.Node<Vertex, Vertex>> path = new DoublyLinkedList<>();
       for (int i = 1; i < vertex.getSize(); i++)
           path.enqueue(new PairList.Node<>(vertex.getElement(i-1),vertex.getElement(i)));
       return path;
   }

    public static void main(String[] args) {
        Graph g = new Graph(new File("src/main/finals/grp2/lab/data/in.csv"));

        PairList<String[], Queue<PairList.Node<Vertex, Vertex>>> data = g.dijkstra("0");

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < 3; j++)
                System.out.println(data.getAt(i).key[j]);
            System.out.println(data.getAt(i).val);
        }

    }

    // for debugging only:

    private static void print2DArr(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i][0]);
            for (int j = 1; j < arr[i].length; j++) {
                System.out.print(" " + arr[i][j]);
            }
            System.out.println();
        }
    }

    private static void print2DArrSpecial(String[][] arr) {
        for (int i = 0; i < arr[0].length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.println(arr[j][i] + " ");
            }
            System.out.println();
        }
    }

}
