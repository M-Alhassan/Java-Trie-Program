import java.util.*;

public class Trie {
	static TrieNode root;
	private int size;

	public Trie() {
		this.root = new TrieNode();
		this.size = 0;
	}

	// helper method
	public boolean isMatch(String s, TrieNode node, int index, boolean isFullMatch) {
		if (node == null)
			return false;
		if (index == s.length()) {
			return !isFullMatch || node.endOfString;
		}
		return isMatch(s, node.keys[s.charAt(index) - 'A'], index + 1, isFullMatch);
	}

	// checks if the trie contains the String (full word)
	public boolean contains(String s) {
		return isMatch(s, root, 0, true);
	}

	// checks if the string is a prefix in the trie
	public boolean isPrefix(String s) {
		return isMatch(s, root, 0, false);
	}

	// insert a new string in the trie
	public void insert(String s) {
		TrieNode node = root; // root
		for (char ch : s.toCharArray()) {
			// if the character exists in the trie then use it, else, create a new one
			if (node.keys[ch - 'A'] == null) { // ch - 'A' is the index of the character in the string
				node.keys[ch - 'A'] = new TrieNode();
				size++;
			}
			node = node.keys[ch - 'A'];
		}
		// end of string
		node.setEndOfString(true);
	}

	// a method to delete strings
	public void delete(String s) {
		if (!contains(s)) {
			System.out.println("The word does not exist");
		} else {
			delete(root, s, 0);
		}
	}

	// delete helper method
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
		TrieNode node = current.keys[ch - 'A'];
		if (node == null) {
			return false;
		}
		boolean deleteNode = delete(node, word, index + 1) && !node.isEndOfString();
		if (deleteNode) {
			current.keys[ch - 'A'] = null;
			return isEmpty();
		}
		return false;
	}
	
	// checks if the trie is empty or not
	public boolean isEmpty() {
		boolean empty = true;
		for (TrieNode node : root.keys) {
			if (node != null) {
				empty = false;
			}
		}
		return empty;
	}

	// clear method sets all the keys to null & resets the size to 0
	public void clear() {
		for (int i = 0; i < 26; i++) {
			this.root.keys[i] = null;
		}
		size = 0;
	}

	// a method to count all words for a given prefix
	public String[] allWordsPrefix(String p) {

		TrieNode node = root;

		if (!isPrefix(p)) {
			return new String[0];
		}
		ArrayList<String> result = new ArrayList<>();
		Stack<TrieNode> stack1 = new Stack<>();
		Stack<String> stack2 = new Stack<>();

		for (int i = 0; i < p.length(); ++i) {
			node = node.keys[p.charAt(i) - 'A'];
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
					stack2.push(p + ((char) ('A' + count)));
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
}
