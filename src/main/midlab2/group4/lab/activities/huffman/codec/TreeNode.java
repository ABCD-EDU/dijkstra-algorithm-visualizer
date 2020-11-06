package main.midlab2.group4.lab.activities.huffman.codec;

public class TreeNode implements Comparable<TreeNode> {
    public int weight;
    public char letter;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(char letter, int weight, TreeNode left, TreeNode right) {
        this.letter = letter;
        this.weight = weight;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(TreeNode o) {
        return Integer.compare(weight, o.weight);
    }

    @Override
    public String toString() {
        return "[Letter: " + letter + " | Weight: " + weight + "]";
    }

    public boolean isLeaf() {
        return (left == null) && (right == null);
    }
}
