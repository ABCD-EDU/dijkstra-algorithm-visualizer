package main.midlab2.group4.lab.activities.huffman.codec;

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

    private void buildCode(TreeNode node, String code) {
        if (node != null) {
            if (!node.isLeaf()) {
                buildCode(node.left, code + '0');
                buildCode(node.right, code + '1');
            } else {
                pairCharCode.put(node.letter, code);
                pairCodeChar.put(code, node.letter);
            }
        }
    }

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
