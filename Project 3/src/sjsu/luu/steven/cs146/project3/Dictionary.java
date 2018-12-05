package sjsu.luu.steven.cs146.project3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {
	private final static String INPUT_FILE_LOCATION = "src/sjsu/luu/steven/cs146/project3/dictionary.txt";
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
	
	public static void main (String args[]) throws IOException {
		double startTime = System.currentTimeMillis();
		Dictionary dictionaryObj = new Dictionary(INPUT_FILE_LOCATION);
		dictionaryObj.dictionary.printTree();
		double finishTime = System.currentTimeMillis();
		System.out.println(finishTime-startTime);
	}

}
