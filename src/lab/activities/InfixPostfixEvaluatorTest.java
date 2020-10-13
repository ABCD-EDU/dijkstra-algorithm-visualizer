package lab.activities;

import org.junit.jupiter.api.Test;

import static lab.activities.InfixPostfixEvaluator.*;
import static org.junit.jupiter.api.Assertions.*;

class InfixPostfixEvaluatorTest {
    @Test
    void testPostfix() {
        String infix = "2+4/5*(5-3)^5^4";
        String infix1 = "A*B+C/D";
        String infix2 = "A*(B+C)/D";
        String infix3 = "A*(B+C/D)";
        String infix4 = "A+B+C+D";

        assertEquals("2 4 5 / 5 3 - 5 4 ^ ^ * + ", toPostfix(infix));
        assertEquals("A B * C D / + ", toPostfix(infix1));
        assertEquals("A B C + * D / ", toPostfix(infix2));
        assertEquals("A B C D / + * ", toPostfix(infix3));
        assertEquals("A B + C + D + ", toPostfix(infix4));
    }

    @Test
    void testComputePostfix() {
        String postfix = "7 2 ^ 25 10 5 / + * 13 -";
        String postfix1 = "78 30 0.5 28 8 + * - 6 / +";
        String postfix2 = "2 4 / 5 * 5 3 - ^ 5 ^ 4 +";
        String postfix3 = "2.1 2 ^ 5.2 + 7.2 - 7.1 *";
        String postfix4 = "2 20 * 2 / 3 4 + 3 2 ^ * + 6 - 15 +";
        String postfix5 = "2 4 5 / 5 3 - 5 4 ^ ^ * +";

        assertEquals(1310.00, computePostFix(postfix));
        assertEquals(80.00, computePostFix(postfix1));
        assertEquals(9540.7431640625, computePostFix(postfix2));
        assertEquals(17.110999999999994, computePostFix(postfix3));
        assertEquals(92.00, computePostFix(postfix4));
        assertEquals(1.1138771039116688E188, computePostFix(postfix5));
    }
}