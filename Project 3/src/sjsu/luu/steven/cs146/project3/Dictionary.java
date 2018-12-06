package sjsu.luu.steven.cs146.project3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionary {
	RedBlackTree<String> dictionary;

	public Dictionary(String fileLocation) throws IOException {
		dictionary = new RedBlackTree<String>();
		addWordsToRBT(fileLocation);
	}

	public void addWordsToRBT(String fileLocation) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileLocation));
		String readLine = br.readLine();
		while (readLine != null) {
			dictionary.addNode(readLine);;
			readLine = br.readLine();
		}
		br.close();
	}
	/**
	 * @param word to be checked in a dictionary.
	 * @return false the word cannot be found. Returns true if the word can be found.
	 * 
	 */
	public boolean spellCheck(String word) {
		if(dictionary.lookup(word) == null)
			return false;
		else
			return true;
	}
	
	public ArrayList<String> wordsThatDidNotAppear(ArrayList<String> words) {
		ArrayList<String> wordsThatDidNotAppear = new ArrayList<String>();
		for(String elements : words) {
			if(spellCheck(elements) == false)
				wordsThatDidNotAppear.add(elements);
		}
		return wordsThatDidNotAppear;
	}
}
