package sjsu.luu.steven.cs146.project3;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class DictionaryTester {
	private final static String INPUT_FILE_LOCATION = "src/sjsu/luu/steven/cs146/project3/dictionary.txt";
	
	@Test
	//Test Dictionary Red Black Tree
	public void test() throws IOException {
		double startTime = System.currentTimeMillis();
		Dictionary dictionaryObj = new Dictionary(INPUT_FILE_LOCATION);
		//dictionaryObj.dictionary.printTree();
		double finishTime = System.currentTimeMillis();
		System.out.println("Time to Finish building tree : " + (finishTime-startTime));
		
		
	}
}
