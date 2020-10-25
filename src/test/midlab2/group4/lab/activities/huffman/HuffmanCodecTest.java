package test.midlab2.group4.lab.activities.huffman;

import main.midlab2.group4.lab.activities.huffman.Huffman;
import main.midlab2.group4.lab.activities.huffman.HuffmanCodec;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HuffmanCodecTest {
    public static HuffmanCodec codec;
    public static Huffman huffman;
    private static final String text =
            "this sentence contains one hundred and ninety-seven letters: " +
                    "four a's, one b, three c's, five d's, thirty-four e's, " +
                    "seven f's, one g, six h's, twelve i's, three l's, twenty-six n's, " +
                    "ten o's, ten r's, twenty-nine s's, nineteen t's, six u's, seven v's, " +
                    "four w's, four x's, five y's, and one z.";

    @BeforeAll
    static void setUp() {
        huffman = new Huffman(text);
        codec = new HuffmanCodec(huffman);
    }

    @AfterAll
    static void tearDown() {
        huffman = null;
        codec = null;
    }

    @Test
    void testEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            Huffman testH = new Huffman("");
            @SuppressWarnings("unused")
            HuffmanCodec testC = new HuffmanCodec(testH);
        });
    }

    @Test
    void encodeThenDecodeTestOne() {
        String expected = "this sentence contains one hundred and ninety-seven letters: " +
                        "four a's, one b, three c's, five d's, thirty-four e's, " +
                        "seven f's, one g, six h's, twelve i's, three l's, twenty-six n's, " +
                        "ten o's, ten r's, twenty-nine s's, nineteen t's, six u's, seven v's, " +
                        "four w's, four x's, five y's, and one z.";
        codec.encode();

        String decoded = codec.decode();
        assertEquals(expected, decoded);
    }
}
