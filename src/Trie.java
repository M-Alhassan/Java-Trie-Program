import java.util.*;

public class Trie {
	static TrieNode root;
	private int size;

	public Trie() {
		this.root = new TrieNode();
		this.size = 0;
	}

	// insert method
	public void insert(String s) {
		TrieNode node = root; // root
		for (char ch : s.toCharArray()) {
			// if the character exists in the trie then use it, else, create a new one
			if (node.keys[ch - 'a'] == null) { // ch - 'a' is the index of the character in the string
				node.keys[ch - 'a'] = new TrieNode();
				size++;
			}

			node = node.keys[ch - 'a'];
		}
		// end of string
		node.setEndOfString(true);
	}

	// contains method
	public boolean contains(String s) {
		return isMatch(s, root, 0, true);
	}

	public boolean isMatch(String s, TrieNode node, int index, boolean isFullMatch) {
		if (node == null)
			return false;
		if (index == s.length()) {
			return !isFullMatch || node.endOfString;
		}
		return isMatch(s, node.keys[s.charAt(index) - 'a'], index + 1, isFullMatch);
	}

	public boolean isEmpty() {
		boolean empty = true;
		for (TrieNode node : root.keys) {
			if (node != null) {
				empty = false;
			}
		}
		return empty;
	}

	public void clear() {
		for (int i = 0; i < 26; i++) {
			this.root.keys[i] = null;
		}
		size = 0;
	}

	public void delete(String s) {
		if (!contains(s)) {
			System.out.println("Does not exist");
		} else {
			delete(root, s, 0);
		}
	}

	public boolean delete(TrieNode current, String word, int index) {
		if (index == word.length()) {
			if (!current.isEndOfString()) {
				return false;
			}
			size--;
			current.setEndOfString(false);
			return isEmpty();
		}
		char ch = word.charAt(index);
		TrieNode node = current.keys[ch - 'a'];
		if (node == null) {
			return false;
		}
		boolean deleteNode = delete(node, word, index + 1) && !node.isEndOfString();
		if (deleteNode) {
			current.keys[ch - 'a'] = null;
			return isEmpty();
		}
		return false;
	}

	public boolean isPrefix(String s) {
		return isMatch(s, root, 0, false);
	}

	public String[] allWordsPrefix(String p) {

		TrieNode node = root;

		if (!isPrefix(p)) {
			return new String[0];
		}
		ArrayList<String> result = new ArrayList<>();
		Stack<TrieNode> stack1 = new Stack<>();
		Stack<String> stack2 = new Stack<>();

		for (int i = 0; i < p.length(); ++i) {
			node = node.keys[p.charAt(i) - 'a'];
		}
		stack1.push(node);
		stack2.push(p);

		while (!stack1.empty() && !stack2.empty()) {
			node = stack1.pop();
			p = stack2.pop();
			if (node.isEndOfString()) {
				result.add(p);
			}
			int count = 0;
			for (TrieNode keyNode : node.keys) {
				if (keyNode != null) {
					stack1.push(keyNode);
					stack2.push(p + ((char) ('a' + count)));
				}
				count++;
			}
		}
		String[] finalResult = new String[result.size()];
		int i = 0;
		for (String str : result) {
			finalResult[i] = str;
			i++;
		}
		return finalResult;

	}

	public int size() {
		return size;
	}

	public static void main(String[] Args) {
		Trie trie = new Trie();
		System.out.println("inserting cat...");
		trie.insert("cat");
		System.out.println("inserting car...");
		trie.insert("car");
		System.out.println("insering dog...");
		trie.insert("dog");
		System.out.println("insering pick...");
		trie.insert("pick");
		System.out.println("insering pickle...");
		trie.insert("pickle");
		System.out.println("is trie empty? >> " + trie.isEmpty());
		System.out.println("Contains cat >> " + trie.contains("cat"));
		System.out.println("Contains can >> " + trie.contains("can"));
		System.out.println("is \"do\" a prefix >> " + trie.isPrefix("do"));
		System.out.println("is \"d\" a prefix >> " + trie.isPrefix("d"));
		System.out.println("deleting cat...");
		trie.delete("cat");
		System.out.println("Contains cat >> " + trie.contains("cat"));
		System.out.println("deleting pickle...");
		// trie.delete("pickle");
		System.out.println("Contains pickle >> " + trie.contains("pickle"));
		System.out.println("Contains pick >> " + trie.contains("pick"));
		System.out.println("Trie size >> " + trie.size());
		System.out.println("pi all words prefix ");
		String list1[] = trie.allWordsPrefix("pi");
		for (int i = 0; i < list1.length; i++) {
			System.out.println(list1[i]);
		}
		System.out.println("po all words ");
		String list2[] = trie.allWordsPrefix("po");
		for (int i = 0; i < list2.length; i++) {
			System.out.println(list2[i]);
		}

//		System.out.println("Clearing Trie...");
//		trie.clear();
//		System.out.println("is trie empty? >> " + trie.isEmpty());
//		System.out.println("Trie size >> " + trie.size());
	}
}
