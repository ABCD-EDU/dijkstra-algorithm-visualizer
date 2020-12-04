package main.finals.grp2.lab.UI;

import main.finals.grp2.lab.Graph;
import main.finals.grp2.lab.PairList;

import javax.swing.*;
import java.awt.*;

// TODO: INTEGRATE NEW GRAPH
public class GraphVisualizerWindow extends JPanel {

    private JFrame frame = new JFrame("Graph Visualizer");
    private GraphCanvas canvas = new GraphCanvas();

    private Graph graph;
    private main.finals.grp2.util.List<Graph.Vertex> vertices;
//    private main.finals.grp2.util.List<Graph.Edge> edges;
    private PairList<Graph.Vertex, PairList.Node<Integer, Integer>> vertexCoordsPairList;

    private int vSize;
    private int rad = 200; // Graph Radius
    private PairList<Integer, Integer> vCoords;

    private final int VRAD = 30; // Vertex Radius

    public GraphVisualizerWindow(Graph graph) {

        this.graph = graph;
        vertices = this.graph.getVertices();
        vSize = vertices.getSize();
        vCoords = calculateVCoords();
//        edges = initializeEdges();
        vertexCoordsPairList = initializeVertexCoordsPairList();

//        System.out.println(edges);
        System.out.println(        );
        System.out.println(graph);
//        System.out.println(vertices);
        this.add(canvas);
//        frame.add(canvas);
//        frame.setSize(1392,769);
//        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setLocation((d.width / 2) - frame.getWidth() / 2, (d.height / 2) - frame.getHeight() / 2);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
//        frame.setResizable(true);
//        frame.setVisible(true);

//        frame.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                System.out.println(frame.getSize());
//            }
//        });

    }

    private class GraphCanvas extends Canvas {

        @Override
        public void paint(Graphics g) {
            g.translate(frame.getWidth()/2,frame.getHeight()/2); // center
            g.drawOval(0,0, VRAD, VRAD);
            paintVertices(g);
            g.setColor(Color.BLACK);
            g.translate(VRAD/2,VRAD/2);
            if (graph.isDirected()) {
                paintEdgesDirected(g);
            }else {
                paintEdgesUndirected(g);
            }
        }

        public void paintVertices(Graphics g) {
            for (int i = 0; i < vSize; i++) {
                PairList.Node<Integer, Integer> coord = vCoords.getAt(i);
                g.setColor(Color.BLACK);
                g.fillOval(coord.key, coord.val, VRAD, VRAD);
                g.setColor(Color.PINK);
                g.translate(VRAD/2,VRAD/2);
                g.drawString(Integer.toString(i), coord.key, coord.val);
                g.translate(-VRAD/2, -VRAD/2);
            }
        }

        public void paintEdgesUndirected(Graphics g) {
//            for (int i = 0; i < edges.getSize(); i++) {
//                PairList.Node<Integer, Integer> p1 = vertexCoordsPairList.get(edges.getElement(i).from);
//                System.out.println("FROM: " + edges.getElement(i).from);
//                PairList.Node<Integer, Integer> p2 = vertexCoordsPairList.get(edges.getElement(i).to);
//                System.out.println("TO: " + edges.getElement(i).from);
//                g.drawLine(p1.x, p1.y, p2.x, p2.y);
//            }
        }

        public void paintEdgesDirected(Graphics g) {
            //TODO: implement paintEdgesDirected()
            System.out.println("DIRECTED");
        }

    }

    private PairList<Integer, Integer> calculateVCoords() {
        PairList<Integer, Integer> coords = new PairList<>();
        double d = Math.toRadians(360.f/vSize);
        double incD = d;
        coords.put(0, rad);
        if (vSize == 1) return coords;
        PairList.Node<Integer, Integer> p = coords.getAt(0);
        for (int i = 0; i < vSize-1; i++) {
            int x1 = (int)(p.key *Math.cos(d) + p.val *Math.sin(d));
            int y1 = (int)(-p.key *Math.sin(d) + p.val *Math.cos(d));
            d = d + incD;
            coords.put(x1,y1); // big brain moment
        }
        return coords;
    }

//    private ArrayList<Graph.Edge> initializeEdges() {
//        ArrayList<Graph.Edge> edges = new ArrayList<>();
//        for(int i = 0; i < vertices.getSize(); i++) {
//            for (int j = 0; j < vertices.getElement(i).edges.getSize(); j++) {
//                edges.insert((Graph.Edge) vertices.getElement(i).edges.getElement(j));
//            }
//        }
//        return edges;
//    }

    private PairList<Graph.Vertex, PairList.Node<Integer, Integer>> initializeVertexCoordsPairList() {
        PairList<Graph.Vertex, PairList.Node<Integer, Integer>> pl = new PairList<>();
        for (int i = 0; i < vertices.getSize(); i++) {
            pl.put(vertices.getElement(i), vCoords.getAt(i));
        }
        return pl;
    }

}
