package main.finals.grp2.lab;

import main.finals.grp2.util.ArrayList;
import main.finals.grp2.util.List;

import java.io.*;

public class Graph<E extends Comparable<E>> {

    public class Vertex<T> {
        protected T value;
        protected List<Edge<T>> edges = new ArrayList<>();

        public Vertex(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Value=").append(value).append("\n");
            for (int i = 0; i < edges.getSize(); i++)
                builder.append("\t").append(edges.getElement(i).toString());
            return builder.toString();
        }
    }

    public class Edge<T> {
        protected Vertex<T> from;
        protected Vertex<T> to;
        protected int cost;

        public Edge(int cost, Vertex<T> from, Vertex<T> to) {
            if (from == null || to == null) {
                throw (new NullPointerException("From and To vertices must be non-null"));
            }

            this.cost = cost;
            this.from = from;
            this.to = to;
        }

        private Edge(Edge<T> e) {
            this(e.cost, e.from, e.to);
        }

        public String toString() {
            return "[ " + from.value + "]" + " -> " +
                    "[ " + to.value + "]" + " = " + cost + "\n";
        }
    }

    private List<Vertex<E>> vertices;

    public enum TYPE {
        DIRECTED,
        UNDIRECTED
    }

    private TYPE type;

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
                addEdge(Integer.parseInt(data[0]), (E) data[1], (E) data[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEdge(int weight, E from, E to) {
        if (type == TYPE.UNDIRECTED)
            insertUndirected(weight, from, to);
        else if (type == TYPE.DIRECTED)
            insertDirected(weight, from, to);
    }

    private void insertDirected(int weight, E from, E to) {
        Vertex<E> origin = getVertex(from);
        if (origin != null) {
            origin.edges.insert(new Edge<>(weight, origin, new Vertex<>(to)));
        } else {
            Vertex<E> start = new Vertex<>(from);
            Vertex<E> end = new Vertex<>(to);
            start.edges.insert(new Edge<>(weight, start, end));
            vertices.insert(start);
        }
    }

    private void insertUndirected(int weight, E from, E to) {
        Vertex<E> head = getVertex(from);
        if (head != null) {
            Vertex<E> tail = getVertex(to);
            if (tail != null) {
                head.edges.insert(new Edge<>(weight, head, tail));
                tail.edges.insert(new Edge<>(weight, tail, head));
            } else {
                head.edges.insert(new Edge<>(weight, head, new Vertex<>(to)));
            }
        } else {
            Vertex<E> temp = new Vertex<>(from);
            Edge<E> temp2 = new Edge<>(weight, new Vertex<>(from), new Vertex<>(to));
            temp.edges.insert(temp2);
            vertices.insert(temp);
        }
    }

    private Vertex<E> getVertex(E item) {
        final Vertex<E> temp = new Vertex<>(item);
        for (int i = 0; i < vertices.getSize(); i++) {
            final Vertex<E> toReturn = vertices.getElement(i);
            if (temp.value.equals(toReturn.value))
                return toReturn;
        }
        return null;
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
        Graph<Integer> g = new Graph<>(TYPE.UNDIRECTED);
        System.out.println("UNDIRECTED");
        g.addEdge(0, 1, 0);
        g.addEdge(0, 4, 0);
        g.addEdge(1, 2, 0);
        g.addEdge(1, 3, 0);
        g.addEdge(1, 4, 0);
        g.addEdge(2, 3, 0);
        g.addEdge(3, 4, 0);
        System.out.println(g.toString());

        System.out.println("DIRECTED");
        Graph<Integer> g2 = new Graph<>(TYPE.DIRECTED);
        g2.addEdge(0, 1, 0);
        g2.addEdge(0, 4, 0);
        g2.addEdge(1, 2, 0);
        g2.addEdge(1, 3, 0);
        g2.addEdge(1, 4, 0);
        g2.addEdge(2, 3, 0);
        g2.addEdge(3, 4, 0);
        System.out.println(g2.toString());

        Graph<Integer> g3 = new Graph<>(TYPE.UNDIRECTED);
        System.out.println("UNDIRECTED");
        g3.addEdge(0, 0, 1);
        g3.addEdge(0, 0, 2);
        g3.addEdge(0, 1, 2);
        g3.addEdge(0, 1, 3);
        g3.addEdge(0, 1, 4);
        g3.addEdge(0, 2, 3);
        g3.addEdge(0, 2, 4);
        g3.addEdge(0, 3, 1);
        g3.addEdge(0, 3, 2);
        g3.addEdge(0, 4, 5);
        System.out.println(g.toString());

        Graph<Integer> g4 = new Graph<>(new File("D:\\git-repos\\9413-final-grp2\\src\\main\\finals\\grp2\\lab\\data\\in.csv"));
        System.out.println(g4.toString());
    }
}
