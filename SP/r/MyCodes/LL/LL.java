import java.util.*;
import java.io.*;

public class LL{
	public static void main(String args[]){
		HashMap<String, HashMap<String, String>> table = new HashMap<String, HashMap<String, String>>();

		HashMap<String, String> productions1 = new HashMap<String, String>();
		productions1.put("i", "TR");
		productions1.put("(", "TR");

		table.put("E", productions1);

		String epsilon = "\u03B5";

		HashMap<String, String> productions2 = new HashMap<String, String>();
		productions2.put("+", "+TR");
		productions2.put(")", epsilon);
		productions2.put("$", epsilon);

		table.put("R", productions2);

		HashMap<String, String> productions3 = new HashMap<String, String>();
		productions3.put("i", "FS");
		productions3.put("(", "FS");

		table.put("T", productions3);

		HashMap<String, String> productions4 = new HashMap<String, String>();
		productions4.put("+", epsilon);
		productions4.put("*", "*FS");		
		productions4.put(")", epsilon);
		productions4.put("$", epsilon);

		table.put("S", productions4);

		HashMap<String, String> productions5 = new HashMap<String, String>();
		productions5.put("i", "i");
		productions5.put("(", "(E)");

		table.put("F", productions5);

		Scanner sc = new Scanner(System.in);

		String input = sc.nextLine();

		LinkedList<String> buffer = new LinkedList<String>(Arrays.asList(input.split("")));

		Stack<String> stack = new Stack<String>();
		stack.push("$");
		stack.push("E");

		String symbol = stack.peek();
		String in = buffer.peek();

		while(true){

			HashMap<String, String> productions = table.get(symbol);

			if(productions.containsKey(in)){

				String production = productions.get(in);

				if(production.equals(epsilon)){
					stack.pop();
				}
				else{
                    stack.pop();
					for(int i = production.length() - 1; i >= 0; i--){
						stack.push(Character.toString(production.charAt(i)));
					}	
				}

				while(!(stack.isEmpty() || buffer.isEmpty()) && stack.peek().equals(buffer.peek())){
					stack.pop();
					buffer.remove();
				}

                if(stack.isEmpty()&&buffer.isEmpty()){
                    System.out.println("Acceptance");
                    break;
                }
				System.out.println("\nStack is:");
                System.out.println(stack);
                System.out.println("\nbuffer is:");
                System.out.println(buffer);
				

				symbol = stack.peek();
				in = buffer.peek();

			}
			else{
				System.out.println("Rejection");
                break;
			}
		}
	}
}

/*
i+i$

Stack is:
[$, R, T]

buffer is:
[i, +, i, $]

Stack is:
[$, R, S, F]

buffer is:
[i, +, i, $]

Stack is:
[$, R, S]

buffer is:
[+, i, $]

Stack is:
[$, R]

buffer is:
[+, i, $]

Stack is:
[$, R, T]

buffer is:
[i, $]

Stack is:
[$, R, S, F]

buffer is:
[i, $]

Stack is:
[$, R, S]

buffer is:
[$]

Stack is:
[$, R]

buffer is:
[$]
Acceptance

*/