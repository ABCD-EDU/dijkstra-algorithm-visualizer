package lab.activities;

import lab.datastructure.MyLinkedList;
import lab.datastructure.Stack;

// TODO: TABLE and TESTS and toPREFIX
public class InfixPostfixEvaluator {

    private static final Object[][] ops = {{"+", "-", "/", "*", "^"}, {2, 2 ,3 ,3 ,4}};

    public static String[][] toPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<String> stack = new MyLinkedList<>();
        String[] symbolsArr = infix.split("(?<=[-+*/^()])|(?=[-+*/^()])");
        symbolsArr = formatSymbolsArr(symbolsArr);
        validateInfix(symbolsArr);
        String[][] rows = new String[symbolsArr.length*2][];
        int currentIdx = 0;

        System.out.println("\n|-------------------------------------------------------------------------------------|");
        System.out.printf("| %-19s %-64s|", "Infix expression ->", infix);
        System.out.printf("\n| %-9s| %-40s| %-30s |\n", "Symbol", "Postfix", "Stack");
        System.out.print("|-------------------------------------------------------------------------------------|");
        for (String token : symbolsArr) {
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
            rows[currentIdx++] = new String[]{token, output.toString(), stack.toString()};
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(' ');
            System.out.printf("\n| %-9s| %-40s| %-30s |", "", output.toString(), stack.toString());
            rows[currentIdx++] = new String[]{"", output.toString(), stack.toString()};
        }
        System.out.println("\n|-------------------------------------------------------------------------------------|");
        return rows = trimToSize(rows, currentIdx);
    }

    public static String[][] computePostFix(String postfix) {
        Stack<Double> stack = new MyLinkedList<>();
        String[][] rows = new String[postfix.split("\\s").length*2][];
        int currentIdx = 0;
        String op1 = "";
        String op2 = "";
        String ans = "";

        System.out.println("\n|-------------------------------------------------------------------------------|");
        System.out.printf("| %-19s %-56s|", "Postfix expression ->", postfix);
        System.out.printf("\n| %-10s| %-10s| %-10s| %-10s| %-30s|\n", "Symbol", "31", "operand2", "value", "Stack");
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
            String s = formatVariablesToPrint(stack);
            System.out.printf("| %-10s| %-10s| %-10s| %-10s| %-30s|\n", token, formatDouble(op1),
                    formatDouble(op2), formatDouble(ans), s);
            rows[currentIdx++] = new String[]{token, formatDouble(op1), formatDouble(op2), formatDouble(ans), s};
        }
        System.out.println("|-------------------------------------------------------------------------------|");

        return rows = trimToSize(rows, currentIdx);
    }

    /**
     * Helper method used to set a multi-dimensional array's size lower than its original size.
     */
    private static String[][] trimToSize(String[][] origArr, int size) {
        String[][] trimmed = new String[size][origArr[0].length];
        for (int i = 0; i < size; i++)
            trimmed[i] = origArr[i];
        return trimmed;
    }

    /**
     * Helper method used to set an array's size lower than its original size.
     */
    private static String[] trimToSize(String[] origArr, int size) {
        String[] trimmed = new String[size];
        for (int i = 0; i < size; i++)
            trimmed[i] = origArr[i];
        return trimmed;
    }

    private static String formatVariablesToPrint(Stack<Double> stack) {
        if (stack.size() == 0) return "";
        if (stack.size() == 1) return formatDouble(String.valueOf(stack.peek()));

        StringBuilder sb = new StringBuilder();
        String[] numbers = stack.toString().split(",");

        for (int i = 0; i < numbers.length-1; i++ )
            sb.append(formatDouble(numbers[i]));
        sb.append(formatDouble(numbers[numbers.length - 1]));

        return sb.toString();
    }

    public static String formatDouble(String value) {
        if (value.equals("")) return "";
        String[] parts = value.split("\\.");
        if (parts[1].length() < 3) return value;
        return parts[0] + "." + parts[1].substring(0,3);
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
        for (var op : ops[0]) if (op.equals(token)) return true;
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

    /**
     * Infix is invalid if:
     * 1. Two operators are next to each other || two numerical values are next to each other
     * 2. A non numerical value and a non operator symbol exists within the symbolsArray
     * @param symbolsArr arr containing all symbols within the infix expression to be checked
     */
    private static void validateInfix(String[] symbolsArr) {
        boolean prevIsOperator = true;
        for (int i = 0; i < symbolsArr.length; i++) {
            if (symbolsArr[i].contains(" "))
                throw new InvalidInfixException("Infix Expression Contains Whitespace(s).");
            boolean currentIsOperator = isValidOperator(symbolsArr[i]);
            if (!currentIsOperator && !symbolsArr[i].equals("(") && !symbolsArr[i].equals(")")) {
                try {
                    Double.parseDouble(symbolsArr[i]);
                }catch (NumberFormatException e) {
                    throw new InvalidInfixException("Invalid symbol: " + symbolsArr[i]);
                }
            }
            if (symbolsArr[i].equals("(") && (!symbolsArr[i+1].equals("-") && isValidOperator(symbolsArr[i+1])))
                throw new InvalidInfixException("Misplaced Symbol: " + symbolsArr[i+1]);
            if (currentIsOperator && prevIsOperator) throw new InvalidInfixException("Misplaced Symbol: " + symbolsArr[i]);
            prevIsOperator = currentIsOperator;
        }
    }

    private static void printArr(String[] arr) {
        if (arr.length == 0) System.out.println("[ empty ]");
        System.out.print("[ ");
        for(int i = 0; i < arr.length-1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println(arr[arr.length-1] + " ]");
    }

    /**
     * Conditions:
     * If arr[0] == '-' then concatenate
     * If '(' precedes '-' then concatenate
     * If '-' is preceded by an operator then concatenate
     * Else let '-' be its own symbol
     */
    private static String[] formatSymbolsArr(String[] symbols) {
        String[] toReturn = new String[symbols.length];
        int ctr = 0;

        if (symbols[symbols.length-1].equals("(") || isValidOperator(symbols[symbols.length-1]))
            throw new InvalidInfixException("Misplaced Symbol: " + symbols[symbols.length-1]);

        for (int i = 0; i < symbols.length; i++) {
            if ( (symbols[i].equals("(") && symbols[i+1].equals("-")) ||
                    (isValidOperator(symbols[i]) && symbols[i+1].equals("-")) ) {
                toReturn[ctr++] = symbols[i];
                toReturn[ctr++] = symbols[i+1] + symbols[i+2];
                i+=2;
            }
//            else if(symbols[i].equals("-") && symbols[i+1].equals("(")) {
//                  TODO: Implement if negative parenthesis should be taken into consideration
//            }
            else if(i == 0 && symbols[i].equals("-") && !symbols[i+1].equals("(")){
                toReturn[ctr++] = symbols[i] + symbols[i+1];
                i++;
            }
            else {
                toReturn[ctr++] = symbols[i];
            }
        }

        return trimToSize(toReturn, ctr);
    }

}
