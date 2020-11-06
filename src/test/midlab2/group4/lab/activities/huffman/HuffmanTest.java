package test.midlab2.group4.lab.activities.huffman;

import main.midlab2.group4.lab.activities.huffman.codec.Huffman;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class HuffmanTest {
    private static Huffman huffman;

    @BeforeAll
    static void setUp() {
        huffman = new Huffman();
    }

    @AfterAll
    static void tearDown() {
        huffman = null;
    }

    @Test
    void testEmptyInput() {
        assertThrows(NullPointerException.class, () -> {
            @SuppressWarnings("unused")
            Huffman testH = new Huffman();
            testH.generateTree();
        });
    }

    @Test
    void testTextInsertion() {
        assertNotNull(huffman);
    }

    @Test
    void testCharacterFrequency() {
        // data
        final String text =
                "this sentence contains one hundred and ninety-seven letters: " +
                        "four a's, one b, three c's, five d's, thirty-four e's, " +
                        "seven f's, one g, six h's, twelve i's, three l's, twenty-six n's, " +
                        "ten o's, ten r's, twenty-nine s's, nineteen t's, six u's, seven v's, " +
                        "four w's, four x's, five y's, and one z.";

        // set prefix
        huffman.setText(text);

        // generate tree
        huffman.generateTree();

        // test
        String expected = "t:19 h:6 i:12 s:29  :50 e:34 n:26 c:3 o:10 a:4 u:6 d:5 r:10 y:5 -:4 v:7 l:3 ::1 f:7 ':18 ,:20 b:1 g:1 x:4 w:4 z:1 .:1 ";
        assertEquals(expected, huffman.getPairs());
    }

    @Test
    void testCharacterFrequencyTwo() {
        // data
        final String text = "MISSISSIPPI RIVER";

        // set prefix
        huffman.setText(text);

        // generate tree
        huffman.generateTree();

        // test
        String expected = "M:1 I:5 S:4 P:2  :1 R:2 V:1 E:1 ";
        assertEquals(expected, huffman.getPairs());
    }

    @Test
    void testFileCharacterFrequency() {
        // data
        final Path text = Paths.get("D:\\git-repos\\9413-midterm-grp4\\src\\test\\midlab2\\group4\\lab\\activities\\huffman\\files\\test1.txt");

        // set prefix
        huffman.setText(text);

        // generate tree
        huffman.generateTree();

        // test
        String expected = "t:19 h:6 i:12 s:29  :50 e:34 n:26 c:3 o:10 a:4 u:6 d:5 r:10 y:5 -:4 v:7 l:3 ::1 f:7 ':18 ,:20 b:1 g:1 x:4 w:4 z:1 .:1 ";
        assertEquals(expected, huffman.getPairs());
    }

    @Test
    void testFileCharacterFrequencyTwo() {
        // data
        final Path text = Paths.get("D:\\git-repos\\9413-midterm-grp4\\src\\test\\midlab2\\group4\\lab\\activities\\huffman\\files\\test2.txt");

        // set prefix
        huffman.setText(text);

        // generate tree
        huffman.generateTree();

        // test
        String expected = "M:1 I:5 S:4 P:2  :1 R:2 V:1 E:1 ";
        assertEquals(expected, huffman.getPairs());
    }
}
