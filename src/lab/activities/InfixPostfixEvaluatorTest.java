package lab.activities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static lab.activities.InfixPostfixEvaluator.*;
import static org.junit.jupiter.api.Assertions.*;

class InfixPostfixEvaluatorTest {
    String infix1 = "12*7+420/3";
    String postfix1 = "12 7 * 420 3 / +";
    double answer1 = 224.0D;
    String infix2 = "3^3*(37+3)/18";
    String postfix2 = "3 3 ^ 37 3 + * 18 /";
    double answer2 = 60.0D;
    String infix4 = "30/3*46+(23*3)/34";
    String postfix4 = "30 3 / 46 * 23 3 * 34 / +";
    double answer4 = 462.02941176470586D;
    String infix3 = "2+4/5*(5-3)^3^2";
    String postfix3 = "2 4 5 / 5 3 - 3 2 ^ ^ * +";
    double answer3 = 411.6D;
    String infix5 = "1+23/69*(1+2)-1";
    String postfix5 = "1 23 69 / 1 2 + * + 1 -";
    double answer5 = 1.0D;
    String infix6 = "(28+((8/4)*10)+78*2-4)/2";
    String postfix6 = "28 8 4 / 10 * + 78 2 * + 4 - 2 /";
    double answer6 = 100.0D;
    String infix7 = "23-11*2+(420+((3*9)*2+3)-69)/3";
    String postfix7 = "23 11 2 * - 420 3 9 * 2 * 3 + + 69 - 3 / +";
    double answer7 = 137.0D;

    InfixPostfixEvaluatorTest() {
    }

//    @Test
//    void testCase1() {
//        System.out.print("|==================================== Test Case 1 ====================================|");
//        String postfixAnswer = InfixPostfixEvaluator.toPostfix(this.infix1);
//        Assertions.assertEquals(this.postfix1, postfixAnswer);
//        Assertions.assertEquals(this.answer1, InfixPostfixEvaluator.computePostFix(postfixAnswer));
//    }
//
//    @Test
//    void testCase2() {
//        System.out.print("|==================================== Test Case 2 ====================================|");
//        String postfixAnswer = InfixPostfixEvaluator.toPostfix(this.infix2);
//        Assertions.assertEquals(this.postfix2, postfixAnswer);
//        Assertions.assertEquals(this.answer2, InfixPostfixEvaluator.computePostFix(postfixAnswer));
//    }
//
//    @Test
//    void testCase3() {
//        System.out.print("|==================================== Test Case 3 ====================================|");
//        String postfixAnswer = InfixPostfixEvaluator.toPostfix(this.infix3);
//        Assertions.assertEquals(this.postfix3, postfixAnswer);
//        Assertions.assertEquals(this.answer3, InfixPostfixEvaluator.computePostFix(postfixAnswer));
//    }
//
//    @Test
//    void testCase4() {
//        System.out.print("|==================================== Test Case 4 ====================================|");
//        String postfixAnswer = InfixPostfixEvaluator.toPostfix(this.infix4);
//        Assertions.assertEquals(this.postfix4, postfixAnswer);
//        Assertions.assertEquals(this.answer4, InfixPostfixEvaluator.computePostFix(postfixAnswer));
//    }
//
//    @Test
//    void testCase5() {
//        System.out.print("|==================================== Test Case 5 ====================================|");
//        String postfixAnswer = InfixPostfixEvaluator.toPostfix(this.infix5);
//        Assertions.assertEquals(this.postfix5, postfixAnswer);
//        Assertions.assertEquals(this.answer5, InfixPostfixEvaluator.computePostFix(postfixAnswer));
//    }
//
//    @Test
//    void testCase6() {
//        System.out.print("|==================================== Test Case 6 ====================================|");
//        String postfixAnswer = InfixPostfixEvaluator.toPostfix(this.infix6);
//        Assertions.assertEquals(this.postfix6, postfixAnswer);
//        Assertions.assertEquals(this.answer6, InfixPostfixEvaluator.computePostFix(postfixAnswer));
//    }
//
//    @Test
//    void testCase7() {
//        System.out.print("|==================================== Test Case 7 ====================================|");
//        String postfixAnswer = InfixPostfixEvaluator.toPostfix(this.infix7);
//        Assertions.assertEquals(this.postfix7, postfixAnswer);
//        Assertions.assertEquals(this.answer7, InfixPostfixEvaluator.computePostFix(postfixAnswer));
//    }
}