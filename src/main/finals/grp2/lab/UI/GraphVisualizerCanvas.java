package main.finals.grp2.lab.UI;

import main.finals.grp2.lab.Graph;
import main.finals.grp2.lab.PairList;
import main.finals.grp2.util.ArrayList;
import main.finals.grp2.util.List;

import javax.swing.*;
import java.awt.*;

// TODO: INTEGRATE NEW GRAPH
public class GraphVisualizerCanvas extends Canvas {

    private Graph graph;
    private main.finals.grp2.util.ArrayList<Graph.Vertex> vertices;
//    private main.finals.grp2.util.List<Graph.Edge> edges;
    private PairList<Graph.Vertex, PairList.Node<Integer, Integer>> vertexCoordsPairList;
    private ArrayList<Graph.Vertex> path;

    private int vSize;
    private int rad = 200; // Graph Radius
    private PairList<Integer, Integer> vCoords;

    private final int VRAD = 30; // Vertex Radius

    private String algoLabel = "Algorithm";
    private String fromLabel = "From";
    private String toLabel = "To";

    public GraphVisualizerCanvas(Graph graph, Color bgColor) {
        this.setBackground(bgColor);
        setGraph(graph);
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        vertices = initializeVertices();
        vSize = vertices.getSize();
        vCoords = calculateVCoords();
        vertexCoordsPairList = initializeVertexCoordsPairList();
        path = new ArrayList<>();
        System.out.println("Graph initialized - visualizer");
        System.out.println(graph);
        this.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        System.out.println("Canvas Painting!");
        g.drawString(algoLabel + ": " + fromLabel + " - " + toLabel, 10, 10);
        g.translate(this.getWidth()/2,this.getHeight()/2); // center
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
        for (int i = 0; i < vertices.getSize(); i++) {
//            System.out.println("TO: " + vertices.getElement(i).edges.size());
            for (int j = 0; j <  vertices.getElement(i).edges.size(); j++) {
                PairList.Node<Integer, Integer> p1 = vertexCoordsPairList.get(vertices.getElement(i));
//                System.out.println(vertices.getElement(i).edges.getAt(j).key);
//                PairList.Node<Graph.Vertex, Integer> toFind = vertices.getElement(i).edges.getAt(j);
                PairList.Node<Integer, Integer> p2 = vertexCoordsPairList.get(vertices.getElement(i).edges.getAt(j).key);
                int cost = vertices.getElement(i).edges.getAt(j).val;
                g.setColor(Color.BLACK);
                g.drawLine(p1.key, p1.val, p2.key, p2.val);
            }

        }

        // paint path
        for (int i = 1; i < path.getSize(); i++) {
            PairList.Node<Integer, Integer> p1 = vertexCoordsPairList.get(path.getElement(i));
            PairList.Node<Integer, Integer> p2 = vertexCoordsPairList.get(path.getElement(i-1));
            g.setColor(Color.GREEN);
            g.drawLine(p1.key, p1.val, p2.key, p2.val);
        }
    }

    public void paintEdgesDirected(Graphics g) {
        //TODO: implement paintEdgesDirected()
        System.out.println("DIRECTED");
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

    private ArrayList<Graph.Vertex> initializeVertices() {
        ArrayList<Graph.Vertex> toReturn = new ArrayList<>();
        List<Graph.Vertex> v = graph.getVertices();
        for (int i = 0; i < v.getSize(); i++) {
            toReturn.insert(v.getElement(i));
        }
        return toReturn;
    }

    private PairList<Graph.Vertex, PairList.Node<Integer, Integer>> initializeVertexCoordsPairList() {
        PairList<Graph.Vertex, PairList.Node<Integer, Integer>> pl = new PairList<>();
        for (int i = 0; i < vertices.getSize(); i++) {
            pl.put(vertices.getElement(i), vCoords.getAt(i));
        }
        return pl;
    }

    public void setLabels(String algoLabel, String fromLabel, String toLabel) {
        this.algoLabel = algoLabel;
        this.fromLabel = fromLabel;
        this.toLabel = toLabel;
    }

    public void setPath(ArrayList<Graph.Vertex> path) {
        this.path = path;
        this.repaint();
    }


}
