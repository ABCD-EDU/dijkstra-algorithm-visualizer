package main.midlab2.group4.lab.activities.huffman;

public class HuffmanCodec {
    private final String original;
    private String encoded;
    private String decoded;
    private final Dictionary<String, Character> pairCodeChar;
    private final Dictionary<Character, String> pairCharCode;

    public HuffmanCodec(String text, TreeNode root) {
        if (text.isBlank() || root == null) throw new IllegalArgumentException();

        original = text;
        encoded = "";
        decoded = "";
        pairCharCode = new Dictionary<>();
        pairCodeChar = new Dictionary<>();
        buildHuffmanCode(root);
    }

    private void buildHuffmanCode(TreeNode node) {
        String code = "";
        buildCode(node, code);
    }

    /**
     * Works just like the three common BST Traversals using recursion or the implemented search method in {@link main.midlab2.group4.lab.util.BinaryTree}
     *
     * @param node current node
     * @param code bits
     * @see main.midlab2.group4.lab.util.BinaryTree
     */
    private void buildCode(TreeNode node, String code) {
        if (node != null) {
            if (!node.isLeaf()) {
                buildCode(node.left, code + '0');  // left is 0
                buildCode(node.right, code + '1'); // right is 1
            } else { // if we've reached the character, insert the code to the dictionary
                pairCharCode.put(node.letter, code);
                pairCodeChar.put(code, node.letter);
            }
        }
    }

    /**
     * For each letter in the original string, insert the corresponding code into the {@code encoded} string.
     * Repeat until the last character of the original string.
     *
     * @return encoded bits
     */
    public String encode() {
        StringBuilder code = new StringBuilder();
        char letter;
        for (int i = 0; i < original.length(); i++) {
            letter = original.charAt(i);
            code.append(pairCharCode.get(letter));
        }
        encoded = code.toString();
        return encoded;
    }

    /**
     * Add one bit at a time into the temporary variable until the temp matches a code from the dictionary.
     * If it matches something, empty the temporary string and repeat the process.
     *
     * @return decoded string
     */
    public String decode() {
        StringBuilder text = new StringBuilder();
        String temp = "";
        for (int i = 0; i < encoded.length(); i++) {
            temp += encoded.charAt(i);
            if (pairCodeChar.contains(temp)) {
                text.append(pairCodeChar.get(temp));
                temp = "";
            }
        }
        decoded = text.toString();
        return decoded;
    }

    /**
     * Modify this if you want to print the values in a table format
     *
     * @return formatted values
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(String.format("%-30s%-30s%-30s%n", "Character", "Code", "Bits"));
        for (int i = 0; i < pairCharCode.size(); i++) {
            Dictionary.Node<Character, String> node = pairCharCode.getAt(i);
            string.append(String.format("%-30s%-30s%-30s%n", node.key, node.val, node.val.length()));
        }
        return string.toString();
    }
}
