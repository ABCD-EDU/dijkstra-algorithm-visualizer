package main.finals.grp2.lab;

import java.io.File;

public class GraphExecutable {

    public static void main(String[] args) {
        try {
            run();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void run(){
        Graph graph = new Graph(new File("src/main/finals/grp2/lab/data/in.csv"));
        new GraphVisualizerWindow(graph);
    }

}
