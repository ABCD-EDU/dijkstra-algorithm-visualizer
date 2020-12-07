package main.finals.grp2.lab;

import main.finals.grp2.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Graph {

    // ============================================ INNER CLASSES ============================================

    public static class Vertex {
        public String ID;
        public PairList<Vertex, Integer> edges = new PairList<>();

        public Vertex(String ID) {
            this.ID = ID;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("ID = ").append(ID).append("\n");
            for (int i = 0; i < edges.size(); i++) {
                PairList.Node<Vertex, Integer> curr = edges.getAt(i);
                builder.append("\t EDGE = ").append(curr.key.ID).append(" WEIGHT = ").append(curr.val).append("\n");
            }
            return builder.toString();
        }

        @Override
        public boolean equals(Object o) {

            return ((Vertex) o).ID.equalsIgnoreCase(this.ID);
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
     * @param end   ending pos
     * @return pathway to ending pos
     */
    public Queue<Vertex> depthFirstSearch(String start, String end) {
        Queue<Vertex> path = new DoublyLinkedList<>();
        Stack<Vertex> stack = new DoublyLinkedList<>();
        Dictionary<Vertex, Boolean> visitedNodes = initVisitedNodes();

        stack.push(getVertex(start));
        while (!stack.isEmpty()) {
            Vertex v = stack.pop();
            if (!visitedNodes.getNode(v).val) {
                Dictionary.Node<Vertex, Boolean> currNode = visitedNodes.getNode(v);
                if (v.ID.equalsIgnoreCase(end)) return path;
                path.enqueue(v);
                currNode.val = true;
                for (int i = 0; i < v.edges.size(); i++) {
                    path.enqueue(v.edges.getAt(i).key);
                    if (v.edges.getAt(i).key.ID.equalsIgnoreCase(end))
                        return path;
                    else
                        stack.push(getVertex(v.edges.getAt(i).key.ID));

                }
            }
        }
        throw new NoSuchElementException("path not found");
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
     * @param end   ending pos
     * @return pathway to ending pos
     */
    public Queue<Vertex> breadthFirstSearch(String start, String end) {
        Queue<Vertex> path = new DoublyLinkedList<>();
        Queue<Vertex> stack = new DoublyLinkedList<>();
        Dictionary<Vertex, Boolean> visitedNodes = initVisitedNodes();

        stack.enqueue(getVertex(start));
        while (!stack.isEmpty()) {
            Vertex v = stack.dequeue();
            if (!visitedNodes.getNode(v).val) {
                Dictionary.Node<Vertex, Boolean> currNode = visitedNodes.getNode(v);
                if (v.ID.equalsIgnoreCase(end)) return path;
                path.enqueue(v);
                currNode.val = true;
                for (int i = 0; i < v.edges.size(); i++) {
                    path.enqueue(v.edges.getAt(i).key);
                    if (v.edges.getAt(i).key.ID.equalsIgnoreCase(end))
                        return path;
                    else
                        stack.enqueue(getVertex(v.edges.getAt(i).key.ID));

                }
            }
        }
        throw new NoSuchElementException("path not found");
    }

    private Dictionary<Vertex, Boolean> initVisitedNodes() {
        Dictionary<Vertex, Boolean> vN = new Dictionary<>();
        for (int i = 0; i < vertices.getSize(); i++) {
            vN.put(vertices.getElement(i), false);
        }
        return vN;
    }

    public static void main(String[] args) {
//        Graph g = new Graph(new File("src/main/finals/grp2/lab/data/in.csv"));
//        System.out.println(g.toString());
//        Queue<String> depthPath = g.depthFirstSearch("0", "4");
//        System.out.println("DEPTH FIRST SEARCH");
//        while (!depthPath.isEmpty()) {
//            System.out.println(depthPath.dequeue());
//        }
//        Queue<String> breadthPath = g.breadthFirstSearch("0", "4");
//        System.out.println("BREADTH FIRST SEARCH");
//        while (!breadthPath.isEmpty()) {
//            System.out.println(breadthPath.dequeue());
//        }
    }
}
