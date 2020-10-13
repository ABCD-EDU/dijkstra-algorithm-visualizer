package lab.activities;

import lab.datastructure.MyLinkedList;
import lab.datastructure.Stack;

// TODO: TABLE and TESTS and toPREFIX
public class InfixPostfixEvaluator {

    private static final Object[][] ops = {{"+", "-", "/", "*", "^"}, {2, 2 ,3 ,3 ,4}};

    public static String toPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<String> stack = new MyLinkedList<>();

        System.out.println("\n|-------------------------------------------------------------------------------------|");
        System.out.printf("| %-19s %-64s|", "Infix expression ->", infix);
        System.out.printf("\n| %-9s| %-40s| %-30s |\n", "Symbol", "Postfix", "Stack");
        System.out.print("|-------------------------------------------------------------------------------------|");
        for (String token : infix.split("(?<=[-+*/^()])|(?=[-+*/^()])")) {
            // operator
            if (isValidOperator(token)) {
                while (!stack.isEmpty() && isHigherPrecedence(token, stack.peek()))
                    output.append(stack.pop()).append(' ');
                stack.push(token);
            }

            // left parenthesis
            else if (token.equals("(")) {
                stack.push(token);
            }

            // right parenthesis
            else if (token.equals(")")) {
                while (!(stack.peek() != null && stack.peek().equals("(")))
                    output.append(stack.pop()).append(' ');
                stack.pop();
            } else {
                output.append(token).append(' ');
            }
            System.out.printf("\n| %-9s| %-40s| %-30s |", token, output.toString(), stack.toString());
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(' ');
            System.out.printf("\n| %-9s| %-40s| %-30s |", "", output.toString(), stack.toString());
        }
        System.out.println("\n|-------------------------------------------------------------------------------------|");

        return output.toString();
    }

    public static double computePostFix(String postfix) {
        Stack<Double> stack = new MyLinkedList<>();
        String op1 = "";
        String op2 = "";
        String ans = "";

        System.out.println("\n|-------------------------------------------------------------------------------|");
        System.out.printf("| %-19s %-56s|", "Postfix expression ->", postfix);
        System.out.printf("\n| %-10s| %-10s| %-10s| %-10s| %-30s|\n", "Symbol", "operand1", "operand2", "value", "Stack");
        System.out.println("|-------------------------------------------------------------------------------|");
        for (String token : postfix.split("\\s")) {
            try {
                stack.push((Double.parseDouble(token)));
            } catch (Exception e) {
                try {
                    double a = stack.pop();
                    double b = stack.pop();
                    double answer = compute(b, a, token);
                    stack.push(answer);
                    op1 = Double.toString(b);
                    op2 = Double.toString(a);
                    ans = Double.toString(answer);
                } catch (Exception ex) {
                    System.out.println("Syntax Error");
                }
            }
            System.out.printf("| %-10s| %-10s| %-10s| %-10s| %-30s|\n", token, op1, op2, ans, stack.toString());
        }
        System.out.println("|-------------------------------------------------------------------------------|");

        return stack.peek();
    }

    private static double compute(double x, double y, String op) {
        switch (op) {
            case "+": return x + y;
            case "-": return x - y;
            case "*": return x * y;
            case "/": return x / y;
            case "^": return Math.pow(x, y);
            default: return 0;
        }
    }

    private static boolean isValidOperator(String token) {
        for (var arr : ops)
            for (var item : arr)
                if (item.equals(token))
                    return true;

        return false;
    }

    private static boolean isHigherPrecedence(String op, String sub) {
        if (op.equals("^") && sub.equals("^")) return false; // this is for the left/right associativity so that the "^" would be added to the stack instead
        return (isValidOperator(sub) && getValue(sub) >= getValue(op));
    }

    private static int getValue(String key) {
        for (int i = 0; i < ops[1].length; i++)
            if (ops[0][i].equals(key))
                return Integer.parseInt(String.valueOf(ops[1][i]));

        return 0;
    }
}
