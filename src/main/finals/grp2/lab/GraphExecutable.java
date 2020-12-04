package main.finals.grp2.lab;

import main.finals.grp2.lab.UI.GraphWindow;

public class GraphExecutable {

    public static void main(String[] args) {
        try {
            run();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void run(){
        new GraphWindow();
    }

}
