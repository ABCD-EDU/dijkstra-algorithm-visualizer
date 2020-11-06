package test.midlab2.group4.lab.activities.huffman;

import main.midlab2.group4.lab.activities.huffman.codec.Huffman;
import main.midlab2.group4.lab.activities.huffman.codec.HuffmanCodec;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class HuffmanCodecTest {
    public static Huffman huffman;
    public static HuffmanCodec codec;

    @BeforeAll
    static void setUp() {
        huffman = new Huffman();
        codec = new HuffmanCodec(huffman);
    }

    @AfterAll
    static void tearDown() {
        huffman = null;
        codec = null;
    }

    @Test
    void testEmptyInput() {
        assertThrows(NullPointerException.class, () -> {
            Huffman testH = new Huffman();
            @SuppressWarnings("unused")
            HuffmanCodec testC = new HuffmanCodec(testH);
            testC.encode();
            testC.decode();
        });
    }

    @Test
    void encodeThenDecodeTestOne() {
        // setup
        final String expected = "this sentence contains one hundred and ninety-seven letters: " +
                "four a's, one b, three c's, five d's, thirty-four e's, " +
                "seven f's, one g, six h's, twelve i's, three l's, twenty-six n's, " +
                "ten o's, ten r's, twenty-nine s's, nineteen t's, six u's, seven v's, " +
                "four w's, four x's, five y's, and one z.";

        // set prefix
        huffman.setText(expected);

        // generate tree
        huffman.generateTree();

        // encode
        codec.buildHuffmanCode();
        codec.encode();

        // test
        String decoded = codec.decode();
        assertEquals(expected, decoded);
    }

    @Test
    void encodeThenDecodeFileTestOne() {
        // data
        final Path text = Paths.get("D:\\git-repos\\9413-midterm-grp4\\src\\test\\midlab2\\group4\\lab\\activities\\huffman\\files\\test1.txt");
        String expected = huffman.getText();

        // set prefix
        huffman.setText(text);

        // generate tree
        huffman.generateTree();

        // encode
        codec.buildHuffmanCode();
        codec.encode();

        // test
        String decoded = codec.decode();
        assertEquals(expected, decoded);
    }
}
