import java.util.*;
public class Left {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int j = 0;
        System.out.println("Enter number of productions");
        int n = sc.nextInt();
        String productions;
        ArrayList < String > al = new ArrayList < String > ();
        ArrayList < String > lengthOne = new ArrayList < > ();
        ArrayList < String > lengthTwo = new ArrayList < > ();
        while (j < n) {
            productions = sc.next();
            al.add(productions);
            j++;
        }
        for (j = 0; j < n; j++) {
            System.out.println("Rules for production " + j);
            System.out.println(al.get(j));
            String prods[] = al.get(j).split(":");
            // for(int i=0; i<prods.length; i++)
            //
            System.out.println(prods[i]);
            if (prods[1].contains(prods[0])) {
                String right[] = prods[1].split(prods[0]);
                // for(int i=0; i<right.length; i++)
                //
                System.out.println(right[i]);
                String beta[] = right[1].split("\\|");
                // for(int i=0; i<beta.length; i++)
                //
                System.out.println(beta[i]);
                String nonterm = prods[0];
                String[] rhs = prods[1].split("\\|");
                for (int i = 0; i < rhs.length; i++) {
                    switch (rhs[i].length()) {
                        case 1:
                            lengthOne.add(rhs[i]);
                            break;
                        case 2:
                            lengthTwo.add(rhs[i]);
                            break;
                    }
                }
                System.out.print("A->");
                for (int i = 0; i < lengthOne.size(); i++) {
                    System.out.print(lengthOne.get(i) + "B|");
                }
                System.out.print("\n\nB->");
                for (int i = 0; i < lengthTwo.size(); i++) {
                    if (lengthTwo.get(i).charAt(0) == 'A') {
                        System.out.print(lengthTwo.get(i).charAt(1) + "B|");
                    }
                }
                System.out.println("\u03B5\n");
                // System.out.println("Non-Terminal is: "+nonterm);
                // String terminal = beta[0];
                // System.out.println("Terminal is: "+terminal);
                // String beeta = beta[1];
                // System.out.println("Beta is: "+beeta);
                // System.out.println("Production rules are: ");
                // System.out.println(nonterm+" : "+beeta+nonterm+"\'\n" + nonterm+"\': "+terminal+nonterm+"\' | \u03B5");
            } else {
                System.out.println("Does not contain left recursion");
            }
        }
        // System.out.println("Enter the production: ");
        // String prod = sc.nextLine();
    }
}