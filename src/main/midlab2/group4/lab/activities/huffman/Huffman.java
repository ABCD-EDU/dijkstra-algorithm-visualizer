package main.midlab2.group4.lab.activities.huffman;

import main.midlab2.group4.lab.util.MinPriorityQueue;
import main.midlab2.group4.lab.util.Queue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Before using this class, using the setText() method first to set a text to parse then proceed to generateTree() method
 * for the huffman tree. In case the text string is still blank, a {@code NullPointerException} will be thrown.
 */
public class Huffman {
    private Dictionary<Character, Integer> pairCharWeight;
    private Queue<TreeNode> nodeQueue;
    private TreeNode root;
    private String text;

    /**
     * Step by step process for the huffman code
     * <p>1. Count the frequency of each character and put it into a dictionary</p>
     * <p>2. Create the nodes based on minheap using the {@link MinPriorityQueue}</p>
     * <p>3. Generate the tree by using the huffman coding algorithm</p>
     * <p>4. Encode the bits for each character</p>
     * <p>5. Decode the encoded bits for each character</p>
     */
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

    /**
     * Put's all of the characters and its count to a dictionary of char:weight
     *
     * @param text to parse
     * @return dictionary of the character and its frequency
     */
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

    /**
     * Generation of the huffman tree is based on using the minheap queue in order to generate the nodes.
     * It works similar with the Infix to Postfix conversion where you pop two items and add their weights
     * to make a new parent node. Reinsert the node to the queue then repeat the process.
     *
     * @param nQ nodes to process
     * @see <a href="https://en.wikipedia.org/wiki/Huffman_coding">Huffman Coding</a>
     */
    private void generateTree(Queue<TreeNode> nQ) {
        TreeNode left, right;
        while (!nQ.isEmpty()) {
            left = nQ.dequeue();
            if (nQ.peek() != null) { // if we can still insert a node on both left and right side of tree
                right = nQ.dequeue();
                root = new TreeNode('\0', left.weight + right.weight, left, right);
            } else { // no node left to dequeue
                root = new TreeNode('\0', left.weight, left, null);
            }

            if (nQ.peek() != null) {
                nQ.enqueue(root);
            } else { // queue is empty
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

    /**
     * This is used for a smaller format or for testing
     * TODO: REMOVE THIS IN THE FINAL OUTPUT
     *
     * @return string
     */
    public String getPairs() {
        StringBuilder pairs = new StringBuilder();
        for (int i = 0; i < pairCharWeight.size(); i++) {
            Dictionary.Node<Character, Integer> pair = pairCharWeight.getAt(i);
            pairs.append(String.format("%s%s", pair.key + ":" + pair.val, " "));
        }
        return pairs.toString();
    }

    /**
     * Modify this if you want to print the values in a table format
     *
     * @return formatted values
     */
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
//        text.append(String.format("%-30s%-30s%n", "Character", "Weight")); // -- for the table format
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
