package main.midlab2.group4.lab.activities.huffman;

import main.midlab2.group4.lab.activities.huffman.window.HuffmanWindow;

public class HuffmanExecutable {
    public static void main(String[] args) {
        try {
            new HuffmanWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
