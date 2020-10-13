package lab.activities;

import java.util.EmptyStackException;

public class Tester {
    public static void main(String[] args) {
        Tester tester;
        try {
            tester = new Tester();
            tester.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        EvaluatorWindow window = new EvaluatorWindow();
    }
}
