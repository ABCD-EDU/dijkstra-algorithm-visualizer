package main.finals.grp2.util;

public class Graph<E extends Comparable<E>> {
    public class Vertex<E> {
        protected E value = null;
        protected int weight = 0;
        protected List<Edge<E>> edges = new ArrayList<>();

        public Vertex(E value) {
            this.value = value;
        }

        public Vertex(E value, int weight) {
            this(value);
            this.weight = weight;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Value=").append(value).append(" weight=").append(weight).append("\n");
            for (int i = 0; i < edges.getSize(); i++)
                builder.append("\t").append(edges.getElement(i).toString());
            return builder.toString();
        }
    }

    public class Edge<E> {
        protected Vertex<E> from = null;
        protected Vertex<E> to = null;
        protected int cost = 0;

        public Edge(int cost, Vertex<E> from, Vertex<E> to) {
            if (from == null || to == null) {
                throw (new NullPointerException("From and To vertices must be non-null"));
            }

            this.cost = cost;
            this.from = from;
            this.to = to;
        }

        private Edge(Edge<E> e) {
            this(e.cost, e.from, e.to);
        }

        public String toString() {
            return "[ " + from.value + "(" + from.weight + ") " + "]" + " -> " +
                    "[ " + to.value + "(" + to.weight + ") " + "]" + " = " + cost + "\n";
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

    public void addEdge(E from, E to, int weight) {
        if (type == TYPE.UNDIRECTED) {
            insertUndirected(weight, from, to);
        } else if (type ==  TYPE.DIRECTED) {
            insertDirected(weight, from, to);
        }
    }

    private void insertDirected(int weight, E from, E to) {
        Vertex<E> origin = getVertex(from);
        if (origin != null) {
            origin.edges.insert(new Edge<>(weight, origin, new Vertex<>(to)));
        }else {
            Vertex<E> start = new Vertex<>(from);
            Vertex<E> end = new Vertex<>(to);
            start.edges.insert(new Edge<>(weight, start, end));
            vertices.insert(start);
        }
    }

    private void insertUndirected(int weight, E from, E to) {;
        Vertex<E> head = getVertex(from);
        Vertex<E> tail = getVertex(to);

        if (head != null)
            head.edges.insert(new Edge<>(weight, head, new Vertex<E>(to)));
        if (tail != null)
            tail.edges.insert(new Edge<>(weight, new Vertex<E>(from), tail));

        if (head == null || tail == null) {
            Vertex<E> start = new Vertex<>(from);
            Vertex<E> end = new Vertex<>(to);

            if (head == null) {
                vertices.insert(start);
                start.edges.insert(new Edge<>(weight, start, end));
            }
            if (tail == null) {
                end.edges.insert(new Edge<>(weight, end, start));
                vertices.insert(end);
            }
        }
    }

    // TODO: MIGHT DELTE THIS
    private boolean contains(E item) {
        final Vertex<E> temp = new Vertex<>(item);
        for (int i = 0; i < vertices.getSize(); i++) {
            if (vertices.getElement(i).value == temp.value)
                return true;
        }

        return false;
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

        for (int i = 0; i < g.vertices.getSize(); i++) {
            System.out.println(g.vertices.getElement(i).value);
        }
    }
}
