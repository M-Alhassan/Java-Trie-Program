
public class TrieNode {
	public boolean endOfString;
	public TrieNode keys[];

	public boolean isEndOfString() {
		return endOfString;
	}

	public void setEndOfString(boolean endOfString) {
		this.endOfString = endOfString;
	}

	public TrieNode() {
		this.endOfString = false;
		this.keys = new TrieNode[26];
	}

}
