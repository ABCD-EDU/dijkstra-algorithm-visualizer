package main.midlab2.group4.lab.activities.huffman;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HuffmanDemo {
    public static void main(String[] args) {
        String text = "this sentence contains one hundred and ninety-seven letters: " +
                "four a's, one b, three c's, five d's, thirty-four e's, " +
                "seven f's, one g, six h's, twelve i's, three l's, twenty-six n's, " +
                "ten o's, ten r's, twenty-nine s's, nineteen t's, six u's, seven v's, " +
                "four w's, four x's, five y's, and one z.";
        Huffman h = new Huffman();
        HuffmanCodec t = new HuffmanCodec(h);

        Path file = Paths.get("D:\\git-repos\\9413-midterm-grp4\\src\\main\\midlab2\\group4\\lab\\activities\\huffman\\HuffmanDemo.txt");
        h.setText("MISSISSIPPI RIVER");
        h.generateTree();

        System.out.println("=============================================================");
        System.out.println("Frequency Values");
        System.out.println(h.toString());

        t.buildHuffmanCode();

        System.out.println(t.toString());

        String encoded = t.encode();
        String decoded = t.decode();
        System.out.println("=============================================================");
        System.out.print("Encoded Text: ");
        System.out.println(encoded);
        System.out.print("Original Text: ");
        System.out.println(h.getText());
        System.out.print("Decoded Text:  ");
        System.out.println(decoded);

        // ---- OUTPUT VALUES COMPUTATION ----
        float origSize = h.getText().length() * 7;
        float compressedSize = encoded.length();
        float compressionRate = ((compressedSize - origSize) / origSize) * 100;

        System.out.println("\nIs it the same as the original: " + (h.getText().equals(decoded)));
        System.out.println("\nOriginal Size:    " + (int) origSize + " bits");
        System.out.println("Compressed Size:  " + (int) compressedSize + " bits");
        System.out.println("Compression Rate: " + -compressionRate + "%");
        System.out.println("=============================================================");
    }
}
