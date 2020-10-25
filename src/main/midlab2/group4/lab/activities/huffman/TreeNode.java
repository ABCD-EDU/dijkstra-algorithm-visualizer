package main.midlab2.group4.lab.activities.huffman;

public class TreeNode implements Comparable<TreeNode> {
    protected int weight;
    protected char letter;
    protected TreeNode left, right;

    protected TreeNode(char letter, int weight, TreeNode left, TreeNode right) {
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
