package sjsu.luu.steven.cs146.project3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Steven Luu
 * 
 * Creates a red black tree and stores all the words from the inputed dictionary.
 * It can spell check whether the word in in the dictionary and add those words not found intro a returned arraylist.
 *
 */
public class Dictionary {
	RedBlackTree<String> dictionary;

	/**
	 * Creates a dictionary which uses a red black tree to store the data.
	 * 
	 * @param fileLocation is where the file should be located.
	 * @throws IOException if the file cannot be found.
	 */
	public Dictionary(String fileLocation) throws IOException {
		dictionary = new RedBlackTree<String>();
		addWordsToRBT(fileLocation);
	}
	

	/**
	 * Reads from the file location and adds it to the red black tree.
	 * 
	 * @param fileLocation is where the file should be located
	 * @throws IOException if file cannot be found.
	 */
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
	 * If the word can be found in the dictionary return true, otherwise false.
	 * 
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
	
	
	/**
	 * Given an array list of words check to see which words do not appear in the dictionary and return those words.
	 * 
	 * @param words to be checked in the dictionary.
	 * @return an array list of words that did not appear.
	 */
	public ArrayList<String> wordsThatDidNotAppear(ArrayList<String> words) {
		ArrayList<String> wordsThatDidNotAppear = new ArrayList<String>();
		for(String elements : words) {
			if(spellCheck(elements) == false)
				wordsThatDidNotAppear.add(elements);
		}
		return wordsThatDidNotAppear;
	}
}
