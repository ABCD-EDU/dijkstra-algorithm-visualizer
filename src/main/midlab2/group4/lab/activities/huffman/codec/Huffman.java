package main.midlab2.group4.lab.activities.huffman.codec;

import main.midlab2.group4.lab.util.MinPriorityQueue;
import main.midlab2.group4.lab.util.Queue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Huffman {
    private Dictionary<Character, Integer> pairCharWeight;
    private Queue<TreeNode> nodeQueue;
    private TreeNode root;
    private String text;

    public Huffman() {
        text = "";
        pairCharWeight = new Dictionary<>();
        nodeQueue = new MinPriorityQueue<>();
    }

    public void generateTree() {
        if (text.isBlank()) throw new NullPointerException("Set prefix first before generating");

        pairCharWeight = countLetters(text);
        nodeQueue = generateNodes(pairCharWeight);
        generateTree(nodeQueue);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setText(Path file) {
        StringBuilder sb = new StringBuilder();

        try (Stream<String> stream = Files.lines(file, StandardCharsets.UTF_8)) {
            stream.forEach(sb::append);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            text = sb.toString();
        }
    }

    public String getText() {
        return text;
    }

    private Dictionary<Character, Integer> countLetters(String text) {
        Dictionary<Character, Integer> pairCW = new Dictionary<>();

        Character ch;
        int weight;
        for (int i = 0; i < text.length(); i++) {
            ch = text.charAt(i);
            if (!pairCW.contains(ch))
                weight = 1;
            else
                weight = pairCW.get(ch) + 1;
            pairCW.put(ch, weight);
        }

        return pairCW;
    }

    private void generateTree(Queue<TreeNode> nQ) {
        TreeNode left, right;
        while (!nQ.isEmpty()) {
            left = nQ.dequeue();
            if (nQ.peek() != null) {
                right = nQ.dequeue();
                root = new TreeNode('\0', left.weight + right.weight, left, right);
            } else {
                root = new TreeNode('\0', left.weight, left, null);
            }

            if (nQ.peek() != null) {
                nQ.enqueue(root);
            } else {
                break;
            }
        }
    }

    private Queue<TreeNode> generateNodes(Dictionary<Character, Integer> data) {
        Queue<TreeNode> nodes = new MinPriorityQueue<>();

        for (int i = 0; i < data.size(); i++) {
            Dictionary.Node<Character, Integer> node = data.getAt(i);
            nodes.enqueue(new TreeNode(node.key, node.val, null, null));
        }
        return nodes;
    }

    public String getPairs() {
        StringBuilder pairs = new StringBuilder();
        for (int i = 0; i < pairCharWeight.size(); i++) {
            Dictionary.Node<Character, Integer> pair = pairCharWeight.getAt(i);
            pairs.append(String.format("%s%s", pair.key + ":" + pair.val, " "));
        }
        return pairs.toString();
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < pairCharWeight.size(); i++) {
            text.append(pairCharWeight.getAt(i).toString());
        }
        return text.toString();
    }

    public String[][] returnFreqValuesAsTableArray() {
        String[][] values = new String[pairCharWeight.size()][2];
        for (int i = 0; i < pairCharWeight.size(); i++) {
            values[i][0] = pairCharWeight.getAt(i).key.toString();
            values[i][1] = pairCharWeight.getAt(i).val.toString();
        }
        return values;
    }

}
