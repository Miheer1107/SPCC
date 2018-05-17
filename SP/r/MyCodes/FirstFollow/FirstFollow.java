import java.util.*;
import java.io.*;

public class FirstFollow{
	public static void main(String args[]){
		int number;

		System.out.println("Enter the number of productions");

		Scanner sc = new Scanner(System.in);

		number = sc.nextInt();

		HashMap<Character, String> productions =  new HashMap<Character, String>();

		Scanner sc2 = new Scanner(System.in);
		for(int i = 0; i < number; i++){
			String s = sc2.nextLine();
			String spl[] = s.split(" -> ");
			productions.put(spl[0].charAt(0), spl[1]);
		}

		FirstParser fip = new FirstParser(productions);
		HashMap<Character, HashSet<Character>> first = fip.parse();

		FollowParser flp = new FollowParser(productions, fip);

		flp.parse();

	}
}

class FirstParser{
	private HashMap<Character, String> productions;
	private Set<Character> nonterminals; 
	private HashMap<Character, HashSet<Character>> first;
	
	FirstParser(HashMap<Character, String> productions){
		this.productions =  productions;
		this.nonterminals = this.productions.keySet();
		this.first = new HashMap<Character, HashSet<Character>>();
		for(Character c : nonterminals){
			this.first.put(c, new HashSet<Character>());
		}
	}

	public HashMap<Character, HashSet<Character>> parse(){
		for(Character c : nonterminals){
			HashSet<Character> first_set = first_prod(c);
			System.out.print("The first set of " + c + " is: ");
			System.out.println(first_set);
		}

		return this.first;
	}

	public HashSet<Character> first_char(Character c){
		HashSet<Character> first_set = null;

		if(!this.nonterminals.contains(c)){
			first_set = new HashSet<Character>();
			first_set.add(c);
		}
		else{
			first_set = first_prod(c);
		}

		return first_set;
	}

	public HashSet<Character> first_expr(String expr){
		HashSet<Character> first_set = new HashSet<Character>();

		Boolean all_nullable = true;
		for(int i = 0 ; i < expr.length() ; i++){

			Character c = expr.charAt(i);
			HashSet<Character> first_set_char = first_char(c);

			Boolean this_nullable = first_set_char.contains('>');

			first_set.addAll(first_set_char);

			all_nullable = all_nullable&&this_nullable;

			if(!this_nullable){
				break;
			}
		}

		if(!all_nullable){
			first_set.remove('>');
		}

		return first_set;
	}

	public HashSet<Character> first_prod(Character c){
		HashSet<Character> first_set = this.first.get(c);

		if(first_set.size() == 0){
			String production = this.productions.get(c);

			String productions[] =  production.split("/");

			for(String prod : productions){
				first_set.addAll(first_expr(prod));
			}

			this.first.put(c, first_set);
		}

		return first_set;
	}
}

class FollowParser{
	private HashMap<Character, String> productions;
	private Set<Character> nonterminals; 
	private FirstParser first_parser;
	private HashMap<Character, HashSet<Character>> follow;

	FollowParser(HashMap<Character, String> productions, FirstParser first_parser){
		this.productions =  productions;

		this.nonterminals = this.productions.keySet();

		this.first_parser = first_parser;

		this.follow = new HashMap<Character, HashSet<Character>>();
		for(Character c : this.productions.keySet()){
			this.follow.put(c, new HashSet<Character>());
		}
	}

	public HashMap<Character, HashSet<Character>> parse(){
		for(Character c : nonterminals){
			HashSet<Character> follow_set = follow_char(c);
			System.out.print("The follow set of " + c + " is: ");
			System.out.println(follow_set);
		}

		return this.follow;
	}

	public HashSet<Character> follow_char(Character c){
		HashSet<Character> follow_set = follow.get(c);

		if(follow_set.size() == 0){
			if (c == 'S'){
				follow_set.add('$');
			}

			for (Character ch : this.nonterminals){
				String production = this.productions.get(ch);

				String productions[] = production.split("/");

				for(String prod : productions){
					for(int i = 0; i < prod.length(); i++){
						if (prod.charAt(i) == c){

							if(i == prod.length() - 1 && ch != c){
								follow_set.addAll(follow_char(ch));
							}
							else{
								HashSet<Character> first_set = this.first_parser.first_expr(prod.substring(i+1, prod.length()));

								if(first_set.contains('>')){
									follow_set.addAll(follow_char(ch));
								}

								first_set.remove('>');

								follow_set.addAll(first_set);
							}
						}
					}
				}
			}

			follow.put(c, follow_set);
		}

		return follow_set;
	}

}

/*
> is epsilon


Enter the number of productions
3
S -> AaAb|BbBa
A -> >
B -> >
The first set of A is: [>]
The first set of B is: [>]
The first set of S is: [a]
The follow set of A is: [a, b]
The follow set of B is: [a, b]
The follow set of S is: [$]
*/