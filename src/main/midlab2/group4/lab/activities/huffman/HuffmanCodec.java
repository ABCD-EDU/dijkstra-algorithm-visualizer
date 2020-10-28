package main.midlab2.group4.lab.activities.huffman;

/**
 * This class can only be used if the given Huffman Tree using {@link Huffman} class has a string
 * already set. In order to use the codec, build the huffman code first using the {@code buildHuffmanCode()}
 * method then encode (using encode()) to get the compressed bits then decode (using decode())
 * to get the decoded message.
 * <p>
 * {@link Huffman}
 */
public class HuffmanCodec {
    private String original;
    private String encoded;
    private String decoded;
    private final Dictionary<String, Character> pairCodeChar;
    private final Dictionary<Character, String> pairCharCode;
    private final Huffman huffman;

    public HuffmanCodec(Huffman tree) {
        huffman = tree;
        original = tree.getText();
        encoded = "";
        decoded = "";
        pairCharCode = new Dictionary<>();
        pairCodeChar = new Dictionary<>();
    }

    public void buildHuffmanCode() {
        if (huffman.getText().isBlank() || huffman.getRoot() == null) throw new IllegalArgumentException();
        original = huffman.getText();

        String code = "";
        buildCode(huffman.getRoot(), code);
    }

    /**
     * Works just like the three common BST Traversals using recursion or the implemented search method in {@link main.midlab2.group4.lab.util.BinaryTree}
     * <p>
     * NOTE: this is a helper class
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
        if (huffman.getText().isBlank() || huffman.getRoot() == null) throw new NullPointerException();

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
        if (huffman.getText().isBlank() || huffman.getRoot() == null || encoded.isBlank())
            throw new NullPointerException();

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

    public String[][] returnPairCharCodeAsArr() {
        String[][] values = new String[pairCharCode.size()][3];
        for (int i = 0; i < pairCharCode.size(); i++) {
            values[i][0] = pairCharCode.getAt(i).key.toString();
            values[i][1] = pairCharCode.getAt(i).val;
            values[i][2] = String.valueOf(pairCharCode.getAt(i).val.length());
        }
        return values;
    }

}
