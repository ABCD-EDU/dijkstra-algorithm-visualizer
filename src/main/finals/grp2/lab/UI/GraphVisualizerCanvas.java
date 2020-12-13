package main.finals.grp2.lab.UI;

import main.finals.grp2.lab.Graph;
import main.finals.grp2.lab.PairList;
import main.finals.grp2.util.ArrayList;
import main.finals.grp2.util.Dictionary;
import main.finals.grp2.util.List;

import javax.swing.*;
import java.awt.*;

// TODO: INTEGRATE NEW GRAPH
public class GraphVisualizerCanvas extends Canvas {

    private Graph graph;
    private main.finals.grp2.util.ArrayList<Graph.Vertex> vertices;
//    private main.finals.grp2.util.List<Graph.Edge> edges;
    private PairList<Graph.Vertex, PairList.Node<Integer, Integer>> vertexCoordsPairList;
    private ArrayList<Dictionary.Node<Graph.Vertex, Graph.Vertex>> path;

    private int vSize;
    private int rad = 235; // Graph Radius
    private PairList<Integer, Integer> vCoords;

    private final int VRAD = 35; // Vertex Radius

    private String algoLabel = "";
    private String fromLabel = "";
    private String toLabel = "";

    Color mainFg, vertexFg, vertexColor, edgeColor;

    public GraphVisualizerCanvas(Graph graph, Color bgColor, Color mainFg, Color vertexFg, Color vertexColor, Color edgeColor) {
        this.setBackground(bgColor);
        this.mainFg = mainFg;
        this.vertexFg = vertexFg;
        this.vertexColor = vertexColor;
        this.edgeColor = edgeColor;
        setGraph(graph);
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        vertices = initializeVertices();
        vSize = vertices.getSize();
        vCoords = calculateVCoords();
        vertexCoordsPairList = initializeVertexCoordsPairList();
        path = new ArrayList<>();
//        System.out.println("Graph initialized - visualizer");
//        System.out.println(graph);
        this.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        System.out.println("Canvas Painting");
        paintHeader(g);
        g.translate((this.getWidth()/2)-40,(this.getHeight()/2)-40); // center
//        g.drawOval(0,0, VRAD, VRAD);
//        g.setColor(Color.BLACK);
//        if (graph.isDirected()) {
//            paintEdgesDirected(g);
//        }else {
            paintEdgesUndirected(g);
//        }
        paintVertices(g);
    }

    private void paintHeader(Graphics g) {
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
        g.setColor(mainFg); // VISUALIZER PANEL FOREGROUND
        g.drawString(algoLabel,10,30);
        if (!fromLabel.equals(""))
            g.drawString("Start: " + fromLabel, 10, 65);
        if (!toLabel.equals(""))
            g.drawString("End: " + toLabel, 10, 100);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
    }

    public void paintVertices(Graphics g) {
//        g.translate(-VRAD/2, -VRAD/2);
        for (int i = 0; i < vSize; i++) {
            PairList.Node<Integer, Integer> coord = vCoords.getAt(i);
            g.setColor(vertexColor);    // VERTEX COLOR
            g.translate(-VRAD/2, -VRAD/2);
            g.fillOval(coord.key, coord.val, VRAD, VRAD);
            g.setColor(vertexFg);    // VERTEX FOREGROUND COLOR
            g.translate(VRAD/2,VRAD/2);
            g.drawString(Integer.toString(i), coord.key, coord.val);
        }
        if (path.getSize() != 0) {
            g.translate(-VRAD/2, -VRAD/2);
            PairList.Node<Integer, Integer> head = vertexCoordsPairList.get(path.getElement(path.getSize()-1).val);
            g.setColor(Color.RED);
            g.drawOval(head.key, head.val, VRAD, VRAD);
            g.translate(VRAD/2,VRAD/2);

        }
    }

    public void paintEdgesUndirected(Graphics g) {
        g.translate(30,30);
        for (int i = 0; i < vertices.getSize(); i++) {
            for (int j = 0; j <  vertices.getElement(i).edges.size(); j++) {
                PairList.Node<Integer, Integer> p1 = vertexCoordsPairList.get(vertices.getElement(i));
                PairList.Node<Integer, Integer> p2 = vertexCoordsPairList.get(vertices.getElement(i).edges.getAt(j).key);
                if (vertices.getElement(i).equals(vertices.getElement(i).edges.getAt(j).key)) {
                    g.drawOval(p1.key, p1.val, VRAD, VRAD);
                    continue;
                }
                int cost = vertices.getElement(i).edges.getAt(j).val;
                g.setColor(edgeColor); //EDGE COLOR
                g.drawLine(p1.key, p1.val, p2.key, p2.val);
            }
        }

        for (int i = 0; i < path.getSize(); i++) {
            PairList.Node<Integer, Integer> p1 = vertexCoordsPairList.get(path.getElement(i).key);
            PairList.Node<Integer, Integer> p2 = vertexCoordsPairList.get(path.getElement(i).val);
            g.setColor(Color.GREEN);
            if (i == path.getSize()-1)
                g.setColor(Color.RED);
            if (p1.equals(p2)) {
                g.drawOval(p1.key, p1.val, VRAD, VRAD);
                continue;
            }
            g.drawLine(p1.key, p1.val, p2.key, p2.val);
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

    public void setPath(ArrayList<Dictionary.Node<Graph.Vertex, Graph.Vertex>> path) {
        this.path = path;
        this.repaint();
    }
}