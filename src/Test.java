import java.util.*;
import java.io.*;

public class Test {
	public static void displayHelp() {
		System.out.println("\n\tCreate an empty trie:\t\t\t\tcreates a new empty trie.\n"
				+ "\tCreate a trie with initial letters:\t\tgenerate all permutations of the letters and insert valid words in the trie (provided by user).\n"

				+ "\tInsert a word:\t\t\t\t\tinsert a new word to the existing trie (provided by user).\n"
				+ "\tDlete a word:\t\t\t\t\tdeletes a word from the existing trie (provided by user).\n"
				+ "\tList all words that begin with a prefix:\tfinds all the words in the trie that begins with the prefix (provided by user).\n"
				+ "\tSize of the trie:\t\t\t\tdisplays the number of letters in the trie.\n"
				+ "\tEnd:\t\t\t\t\t\tTerminates the program.\n");
		System.out.println("\tComands:\n" + "	-h\thelp\n" + "	-v\tview the menu\n"
				+ "	-info\tdisplay information about the trie data structure");
	}

	public static void displayInfo() {
		System.out.println("The word \"Trie\" is an excerpt from the word \"retrieval\".\n"
				+ "Trie is a sorted tree-based data-structure that stores the set of strings.\n"
				+ "It has the number of pointers equal to the number of characters of the alphabet in each node.\n"
				+ "It can search a word in the dictionary with the help of the word's prefix.\n"
				+ "For example, if we assume that all strings are formed from the letters 'a' to 'z' in the English alphabet.\n"
				+ "Each trie node can have a maximum of 26 points.");
	}

	// permutations are the different arrangements of the letters in a word
	// (same size of the original word)
	// combinations are all strings that can be formed from the letters of a string
	// (doesn't have to be the same size of the original word)

	// method to create an ArrayList of all permutations of a string
	public static ArrayList<String> getPermutations(String s) {
		// creates an arraylist
		ArrayList<String> permutations = new ArrayList<String>();
		if (s == null) {
			return null;
		} else if (s.length() == 0) {
			// if the string is ""
			permutations.add("");
			return permutations;
		}
		char firstCh = s.charAt(0); // first letter
		String subStr = s.substring(1); // rest of word
		ArrayList<String> words = getPermutations(subStr); // new list (recursive)
		for (String str : words) {
			for (int i = 0; i <= str.length(); i++) {
				// for each word it will loop and add the substring, then the first letter
				// then the rest of the word
				permutations.add(str.substring(0, i) + firstCh + str.substring(i));
			}
		}
		return permutations; // returns the permutations of the string
	}

	public static void displayMenu() {
		System.out.println("\t1) Create an empty trie");
		System.out.println("\t2) Create a trie with initial letters");
		System.out.println("\t3) Insert a word");
		System.out.println("\t4) Delete a word");
		System.out.println("\t5) List all words that begin with a prefix");
		System.out.println("\t6) Size of the trie");
		System.out.println("\t7) End");
		System.out.println("\n\t-h for help");
	}

	public static void main(String[] args) {
		Trie trie = null;
		Scanner kb = new Scanner(System.in);
		System.out.println("TRIE PROJECT: ");
		displayMenu();
		while (true) {
			try {
				System.out.println("------------------------------------------");
				System.out.print("please select an operation: ");
				String choice = kb.nextLine();
				// kb.nextLine();
				if (choice.equals("1")) {
					trie = new Trie();
					System.out.println("Trie created!");
				} else if (choice.equals("2")) {
					trie = new Trie();
					ArrayList<String> dict = new ArrayList<String>();

					FileReader fr = new FileReader(new File("Dictionary.txt"));
					BufferedReader br = new BufferedReader(fr);
					String line = "";
					while ((line = br.readLine()) != null) {
						dict.add(line.toUpperCase());
					}

					System.out.print("Enter the letters: ");
					String letters = kb.nextLine();
					ArrayList<String> permutations = getPermutations(letters); // create permutations
					ArrayList<String> combinations = new ArrayList<String>(); // create combinations

					// creating the combinations by taking all substrings of the permutations
					// (excluding duplicates):
					for (String word : permutations) {
						word = word.toUpperCase();
						for (int i = 2; i <= word.length(); i++) {
							if (!combinations.contains(word.substring(0, i)) && dict.contains(word.substring(0, i))) {
								combinations.add(word.substring(0, i));
							}
						}
					}
					for (String word : combinations) {
						trie.insert(word);
					}
					System.out.println("Trie created!");
				} else if (choice.equals("3")) {
					System.out.println("Please enter a word: ");
					String word = kb.nextLine();
					trie.insert(word);
					System.out.println(word + " inserted!");
				} else if (choice.equals("4")) {
					System.out.print("Enter a word to delete: ");
					String word = kb.nextLine();
					trie.delete(word);
					if (trie.contains(word))
						System.out.println(word + " deleted");

				} else if (choice.equals("5")) {
					System.out.print("Enter the prefix: ");
					String prefix = kb.nextLine();
					String list[] = trie.allWordsPrefix(prefix);
					if (list.length == 0) {
						System.out.println("The prefix does not exist in the trie.");
					} else {
						System.out.print("All words with prefix \"" + prefix + "\": ");
						for (int i = 0; i < list.length; i++) {
							System.out.print(list[i] + " ");
						}
					}
					System.out.println("");
				} else if (choice.equals("6")) {
					System.out.println("The trie size is: " + trie.size());
				} else if (choice.equals("7")) {
					break;
				} else if (choice.equals("-v")) {
					displayMenu();
				} else if (choice.equals("-h")) {
					displayHelp();
				} else if (choice.equals("-info")) {
					displayInfo();
				} else {
					System.out.println("command not found");
				}
			} catch (Exception e) {
				System.err.println("ERROR! Type -h for help");
			}
		}
		kb.close();
		System.out.println("Terminating program");
		System.exit(0);

	}
}
