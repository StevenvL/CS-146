package sjsu.luu.steven.cs146.project3;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class DictionaryTester {
	private final static String DICTIONARY_FILE_LOCATION = "src/sjsu/luu/steven/cs146/project3/dictionary.txt";
	private final static String POEM_FILE_LOCATION = "src/sjsu/luu/steven/cs146/project3/poem.txt";

	@Test
	// Test Dictionary Red Black Tree
	public void test() throws IOException, InterruptedException {

		// Test 1: Creates a dictionary and returns the amount time(milliseconds) for
		// the creation of RBT.
		double startTime = System.currentTimeMillis();
		Dictionary dictionaryObj = new Dictionary(DICTIONARY_FILE_LOCATION);
		double finishTime = System.currentTimeMillis();
		System.out.println("Time to finish building tree : " + (finishTime - startTime));
		
		

		// Test 2: Looks up the word "accolade" and returns the time to do so.
		startTime = System.currentTimeMillis();
		String lookMeUp = "accolade";
		assertTrue(dictionaryObj.spellCheck(lookMeUp));
		finishTime = System.currentTimeMillis();

		if (dictionaryObj.spellCheck(lookMeUp) == false)
			System.out.println(lookMeUp + " was not found in the dictionary");
		System.out.println("Time to find " + lookMeUp + ": " + (finishTime - startTime));
		
		

		// Test 2: Looks up the word "adgjkadgkja;" and returns the time to do so.
		startTime = System.currentTimeMillis();
		lookMeUp = "adgjkadgkja;";
		assertFalse(dictionaryObj.spellCheck(lookMeUp));
		finishTime = System.currentTimeMillis();
		if (dictionaryObj.spellCheck(lookMeUp) == false)
			System.out.println(lookMeUp + " was not found in the dictionary");
		System.out.println("Time to find " + lookMeUp + ": " + (finishTime - startTime));
		
		

		// Test 3: Creates a Array List of the poem and prints out words that did not
		// appear in the dictionary.
		ArrayList<String> poem = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(POEM_FILE_LOCATION));
		String readLine = br.readLine();
		while (readLine != null) {
			String[] temp = readLine.split(" ");
			for(String element: temp)
				poem.add(element.toLowerCase());
			readLine = br.readLine();
		}
		br.close();

		ArrayList<String> wordsNotInDictionary = dictionaryObj.wordsThatDidNotAppear(poem);
		if (wordsNotInDictionary.size() == 0)
			System.out.println("All words in poem are spelt correctly.");
		else {
			for (String elements : wordsNotInDictionary) {
				System.out.println(elements);
			}
		}
		
		
		
		// Test 4: Checks that every word said to be false by test above is in fact false.
		for(String elements: wordsNotInDictionary) {
			if(dictionaryObj.spellCheck(elements) == true)
				System.out.println("This word " + elements + "is actually in the dictionary.");
			assertFalse(dictionaryObj.spellCheck(elements));
		}
		System.out.println("These words cannot be found in the dictionary because of various reasons such as periods or quotations.");
	}
}
