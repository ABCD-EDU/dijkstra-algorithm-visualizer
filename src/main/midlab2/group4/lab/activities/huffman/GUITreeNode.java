package main.midlab2.group4.lab.activities.huffman;

public class GUITreeNode {

    private String character;
    private int weight;

    GUITreeNode(String character, int weight) {
        this.character = character;
        this.weight = weight;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        if (character.equals("\0"))
            return Integer.toString(weight);
        else
            return character + " : " + weight;
    }

}
