import java.util.*;
public class Quad {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String infix_input = sc.nextLine();
        String input = Test.infixToPostfix(infix_input);
        System.out.println(input);
        Stack < String > stack = new Stack < String > ();
        stack.push("$");
        ArrayList < Entry > table = new ArrayList < Entry > ();
        int count = 1;
        int i = 0;
        while (!stack.empty() && i < input.length()) {
            char ch = input.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                stack.push(Character.toString(ch));
            } else {
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                String newop = "t" + count;
                count++;
                stack.push(newop);
                Entry entry = new Entry(operand1, operand2,
                    Character.toString(ch), newop);
                table.add(entry);
            }
        }
        i++;
    }
    System.out.println("Operand1\tOperand2\tOperator\tResult");
    for (Entry e : table) {
        e.display();
    }
}
class Entry {
    String
    String
    String
    String
    operand1;
    operand2;
    operator;
    result;
    Entry(String operand1, String operand2, String operator, String result) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
        this.result = result;
    }
    void display() {
        System.out.println(String.format("%s\t\t%s\t\t%s\t\t%s",
            this.operand1, this.operand2, this.operator, this.result));
    }
}
class Test {
    static int Prec(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        case '^':
            return 3;
    }
    return -1;
    static String infixToPostfix(String exp) {
        String result = new String("");
        Stack < Character > stack = new Stack < > ();
        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);
            if (Character.isLetterOrDigit(c))
                result += c;
            else if (c == '(')
                stack.push(c);
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result += stack.pop();
                if (!stack.isEmpty() && stack.peek() != '(')
                    return "Invalid Expression"; // invalid expression
                else
                    stack.pop();
            } else // an operator is encountered
            {
                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek()))
                    result += stack.pop();
                stack.push(c);
            }
        }
        // pop all the operators from the stack
        while (!stack.isEmpty())
            result += stack.pop();
    }
    return result;
}