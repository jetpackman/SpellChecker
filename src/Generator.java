import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Generator {
 
	
	public static boolean isVowel(char ch){
		return ("aeiou".indexOf(ch) >= 0);
	}
	
	public static char randomVowel(){
		return "aeiou".charAt((int)(Math.random() * 5)); 
	}
	
	public static String randomLengthBuilder(char c){
		int length = (int)(Math.random() * 4) + 1;
		String chars = "" + c;
		StringBuffer buffer = new StringBuffer();
		for (int i = 1; i <= length;i++){
			int upper = (int)(Math.random() * 2);
			if (upper == 1)
				buffer.append(chars.toUpperCase());
			else 
				buffer.append(chars.toLowerCase());
		}
		return buffer.toString();
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayList<String> dictionary = new ArrayList<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader ("dict.txt"));
			String line = reader.readLine();
			while (line != null){
				line = line.trim().toLowerCase();
				dictionary.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i = 1; i <= 1000; i++){
			int index = (int)(Math.random() * dictionary.size());
			String word = dictionary.get(index);
			StringBuffer buffer = new StringBuffer();
			for (int strIndex = 0; strIndex < word.length() ; strIndex++){
				char c = word.charAt(strIndex);
				if (isVowel(c))
					c =randomVowel();
				buffer.append(randomLengthBuilder(c));
			}
			System.out.println(buffer.toString());
		}
		
	}

}
