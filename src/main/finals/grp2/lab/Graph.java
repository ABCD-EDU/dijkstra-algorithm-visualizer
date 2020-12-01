package main.finals.grp2.lab;

import main.finals.grp2.util.ArrayList;
import main.finals.grp2.util.List;

import java.io.*;

public class Graph {

    // ============================================ INNER CLASSES ============================================

    public class Vertex {
        protected String ID;
        protected List<Edge> edges = new ArrayList<>();

        public Vertex(String ID) {
            this.ID = ID;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("ID = ").append(ID).append("\n");
            for (int i = 0; i < edges.getSize(); i++)
                builder.append("\t").append(edges.getElement(i).toString());
            return builder.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            Vertex vertex = (Vertex) o;
            return ID.equals(vertex.ID);
        }

    }

    public class Edge {
        protected Vertex from;
        protected Vertex to;
        protected int weight;

        public Edge(int weight, Vertex from, Vertex to) {
            if (from == null || to == null) {
                throw (new NullPointerException("From and To vertices must be non-null"));
            }

            this.weight = weight;
            this.from = from;
            this.to = to;
        }

        private Edge(Edge e) {
            this(e.weight, e.from, e.to);
        }

        public String toString() {
            return "[ " + from.ID + "]" + " -> " +
                    "[ " + to.ID + "]" + " = " + weight + "\n";
        }
    }

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
        Vertex origin = getVertex(from);
        Vertex tail = getVertex(to);
        if (origin == null && tail == null) {
            if (from.equals(to)) { // if pointing at self
                Vertex head = new Vertex(from);
                head.edges.insert(new Edge(weight, head, head));
                vertices.insert(head);
                return;
            }
            Vertex head = new Vertex(from);
            Vertex end = new Vertex(to);
            head.edges.insert(new Edge(weight, head, end));
            vertices.insert(head);
            vertices.insert(end);
        }
        if (origin != null && tail != null) {
            origin.edges.insert(new Edge(weight, origin, tail));
        }
        if (origin != null && tail == null) {
            Vertex end = new Vertex(to);
            origin.edges.insert(new Edge(weight, origin, end));
            vertices.insert(end);
        }
        if (tail != null && origin == null) {
            Vertex head = new Vertex(from);
            head.edges.insert(new Edge(weight, head, tail));
            vertices.insert(head);
        }

    }

    private void insertUndirected(int weight, String from, String to) {
        Vertex origin = getVertex(from);
        Vertex tail = getVertex(to);
        if (origin == null && tail == null) {
            if (from.equals(to)) { // if pointing at self
                Vertex head = new Vertex(from);
                head.edges.insert(new Edge(weight, head, head));
                vertices.insert(head);
                return;
            }
            Vertex head = new Vertex(from);
            Vertex end = new Vertex(to);
            head.edges.insert(new Edge(weight, head, end));
            end.edges.insert(new Edge(weight, head, end));
            vertices.insert(head);
            vertices.insert(end);
            return;
        }
        if (origin != null && tail != null) {
            origin.edges.insert(new Edge(weight, origin, tail));
            tail.edges.insert(new Edge(weight, origin, tail));
        }
        if (origin != null && tail == null) {
            Vertex end = new Vertex(to);
            origin.edges.insert(new Edge(weight, origin, end));
            end.edges.insert(new Edge(weight, origin, end));
            vertices.insert(end);
        }
        if (tail != null && origin == null) {
            Vertex head = new Vertex(from);
            head.edges.insert(new Edge(weight, head, tail));
            tail.edges.insert(new Edge(weight, head, tail));
            vertices.insert(head);
        }
    }

    private Vertex getVertex(String ID) {
        final Vertex temp = new Vertex(ID);
        for (int i = 0; i < vertices.getSize(); i++) {
            final Vertex toReturn = vertices.getElement(i);
            if (temp.ID.equals(toReturn.ID))
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

    public static void main(String[] args) {
//        Graph<Integer> g = new Graph<>(TYPE.UNDIRECTED);
//        System.out.println("UNDIRECTED");
//        g.addEdge(0, 1, 0);
//        g.addEdge(0, 4, 0);
//        g.addEdge(1, 2, 0);
//        g.addEdge(1, 3, 0);
//        g.addEdge(1, 4, 0);
//        g.addEdge(2, 3, 0);
//        g.addEdge(3, 4, 0);
//        System.out.println(g.toString());
//
//        System.out.println("DIRECTED");
//        Graph<Integer> g2 = new Graph<>(TYPE.DIRECTED);
//        g2.addEdge(0, 1, 0);
//        g2.addEdge(0, 4, 0);
//        g2.addEdge(1, 2, 0);
//        g2.addEdge(1, 3, 0);
//        g2.addEdge(1, 4, 0);
//        g2.addEdge(2, 3, 0);
//        g2.addEdge(3, 4, 0);
//        System.out.println(g2.toString());
//
//        Graph<Integer> g3 = new Graph<>(TYPE.UNDIRECTED);
//        System.out.println("UNDIRECTED");
//        g3.addEdge(0, 0, 1);
//        g3.addEdge(0, 0, 2);
//        g3.addEdge(0, 1, 2);
//        g3.addEdge(0, 1, 3);
//        g3.addEdge(0, 1, 4);
//        g3.addEdge(0, 2, 3);
//        g3.addEdge(0, 2, 4);
//        g3.addEdge(0, 3, 1);
//        g3.addEdge(0, 3, 2);
//        g3.addEdge(0, 4, 5);
//        System.out.println(g.toString());

//        Graph<> g4 = new Graph(new File("D:\\git-repos\\9413-final-grp2\\src\\main\\finals\\grp2\\lab\\data\\in.csv"));
//        System.out.println(g4.toString());
    }
}
