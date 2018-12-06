package sjsu.luu.steven.cs146.project3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
	public boolean spellCheck(String word) {
		if(dictionary.lookup(word) != null)
			return true;
		else
			return false;
	}
}
