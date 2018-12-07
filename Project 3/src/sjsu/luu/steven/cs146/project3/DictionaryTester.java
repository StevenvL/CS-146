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
		long startTime = System.currentTimeMillis();
		Dictionary dictionaryObj = new Dictionary(DICTIONARY_FILE_LOCATION);
		long finishTime = System.currentTimeMillis();
		System.out.println("Time to finish building tree : " + (finishTime - startTime));
		System.out.println();
		

		// Test 2: Looks up the word "accolade" and returns the time to do so.
		startTime = System.currentTimeMillis();
		String lookMeUp = "accolade";
		assertTrue(dictionaryObj.spellCheck(lookMeUp));
		finishTime = System.currentTimeMillis();

		if (dictionaryObj.spellCheck(lookMeUp) == false)
			System.out.println(lookMeUp + " was not found in the dictionary");
		System.out.println("Time to find " + lookMeUp + ": " + (finishTime - startTime));
		System.out.println();
		

		// Test 3: Looks up the word "adgjkadgkja;" and returns the time to do so.
		startTime = System.currentTimeMillis();
		lookMeUp = "adgjkadgkja;";
		assertFalse(dictionaryObj.spellCheck(lookMeUp));
		finishTime = System.currentTimeMillis();
		if (dictionaryObj.spellCheck(lookMeUp) == false)
			System.out.println(lookMeUp + " was not found in the dictionary");
		System.out.println("Time to find " + lookMeUp + ": " + (finishTime - startTime));
		System.out.println();
		

		// Test 4: Creates a Array List of the poem and prints out words that did not
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
		startTime = System.currentTimeMillis();
		ArrayList<String> wordsNotInDictionary = dictionaryObj.wordsThatDidNotAppear(poem);
		finishTime = System.currentTimeMillis();
		System.out.println("Time to spell check the poem: " + (finishTime-startTime));
		if (wordsNotInDictionary.size() == 0)
			System.out.println("All words in poem are spelt correctly.");
		else {
			for (String elements : wordsNotInDictionary) {
				System.out.println(elements);
			}
		}
		System.out.println();
		
		
		// Test 5: Checks that every word said to be false by test above is in fact false.
		for(String elements: wordsNotInDictionary) {
			if(dictionaryObj.spellCheck(elements) == true)
				System.out.println("This word " + elements + "is actually in the dictionary.");
			assertFalse(dictionaryObj.spellCheck(elements));
		}
		System.out.println("There are/is " +wordsNotInDictionary.size() + " word(s) in the poem that were not found in the dictionary.");
		assertEquals(6,wordsNotInDictionary.size()); //6 words in the poem should not be in the dictionary.
		System.out.println();
	}
}
