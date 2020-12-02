package main.finals.grp2.lab;

import main.finals.grp2.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Graph {

    // ============================================ INNER CLASSES ============================================

    public class Vertex {
        protected String ID;
        protected PairList<Vertex, String> edges = new PairList<>();

        public Vertex(String ID) {
            this.ID = ID;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("ID = ").append(ID).append("\n");
            for (int i = 0; i < edges.size(); i++) {
                PairList.Node<Vertex, String> curr = edges.getAt(i);
                builder.append("\t EDGE = ").append(curr.key.ID).append(" WEIGHT = ").append(curr.val).append("\n");
            }
            return builder.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            Vertex vertex = (Vertex) o;
            return ID.equals(vertex.ID);
        }

    }

//    public class Edge {
//        protected TestVertex from;
//        protected TestVertex to;
//        protected int weight;
//
//        public Edge(int weight, TestVertex from, TestVertex to) {
//            if (from == null || to == null) {
//                throw (new NullPointerException("From and To vertices must be non-null"));
//            }
//
//            this.weight = weight;
//            this.from = from;
//            this.to = to;
//        }
//
//        private Edge(TestEdge e) {
//            this(e.weight, e.from, e.to);
//        }
//
//        public String toString() {
//            return "[ " + from.ID + "]" + " -> " +
//                    "[ " + to.ID + "]" + " = " + weight + "\n";
//        }
//    }

    // ============================================ DATA MEMBERS ============================================

    private List<Vertex> vertices;
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

    @SuppressWarnings("unchecked")
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
                addEdge(data[0], data[1], data[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEdge(String weight, String from, String to) {
        if (type == TYPE.UNDIRECTED)
            insertUndirected(weight, from, to);
        else if (type == TYPE.DIRECTED)
            insertDirected(weight, from, to);
    }

    private void insertDirected(String weight, String from, String to) {
        Vertex v = getVertex(from);
        if (v != null) { // if node exists just insert the destination
            v.edges.put(new Vertex(to), weight);
        } else { // if not then create a node then insert to graph
            Vertex nV = new Vertex(from); // new vertex
            nV.edges.put(new Vertex(to), weight);
            vertices.insert(nV);
        }
    }

    private void insertUndirected(String weight, String from, String to) {
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

    protected List<Vertex> getVertices() {
        return vertices;
    }

    protected boolean isDirected() {
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
     *  S.push(v)
     *      while S is not empty do
     *      v = S.pop()
     *      if v is not labeled as discovered then
     *          label v as discovered
     *          if v is the goal then
     *              return v
     *          for all edges from v to w in G.adjacentEdges(v) do
     *          S.push(w)
     *
     * @param start starting pos
     * @param end ending pos
     * @return pathway to ending pos
     */
    public Queue<String> depthFirstSearch(String start, String end) {
        Queue<String> path = new DoublyLinkedList<>();
        Stack<Vertex> stack = new DoublyLinkedList<>();
        Dictionary<Vertex, Boolean> visitedNodes = initVisitedNodes();

        stack.push(getVertex(start));
        while (!stack.isEmpty()) {
            Vertex v = stack.pop();
            if (!visitedNodes.getNode(v).val) {
                Dictionary.Node<Vertex, Boolean> currNode = visitedNodes.getNode(v);
                if (v.ID.equalsIgnoreCase(end)) return path;
                path.enqueue(v.ID);
                currNode.val = true;
                for (int i = 0; i < v.edges.size(); i++) {
                    path.enqueue(v.edges.getAt(i).key.ID);
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
     *  procedure BFS(G, root) is
     *      let Q be a queue
     *      label root as discovered
     *      Q.enqueue(root)
     *      while Q is not empty do
     *          v := Q.dequeue()
     *          if v is the goal then
     *              return v
     *          for all edges from v to w in G.adjacentEdges(v) do
     *              if w is not labeled as discovered then
     *                  label w as discovered
     *                  Q.enqueue(w)
     *
     * @param start starting pos
     * @param end ending pos
     * @return pathway to ending pos
     */
    public Queue<String> breadthFirstSearch(String start, String end) {
        Queue<String> path = new DoublyLinkedList<>();
        Queue<Vertex> stack = new DoublyLinkedList<>();
        Dictionary<Vertex, Boolean> visitedNodes = initVisitedNodes();

        stack.enqueue(getVertex(start));
        while (!stack.isEmpty()) {
            Vertex v = stack.dequeue();
            if (!visitedNodes.getNode(v).val) {
                Dictionary.Node<Vertex, Boolean> currNode = visitedNodes.getNode(v);
                if (v.ID.equalsIgnoreCase(end)) return path;
                path.enqueue(v.ID);
                currNode.val = true;
                for (int i = 0; i < v.edges.size(); i++) {
                    path.enqueue(v.edges.getAt(i).key.ID);
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
        Graph g = new Graph(new File("src/main/finals/grp2/lab/data/in.csv"));
        System.out.println(g.toString());
        Queue<String> depthPath = g.depthFirstSearch("0", "4");
        System.out.println("DEPTH FIRST SEARCH");
        while (!depthPath.isEmpty()) {
            System.out.println(depthPath.dequeue());
        }
        Queue<String> breadthPath = g.breadthFirstSearch("0", "4");
        System.out.println("BREADTH FIRST SEARCH");
        while (!breadthPath.isEmpty()) {
            System.out.println(breadthPath.dequeue());
        }
    }
}