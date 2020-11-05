package main.midlab2.group4.lab.activities.huffman.window;

public class GUITreeNode {

    protected String character;
    protected int weight;

    GUITreeNode(String character, int weight) {
        this.character = character;
        this.weight = weight;
    }

    public String getCharacter() {
        return character;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        if (character.equals("\0"))
            return Integer.toString(weight);
        else
            return character + " : " + weight;
    }

}
