
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		return str.substring(1);
	}

	public static int levenshtein(String word1, String word2) {
		String lowerCaseWord1 = word1.toLowerCase();
		String lowerCaseWord2 = word2.toLowerCase();

		if (lowerCaseWord2.length() == 0)
		{
			return lowerCaseWord1.length();
		}
		else if (lowerCaseWord1.length() == 0)
		{
			return lowerCaseWord2.length();
		}
		else if (lowerCaseWord1.charAt(0) == lowerCaseWord2.charAt(0))
		{
			return levenshtein(tail(lowerCaseWord1), tail(lowerCaseWord2));
		}
		else
		{
			return 1 + Math.min(Math.min(levenshtein(tail(lowerCaseWord1), lowerCaseWord2), levenshtein(lowerCaseWord1, tail(lowerCaseWord2))), levenshtein(tail(lowerCaseWord1), tail(lowerCaseWord2)));
		}
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

		for (int i = 0; i < dictionary.length; i++)
		{
			dictionary[i] = in.readString();
		}

		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		String mostSimilarWord = word;
		boolean isInThreshold = false;
		for (int i = 0; i < dictionary.length; i++)
		{
			if(levenshtein(word, dictionary[i]) <= threshold && !isInThreshold)
			{
				mostSimilarWord = dictionary[i];
				threshold = levenshtein(word, dictionary[i]);
				isInThreshold = true;
			}
			else if (levenshtein(word, dictionary[i]) < threshold && isInThreshold)
			{
				mostSimilarWord = dictionary[i];
				threshold = levenshtein(word, dictionary[i]);
			}
		}

		return mostSimilarWord;
	}

}
