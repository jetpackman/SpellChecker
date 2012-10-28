import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class SpellCheckerHashTable {
	
	public static boolean isVowel(char ch){
		return ("aeiou".indexOf(ch) >= 0);
	}

	public static void main(String[] args) {
		

		HashMap<String, Boolean> dictionary = new HashMap<String, Boolean>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader ("dict.txt"));
			String line = reader.readLine();
			while (line != null){
				line = line.trim().toLowerCase();
				dictionary.put(line, true);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		Scanner in = new Scanner(System.in);
		while (true){
			System.out.print("> ");
			String word = in.nextLine();
			word = word.trim().toLowerCase();
			if (word.length() < 0){
				continue;
			}
			ArrayList<String> possibleMatches = new ArrayList<String>();
			possibleMatches.add("");
			char prevChar = word.charAt(0);
			int repeats = 1;
			for (int i = 1; i < word.length(); i++){
				char currentChar = word.charAt(i);
				if (prevChar == currentChar)
					repeats++;
				else
				{
					String[] additionStrings;
					if (isVowel(prevChar))
						additionStrings = new String[]{"a","e","i","o","u"};
					else
						additionStrings = new String[]{""+prevChar};
					ArrayList<String> newMatches = new ArrayList<String>();
					for (int times = 1; times <= repeats; times++){
						for (String buffer: possibleMatches){
							for (int index = 0; index < additionStrings.length;index++){
								newMatches.add(buffer + (additionStrings[index]));
							}
						}
						for (int index = 0; index < additionStrings.length;index++){
							additionStrings[index] = additionStrings[index] + additionStrings[index].charAt(0);
						}
					}
					possibleMatches = newMatches;
					prevChar = currentChar;
					repeats = 1;
				}
			}
			String[] additionStrings;
			if (isVowel(prevChar))
				additionStrings = new String[]{"a","e","i","o","u"};
			else
				additionStrings = new String[]{""+prevChar};
			ArrayList<String> newMatches = new ArrayList<String>();
			for (int times = 1; times <= repeats; times++){
				for (String buffer: possibleMatches){
					for (int index = 0; index < additionStrings.length;index++){
						newMatches.add(buffer + (additionStrings[index]));
					}
				}
				for (int index = 0; index < additionStrings.length;index++){
					additionStrings[index] = additionStrings[index] + additionStrings[index].charAt(0);
				}
			}
			possibleMatches = newMatches;
			boolean lookup = false;
			for (String possibleString : possibleMatches){
				 lookup = dictionary.containsKey(possibleString);
				if (lookup)
				{
					System.out.println(possibleString);
					break;
				}
			}
			if (!lookup){
				System.out.println("NO SUGGESTION");
			}
		}
	}
}
