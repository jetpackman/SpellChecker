import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class SpellChecker {

	public static boolean isVowel(char c) {
		return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
	}

	public static class Trie {
		// The minus at the end is to handle the - characters in names for the
		// linux dictionary
		public static final String reference = "abcdefghijklmnopqrstuvwxyz-";
		static int a = (int) 'a' - 97;
		static int e = (int) 'e' - 97;
		static int i = (int) 'i' - 97;
		static int o = (int) 'o' - 97;
		static int u = (int) 'u' - 97;
		public boolean isWord;
		public char character;
		public Trie[] nextLetter = new Trie[reference.length()];

		public Trie(char c) {
			isWord = false;
			this.character = c;
			for (int i = 0; i < nextLetter.length; i++)
				nextLetter[i] = null;
		}

		public Trie() {
			isWord = false;
			for (int i = 0; i < nextLetter.length; i++)
				nextLetter[i] = null;
		}

		public void insertWord(String word) {
			if (word.length() == 0)
				return;
			char c = word.charAt(0);
			int index = reference.indexOf(c);
			Trie trie = nextLetter[index];
			if (trie == null) {
				trie = new Trie(c);
				this.nextLetter[index] = trie;
			}
			if (word.length() == 1)
				trie.isWord = true;
			else
				trie.insertWord(word.substring(1));
		}

		public static int findValidVowel(Trie[] trie) {

			if (trie[a] != null && trie[a].isWord) {
				return a;
			} else if (trie[e] != null && trie[e].isWord) {
				return e;
			} else if (trie[i] != null && trie[i].isWord) {
				return i;
			} else if (trie[o] != null && trie[o].isWord) {
				return o;
			} else if (trie[u] != null && trie[u].isWord) {
				return u;
			}
			return -1;
		}

		public String findSpellCheckWord(String prev, String toFind) {
			if (toFind.length() == 0) {
				if (isWord)
					return prev;
				return null;
			}
			char c = toFind.charAt(0);
			int index = reference.indexOf(c);
			Trie trie = nextLetter[index];
			if (toFind.length() == 1) {
				if (trie != null && trie.isWord)
					return prev + toFind;
				else {
					if (prev.length() > 0) {
						if (prev.charAt(prev.length() - 1) == c && this.isWord)
							return prev;
						if (isVowel(c)) {
							if (isVowel(prev.charAt(prev.length() - 1))) {
								String avoid = this.findSpellCheckWord(prev,
										toFind.substring(1));
								if (avoid != null)
									return avoid;
							}
							int location = findValidVowel(nextLetter);
							if (location < 0)
								return null;
							else
								return prev + reference.charAt(location);
						} else
							return null;
					}
				}
			} else {
				if (trie != null) {
					String returnString = trie.findSpellCheckWord(prev + c,
							toFind.substring(1));
					if (returnString != null)
						return returnString;
				}
				if (prev.length() > 0 && prev.charAt(prev.length() - 1) == c) {
					String returnString = this.findSpellCheckWord(prev,
							toFind.substring(1));
					if (returnString != null)
						return returnString;
				}
				if (isVowel(c)) {

					String aString = null;
					if (nextLetter[a] != null) {
						aString = nextLetter[a].findSpellCheckWord(prev + "a",
								toFind.substring(1));
					}
					String eString = null;
					if (nextLetter[e] != null) {
						eString = nextLetter[e].findSpellCheckWord(prev + "e",
								toFind.substring(1));
					}
					String iString = null;
					if (nextLetter[i] != null) {
						iString = nextLetter[i].findSpellCheckWord(prev + "i",
								toFind.substring(1));
					}
					String oString = null;
					if (nextLetter[o] != null) {
						oString = nextLetter[o].findSpellCheckWord(prev + "o",
								toFind.substring(1));
					}
					String uString = null;
					if (nextLetter[u] != null) {
						uString = nextLetter[u].findSpellCheckWord(prev + "u",
								toFind.substring(1));
					}

					String twoVowels = null;
					if (prev.length() > 0
							&& isVowel(prev.charAt(prev.length() - 1))) {
						twoVowels = this.findSpellCheckWord(prev,
								toFind.substring(1));
					}

					if (aString != null)
						return aString;
					if (eString != null)
						return eString;
					if (iString != null)
						return iString;
					if (oString != null)
						return oString;
					if (uString != null)
						return uString;
					if (twoVowels != null)
						return twoVowels;
					else
						return null;
				} else
					return null;
			}
			return null;
		}

		// Used to test the Trie, Unrelated to the spellcheck method
		public String findWord(String prev, String toFind) {
			if (toFind.length() == 0)
				return null;
			char c = toFind.charAt(0);
			int index = reference.indexOf(c);
			Trie trie = nextLetter[index];
			if (toFind.length() == 1) {
				if (trie == null) {
					return null;
				} else if (trie.isWord)
					return prev + toFind;
				else
					return null;
			} else {
				if (trie == null)
					return null;
				else
					return trie.findWord(prev + c, toFind.substring(1));
			}
		}
	}

	public static void main(String[] args) {

		Trie trie = new Trie();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("dict.txt"));
			String line = reader.readLine();
			while (line != null) {
				line = line.trim().toLowerCase();
				trie.insertWord(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print("> ");
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String word = in.nextLine();
			word = word.trim().toLowerCase();
			if (word.length() < 0) {
				continue;
			}
			String x = trie.findSpellCheckWord("", word);
			if (x == null) {
				System.out.println("NO SUGGESTION");
			} else
				System.out.println(x);
			System.out.print("> ");
		}

	}

}
